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
class CSVDataSource   extends GradeDataSource {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private var assignments = Set[Assignment]()
  private var grades = Map[(Student, Assignment), GradeOutcome]()

  def getGraders: Set[Grader] = graders
	
	def getStudents: Set[Student] = students

  def getAssignments: Set[Assignment] = assignments

  def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
    grades get (student, assignment)

  def loadData(descriptor: GradeDataDescriptor): Unit = {
    descriptor match {
      case CSVDataDescriptor(file, fields) => loadData(file, fields)
      case _ => throw new IllegalArgumentException("CSVDataSource.loadData() " +
        "does not support this descriptor: " + descriptor)
    }
  }

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

    var graders = List[Grader]()
    var students = List[Student]()
    var assignments = List[Assignment]()
    var grades = List[((Student, Assignment), GradeOutcome)]()

    while (row != null) {

      val rowData = fields zip row
      // validate each input string
      rowData.foreach((DataSourceUtil.checkString _).tupled)
      val dataMap = rowData.toMap[GradeData, String]

      // create objects and add them to lists
      val grade = new GPAGrade(
        dataMap(ScoreData).toInt,
        dataMap(MaxScoreData).toInt)

      graders ::= new Grader(dataMap(GraderData))
      students ::= new Student(dataMap(StudentData))
      assignments ::= new Assignment(dataMap(AssignmentData))
      grades ::= ((students.head, assignments.head),
        new GradeOutcome(grade, graders.head))

      // get next row
      row = reader.readNext()
    }

    // close csv reader
    reader.close()

    this.graders = graders.toSet[Grader]
    this.students = students.toSet[Student]
    this.assignments = assignments.toSet[Assignment]
    this.grades = grades.toMap[(Student, Assignment), GradeOutcome]
  }
}

case class CSVDataDescriptor(file: String, fields: List[GradeData])
  extends GradeDataDescriptor
