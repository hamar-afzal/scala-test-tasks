package com.test.stage3

import com.test.configs.SparkFactory
import org.slf4j.LoggerFactory

/**
 * Created by akhtar on 13/12/2015.
 */

/**
 * Computes token counts using spark
 */
class SparkTokenCounts {
  val logger = LoggerFactory.getLogger(classOf[SparkTokenCounts])

  /**
   * Computes token counts for input file and saves results into output file
   * @param inFilePath
   * @param outFilePath
   */
  def computeTokenCount(inFilePath: String, outFilePath: String) = {
    // gets spark context
    logger.info("Creating Spark Context ...")
    val sc = SparkFactory.getSparkContext()

    // creates RDD
    logger.info("Creating RDD ...")
    var rdd = sc.textFile(inFilePath)

    // discards header row
    logger.info("Discarding Header Row ...")
    rdd = rdd.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    // computes token counts
    logger.info("Computing Tokens Counts ...")
    val counts = rdd.flatMap(line => line.split(","))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    // saves results into output file
    logger.info("Saving Results ...")
    counts.saveAsTextFile(outFilePath)

    logger.info("Done Processing ...")
  }





}
