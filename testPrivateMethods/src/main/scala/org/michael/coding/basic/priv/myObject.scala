package org.michael.coding.basic.priv

object myObject {

  // private method that should be tested
  private def thisIsPrivate(num1: Int, num2: Int): Int = {
    num1 + num2
  }
}
