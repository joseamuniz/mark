package com.mark.web

import com.mark.adt.Grader
import com.mark.embedding.Point

/**
 * Representation of a Google Scatter Chart
 */

class GoogleScatterChart(title: String, points: Set[(Grader,Point[Double])]) {

  val AxisRangeMargin = 0.10

  val pointList = points.map(t => t._2).toList
  val xmax = getMax(pointList, 0)
  val xmin = getMin(pointList, 0)
  val ymax = getMax(pointList, 1)
  val ymin = getMin(pointList, 1)
  val xmargin = (xmax - xmin)*AxisRangeMargin
  val ymargin = (ymax - ymin)*AxisRangeMargin
  val xRange = new Point(xmin - xmargin, xmax + xmargin)
  val yRange = new Point(ymin - ymargin, ymax + ymargin)

  val chartUrl = new GoogleScatterChartUrl(
    title,
    pointList,
    points.map(t => t._1.getName).toList,
    List(xRange, yRange))

  def getUrl = chartUrl.getUrl

  private def getMax(points: List[Point[Double]], index: Int) =
    points.foldLeft(Double.MinValue)((maxValue, p) => p(index).max(maxValue))

  private def getMin(points: List[Point[Double]], index: Int) =
    points.foldLeft(Double.MaxValue)((minValue, p) => p(index).min(minValue))
}

/**
 * Class to build the Google Scatter Chart URL
 */

class GoogleScatterChartUrl(
    title: String,
    dataPoints: List[Point[Double]],
    dataLabels: List[String],
    axisRange: List[Point[Double]]) {

  // constant values used to write the URL
  val BaseUrl = "https://chart.googleapis.com/chart?"
  val ParameterSeparator = "&"
  val SeriesSeparator = "|"
  val SizeSeparator = "x"
  val Equal = "="
  val Space = " "
  val EscapedSpace = "+"
  val Coma = ","

  // constant values that define the type and style of the chart
  val chartType = "s" // scatter chart
  val dataFormatTag = "t:" // text format
  val axes = List("x", "y")
  val width = 500
  val height = 400
  val color = List(
    "FF0000","330099","00CC00","FFCC00",
    "CC0099","0033CC","99FF00","FF9900",
    "660099","009999","FFFF00","FF6600")

  // do not use the default scaling nor automatic scaling (chds=a)
  // instead define the data scale to be the same as the axis range
  // provided in the constructor
  val dataScale = axisRange

  def getType = List(GoogleChartUrlTag.Type, chartType)
  def getTitle = List(GoogleChartUrlTag.Title, title.replaceAll(Space, EscapedSpace))
  def getSize = List(GoogleChartUrlTag.Size, width.toString + SizeSeparator + height.toString)
  def getColor = {
    val string = color.take(dataPoints.size).mkString(SeriesSeparator)
    List(GoogleChartUrlTag.Color, string)
  }
  def getAxes = List(GoogleChartUrlTag.Axes, axes.mkString(Coma))
  def getDataLabel = List(GoogleChartUrlTag.DataLabel,
    dataLabels.mkString(SeriesSeparator).replaceAll(Space, EscapedSpace))

  def getData = {
    val string = dataPoints.map(p => p(0)).mkString(Coma) +
      SeriesSeparator +
      dataPoints.map(p => p(1)).mkString(Coma)
    List(GoogleChartUrlTag.Data, dataFormatTag + string)
  }

  def getDataScale = {
    val string = dataScale.map(
      p => (p(0) + Coma + p(1))
    ) mkString(Coma)
    List(GoogleChartUrlTag.DataScale, string)
  }

  def getAxisRange = {
    val string = axisRange.map(
      p => (p(0) + Coma + p(1))
    ).zipWithIndex.map(
      t => t._2 + Coma + t._1
    ) mkString(SeriesSeparator)
    List(GoogleChartUrlTag.AxisRange, string)
  }

  def allParameters = List(getType, getTitle, getSize, getColor,
    getData, getDataLabel, getDataScale, getAxes, getAxisRange)

  def getUrl = BaseUrl + ParameterSeparator +
    allParameters.map(param => param.mkString(Equal)).mkString(ParameterSeparator)
}

/**
 * Enumerates the Google Chart URL parameter tags
 */

object GoogleChartUrlTag {
  val Type = "cht"
  val Title = "chtt"
  val Size = "chs"
  val Color = "chco"
  val Data = "chd"
  val DataLabel = "chdl"
  val DataScale = "chds"
  val DataMarker = "chm"
  val Axes = "chxt"
  val AxisRange = "chxr"
}