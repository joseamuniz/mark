package com.mark.embedding

/** 
 * Performs embedding using the Principal Component Analysis (PCA) procedure. 
 * 
 * @see http://en.wikipedia.org/wiki/Principal_component_analysis
 */
class PCAEmbedder[X, P] extends Embedder[X, P]{

  def embed(points : Set[X], distance : (X,X) => Int) : Set[P] = null;

}