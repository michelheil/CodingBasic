import play.api.libs.json.JsValue
import play.api.libs.json.JsDefined
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must
import org.scalatest.PrivateMethodTester

import model.Result


class KafkaInputAddDateLineageTest extends AnyFlatSpec
  with must.Matchers
  with PrivateMethodTester {
  import KafkaInputAddDateLineageTest._

  val appName = "testName"
  val appVersion = "1.3.37"
  val addDataLineage: AddDataLineage = AddDataLineage(appName, appVersion)

  val createNewLineageContent: PrivateMethod[JsObject] = PrivateMethod[JsObject](Symbol("createNewLineageContent"))
  val createLineageInKey: PrivateMethod[Result[String]] = PrivateMethod[Result[String]](Symbol("createLineageInKey"))
  val addLineageToKey: PrivateMethod[Result[String]] = PrivateMethod[Result[String]](Symbol("addLineageToKey"))

  "AddDataLineage" should "return Success and a json with lineage as key when input key cannot be parsed as Json" in {
      // given
      val givenKey = """noJson"""
      val givenCR = someCrWithGivenKey(givenKey)
      val expectedKey = """{"lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}"""

      // when
      val actualKey = addDataLineage(givenCR)

      // then
      actualKey must be ('success)
      actualKey.get must be (expectedKey)

    }

    it should "return Success and a json with lineage as key when input key is null" in {
      // given
      val givenKey: String = null
      val givenCR = someCrWithGivenKey(givenKey)
      val expectedKey = """{"lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}"""

      // when
      val actualKey = addDataLineage(givenCR)

      // then
      actualKey must be ('success)
      actualKey.get must be (expectedKey)
    }

    it should "return Success and a json with lineage as key when input key is 'null'" in {
      // given
      val givenKey: String = "null"
      val givenCR = someCrWithGivenKey(givenKey)
      val expectedKey = """{"lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}"""

      // when
      val actualKey = addDataLineage(givenCR)

      // then
      actualKey must be ('success)
      actualKey.get must be (expectedKey)
    }

    it should "return Success and add lineage from ConsumerRecord to Key with no existing lineage" in {
      // given
      val givenKey = """{"id":"test"}"""
      val givenCR = someCrWithGivenKey(givenKey)

      // when
      val actual = addDataLineage(givenCR)

      // then
      actual must be ('success)
      actual.get must be (s"""{"id":"test",$someLineageString}""")
    }

    it should "return Success and add lineage from ConsumerRecord to Key with one existing lineage" in {
      // given
      val givenKey = someJsonKeyWithOneLineage
      val givenCR = someCrWithGivenKey(givenKey)

      // when
      val actual = addDataLineage(givenCR)

      // then
      actual must be ('success)
      actual.get must be ("""{"field1":"foo","field2":"bar","lineage":{"existingApp":{"topic":"topic","partition":"5","offset":"115","app-version":"1.33.7","kafka_timestamp":"-1"},"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}""")
    }

    it should "return Success and add lineage from ConsumerRecord to Key with two existing lineage" in {
      // given
      val givenKey = someJsonKeyWithTwoLineage
      val givenCR = someCrWithGivenKey(givenKey)

      // when
      val actual = addDataLineage(givenCR)
      println(actual)

      // then
      actual must be ('success)
      actual.get must be ("""{"field1":"foo","field2":"bar","lineage":{"existingApp1":{"topic":"topic","partition":"5","offset":"115","app-version":"1.33.7","kafka_timestamp":"-1"},"existingApp2":{"topic":"topic","partition":"6","offset":"116","app-version":"1.33.6","kafka_timestamp":"-1"},"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}""")
    }

  "method createNewLineageContent" should "extract values from consumerRecord to a map" in {
      // given
      val givenCr = someCr

      // when
      val actual: JsObject = addDataLineage invokePrivate createNewLineageContent(givenCr)
      val actualMap = actual.as[Map[String, JsValue]]
      val actualJson = Json.toJson(actualMap)

      // then
      (actualJson \ appName) mustBe a [JsDefined]
      Json.stringify(actualJson) must be (someAppString)
      (actualJson \ appName \ "topic").get must be (Json.toJson(someTopic))
      (actualJson \ appName \ "partition").get must be (Json.toJson(somePartition.toString))
  }

  "method createLineageInKey" should "create a lineage field in existing JSON string" in {
      // given
      val givenKey = """{"field1":"foo","field2":"bar"}"""
      val givenMap = Json.parse(givenKey).as[Map[String, JsValue]]
      val givenCr = someCrWithGivenKey(givenKey)

      // when
      val actual = addDataLineage invokePrivate createLineageInKey(givenMap, givenCr)

      // then
      actual must be ('success)
      actual.get must be ("""{"field1":"foo","field2":"bar","lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}""")
  }

  "method addLineageToKey" should "return Success and add lineage of current application to existing JSON string" in {
      // given
      val givenKey = someJsonKeyWithOneLineage
      val givenMap = Json.parse(givenKey).as[Map[String, JsValue]]
      val givenCr = someCrWithGivenKey(givenKey)

      // when
      val actual = addDataLineage invokePrivate addLineageToKey(givenMap, givenCr)

      // then
      actual must be ('success)
      actual.get must be ("""{"field1":"foo","field2":"bar","lineage":{"existingApp":{"topic":"topic","partition":"5","offset":"115","app-version":"1.33.7","kafka_timestamp":"-1"},"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}""")
  }

    it should "return Failure when lineage is mal-formed" in {
      // given
      val givenKey = """{"field1":"foo","field2":"bar","lineage":"lineage"}"""
      val givenMap = Json.parse(givenKey).as[Map[String, JsValue]]
      val givenCr = someCrWithGivenKey(givenKey)
      val expectedKey = """{"lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}}"""

      // when
      val actualKey = addDataLineage invokePrivate addLineageToKey(givenMap, givenCr)

      // then
      actualKey must be ('success)
      actualKey.get must be (expectedKey)
    }
}


object KafkaInputAddDateLineageTest {
  val someTopic = "topic"
  val somePartition = 0
  val someOffset = 5L
  val someKey = "key"
  val someJsonKeyWithOneLineage = """{"field1":"foo","field2":"bar","lineage":{"existingApp":{"topic":"topic","partition":"5","offset":"115","app-version":"1.33.7","kafka_timestamp":"-1"}}}"""
  val someJsonKeyWithTwoLineage = """{"field1":"foo","field2":"bar","lineage":{"existingApp1":{"topic":"topic","partition":"5","offset":"115","app-version":"1.33.7","kafka_timestamp":"-1"},"existingApp2":{"topic":"topic","partition":"6","offset":"116","app-version":"1.33.6","kafka_timestamp":"-1"}}}"""
  val someValue = "value"

  def someCrWithGivenKey(key: String): ConsumerRecord[String, String] =
    new ConsumerRecord[String, String](someTopic, somePartition, someOffset, key, someValue)
  def someCr: ConsumerRecord[String, String] = someCrWithGivenKey(someKey)

  val someAppString = s"""{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}"""
  val someLineageString = s""""lineage":{"testName":{"topic":"topic","partition":"0","offset":"5","app-version":"1.3.37","kafka_timestamp":"-1"}}"""
}