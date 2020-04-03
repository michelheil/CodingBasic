package org.michael.coding.basic.scala.config

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {

  // ensure that the folder where the conf files are located are marked as project resources
  def getConfigList(resourcePath: String = "/"): List[File] = {
    val configFolder: File = new File(getClass.getResource(resourcePath).getPath)
    if (configFolder.exists && configFolder.isDirectory) {
      val configFiles: List[File] = configFolder
        .listFiles
        .toList
        .filter(_.getName.contains("conf"))

      configFiles
    } else {
      List()
    }
  }

  def loadConfig(appFileList: List[File], startConfig: Config = ConfigFactory.empty()): Config = {
    val xs: List[Config] = appFileList.map(file => ConfigFactory.parseResources(file.getName))
    def op(confA: Config, confB: Config): Config = confA.withFallback(confB)

    val merged = (xs :\ startConfig)(op)
    merged
  }

  def main(args: Array[String]): Unit = {

    val fileList: List[File] = getConfigList("/")
    val config: Config = loadConfig(fileList)

    println(config)
    println(config.getString("auto.offset.reset"))
    println(config.getString("spark.streaming.backpressure.enabled"))
    println(config.getObject("spark.streaming").toString)
  }
}
