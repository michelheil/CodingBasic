package org.michael.coding.basic.priv

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{GivenWhenThen, Matchers, PrivateMethodTester}

class myClassTest extends AnyFeatureSpec with GivenWhenThen with Matchers with PrivateMethodTester {

  // declare input parameters
  val num1: Int = 1
  val num2: Int = 2

  // calculate expected result
  val expectedResult: Int = num1 + num2

  // PrivateMethod (from org.scalatest.PrivateMethodTester)
  // [Int] is the output type of the private method
  // Symbol("thisIsPrivate") is the name of the private method
  val anotherPrivateMethod: PrivateMethod[Int] = PrivateMethod[Int](Symbol("thisIsAnotherPrivate"))

  // create instance of class with private method
  val c1 = new myClass

  // invoke the private method of the myClass instance
  val actualResult: Int = c1 invokePrivate anotherPrivateMethod(num1, num2)

  // assert actual with expected result
  actualResult shouldBe expectedResult
}
