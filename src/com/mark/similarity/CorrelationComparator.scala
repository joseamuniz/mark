package com.mark.similarity
import com.mark.learner.GraderPredictor

class CorrelationComparator[X] extends SimilarityComparator[GraderPredictor] {
	def similarity(from: GraderPredictor, to: GraderPredictor) : Int = 0; 

}