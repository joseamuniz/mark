package com.mark.learner

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import com.mark.data.GradeDataSource
import com.mark.adt._

/**
 * A SimpleLearner does not perform any inferencing over the grades provided in
 * GradeDataSource.
 *
 * In other words, let s be a Student and G(s) = \avg_{a}(G(s,a)). That is, let
 * G(s) be the average grade given by G on any assignment given by G(s,a). Then,
 * we construct G(s,a) as follows:
 *
 * G(s,a) = DS.getGrade(G, s, a) iff DS.getGrade(G, s, a) != null
 * G(s,a) = G(s) otherwise
 *
 * That is, if we have an actual record in the GradeDataSource of grader G
 * grading assignment a for student s, then we simply return that value.
 * Otherwise, we guess the grade for any other assignment by this student as
 * the average grade the grader gave him/her.
 *
 */
class SimpleLearner[G <: GPAGrade] extends PredictorLearner[G] {

  def train(ds: GradeDataSource): Map[Grader, GraderPredictor[G]] = {
    val graderPredictorMap = HashMap[Grader, GraderPredictor[G]]()
    (ds getGraders).foreach(grader => graderPredictorMap += (grader ->
      createPredictor(grader, ds)))
    graderPredictorMap
  }

  def createPredictor(grader: Grader, ds: GradeDataSource):
  GraderPredictor[G] = {
    new GraderPredictor[G] {

      override def predict(student: Student, assignment: Assignment): G = {
        var avgByGrader = 0
        var avgByGraderScale = 0
        var marksMatched = 0
        for(assignment <- ds.getAssignments){
          ds getGrade(student, assignment) match {
            case Some(grade) => if(grade.getGrader.equals(grader)) {
              avgByGrader += grade.getGrade.intValue
              avgByGraderScale += grade.getGrade.getScale
              marksMatched = marksMatched + 1
            }

          }
        }

        if(marksMatched == 0) throw new RuntimeException("Cannot create " +
          "predictor because marker did not mark any assignment for given " +
          "student.")

        val avgGpaByGrader = new GPAGrade(avgByGrader / marksMatched,
                                          avgByGraderScale / marksMatched)


        throw new RuntimeException("")
        //ds.getGrade(student, assignment) match{
          //case Some(grade) if(grade.getGrader.equals(grader)) => grade.getGrade
          //case _ => avgGpaByGrader
        //}

      }
    }
  }
}