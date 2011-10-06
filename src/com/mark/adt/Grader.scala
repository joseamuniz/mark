package com.mark.adt

/**
 * A grader represents a person or system that assigns marks (grades) to a 
 * subset of students on a subset of assignments. 
 * 
 * @param name A (preferably unique) identifying name for this grader.
 */
class Grader (name: String) {

  require(name != null)
  require(name.length != 0)

  def getName: String = name

  override def toString: String = name

  override def equals(that: Any): Boolean = {
    that match {
      case that: Grader => this.getName.equals(that.getName)
      case _ => false
    }
  }
}