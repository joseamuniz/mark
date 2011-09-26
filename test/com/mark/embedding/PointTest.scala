package com.mark.embedding
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

/**
 * Tests of point initialization and distance between points
 */

class PointTest extends FunSuite with ShouldMatchers {


  def epsilon = 0.1 /* Maximum loss of precision for float operations */
  def doubleVals = List[Double](0, -1, 5, 3.50, 1325.54, -15435.325, -3.50);
  def intVals    = List[Int](0, -1, 5, 10, -5, 150343, -123432);

  test("Equal points") {
    val p1 = new Point(1,2,3)
    val p2 = new Point(1,2,3)

    assert(p1.distanceTo(p1) < epsilon)
  }

  /**
   * For any pair of two dimensional-points, their distance must be always
   * equal to the difference between their absolute values.  In other words,
   *
   * d((x), (y)) = |x - y|
   *
   */
  def testOneDimensional[N : Numeric](values : List[N]) {
    def num = implicitly[Numeric[N]]
    def points = values map (value => (value, new Point(value)))

      for ((valueA, pointA) <- points; (valueB, pointB) <- points)
        test("d(" + pointA.toString + "," + pointB.toString + ") must equal" +
             "|" + valueA + " - " + valueB + "|") {
          assert(((pointA distanceTo pointB)
                  - math.abs(num.toDouble(valueB) - num.toDouble(valueA)))
                  < epsilon)
        }

  }

  testOneDimensional(doubleVals);
  testOneDimensional(intVals);


}