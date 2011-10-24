package com.mark.data

import org.scalatest.FunSuite
import com.mark.data.GradeData._
import org.scalatest.matchers.ShouldMatchers
import com.mark.adt.{GPAGrade, Assignment, Grader, Student}

/**
 * Tests extracting grade data from a CSV file
 */

class CSVDataSourceTest extends FunSuite with ShouldMatchers {

  val Harry = new Student("Harry Potter")
  val Hermione = new Student("Hermione Granger")
  val Ron = new Student("Ron Weasley")
  val Draco = new Student("Draco Malfoy")
  val Snape = new Grader("Severus Snape")
  val McGonagall = new Grader("Minerva McGonagall")
  val Trelawney = new Grader("Sybill Trelawney")
  val PSet1 = new Assignment("PSet 1")
  val Exam1 = new Assignment("Exam 1")

  def files = List(
    // Grade Data 1
    "test/com/mark/data/grade-data-1.csv",
    // Grade Data 2
    "test/com/mark/data/grade-data-2.csv"
  )
  def headers = List(
    // Grade Data 1
    List(StudentData, GraderData, AssignmentData, ScoreData, MaxScoreData, WeightData),
    // Grade Data 2
    List(GraderData, StudentData, AssignmentData, WeightData, MaxScoreData, ScoreData)
  )
  def students = List(
    // Grade Data 1
    List(Harry, Hermione, Ron),
    // Grade Data 2
    List(Hermione, Ron, Draco)
  )
  def studentsNeg = List(
    // Grade Data 1
    List(Draco),
    // Grade Data 2
    List(Harry)
  )
  def graders = List(
    // Grade Data 1
    List(Snape, McGonagall),
    // Grade Data 2
    List(McGonagall, Trelawney, Snape)
  )
  def gradersNeg = List(
    // Grade Data 1
    List(Trelawney),
    // Grade Data 2
    List()
  )
  def assignments = List(
    // Grade Data 1
    List(PSet1),
    // Grade Data 2
    List(PSet1, Exam1)
  )
  def assignmentsNeg = List(
    // Grade Data 1
    List(Exam1),
    // Grade Data 2
    List()
  )
  def marks = List(
    // Grade Data 1
    Map(
      (Harry, PSet1) -> (new GPAGrade(8, 10), Snape),
      (Hermione, PSet1) -> (new GPAGrade(10, 10), Snape),
      (Ron, PSet1) -> (new GPAGrade(7, 10), McGonagall)
    ),
    // Grade Data 2
    Map(
      (Draco, Exam1) -> (new GPAGrade(9, 10), Snape),
      (Ron, PSet1) -> (new GPAGrade(6, 10), Trelawney),
      (Hermione, PSet1) -> (new GPAGrade(10, 10), McGonagall)
    )
  )

  def checkData(testIndex: Int) {
    val i = testIndex
    val dataDescriptor = new CSVDataDescriptor(files(i), headers(i))
    val dataSource = DataSourceFactory.loadCSV(dataDescriptor)

    dataSource.getStudents should have size (students(i).size)
    dataSource.getGraders should have size (graders(i).size)
    dataSource.getAssignments should have size (assignments(i).size)

    shouldContain[Student](students(i), dataSource.getStudents)
    shouldNotContain[Student](studentsNeg(i), dataSource.getStudents)
    shouldContain[Grader](graders(i), dataSource.getGraders)
    shouldNotContain[Grader](gradersNeg(i), dataSource.getGraders)
    shouldContain[Assignment](assignments(i), dataSource.getAssignments)
    shouldNotContain[Assignment](assignmentsNeg(i), dataSource.getAssignments)

    for (key <- marks(i).keys) {
      val dataSourceMark = dataSource.getGrade(key._1, key._2).get
      dataSourceMark.getGrade should equal (marks(i).get(key).get._1)
      dataSourceMark.getGrader should equal (marks(i).get(key).get._2)
    }
  }

  def shouldContain[T](list: List[T], dataSourceSet: Set[T]) {
    for (l <- list) {
      dataSourceSet should contain (l)
    }
  }

  def shouldNotContain[T](list: List[T], dataSourceSet: Set[T]) {
    for (l <- list) {
      dataSourceSet should not contain (l)
    }
  }

  test("Load and check grade data 1") {
     checkData(0)
  }

  test("Load and check grade data 2") {
     checkData(1)
  }
}