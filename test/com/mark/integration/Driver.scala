package com.mark.integration

import com.mark.data.GradeData._
import com.mark.data.{GradeDataSource, CSVDataDescriptor, DataSourceFactory}
import com.mark.embedding.MetricEmbedder
import com.mark.learner.{GraderPredictor, SimpleLearner}
import com.mark.similarity.CorrelationDistance
import com.mark.adt.{Grade, Grader, GPAGrade}

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

    /* Given the predictors, find the set of predictors */
    def embed[M <: Grade] (
      predictors : Set[(Grader, GraderPredictor[M])],
      ds : GradeDataSource) = {

      val embedder = new MetricEmbedder[GraderPredictor[M]]()
      val points = embedder.embed(
        predictors.map({case (grader,predictor) => predictor}),
        new CorrelationDistance[M](ds))

      predictors.map({case (grader,predictor) => grader}) zip points
    }

    val ds = getDS;
    embed(getPredictors(ds), ds)
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