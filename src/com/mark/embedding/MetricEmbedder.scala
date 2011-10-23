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
    def input = toArray(points, distance)
    def output =  MDSJ.classicalScaling(input)
    toPoints(output);


  }

  /**
   * Converts a set of points into an bidimensional array of distances
   *                                                                  ∑
   * TODO: Should this be made an implicit conversion too?
   */
  private def toArray(points: Set[X], distance: (X, X) => Double) =
    (for (from <- points) yield
      ((for (to <- points) yield distance(from, to)) toArray)
    ) toArray


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
    (list map (new Point[Double](_: _*))) toSet
  }
}