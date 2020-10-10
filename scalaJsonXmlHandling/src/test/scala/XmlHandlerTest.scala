import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen}

import scala.io.Source

class XmlHandlerTest extends FunSpec with BeforeAndAfterEach with GivenWhenThen {

  override def beforeEach() {

  }

  override def afterEach() {

  }

  describe("The function splitBulk") {
    it("should split an XML provided as string into single events") {
      Given("a bulk XML file")
      val prettyBulkXML = Source.fromURL(getClass.getClassLoader.getResource("bulk_XML_3"))
      // println(prettyBulkXML)
      //val scorings = XML.loadString(prettyBulkXML) \\ "scoring"
      //print(scorings)

      val flatBulkXML = prettyBulkXML.mkString.split("\n").mkString.replaceAll("\\s", "")
      println(flatBulkXML)

      When("")


      Then("")

      pending
    }
  }





}
