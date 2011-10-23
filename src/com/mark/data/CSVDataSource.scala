package com.mark.data
import com.mark.adt._
import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import com.mark.data.GradeData._
import collection.immutable.Set
import collection.mutable

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
class CSVDataSource extends GradeDataSource {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private var assignments = Set[Assignment]()

  private val scoreMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val maxScoreMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val weightMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val graderSet = mutable.Set[Grader]()
  private val studentSet = mutable.Set[Student]()
  private val assignmentSet = mutable.Set[Assignment]()

  def getGraders: Set[Grader] = graders
	
	def getStudents: Set[Student] = students

  def getAssignments: Set[Assignment] = assignments

  def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
    throw new RuntimeException("Julian")

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

    scoreMap.clear()
    maxScoreMap.clear()
    weightMap.clear()
    graderSet.clear()
    studentSet.clear()
    assignmentSet.clear()

    while (row != null) {
      val rowData = fields zip row
      rowData.foreach((checkString _).tupled)
      retrieveData(rowData)
      row = reader.readNext()
    }

    reader.close()

    // create immutable sets of graders and students from the
    // sets we just populated
    graders = Set[Grader]() ++ graderSet
    students = Set[Student]() ++ studentSet
    assignments = Set[Assignment]() ++ assignmentSet
  }

  private def retrieveData(row: List[(GradeData, String)]): Unit = {

    val data = mutable.Map[GradeData, String]()
    row.foreach(value => data += value)

    val key = (new Grader(data(GraderData)),
      new Student(data(StudentData)),
      new Assignment(data(AssignmentData)))

    scoreMap put (key, data(ScoreData))
    maxScoreMap put (key, data(MaxScoreData))
    weightMap put (key, data(WeightData))

    graderSet += key._1
    studentSet += key._2
    assignmentSet += key._3
  }

  private def checkString(gradeData: GradeData, value: String): Unit = {
    if (value == null) throw new IllegalArgumentException(
      gradeData.toString + " should not be null")
    if (value.isEmpty) throw new IllegalArgumentException(
      gradeData.toString + " should not be empty")
  }

//  private var marks = Map[(Grader, Student, Assignment), GPAGrade]()
//
//  def createMarks(
//    scoreMap: Map[(Grader, Student, Assignment), String],
//    maxScoreMap: Map[(Grader, Student, Assignment), String],
//    weightMap: Map[(Grader, Student, Assignment), String]): Unit = {
//
//    val marksMap = mutable.Map[(Grader, Student, Assignment), GPAGrade]()
//
//    for (key <- scoreMap.keys) {
//      val mark = new GPAGrade(
//        scoreMap.getOrElse(key, throw new RuntimeException("Not a valid key")).toInt,
//        maxScoreMap.getOrElse(key, throw new RuntimeException("Not a valid key")).toInt)
//
//      marksMap put (key, mark)
//    }
//
//    marks = Map[(Grader, Student, Assignment), GPAGrade]() ++ marksMap
//  }
}

case class CSVDataDescriptor(file: String, fields: List[GradeData])
  extends GradeDataDescriptor
