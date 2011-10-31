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
class SimpleLearner[M <: Grade] extends PredictorLearner[GPAGrade] {

  def train(ds: GradeDataSource): Map[Grader, GraderPredictor[GPAGrade]] = {
    val map = HashMap[Grader, GraderPredictor[GPAGrade]]()
    (ds getGraders).foreach(grader => map += (grader -> createPredictor(grader, ds)))
    map
  }

   def createPredictor(grader: Grader, ds: GradeDataSource): GraderPredictor[GPAGrade] = {
    new GraderPredictor[GPAGrade] {
      override def predict(student: Student, assignment: Assignment): GPAGrade = {
        throw new RuntimeException("Alfredo")
//        ds getGrade (grader, student, assignment) match {
//          case Some(grade) => grade;
//          case None => throw new RuntimeException ("Unimplemented");
//        }
      }
    }
  }
}