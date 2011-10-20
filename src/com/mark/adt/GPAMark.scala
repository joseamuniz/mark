package com.mark.adt

/**
 * Represents a simple grading scheme given by value/scale.
 *
 * A grade of 8/10 is thus constructed by specifying a score of 8 in a
 * scale of 10. Any arbitrary (positive) scale is allowed.
 *
 * @param score The actual score represented by this mark.
 * @param scale The maximum score attainable in this assignment.
 */
class GPAMark(score: Int, scale: Int) extends Mark {

  require(scale > 0)

  override def intValue = score
  override def stringValue = score + "/" + scale

  def getScale = scale

  override def equals(that: Any): Boolean = {
    that match {
      case that: GPAMark =>
        this.getScale.equals(that.getScale) &&
          this.intValue.equals(that.intValue)
      case _ => false
    }
  }
}