package com.mark.similarity

import scala.util.Random
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.mark.data.GradeDataSource
import com.mark.adt._
import com.mark.data._
import com.mark.learner._

class CorrelationDistanceTest extends FunSuite with ShouldMatchers{

  def marks = List(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
  def allSameMark = List(5.4, 5.4, 5.4, 5.4, 5.4)
  def randomMarks = Random.shuffle(marks)

  def marker1 = List(0.0, 10.0, 101.0, 102.0)
  def marker2 = List(1.0, 100.0, 500.0, 2000.0)

  def markers = Array(new Grader("G1"), new Grader("G2"))
  def students = Array(new Student("S1"), new Student("S2"), new Student("S3"), new Student("S4"))
  def assignments = Array(new Assignment("A1"))

  val dataSource = new GradeDataSource() {
    override def getGraders: Set[Grader] = markers toSet
    override def getStudents: Set[Student] = students toSet
    override def getAssignments: Set[Assignment] = assignments toSet
//    def getGrade(
//      grader: Grader,
//      student: Student,
//      assignment: Assignment): Option[GPAGrade] = {
//      val map = Map(
//        (students(0), markers(0)) -> marker1(0),
//        (students(0), markers(1)) -> marker2(0),
//        (students(1), markers(0)) -> marker1(1),
//        (students(1), markers(1)) -> marker2(1),
//
//        (students(2), markers(0)) -> marker1(2),
//        (students(2), markers(1)) -> marker2(2),
//        (students(3), markers(0)) -> marker1(3),
//        (students(3), markers(1)) -> marker2(3)
//        )
//      Option(new GPAGrade(map.get((student, grader)).get.toInt, 2000))
//    }
    def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
      throw new RuntimeException("Jose")
    def loadData(d: GradeDataDescriptor) = throw new RuntimeException("Not implemented yet")
  }

  test("should produce correlation 0.7544237083454246 using data source"){
    val simpleLearner = new SimpleLearner[GPAGrade]
    val predictorsMap = simpleLearner.train(dataSource)
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    println(predictorsMap)

    println(markers(0))

    println(predictorsMap.get(markers(0)))
    println(predictorsMap.get(markers(1)))

    //val corr = corrDistance.apply(predictorsMap.get(markers(0)).get, predictorsMap.get(markers(1)).get)
    //println(corr)
  }

  test("should produce correlation 1 with exactly same grades"){
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    corrDistance.correlation(marks zip marks) should be === 1.0
  }

  test("should produce correlation -1 with exactly opposite grades"){
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    corrDistance.correlation(marks zip marks.reverse) should be === -1.0
    corrDistance.correlation(marks.reverse zip marks) should be === -1.0
  }

  test("should return NaN when all grades are the same for one of the markers"){
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    corrDistance.correlation(marks zip allSameMark).isNaN should be === true
    corrDistance.correlation(allSameMark zip marks).isNaN should be === true
  }

  test("should produce correlation 0.7544237083454246"){
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    corrDistance.correlation(marker1 zip marker2) should be === 0.7544237083454246
    corrDistance.correlation(marker2 zip marker1) should be === 0.7544237083454246
  }


  test("should produce correlation -0.795076668955559"){
    val corrDistance = new CorrelationDistance[GPAGrade](dataSource)
    corrDistance.correlation(marker1 zip marker2.reverse) should be === -0.795076668955559
    corrDistance.correlation(marker1.reverse zip marker2) should be === -0.795076668955559
  }
}