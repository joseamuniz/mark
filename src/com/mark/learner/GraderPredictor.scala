package com.mark.learner

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

}