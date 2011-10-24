package com.mark.data

/**
 *
 */

object DataSourceFactory {

  /**
   * Create data source from CSV file
   */
  def loadCSV(descriptor: CSVDataDescriptor): CSVDataSource = {
    val dataSource = new CSVDataSource
    dataSource.loadData(descriptor)
    return dataSource
  }
}