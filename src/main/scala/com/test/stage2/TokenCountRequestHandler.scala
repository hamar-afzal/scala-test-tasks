package com.test.stage2

import akka.actor.{ActorRef, Actor}
import org.slf4j.LoggerFactory

import scala.io.BufferedSource

/**
 * Created by akhtar on 13/12/2015.
 */


/**
 * An actor to handle token compute requests
 * @param fileHandler  ActorRef for file based operations
 */


class TokenCountRequestHandler(var fileHandler:ActorRef) extends  Actor{
  val logger = LoggerFactory.getLogger(classOf[TokenCountRequestHandler])

  def receive = {

    // Starts a token count request
    case request: TokenCountRequest => {
      logger.info("Got a new request for tokens count: (inputFile="+request.req.inFilepath + ", outputFile=" + request.req.outFilePath + ")" )
      logger.info("Sending file read request to file handler ...")
      fileHandler ! new FileReadRequest(request.req)
    }

    case request: ParseRequest => {
      logger.info("Parsing file: (inputFile="+request.req.inFilepath + ")" )
      val map = request.source.getLines.drop(1)
        .flatMap(_.split(","))
        .foldLeft(Map.empty[String, Int]) {
        (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
      }

      logger.info("Sending file write request to file handler ...")
      fileHandler ! new FileWriteRequest(request.req, map)
    }

  }


}
