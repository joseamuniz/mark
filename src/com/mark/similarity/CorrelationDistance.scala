package com.mark.similarity
import com.mark.learner.GraderPredictor
import scala.math._
import com.mark.data.GradeDataSource
import collection.mutable.ListBuffer
import com.mark.adt.{GPAGrade, Grade}

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
class CorrelationDistance[M <: GPAGrade](gradeDataSource: GradeDataSource)
  extends Distance[GraderPredictor[M], Double]{

  def apply(from : GraderPredictor[M], to: GraderPredictor[M]): Double = {
    val gradesList: Iterable[(Double, Double)] =
      for(student <- gradeDataSource.getStudents;
        assignment <- gradeDataSource.getAssignments)
          yield ((from.predict(student, assignment).intValue.toDouble,
            to.predict(student, assignment).intValue.toDouble))

    1.0 - abs(correlation(gradesList))
  }

  def correlation(tuples: Iterable[(Double, Double)]): Double = {
    covariance(tuples) / (stdev(tuples.unzip._1) * stdev(tuples.unzip._2))
  }

  private def covariance(tuples: Iterable[(Double, Double)]):Double = {
    val meanLHS = mean(tuples.unzip._1)
    val meanRHS = mean(tuples.unzip._2)
    tuples.map(x => ((x._1 - meanLHS) * (x._2 - meanRHS))).sum / tuples.size
  }

  private def stdev(numbers: Iterable[Double]): Double = {
    // store mean to avoid computing it more than once by folding
    val m = mean(numbers)
    sqrt(numbers.map(x => pow(x - m, 2)).sum / numbers.size);
  }

  private def mean(numbers: Iterable[Double]): Double = numbers.sum / numbers.size

}