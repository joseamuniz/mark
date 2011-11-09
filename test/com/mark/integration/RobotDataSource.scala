package com.mark.integration

import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import com.mark.data.{GradeDataDescriptor, GradeDataSource}
import com.mark.adt._

/**
 * A special data source that generates data for integration testing
 */

class RobotDataSource extends GradeDataSource {

  private var graders = Set[Grader]()
  private var students = Set[Student]()
  private var assignments = Set[Assignment]()
  private var grades = Map[(Student, Assignment), GradeOutcome]()
  private var dataDescriptor: RobotDataDescriptor = null

  def getGraders: Set[Grader] = graders

  def getStudents: Set[Student] = students

  def getAssignments: Set[Assignment] = assignments

  def getGrade(student: Student, assignment: Assignment): Option[GradeOutcome] =
    grades get (student, assignment)

  def loadData(descriptor: GradeDataDescriptor): Unit = {
    descriptor match {
      case RobotDataDescriptor(file) => {
        loadData(file)
        dataDescriptor = new RobotDataDescriptor(file)
      }
      case _ => throw new IllegalArgumentException("RobotDataSource.loadData() " +
        "does not support this descriptor: " + descriptor)
    }
  }

  private def loadData(file: String): Unit = {
    // lets read a CSV file because we already know how to do this!
    val reader = new CSVReader(new FileReader(file))
    // first line should be the header
    val header: Array[String] = reader.readNext()
    if (header == null) throw new RuntimeException("Input file is empty")

    var row: Array[String] = reader.readNext()
    if (row == null) throw new RuntimeException("Input file " +
      "does not contain data")

    while (row != null) {

      // read the parameters from the input file


      // get next row
      row = reader.readNext()
    }

    // close csv reader
    reader.close()

    // create temporary lists for adding the data objects
    var graders = List[Grader]()
    var students = List[Student]()
    var assignments = List[Assignment]()
    var grades = List[((Student, Assignment), GradeOutcome)]()

    // do magical-AI-robotic-wizard-of-oz stuff to create
    // graders, students, assignments, and grades for the tests

    // set the description of created data set in the data descriptor

    // set the global fields with the values in the temporary lists
    this.graders = graders.toSet[Grader]
    this.students = students.toSet[Student]
    this.assignments = assignments.toSet[Assignment]
    this.grades = grades.toMap[(Student, Assignment), GradeOutcome]
  }

  // after loading the data, the data descriptor contains information
  // to interpret the data created by the robot
  def getDataDescriptor: RobotDataDescriptor = dataDescriptor
}

case class RobotDataDescriptor(file: String)
  extends GradeDataDescriptor
