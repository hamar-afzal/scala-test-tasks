package com.test

import akka.actor.{Props, ActorRef, ActorSystem}
import com.test.configs.PropertiesLoader
import com.test.stage1._
import com.test.stage2._

/**
 * Created by akhtar on 12/12/2015.
 */

object Stage2Driver{

    def main(args: Array[String]) = {
        // default input/output paths
        var inFilePath  = PropertiesLoader.getSampleInputPath()
        var outFilePath = PropertiesLoader.getStage2OutputPath()

        // sets user provided paths if user provided that
        if(args!=null && args.length==2){
            inFilePath  = args(0)
            outFilePath = args(1)
        }
        else
            println("Running using default paths ...")


        // Instantiating Akka actor system
        val system = ActorSystem("ActorsTest")

        // used to demonstrate shutdown of program
        val programController = system.actorOf(Props(new ProgramController(system)), "ProgramController")

        // Creating an actor for file bases operations
        val fileHandler:ActorRef = system.actorOf(Props(new FileHandler(programController)), "FileHandler")

        // Creating an actor to handle parsing and token computation
        val tokenRequestHandler:ActorRef = system.actorOf(Props( new TokenCountRequestHandler(fileHandler)), "TokenCountRequestHandler")

        // Sending a request to compute tokens count for input file, results will be saved in output file
        tokenRequestHandler ! new TokenCountRequest(new Request(inFilePath, outFilePath))

    }


}
