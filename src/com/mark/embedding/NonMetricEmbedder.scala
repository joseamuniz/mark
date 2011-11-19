package com.mark.embedding

import com.mark.similarity.Distance

/** 
 * Performs embedding using a non-metric embedder.
 */
class NonMetricEmbedder[X,Y] extends Embedder[X,Y]{

  def embed(points : Set[X], distance : Distance[X, Y]) : Set[Point[Y]] =
    throw new RuntimeException("Not yet implemented")

}