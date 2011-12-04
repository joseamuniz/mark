package com.mark.data
import com.mark.adt._
import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import com.mark.data.GradeData._

/**
 * Reads the class gradebook from a CSV file encoded in UTF-8 format 
 * with the following fields:
 * 
 * - Student Name
 * - Grader Name
 * - Assignment Name
 * - Grade
 * - Max Grade
 * - Weight
 * 
 * The first row is reserved for the header. 
 * 
 */
class CSVDataSource(dataDescriptor: CSVDataDescriptor) extends GradeDataSource {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private var assignments = Set[Assignment]()
  private var grades = Map[(Student, Assignment), GradeOutcome]()

  dataDescriptor match {
    case CSVDataDescriptor(file, fields) => loadData(file, fields)
  }

  def getGraders: Set[Grader] = graders
	
	def getStudents: Set[Student] = students

  def getAssignments: Set[Assignment] = assignments

  def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
    grades get (student, assignment)

  private def loadData(file: String, fields: List[GradeData]): Unit = {

    val reader = new CSVReader(new FileReader(file))
    // first line should be the header
    val header: Array[String] = reader.readNext()
    if (header == null) throw new RuntimeException("Input file is empty")
    // TODO: think about how to enforce/check the precense of header
    if (header.length != fields.length) throw new RuntimeException(
      "Number of fields in the file header does not match the number " +
        "of fields in the descriptor")
    var row: Array[String] = reader.readNext()
    if (row == null) throw new RuntimeException("Input file " +
      "does not contain data")

    // instantiate temporary lists to put the grader,
    // student, and assignment objects
    var graders = List[Grader]()
    var students = List[Student]()
    var assignments = List[Assignment]()
    // instantiate temporary list to put tuples of the form
    // ((Student, Assignment), GradeOutcome)
    var grades = List[((Student, Assignment), GradeOutcome)]()

    while (row != null) {

      val rowData = fields zip row
      // validate each input string - it should not be null nor empty
      rowData.foreach((DataSourceUtil.checkString _).tupled)
      // given the row data, create a mapping from
      // grade data fields to string values
      val dataMap = rowData.toMap[GradeData, String]

      // create a new grade using the values from the data map
      val grade = new GPAGrade(
        dataMap(ScoreData).toInt,
        dataMap(MaxScoreData).toInt)

      // create new grader, student, assignment, and grade outcome objects.
      // add each new object to its corresponding list
      graders ::= new Grader(dataMap(GraderData))
      students ::= new Student(dataMap(StudentData))
      assignments ::= new Assignment(dataMap(AssignmentData))
      grades ::= ((students.head, assignments.head),
        new GradeOutcome(grade, graders.head))

      // get next row data
      row = reader.readNext()
    }

    // close csv reader
    reader.close()

    // instantiate global fields using the temporary lists
    this.graders = graders.toSet[Grader]
    this.students = students.toSet[Student]
    this.assignments = assignments.toSet[Assignment]
    this.grades = grades.toMap[(Student, Assignment), GradeOutcome]
  }
}

case class CSVDataDescriptor(file: String, fields: List[GradeData])
  extends GradeDataDescriptor
