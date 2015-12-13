package com.test.stage2

import scala.io.BufferedSource

/**
 * Created by akhtar on 13/12/2015.
 */


// Case Classes for passing information between actors

// Base class to represent a token count with input and output files
sealed case class Request(val inFilepath: String, val outFilePath: String)

// Request to compute tokens count
case class TokenCountRequest(val req:Request)

// Request to read file contents
case class FileReadRequest(val req:Request)

// Request to parse file and compute tokens count
case class ParseRequest(val req:Request, val source: BufferedSource)

// Request to save results into a file
case class FileWriteRequest(val req:Request, val data: Map[String, Int])

trait Command
case class ShutdownCommand() extends Command