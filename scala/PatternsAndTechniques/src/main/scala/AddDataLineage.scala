import model.Result

import scala.util.Try
import org.apache.kafka.clients.consumer.ConsumerRecord
import play.api.libs.json._


// Runtime dependencies
// play-json_2.11-2.6.8.jar play-functional_2.11-2.6.8.jar

case class AddDataLineage(appName: String, appVersion: String)
  extends (ConsumerRecord[String, String] => Result[String]) with Serializable {

  val emptyMap: Map[String, JsValue] = Map()

  override def apply(cr: ConsumerRecord[String, String]): Result[String] = {

    Try(Json.parse(cr.key).validate[Map[String, JsValue]]) match {
      case scala.util.Failure(exception) => {
        println(s"JSONPARSEERROR:Not able to parse Key as Json due to $exception")
        createLineageInKey(emptyMap, cr)
      }
      case scala.util.Success(value) => value match {
        // this case happens when input String is "null"
        case e: JsError => {
          println(s"LINEAGEERROR:String is parsable but it can not be parsed as Map[String, JsValue]: ${e.errors}")
          createLineageInKey(emptyMap, cr)
        }
        case JsSuccess(parsedMap, _) if parsedMap.contains("lineage") => addLineageToKey(parsedMap, cr)
        case JsSuccess(parsedMap, _) => createLineageInKey(parsedMap, cr)
      }
    }
  }

  private def addLineageToKey(givenMap: Map[String, JsValue], cr: ConsumerRecord[String, String]): Result[String] = {

    println("Lineage exist in key. Add lineage of current ConsumerRecord to existing lineage.")

    // get existing lineage
    givenMap.get("lineage").get.validate[Map[String, JsValue]] match {
      case e: JsError => {
        println(s"LINEAGEERROR:Existing lineage in key could not be parsed as Map[String, JsValue] in: ${e.errors}")
        createLineageInKey(emptyMap, cr)
      }
      case JsSuccess(existingLineage, _) => {
        // combine existing and new lineage
        val fullLineageContent = existingLineage ++ createNewLineageContent(cr).as[Map[String, JsValue]]

        // create a lineage map
        val fullLineage = Json.obj("lineage" -> fullLineageContent).as[Map[String, JsValue]]

        // updating (replacing) existing lineage with new lineage map
        val fullKey = givenMap ++ fullLineage

        // return JSON as string
        model.Result.Success(Json.stringify(Json.toJson(fullKey)))
      }
    }
  }

  private def createLineageInKey(givenMap: Map[String, JsValue] , cr: ConsumerRecord[String, String]): Result[String] = {

    println("Lineage does not exist in key. Creating a new field.")

    // creating lineage Map
    val generateApplicationMetadata = Json.obj("lineage" -> createNewLineageContent(cr)).as[Map[String, JsValue]]

    // adding lineage Map to existing map
    val updatedMessageKeyAsMap = givenMap ++ generateApplicationMetadata

    // return JSON as string
    model.Result.Success(Json.stringify(Json.toJson(updatedMessageKeyAsMap)))
  }

  private def createNewLineageContent(cr: ConsumerRecord[String, String]): JsObject = Json.obj(appName ->
    Json.obj(
      "topic" -> cr.topic,
      "partition" -> cr.partition.toString,
      "offset" -> cr.offset.toString,
      "app-version" -> appVersion,
      "kafka_timestamp" -> cr.timestamp.toString
    ))

}