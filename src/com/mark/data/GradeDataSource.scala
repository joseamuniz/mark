package com.mark.data
import com.mark.adt._

trait GradeDataSource[Marker] {
  
	def getGraders()  :  Set[Grader]; 
	
	def getStudents() : Set[Student];
	
	def gradesBy(grader : Grader) : Map[(Student, Assignment), Mark]
	
}