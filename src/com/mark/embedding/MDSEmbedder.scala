package com.mark.embedding
import com.mark.similarity.SimilarityComparator

/** 
 * Performs embedding using classical multidimensional scaling (MDS). 
 * 
 * @see http://en.wikipedia.org/wiki/Multi_dimensional_scaling_(in_marketing)
 */
class MDSEmbedder[X, P] extends Embedder[X, P]{

  def embed(points : Set[X], distance : (X,X) => Int) : Set[P] = null;

}