package com.mark.embedding
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import scala.util.Random

/**
 * Tests of point initialization and distance between points
 */

class PointTest extends FunSuite with ShouldMatchers {
  def random = new Random()
  def epsilon = 0.1 /* Maximum loss of precision for float operations */
  def doubleVals = List[Double](0, -1, 5, 3.50, 1325.54, -15435.325, -3.50)
  def intVals    = List[Int](0, -1, 5, 10, -5, 150343, -123432)

  def intPoints = List(new Point(1,2,3),
                       new Point(-1, 3, 5),
                       new Point(4, 9, 15),
                       new Point(1, 4, 6, 10, 5, -3, 2))

  def manyPoints =
    for (i <- (1 until 20))
      yield new Point(random nextDouble, random nextDouble, random nextDouble)



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


  test("d(x,x) for any x should have distance 0") {
    for (p <- intPoints)
      (p distanceTo p) should be < (epsilon)

  }


  test("Distance to null should throw an exception") {
    def point = new Point(1,2,3)
    evaluating {(point distanceTo null)} should produce
      [IllegalArgumentException]
  }


  test("Distance to different dimensions should throw exception") {
    def pointA = new Point(1,2,3)
    def pointB = new Point(1,2,3,4)

    evaluating {(pointA distanceTo pointB)} should produce
      [IllegalArgumentException]

  }


  test("Points should have a pretty format") {
    def pointA = new Point(1, 3, 5, 10, 15)
    def pointB = new Point(1, 3.5, -2)
    def pointC = new Point(-10)
    assert((pointA toString) === "Point(1, 3, 5, 10, 15)")
    assert((pointB toString) === "Point(1.0, 3.5, -2.0)")
    assert((pointC toString) === "Point(-10)")
  }

  test("Points should satisfy the triangle inequality") {
    for (point1 <- manyPoints; point2 <- manyPoints) {
      for (x <- manyPoints)
        assert(
          (point1 distanceTo point2)
            <= (point1 distanceTo x) + (x distanceTo point2))
    }
  }

  test("d(y,x) should be positive and equal to d(x,y)") {
    for (point1 <- manyPoints; point2 <- manyPoints) {
      assert(math.abs((point1 distanceTo point2) - (point2 distanceTo point1))
             < epsilon)
      assert((point1 distanceTo point2) >= 0)
    }

  }

  /*
   * TODO (jmunizn) Test possible overflows and Int.MaxValue
   */
}