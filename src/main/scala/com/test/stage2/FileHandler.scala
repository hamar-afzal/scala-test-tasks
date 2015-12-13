package com.test.stage2

import java.io.{IOException, FileNotFoundException, FileWriter, PrintWriter}

import akka.actor.{ActorRef, Actor}
import org.slf4j.LoggerFactory

/**
 * Created by akhtar on 13/12/2015.
 */

/**
 * An actor to handle file read & write operations
 */
class FileHandler(var programController: ActorRef) extends  Actor{
  val logger = LoggerFactory.getLogger(classOf[FileHandler])


  def receive = {

    // Request to read file contents
    case request: FileReadRequest => {
      logger.info("Reading file: " + request.req.inFilepath)
      val source = scala.io.Source.fromFile(request.req.inFilepath)
      logger.info("Sending file parse request for file: " + request.req.inFilepath)
      sender ! new ParseRequest(request.req, source)
    }

    case request: FileWriteRequest => {
      logger.info("Writing data to file: " + request.req.outFilePath)
      var writer:PrintWriter = null
      try{
        writer = new PrintWriter(new FileWriter(request.req.outFilePath))
        request.data.foreach( tokenCount =>{
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
      logger.info("Done processing request:  (inputFile="+request.req.inFilepath + ", outputFile=" + request.req.outFilePath + ")" )

      // just for demonstration
      logger.info("Going to send program shutdown request ...")
      programController ! new ShutdownCommand

    }
  }


}
