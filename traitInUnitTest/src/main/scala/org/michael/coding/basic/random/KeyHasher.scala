package org.michael.coding.basic.random

class KeyHasher(randomGenerator: RandomGenerator) {

  def addRandomNumberToInt(num: Int): Int = {
    num + randomGenerator.generateRandomNumber()
  }

}