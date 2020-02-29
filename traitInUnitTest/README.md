# Content
In this project you will learn how to use a trait that can replace a particular method during unit testing

Follow the steps below to do a Coding Dojo.

# Steps
## 1
```scala
package org.michael.coding.basic.random

object KeyHasher {

  private def generateRandomNumber(): Int = {
    0
  }

  def addRandomNumberToInt(num: Int): Int = {
    num + generateRandomNumber()
  }

}
```


## 2a
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test

## 2b
```scala
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{GivenWhenThen, Matchers}

class KeyHasherTest extends AnyFeatureSpec with GivenWhenThen with Matchers {

  Feature("Adding random number to an Integer") {
    Scenario("Fixed random number") {
      Given("a number 3")
      val num: Int = 3

      When("Setting the random number to 7")
      // not possible yet

      And("adding the 'random' number to the given number")
      val result = KeyHasher.addRandomNumberToInt(num)


      Then("The result should be 10")
      result shouldBe 10
    }
  }

}
```

## 3
* KeyHasher object becomes a class
* Test has to be adjusted using 'new' to initialise a new object

## 4
* Create abstract Trait RandomGenerator that defines method generateRandomNumber 
  (simply: `def generateRandomNumber(): Int`)

## 5
Create Main object
```scala
object Main extends App {

  val startInt = 3

  val randomGenerator = new RandomGenerator {
    override def generateRandomNumber(): Int = 5
  }

  val keyHasher = new KeyHasher(randomGenerator)

  println(keyHasher.addRandomNumberToInt(startInt))
}
```

## 6
Finalise Test
```scala
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
```