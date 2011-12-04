package com.mark.learner

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert._
import com.mark.data._
import com.mark.data.GradeData._
import com.mark.adt.{GPAGrade, Grader}

class SimpleLearnerTest extends AssertionsForJUnit {

  private val dataFile = "test/com/mark/data/grade-data-1.csv"
  private val header = List(StudentData, GraderData, AssignmentData, ScoreData, MaxScoreData, WeightData)

  private var grader: Grader = _
  private var dataSource: GradeDataSource = _
  private var simpleLearner: SimpleLearner = _

  @Test def testReturnsDatasourceValue() {
    val map = this.simpleLearner.train(dataSource)
    assertNotNull(map)

    var predictor = map.get(this.grader)

  }

  @Before def setUp() {
    val dataDescriptor = new CSVDataDescriptor(dataFile, header)
    this.dataSource = new CSVDataSource(dataDescriptor)
    this.simpleLearner = new SimpleLearner()
  }
}
