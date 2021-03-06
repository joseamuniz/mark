package com.mark.learner

import com.mark.adt.{GPAGrade, Student, Assignment}

/**
 * A GraderPredictor is a random variable G(s,a) that associates a student s and
 * an assignment a with the hypothetical grade grader G would have given s on 
 * assignment a. 
 * 
 * Notice that in this simple model, the grade itself given by G is a constant,
 * and given by the grades learned by the PredictorLearner. Thus, we normally
 * will use the random variable assuming every combination of students and
 * assignments is equally likely and thereby measure the similarity of the
 * variables.  
 * 
 */
trait GraderPredictor {

  /**
   * Returns the predicted mark for the given student on the given assignment. 
   * Depending on the different implementations this mark can be an inferred 
   * value or a value from a datasource.
   * 
   * @param student student whose mark is to be predicted
   * @param assignment assignment whose mark is to be predicted
   */
  def predict(student : Student, assignment : Assignment) : GPAGrade
}