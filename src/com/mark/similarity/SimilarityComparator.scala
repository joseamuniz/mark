package com.mark.similarity

trait SimilarityComparator[X] {
	def similarity(from: X, to: X) : Int; 
}