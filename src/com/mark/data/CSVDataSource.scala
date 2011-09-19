package com.mark.data
import com.mark.adt._

class CSVDataSource[M] extends GradeDataSource[M] {
 
  	def getGraders()  :  Set[Grader] = {
  	  return null;
  	}
	
	def getStudents() : Set[Student] = {
	  return null;
	}
	
	def gradesBy(grader : Grader) : Map[(Student, Assignment), Mark] = { 
	  return null;
	}

}