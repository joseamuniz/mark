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
class GPAMark(score: Int, scale : Int) extends Mark {
	def intValue() = this.score;
	def stringValue() = this.score + "/" + this.scale;
  
}