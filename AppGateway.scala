package scala

/**
  * Created by Manu Gupta on 11/27/2017.
  */

import java.util.{List, Map}

import jdk.internal.org.xml.sax.XMLReader

object AppGateway {

  def main(args: Array[String]) {
    val dataMap: Map[String, List[String]] = XMLReader.extractData // This will read xml files and extarct data
    DataProcessor.processForInsert(dataMap) // this will make the final data list for insert into Database
  }
}

