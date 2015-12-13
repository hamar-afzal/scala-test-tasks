package com.test.configs

import org.apache.spark.{SparkConf, SparkContext}


object SparkFactory {
  private val conf: SparkConf = new SparkConf().setAppName(PropertiesLoader.getApplicatioName())
  private var sc: SparkContext = null
  private var isInitialized: Boolean = false


  def getSparkContext(): SparkContext = {
    synchronized {
      if (!isInitialized) {
        sc = new SparkContext(conf)
        isInitialized = true
      }
      sc
    }
  }


  def stop(): Unit = {
    synchronized {
      if (isInitialized) {
        sc.stop()
        isInitialized = false
      }
    }
  }



}