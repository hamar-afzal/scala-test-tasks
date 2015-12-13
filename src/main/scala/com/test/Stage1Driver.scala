package com.test

import com.test.configs.PropertiesLoader
import com.test.stage1._

/**
 * Created by akhtar on 12/12/2015.
 */

object Stage1Driver{

    def main(args: Array[String]) = {
        val tokenCount:FileTokenCountHelper = new FileTokenCountHelper

        if(args==null || args.length<=1){
            println("Running using default paths ...")
            tokenCount.computeTokenCount(PropertiesLoader.getSampleInputPath(), PropertiesLoader.getStage1OutputPath())
        }
        else
            tokenCount.computeTokenCount(args(0), args(1))
    }


}
