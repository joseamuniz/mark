package com.mark.embedding

import mdsj.MDSJ

/**
 * Performs embedding using classical multidimensional scaling (MDS). 
 *
 * @see http://en.wikipedia.org/wiki/Multi_dimensional_scaling_(in_marketing)
 */
class MetricEmbedder[X, P] extends Embedder[X, P] {

  def embed(points: Set[X], distance: (X, X) => Int): Set[P] = {
    def input = Array(Array(0.00, 2.04, 1.92),
                      Array(2.04, 0.00, 2.10),
                      Array(1.92, 2.10, 0.00))

    null

    /*def n = input(0) size
    def output = MDSJ.classicalScaling(input); // apply MDS

    return output map (array => 1) toSet;      */
  }

  private def distanceMatrix(points: Set[X], distance: (X, X) => Int):
  Array[Array[Int]] = {
    def distanceMatrix = Array.ofDim(2);

    for (pointA <- points; pointB <- points)
    yield distance(pointA, pointB);



    null;
  }

}