package com.mark.data


/**
 * Tests extracting grade data from a CSV file
 */

class CSVDataSourceTest extends GradeDataSourceTest {

   def files = List(
    // Grade Data 1
    "test/com/mark/example/grade-data-1.csv",
    // Grade Data 2
    "test/com/mark/example/grade-data-2.csv"
  )

  test("Load and check grade data 1") {
    val i = 0
    val dataDescriptor = new CSVDataDescriptor(files(i), headers(i))
    val dataSource = DataSourceFactory.loadCSV(dataDescriptor)
    checkData(i, dataSource)
  }

  test("Load and check grade data 2") {
    val i = 1
    val dataDescriptor = new CSVDataDescriptor(files(i), headers(i))
    val dataSource = DataSourceFactory.loadCSV(dataDescriptor)
    checkData(i, dataSource)
  }
}