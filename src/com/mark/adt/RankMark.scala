package com.mark.adt

/**
 * A mark based on the position of the score with respect to the scores of others
 * on the same assignment.
 *
 * If r is of type RankMark and r.intValue() == 1, then r is the highest score
 * attained by anyone in the GradeDataSource for the same assignment.
 *
 * @param score The actual score represented by this mark.
 * @param scale The maximum score attainable in this assignment.
 */
class RankMark extends Mark {
  override def intValue = throw new RuntimeException("intValue not yet implemented")
  override def stringValue =
    throw new RuntimeException("stringValue not yet implemented")

}