package com.mark.similarity
import com.mark.learner.GraderPredictor
import com.mark.adt.Mark

/**
 * Determines the distance between two GraderPredictors G(s,a) 
 * as follows: 
 * 
 * | G_i(s,a) - G_j(s,a) | = 1 - \abs(\corr(G_i, G_j) )
 * 
 * Here, \corr refers to the Pearson Correlation Coefficient of the
 * GraderPredictors G_i and G_j. Notice that we only consider the absolute 
 * value of the correlation, thus considering highly negatively correlated 
 * graders to be close to each other, and considering uncorrelated graders
 * to be the furthest apart. 
 * 
 * 
 */
class CorrelationComparator[M <: Mark] {
  
  def distance(from : GraderPredictor[M], to: GraderPredictor[M]) = 0;

}