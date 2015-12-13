package com.test.stage2

import akka.actor.{ActorSystem, Actor, ActorRef}
import org.slf4j.LoggerFactory

/**
 * Created by akhtar on 13/12/2015.
 */


/**
 * An actor to terminate the program
 */

class ProgramController(var system:ActorSystem) extends  Actor{
  val logger = LoggerFactory.getLogger(classOf[ProgramController])

  def receive = {

    // shuts down the program
    case cmd: ShutdownCommand => {
     logger.info("Have done enough, its time to shut down ...")
      system.shutdown()
      System.exit(0)
    }


  }


}
