package com.mark.embedding
import com.mark.similarity.SimilarityComparator

class PCAEmbedder[X, P] extends Embedder[X, P]{

  def embed(points : Set[X], distance : SimilarityComparator[X]) : Set[P] = {
    null;
  }

}