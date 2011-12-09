package com.mark.web

import com.mark.adt.Grader
import com.mark.embedding.Point

/**
 * Representation of a Google Scatter Chart
 */

class GoogleScatterChart(points: Set[(Grader,Point[Double])]) {

  val chartUrl = new GoogleChartUrl

  val pointList = points.map(t => t._2).toList
  val xmax = getMax(pointList, 0)
  val xmin = getMin(pointList, 0)
  val ymax = getMax(pointList, 1)
  val ymin = getMin(pointList, 1)
  val xmargin = (xmax - xmin)*0.10
  val ymargin = (ymax - ymin)*0.10
  val xRange = new Point(xmin - xmargin, xmax + xmargin)
  val yRange = new Point(ymin - ymargin, ymax + ymargin)

  chartUrl.setData(pointList)
  chartUrl.setDataLabel(points.map(t => t._1.getName).toList)
  chartUrl.setDataScale(List(xRange, yRange))

  private def getMax(points: List[Point[Double]], index: Int) =
    points.foldLeft(Double.MinValue)((maxValue, p) => p(index).max(maxValue))

  private def getMin(points: List[Point[Double]], index: Int) =
    points.foldLeft(Double.MaxValue)((minValue, p) => p(index).min(minValue))

  def getUrl = chartUrl.getUrl
}

class GoogleChartUrl {
  val BaseUrl = "https://chart.googleapis.com/chart?"
  val ParameterSeparator = "&"
  val SeriesSeparator = "|"
  val Equal = "="
  val Space = " "
  val EscapedSpace = "+"
  val Coma = ","

  val chartType = "s"
  val axes = List("x", "y")
  val title = "Grader Similarity"
  val width = 500
  val height = 400
  val color = List("FF0000","0000FF","00FF00")
  var dataPoints: List[Point[Double]] = List()
  var dataLabel: List[String] = List()
  var dataScale: List[Point[Double]] = List()
  var axisRange: List[Point[Double]] = List()

  def setData(dataPoints: List[Point[Double]]) {this.dataPoints = dataPoints}
  def setDataLabel(dataLabel: List[String]) {this.dataLabel = dataLabel}
  def setDataScale(dataScale: List[Point[Double]]) {
    this.dataScale = dataScale
    this.axisRange = dataScale
  }

  def getType = List(GoogleChartUrlTag.Type, chartType)
  def getTitle = List(GoogleChartUrlTag.Title, title.replaceAll(Space, EscapedSpace))
  def getSize = List(GoogleChartUrlTag.Size, width.toString + "x" + height.toString)
  def getColor = List(GoogleChartUrlTag.Color, color.mkString(SeriesSeparator))
  def getAxes = List(GoogleChartUrlTag.Axes, axes.mkString(Coma))
  def getDataLabel = List(GoogleChartUrlTag.DataLabel,
    dataLabel.mkString(SeriesSeparator).replaceAll(Space, EscapedSpace))

  def getData = {
    val string = dataPoints.map(p => p(0)).mkString(Coma) +
      SeriesSeparator +
      dataPoints.map(p => p(1)).mkString(Coma)
    List(GoogleChartUrlTag.Data, "t:" + string)
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

  def allParameters = List(getType, getTitle, getSize,
    getData, getDataScale, getAxes, getAxisRange)

  def getUrl = BaseUrl + ParameterSeparator +
    allParameters.map(param => param.mkString(Equal)).mkString(ParameterSeparator)
}

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