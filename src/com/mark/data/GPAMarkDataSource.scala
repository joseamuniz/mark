package com.mark.data

import com.mark.adt._
import collection.immutable.Map
import collection.mutable


class GPAMarkDataSource extends GPAMarkSomething with CSVDataSource[GPAMark]

trait GPAMarkSomething extends GradeDataSource[GPAMark] {

  private var marks = Map[(Grader, Student, Assignment), GPAMark]()

  def getGrade(
    grader: Grader,
    student: Student,
    assignment: Assignment): Option[GPAMark] =
    marks get (grader, student, assignment)

  def createMarks(
    scoreMap: Map[(Grader, Student, Assignment), String],
    maxScoreMap: Map[(Grader, Student, Assignment), String],
    weightMap: Map[(Grader, Student, Assignment), String]): Unit = {

    val marksMap = mutable.Map[(Grader, Student, Assignment), GPAMark]()

    for (key <- scoreMap.keys) {
      val mark = new GPAMark(
        scoreMap.getOrElse(key, throw new RuntimeException("Not a valid key")).toInt,
        maxScoreMap.getOrElse(key, throw new RuntimeException("Not a valid key")).toInt)

      marksMap put (key, mark)
    }

    marks = Map[(Grader, Student, Assignment), GPAMark]() ++ marksMap
  }
}