package com.mark.data

import com.mark.data.GradeData._

/**
 * Utilities methods for data sources
 */

object DataSourceUtil {

  def checkString(gradeData: GradeData, value: String): Unit = {
    if (value == null) throw new IllegalArgumentException(
      gradeData.toString + " should not be null")
    if (value.isEmpty) throw new IllegalArgumentException(
      gradeData.toString + " should not be empty")
  }
}