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
trait CSVDataSource[M <: Mark] extends GradeDataSource[M] {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private val scoreMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val maxScoreMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val weightMap = mutable.Map[(Grader, Student, Assignment), String]()
  private val graderSet = mutable.Set[Grader]()
  private val studentSet = mutable.Set[Student]()

  def getGraders: Set[Grader] = graders
	
	def getStudents: Set[Student] = students

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

    while (row != null) {
      val rowData = fields zip row
      rowData.foreach((checkString _).tupled)
      retrieveData(rowData)
      row = reader.readNext()
    }

    reader.close()

    // create immutable sets of graders and students from the
    // sets we just populated
    graders ++ graderSet
    students ++ studentSet

    createMarks(
      Map[(Grader, Student, Assignment), String]() ++ scoreMap,
      Map[(Grader, Student, Assignment), String]() ++ maxScoreMap,
      Map[(Grader, Student, Assignment), String]() ++ weightMap)
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
  }

  private def checkString(gradeData: GradeData, value: String): Unit = {
    if (value == null) throw new IllegalArgumentException(
      gradeData.toString + " should not be null")
    if (value.isEmpty) throw new IllegalArgumentException(
      gradeData.toString + " should not be empty")
  }
}

case class CSVDataDescriptor(file: String, fields: List[GradeData])
  extends GradeDataDescriptor
