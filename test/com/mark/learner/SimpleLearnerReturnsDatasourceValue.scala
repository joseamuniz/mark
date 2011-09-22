package com.mark.learner

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import com.mark.adt.Mark
import com.mark.data.CSVDataSource
import com.mark.data.GradeDataSource
import org.junit.Assert._
import com.mark.adt.Grader

class SimpleLearnerReturnsDatasourceValue extends AssertionsForJUnit {

  private var grader : Grader = _

  private var dataSource : GradeDataSource[Mark] = _
  private var simpleLearner : SimpleLearner[Mark] = _
  
  @Test def testReturnsDatasourceValue() {
    val map = this.simpleLearner.train(dataSource)
    assertNotNull(map)
    
    var predictor = map.get(this.grader)
    
    
  }

  @Before def setUp() {
    this.dataSource = new CSVDataSource[Mark]()
    this.simpleLearner = new SimpleLearner[Mark]()
  }

}
