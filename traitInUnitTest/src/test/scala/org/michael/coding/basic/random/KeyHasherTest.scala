package org.michael.coding.basic.random

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{GivenWhenThen, Matchers}

class KeyHasherTest extends AnyFeatureSpec with GivenWhenThen with Matchers {

  Feature("Adding random number to an Integer") {
    Scenario("Fixed random number") {
      Given("a number 3")
      val num: Int = 3

      When("Setting the random number to 7")
      val randomGenerator = new RandomGenerator {
        override def generateRandomNumber(): Int = 7
      }
      val keyHasher = new KeyHasher(randomGenerator)

      And("adding the 'random' number to the given number")
      val result = keyHasher.addRandomNumberToInt(num)

      Then("The result should be 10")
      result shouldBe 10
    }
  }

}
