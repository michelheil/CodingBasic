import scala.reflect.runtime.universe._

object XmlHandler {

  def main(args: Array[String]): Unit = {

    val myMap: Map[Int, Int] = Map(1 -> 10, 2 -> 20)

    if(myMap.isInstanceOf[Map[String, Int]])
      println("true")
    else
      println("false")

    recognise[Map[String, Int]](myMap)
    recogniseWithTag(myMap)

    if(recogniseWithTag2(myMap) == "Map[Int,Int]")
      println("version2: true")
    else
      println("version2: false")

  }

  def recognise[T](x: Any): Unit = x match {
    case x: T => println("recognise: true")
    case _ => println("recognise: false")
  }


  def recogniseWithTag[T](x: T)(implicit tag: TypeTag[T]): Unit = tag.tpe match {
    case TypeRef(utype, usymbol, args) => println(List(utype, usymbol, args).mkString("\n") + tag.tpe.toString) //println(s"type of $x has type arguments $args")
    case _ => println("recognise: false")
  }

  def recogniseWithTag2[T](x: T)(implicit tag: TypeTag[T]): String = tag.tpe.toString


  def splitBulk(bulkXml: String): Unit = {
    println(bulkXml.split(" "))
  }

}
