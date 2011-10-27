package com.mark.data

import com.mark.adt.{GradeOutcome, Assignment, Student, Grader}
import com.mark.data.GradeData._

/**
 * Reads the class gradebook from an Excel file encoded in UTF-8 format
 * with the following fields:
 *
 * - Student Name
 * - Grader Name
 * - Assignment Name
 * - Grade
 * - Max Grade
 * - Weight
 *
 * The first row is reserved for the header.
 */

class ExcelDataSource extends GradeDataSource {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private var assignments = Set[Assignment]()
  private var grades = Map[(Student, Assignment), GradeOutcome]()

  def getGraders: Set[Grader] = graders

	def getStudents: Set[Student] = students

  def getAssignments: Set[Assignment] = assignments

  def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
    grades get (student, assignment)

  def loadData(descriptor: GradeDataDescriptor): Unit = {
    throw new RuntimeException("method loadData(descriptor: GradeDataDescriptor) has not been implemented")
  }
}

case class ExcelDataDescriptor(file: String)
  extends GradeDataDescriptor
