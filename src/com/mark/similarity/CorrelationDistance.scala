package com.mark.similarity
import com.mark.learner.GraderPredictor
import com.mark.adt.Mark
import scala.math._
import com.mark.data.GradeDataSource
import collection.mutable.ListBuffer

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
class CorrelationDistance[M <: Mark](gradeDataSource: GradeDataSource[M])
  extends Distance[GraderPredictor[M], Double]{

  def apply(from : GraderPredictor[M], to: GraderPredictor[M]): Double = {
    val gradesList = ListBuffer[(Double, Double)]()
    for(student <- gradeDataSource.getStudents;
        assignment <- gradeDataSource.getAssignments)  {
          gradesList += ((from.predict(student, assignment).intValue,
            to.predict(student, assignment).intValue))
    }
    1.0 - abs(correlation(gradesList))
  }

  def correlation(tuples: Iterable[(Double, Double)]): Double = {
    covariance(tuples) / (stdev(tuples.unzip._1) * stdev(tuples.unzip._2))
  }

  def covariance(tuples: Iterable[(Double, Double)]):Double = {
    val meanLHS = mean(tuples.unzip._1)
    val meanRHS = mean(tuples.unzip._2)
    tuples.foldLeft(0.0)(
      (res, x) => res + (x._1 - meanLHS) * (x._2 - meanRHS)) / tuples.size
  }

  def stdev(numbers: Iterable[Double]): Double = {
    // store mean to avoid computing it more than once by folding
    val m = mean(numbers)
    sqrt(numbers.foldLeft(0.0)((res, x) => res + pow(x - m, 2)) / numbers.size)
  }

  def mean(numbers: Iterable[Double]): Double = numbers.sum / numbers.size

}