package com.mark.adt

/**
 * A grade given to a student on a particular assignment.
 */
trait Mark {

  /**
   * Integral representation of the grade
   */
  def intValue: Int

  /**
   * Text representation of the grade. This representation should be easy
   * to read in a user interface, such as "A+", "8/10", or "Pass".
   */
  def stringValue: String

  /**
   * Adds the int values of two marks
   */
  /* def +(mark: Mark): Int = intValue + mark intValue   */
}