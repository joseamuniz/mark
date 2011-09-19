package com.mark.embedding
import com.mark.similarity.SimilarityComparator

trait Embedder[X, T] {
	def embed(points : Set[X], distance : SimilarityComparator[X]) : Set[T];

}
