package com.mark.embedding

import mdsj.MDSJ
import com.mark.similarity.Distance

/**
 * Performs embedding using classical multidimensional scaling (MDS). 
 *
 * @see http://en.wikipedia.org/wiki/Multi_dimensional_scaling_(in_marketing)
 */
class MetricEmbedder[X] extends Embedder[X, Double] {

  def embed(points: Set[X], distance: Distance[X,Double]): Set[Point[Double]] = {
    def input =  toArray(points toList, distance)

    toPoints(MDSJ.classicalScaling(input))


  }

  /**
   * Converts a set of points into a two-dimensional array of distances
   *                                                                  ∑
   * TODO: Should this be made an implicit conversion too?
   */
  private def toArray(pointList: List[X], distance: (X, X) => Double) =
  //Add some random noise to increase very close distances by a little.
  // This keeps MDSJ from throwing NaNs.

    Array.tabulate(pointList size, pointList size) (
        (x,y) => if (x == y) 0 else
                            (10   * math.random +
                             1000 * math.max(0, distance(pointList(x), pointList(y))))
    )


  /**
   * Converts a matrix representing points into a set of points
   *
     * @param arrays A bi-dimensional array representing the output of the MDS
     *               algorithm. Notice that the jth coordinate of the ith point
     *               is given by array(i,j)
     *
     * TODO: Again, can we do implicit conversion?
     */
  private def toPoints(array : Array[Array[Double]]) : Set[Point[Double]] = {
    def list = (array toList).transpose
    (list map (new Point[Double](_: _*))).toSet
  }
}