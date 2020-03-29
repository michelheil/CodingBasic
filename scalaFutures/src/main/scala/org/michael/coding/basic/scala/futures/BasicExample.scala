package org.michael.coding.basic.scala.futures
/*
import java.time.LocalDateTime
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object BasicExample {

  def main(args: Array[String]): Unit = {
    printInfo("Start Program")
    val task: Future[Unit] = taskHello
    printInfo("Continue")
    Await.result(task, Duration.Inf)
  }

  def taskHello: Future[Unit] = {
    Future{
      printInfo("Starting Task-Hello")
      println("Hello")
      printInfo("Finished Task-Hello")
    }
  }

  def printInfo(txt: String): Unit = {
    val thread = Thread.currentThread.getName
    println(s"${LocalDateTime.now} [$thread] $txt")
  }
}

*/



