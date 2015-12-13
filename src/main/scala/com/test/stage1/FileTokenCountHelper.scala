package com.test.stage1

import java.io.{FileWriter, PrintWriter, IOException, FileNotFoundException}

import org.slf4j.LoggerFactory

/**
 * Created by akhtar on 12/12/2015.
 */

class FileTokenCountHelper {
  val logger = LoggerFactory.getLogger(classOf[FileTokenCountHelper])

  /**
   * Computes token counts for input file and saves results into output file
   * @param inFilePath    Input file for which to compute token counts
   * @param outFilePath   Output file where results for saved
   */
  def computeTokenCount(inFilePath: String, outFilePath: String) = {
    logger.info("Computing Token Counts for (input=" + inFilePath + ", output=" + outFilePath + ")" )

    // computes counts for each token
    /**
     * Splits a csv file, after that converts the tokens into a map (token ->  count)
     */
    val tokenCountMap = scala.io.Source.fromFile(inFilePath)
      .getLines.drop(1)

      // use foloowing splitter for word counts
      //.flatMap(_.split("\\W+"))

      // finds tokens,  token boundary is defined by , i.e. each column entry is treated as a token
      .flatMap(_.split(","))
      .foldLeft(Map.empty[String, Int]){
          (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
        }

    logger.info("Done with calculations ...")
    save(outFilePath, tokenCountMap)
    logger.info("Done computing tokens count ...")
  }


  /**
   * Saves map data into a file
   * @param outputFilePath  Output file path
   * @param data            Data to be saved in a file in the form of a Map[String, Int]
   */
  def save(outputFilePath: String, data: Map[String, Int]) = {
    logger.info("Saving results to file ..." + outputFilePath)
    if(outputFilePath==null || outputFilePath.isEmpty){
      logger.error("File path is missing ...")
    }
    else if(data==null){
      logger.error("Tried to save empty data ...")
    }
    else{
      var writer:PrintWriter = null
      try{
         writer = new PrintWriter(new FileWriter(outputFilePath))
         data.foreach( tokenCount =>{
            writer.println( tokenCount._1 + "\t" + tokenCount._2 )
         })
        writer.flush()
      }catch {
        case e: FileNotFoundException => {
          e.printStackTrace
        }
        case e: IOException  => {
          e.printStackTrace
        }
      }
      finally {
        if(writer!=null)
          writer.close()
      }

    }

  }

}
