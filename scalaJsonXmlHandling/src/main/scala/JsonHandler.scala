import scala.util.parsing.json._
import play.api.libs.json._

object JsonHandler extends App {

  val jsonText: String = """{"name": "Naoki",  "lang": ["Java", "Scala"]}"""

  // using scala.util.parsing.json
  val parsedString = JSON.parseFull(jsonText.stripMargin)

  val parsedStringOption = parsedString match {
    case Some(value) => value
    case None => throw new UnsupportedOperationException("failed to parse JSON String. Please check!")
  }

  val mapJson = parsedStringOption.asInstanceOf[Map[String, Any]]

  println(mapJson("name"))

  // using play.api.libs.json
  val json: JsValue = Json.parse(jsonText)

  println(json)

  val addedFieldtoJson: JsValue = json.as[JsObject] + ("totalScorings" -> Json.toJson(30))

  println(addedFieldtoJson)


}

