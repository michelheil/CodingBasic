package org.michael.coding.basic.random

object Main extends App {

  val startInt = 3

  val randomGenerator = new RandomGenerator {
    override def generateRandomNumber(): Int = 5
  }

  val keyHasher = new KeyHasher(randomGenerator)

  println(keyHasher.addRandomNumberToInt(startInt))
}
