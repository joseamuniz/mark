package com.mark.learner

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import com.mark.adt.Assignment
import com.mark.adt.Grader
import com.mark.adt.Student
import com.mark.data.GradeDataSource
import com.mark.adt.Grade

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
class SimpleLearner[M <: Grade] extends PredictorLearner[M] {

  def train(ds: GradeDataSource): Map[Grader, GraderPredictor[M]] = {
    val map = HashMap[Grader, GraderPredictor[M]]()
    (ds getGraders).foreach(grader => map += (grader -> createPredictor(grader, ds)))
    map
  }

  private def createPredictor(grader: Grader, ds: GradeDataSource): GraderPredictor[M] = {
    new GraderPredictor[M] {
      override def predict(student: Student, assignment: Assignment): M = {
        throw new RuntimeException("Alfredo")
//        ds getGrade (grader, student, assignment) match {
//          case Some(grade) => grade;
//          case None => throw new RuntimeException ("Unimplemented");
//        }
      }
    }
  }
}