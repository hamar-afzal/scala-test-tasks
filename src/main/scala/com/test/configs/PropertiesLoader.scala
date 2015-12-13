package com.test.configs

import java.io.{IOException, FileNotFoundException, InputStream}
import java.util.Properties

/**
 * Created by akhtar on 12/12/2015.
 */

object PropertiesLoader {
  private val PROPERTIES_FILE_NAME: String = "config.properties"
  private var properties: Properties = null


  private def load() = {
    properties = new Properties
    val classLoader: ClassLoader = Thread.currentThread.getContextClassLoader
    val inputStream: InputStream = classLoader.getResourceAsStream(PROPERTIES_FILE_NAME)
    properties.load(inputStream)
  }

  private def getProperty(propName: String) : String = {
    val defaultValue:String = ""
    try {
      if(properties==null)
        load()
      return properties.getProperty(propName)
    }
    catch {
      case e: FileNotFoundException => {
        e.printStackTrace
      }
      case e: IOException  => {
        e.printStackTrace
      }
    }
    defaultValue
  }


  def getApplicatioName() = {
    PropertiesLoader.getProperty("app.name")
  }

  def getSampleInputPath() = {
    PropertiesLoader.getProperty("input.file.path")
  }

  def getStage1OutputPath() = {
    PropertiesLoader.getProperty("stage1.output.file.path")
  }

  def getStage2OutputPath() = {
    PropertiesLoader.getProperty("stage2.output.file.path")
  }




}