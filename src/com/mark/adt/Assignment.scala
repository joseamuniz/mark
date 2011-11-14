package com.mark.adt


/**
 * Assignment for a class that can be assigned a grade. This includes an exam,
 * a problem-set, class participation, etc. 
 * 
 * @param name The name of the assignment
 * @param maxScore The maximum possible score that can be given to this homework
 * 
 */
class Assignment(name : String) {
	/* 
	 * TODO (jmunizn) 
	 * 
	 * This class should also be mappable to a particular weight in a particular class. 
	 * However, we may want to have a 'Class' data type which contains a mapping of 
	 * assignments to their corresponding weights.  
	 * 
	 */

  require(name != null)
  require(name.length != 0)

  def getName: String = name

  override def toString: String = name

  override def equals(that: Any): Boolean = {
    that match {
      case that: Assignment => this.getName.equals(that.getName)
      case _ => false
    }
  }

  override def hashCode = getName hashCode
} 
