package com.mark.data

/**
 *
 */

object DataSourceFactory {

  /**
   * Create data source from CSV file
   */
  def loadCSV(descriptor: CSVDataDescriptor): GradeDataSource = {
    val dataSource = new CSVDataSource
    dataSource.loadData(descriptor)
    return dataSource
  }

  /**
   * Create data source from Microsoft Excel file
   */
  def loadExcel(descriptor: ExcelDataDescriptor): GradeDataSource = {
    val dataSource = new ExcelDataSource
    dataSource.loadData(descriptor)
    return dataSource
  }
}