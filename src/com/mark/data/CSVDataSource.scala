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
class CSVDataSource[M <: Mark] extends GradeDataSource[M] {
 
  def getGraders: Set[Grader] =
	  throw new RuntimeException("getGraders not yet implemented");
	
	def getStudents: Set[Student] =
	  throw new RuntimeException("getStudents not yet implemented"); 

  def getAssignments: Set[Assignment] =
    throw new RuntimeException("getAssignments not yet implemented")

	def getGrade(grader : Grader, 
				 student : Student, 
				 assignment : Assignment ) : Option[M] =
	  throw new RuntimeException("getGrade not yet implemented");

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
    var content: Array[String] = reader.readNext()
    if (content == null) throw new RuntimeException("Input file " +
      "does not contain data")

    // TODO: what happens if fields.size is not equal to content.size ?

    while (content != null) {

      var graderName: String = null
      var studentName: String = null
      var assignmentName: String = null
      var assignmentMaxScore: Int = Int.MinValue
      var markValue: Int = Int.MinValue

      for (i <- 0 to fields.size) {
        val dataType = fields(i)
        val value: String = content(i)
        dataType match {
          case Grader => graderName = value
          case Student => studentName = value
          case Assignment => assignmentName = value
          case MaxScore => assignmentMaxScore = value.toInt
          case Mark => markValue = value.toInt
          case _ => // else, do nothing
        }
      }

      if (graderName == null
        || studentName == null
        || assignmentName == null
        || assignmentMaxScore == Int.MinValue
        || markValue == Int.MinValue) {

        throw new IllegalArgumentException("Data file did not contain " +
          "all necessary information")
      }

      checkString(graderName, "Grader name")
      checkString(studentName, "Student name")
      checkString(assignmentName, "Assignment name")
      checkPositiveInt(assignmentMaxScore, "Assignment max score")
      checkPositiveInt(markValue, "Mark value")

      val grader: Grader = new Grader(graderName)
      val student: Student = new Student(studentName)
      val assign: Assignment = new Assignment(assignmentName, assignmentMaxScore)
      //val mark: M = null

      content = reader.readNext()
    }

    reader.close()
  }

  private def checkString(s: String, name: String): Unit = {
    if (s == null) throw new IllegalArgumentException(name +
      " should not be null")
    if (s.isEmpty) throw new IllegalArgumentException(name +
      " should not be empty")
  }

  private def checkPositiveInt(i: Int, name: String): Unit = {
    if (i < 0) throw new IllegalArgumentException(name +
      " should not be negative")
  }
}

case class CSVDataDescriptor(file: String, fields: List[GradeData])
  extends GradeDataDescriptor
