package org.michael.coding.basic.scala.futures

import java.time.LocalDateTime

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object ConcurrentExample {

  def main(args: Array[String]): Unit = {
    val tasks = Future.traverse(1 to 30)(runTask)
    Await.result(tasks, Duration.Inf)
  }

  def runTask(i: Int): Future[Unit] = {
    Future{
      printInfo(s"Task#$i")
      Thread.sleep(1000)
    }
  }

  def printInfo(txt: String): Unit = {
    val thread = Thread.currentThread.getName
    println(s"${LocalDateTime.now} [$thread] $txt")
  }

}
