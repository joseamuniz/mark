package com.mark.learner

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import com.mark.adt.Grade
import com.mark.data.CSVDataSource
import com.mark.data.GradeDataSource
import org.junit.Assert._
import com.mark.adt.Grader

class SimpleLearnerReturnsDatasourceValue extends AssertionsForJUnit {

  private var grader: Grader = _
  private var dataSource: GradeDataSource = _
  private var simpleLearner: SimpleLearner[Grade] = _

  @Test def testReturnsDatasourceValue() {
    val map = this.simpleLearner.train(dataSource)
    assertNotNull(map)

    var predictor = map.get(this.grader)

  }

  @Before def setUp() {
    this.dataSource = new CSVDataSource()
    this.simpleLearner = new SimpleLearner[Grade]()
  }

}
