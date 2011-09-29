package com.mark.embedding

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.mark.similarity.Distance


/**
 * Sanity tests for the Metric Embedder using a map of the world
 *
 * TODO: (jmunizn) I really need to sanitize this code
 */

class MetricEmbedderTest extends FunSuite with ShouldMatchers {

  /**
   * Implicit conversion from an arbitrary function to a
   * distance function
   */
  implicit def toDistance[X, Y](f: (X, X) => Y) =
  //TODO (jmunizn) Find me a home and make it work implicitly
    new Distance[X, Y] {
      def apply(origin: X, dest: X): Y = f(origin, dest)
    }

  test("Basic test") {


    def p1 = new Point[Int](0, 0, 0)
    def p2 = new Point[Int](1, 1, 1)
    def p3 = new Point[Int](10, 10, 10)

    def embedder = new MetricEmbedder[Point[Int]]()
    embedder
      .embed(
      Set(p1, p2, p3),
      toDistance((from: Point[Int], to: Point[Int])=> (from.distanceTo(to))))
  }

  test("Cities") {

    /* City distance retrieved from http://personality-project.org/r/mds.html
    */
    def cities =
      Map(
        ("BOS", "BOS") -> 0, ("BOS", "CHI") -> 963, ("BOS", "SF") -> 3095,
        ("CHI", "BOS") -> 963, ("CHI", "CHI") -> 0, ("CHI", "SF") -> 2124,
        ("SF", "BOS") -> 3095, ("SF", "CHI") -> 2142, ("SF", "SF") -> 0
      );

    def embedder = new MetricEmbedder[String]()
    def output = embedder.embed(
      Set("BOS", "CHI", "SF"),
      toDistance((from: String, to: String) => cities(from, to))) toList

    /* SF is closer to Chicago than SF is to Boston */
    def bosPoint = output(0);
    def chiPoint = output(1);
    def sfPoint = output(2);

    def bosChi : Double =  (bosPoint.distanceTo(chiPoint))
    def sfBos  : Double =  (sfPoint.distanceTo(bosPoint))

    //  assert((sfPoint distanceTo chiPoint) > (sfPoint distanceTo bosPoint))
    /* Boston is closer to Chicago than  SF is to Boston */

    println("bos-chi -> " + bosChi)
    println("sf-bos -> "  + sfBos)
    println(bosChi < sfBos)

    (bosChi) should be < (sfBos)



  }
}


