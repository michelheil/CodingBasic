package org.michael.coding.basic.priv

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{GivenWhenThen, Matchers, PrivateMethodTester}

class myObjectTest extends AnyFeatureSpec with GivenWhenThen with Matchers with PrivateMethodTester {

  // declare input parameters
  val num1: Int = 1
  val num2: Int = 2

  // calculate expected result
  val expectedResult: Int = num1 + num2

  // PrivateMethod (from org.scalatest.PrivateMethodTester)
  // [Int] is the output type of the private method
  // Symbol("thisIsPrivate") is the name of the private method
  val privateMethod: PrivateMethod[Int] = PrivateMethod[Int](Symbol("thisIsPrivate"))

  // instead of myObject.thisIsPrivate we need to write
  // myObject invokePrivate privateMethod(num1, num2)
  val actualResult: Int = myObject invokePrivate privateMethod(num1, num2)

  // assert actual with expected result
  actualResult shouldBe expectedResult
}
