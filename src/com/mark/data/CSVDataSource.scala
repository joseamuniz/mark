package com.mark.data
import com.mark.adt._

/**
 * Reads the class gradebook from a CSV file encoded in UTF-8 format 
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
 * 
 */
class CSVDataSource[M] extends GradeDataSource[M] {
 
  	def getGraders()  :  Set[Grader] = {
	  throw new RuntimeException("getGraders not yet implemented"); 
  	}
	
	def getStudents() : Set[Student] = {
	  throw new RuntimeException("getStudents not yet implemented"); 

	}
	
	def getGrade(grader : Grader, 
				 student : Student, 
				 assignment : Assignment ) : M = { 
	  
	  throw new RuntimeException("getGrade not yet implemented"); 
	}

}