package com.mark.learner

import com.mark.adt.Grader
import com.mark.data.GradeDataSource
import scala.collection.mutable.Map
import com.mark.adt.Mark

/**
 * A PredictorLearner generates a GraderPredictor for each grader in some
 * GraderDataSource.
 *
 * TODO (jmunizn) Should we simply return a mapping of a grader to a function
 * (Student, Assignment)=>Mark ? This would eliminate the need for an explicit
 * GraderPredictor class altogether. But, how can we then different types of
 * predictors? What's the usual Scala style for this?
 *
 */
trait PredictorLearner[M <: Mark] {
  def train(ds: GradeDataSource[M]): Map[Grader, GraderPredictor[M]]
}