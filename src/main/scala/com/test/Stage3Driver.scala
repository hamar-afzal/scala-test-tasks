package com.test

import akka.actor.{ActorRef, ActorSystem, Props}
import com.test.configs.PropertiesLoader
import com.test.stage2.{FileHandler, Request, TokenCountRequest, TokenCountRequestHandler}
import com.test.stage3.SparkTokenCounts

/**
 * Created by akhtar on 12/12/2015.
 */

object Stage3Driver{

    def main(args: Array[String]) = {

        // user should supply input and output paths on HDFS
        if(args!=null && args.length==2){
            val inFilePath  = args(0)
            val outFilePath = args(1)

            val sparkTokensCount:SparkTokenCounts = new SparkTokenCounts
            sparkTokensCount.computeTokenCount(inFilePath, outFilePath)
        }
        else {
            println("Error: Input_File and Output_File Arguments Missing ...")
        }

    }


}
