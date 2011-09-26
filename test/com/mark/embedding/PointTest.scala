package com.mark.embedding
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

/**
 * Tests of point initialization and distance between points
 */

class PointTest extends AssertionsForJUnit {

  @Test def testEqualPoints {
    val p1 = new Point(1,2,3)
    val p2 = new Point(1,2,3)

    assert(p1.distanceTo(p1) == 0)
  }

}