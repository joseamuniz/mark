package com.mark.embedding

/** 
 * Performs embedding using a non-metric embedder.
 */
class NonMetricEmbedder[X, P] extends Embedder[X, P]{

  def embed(points : Set[X], distance : (X,X) => Int) : Set[P] = null;

}