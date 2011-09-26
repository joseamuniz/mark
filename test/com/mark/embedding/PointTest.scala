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
  def doubleVals = List[Double](0, -1, 5, 3.50, 1325.54, -15435.325, -3.50)
  def intVals    = List[Int](0, -1, 5, 10, -5, 150343, -123432)

  def intPoints = List(new Point(1,2,3),
                       new Point(-1, 3, 5),
                       new Point(4, 9, 15),
                       new Point(1, 4, 6, 10, 5, -3, 2))
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
        test ("d(%s,%s) should equal |%s - %s|"
              format (pointA, pointB, valueA, valueB)) {
          def distance = (pointA distanceTo pointB);
          def difference = num.toDouble(valueB) - num.toDouble(valueA)
          assert(distance - math.abs(difference) < epsilon)
        }

  }

  testOneDimensional(doubleVals);
  testOneDimensional(intVals);


  test("Equal points") {
    for (p <- intPoints)
      (p distanceTo p) should be < (epsilon)

  }

}