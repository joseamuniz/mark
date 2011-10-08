package com.mark.data

import com.mark.adt._


class GPAMarkDataSource extends GPAMarkSomething with CSVDataSource[GPAMark]

trait GPAMarkSomething extends GradeDataSource[GPAMark] {

  private var marks = Map[(Grader, Student, Assignment), GPAMark]()

  def getGrade(
    grader: Grader,
    student: Student,
    assignment: Assignment): Option[GPAMark] = marks get (grader, student, assignment)

  def createMarks(
    scoreMap: Map[(Grader, Student, Assignment), String],
    maxScoreMap: Map[(Grader, Student, Assignment), String],
    weightMap: Map[(Grader, Student, Assignment), String]): Unit = {
    throw new RuntimeException("Method not yet implemented")
  }
}