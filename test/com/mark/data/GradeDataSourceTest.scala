package com.mark.data

import com.mark.adt.{GPAGrade, Assignment, Grader, Student}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

/**
 *
 */

trait GradeDataSourceTest extends FunSuite with ShouldMatchers {

  val Harry = new Student("Harry Potter")
  val Hermione = new Student("Hermione Granger")
  val Ron = new Student("Ron Weasley")
  val Draco = new Student("Draco Malfoy")
  val Snape = new Grader("Severus Snape")
  val McGonagall = new Grader("Minerva McGonagall")
  val Trelawney = new Grader("Sybill Trelawney")
  val PSet1 = new Assignment("PSet 1")
  val Exam1 = new Assignment("Exam 1")

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

  def checkData(testIndex: Int, dataSource: GradeDataSource) {

    val i = testIndex

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
      val gradeOut = dataSource.getGrade(key._1, key._2).get
      gradeOut.getGrade should equal (marks(i).get(key).get._1)
      gradeOut.getGrader should equal (marks(i).get(key).get._2)
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
}