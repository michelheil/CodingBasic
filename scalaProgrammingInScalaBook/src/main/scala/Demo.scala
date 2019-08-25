import scala.collection.mutable


object Demo{ // ausfuehren mit ctrl + shift + f10

  val first = List(1, 2, 3, 5, 7, 10, 13)
  val second = List("A", "B", "C")
  val mymap = mutable.Map(1 -> "Tom", 2 -> "Max", 3 -> "John")

  mymap += (4 -> "Jan")

  val anotherMap = mutable.Map[Int, String](43 -> "fwea", 20 -> "foo")

  val pair = (99, "Luftballons")

  val y = new Rational(3)

  def main(args: Array[String]): Unit = {
    println(first.map(_ * 2))

    println(first.map(x => x / 0.2))
    println(first.map(x => "hi" * x))
    println(mymap.mapValues(x => "hi " + x))
    println("hello".map(_.toUpper))
    println(List(List(1,2,3), List(3,4,5)))

    println(first.reduce(_ + _))
    println(first.reduce((x,y) => x+y))
    println(first.reduceLeft(_ + _))
    println(second.reduceLeft(_ + _))
    println(first.reduceLeft((x,y) => {println(x + " , " + y); x+y;}))

    println(first.foldLeft(100)(_ + _))
    println(second.foldLeft("z")(_ + _))

    println(first.scanLeft(100)(_ + _))
    println(second.scanLeft("z")(_ + _))

    println(pair._1 + ", " + pair._2)

    println(mymap(1))
    println(mymap(4))

    println(y)

    println(y.add(new Rational(1,2)))

  }

}