package com.mark.integration

import com.mark.data.GradeData._
import com.mark.data.{GradeDataSource, CSVDataDescriptor, DataSourceFactory}
import com.mark.learner.{GraderPredictor, SimpleLearner}
import com.mark.similarity.CorrelationDistance
import com.mark.adt.{Grader, GPAGrade}
import com.mark.embedding.{Point, MetricEmbedder}

/**
 * Given a path to a DataSource CSV file, print a
 *
 * @requires GNUPlot
 */

class Driver {
  private def stdHeader =
    List(
      StudentData,
      GraderData,
      AssignmentData,
      ScoreData,
      MaxScoreData,
      WeightData);



  def generatePlot(path: String) = {

   /* Obtain data source from file and descriptor */
    def getDS = {
      val dataDescriptor = new CSVDataDescriptor(path, stdHeader)
      DataSourceFactory loadCSV dataDescriptor
    }

    /* Pass data source to learner */
    def getPredictors(ds : GradeDataSource) = {
      val l = new SimpleLearner
      l train ds
      for (grader <- ds getGraders) yield
        (grader, l createPredictor(grader,ds))
    }

    /* Given the predictors, find the set of points */
    def embed (
      predictors : Set[(Grader, GraderPredictor)],
      ds : GradeDataSource) = {

      val embedder = new MetricEmbedder[GraderPredictor]()
      val points = embedder.embed(
        predictors.map({case (grader,predictor) => predictor}),
        new CorrelationDistance(ds))

      predictors.map({case (grader,predictor) => grader}) zip points
    }

    val ds = getDS;
    val points : Set[(Grader,Point[Double])] = embed(getPredictors(ds), ds)
    var strRet = "hold off;\n"

    (for ((grader, point) <- points)
      strRet += "text( " + point.getCoordinates(0) +
                    ", " + point.getCoordinates(1) +
                    ", '" + grader.getName +
                    "' );\n" )

    strRet

  }

}

object Driver {

  val WRONG_ARGUMENTS_RET = -1;
  val SUCCESS_RET = 0;

  def usageString = {
    " Driver file.csv                            \n "
    " Where file.csv is a file containing grades \n "
  }

  def main(args: Array[String]) : Int = {
    if (args.length != 1) {
      println(usageString)
      WRONG_ARGUMENTS_RET
    }

    val d = new Driver
    println(d generatePlot args.head)
    SUCCESS_RET
  }
}