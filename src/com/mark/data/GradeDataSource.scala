package com.mark.data
import com.mark.adt._

/**
 * Provides a facade to an underlying grade data source, providing listings of
 * graders and students in the data source, as well as the grades recorded for
 * each student on their assignments.
 *
 * This class is parameterized on M, used for specifying the type of
 * grade formatting to be returned by the DataSource.
 *
 */

/*
 * TODO: Need to figure out the syntax to specify that Marker should actually be 
 * a subclass of com.mark.adt.Mark 
 */
trait GradeDataSource[M <: Mark] {

  /**
   * Returns a list of all the graders that have graded at least one
   * assignment in this datasource.
   *
   * @return A (non-null) set of graders.
   */
  def getGraders: Set[Grader];

  /**
   * Returns a list of all the students that have at least one assignment
   * graded in this datasource.
   *
   * @return A (non-null) set of graders.
   */
  def getStudents: Set[Student];

  /**
   * Returns the grade recorded for student by grader on assignment.
   *
   * @return the desired grade (or null if the grade doesn't exist)
   * 		   in the required format.
   */
  def getGrade(
    grader: Grader,
    student: Student,
    assignment: Assignment): Option[M]

}