package com.mark.adt

/**
 * A student represents a person that submits assignments to be evaluated by
 * a grader. 
 * 
 */
class Student(name: String) {

  require(name != null)
  require(name.length != 0)

  def getName: String = name

  override def toString: String = name

  override def equals(that: Any): Boolean = {
    that match {
      case that: Student => this.getName.equals(that.getName)
      case _ => false
    }
  }

  override def hashCode = name hashCode
}