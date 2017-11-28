package scala

/**
  * Created by Manu Gupta on 11/27/2017.
  */

import java.io.File
import java.util
import javax.xml.parsers.DocumentBuilderFactory

import org.w3c.dom.{Node, NodeList}

/**
  * This class can be used to read data from xml file.
  * It will read multiple xml file and store data in a map
  * the output map will contain key as unique node name and value as list of items against the key
  * if same node name contains in multiple xmls then values will be added in the corresponding list
  * if the node does not contain in a xml , a null will be added
  *
  *
  */
class XMLReader {

  def extractData: util.Map[String, util.List[String]] = {
    var resultMap = new util.TreeMap[String, util.List[String]]
    resultMap = extractData("C:\\\\work\\\\logi\\\\employee.xml", resultMap, 0)
    resultMap = extractData("C:\\\\work\\\\logi\\\\emp1.xml", resultMap, 1)
    resultMap = extractData("C:\\\\work\\\\logi\\\\emp2.xml", resultMap, 2)
    resultMap = extractData("C:\\\\work\\\\logi\\\\location.xml", resultMap, 3)
    resultMap = extractData("C:\\\\work\\\\logi\\\\employee3.xml", resultMap, 4)

    return resultMap
  }

  private def extractData(filePath: String, resultMap: util.Map[String, util.List[String]], index: Int): util.Map[String, util.List[String]] = {
    try {
      val fXmlFile = new File(filePath)
      val dbFactory = DocumentBuilderFactory.newInstance
      val dBuilder = dbFactory.newDocumentBuilder
      val doc = dBuilder.parse(fXmlFile)
      doc.getDocumentElement.normalize()
      val nList = doc.getDocumentElement.getChildNodes
      // get child node for the root
      var nodeAndValues = new util.LinkedHashMap[String, String]
      nodeAndValues = getElements(nList, nodeAndValues) // call Recursively to fetch values from all the child nodes

      import scala.collection.JavaConversions._
      for (m <- nodeAndValues.entrySet) {
        resultMap = appendData(resultMap, m.getKey, m.getValue, index) // arrange the data

      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    resultMap = reSize(resultMap) // fill up the empty with null

    return resultMap
  }

  private def reSize(resultMap: util.Map[String, util.List[String]]): util.Map[String, util.List[String]] = {
    var size = 0
    import scala.collection.JavaConversions._
    for (pair <- resultMap.entrySet) {
      if (size < pair.getValue.size) size = pair.getValue.size
    }

    import scala.collection.JavaConversions._
    for (pair <- resultMap.entrySet) {
      if (pair.getValue.size < size) pair.getValue.add(null)
    }
    return resultMap
  }

  def appendData(resultMap: util.Map[String, util.List[String]], key: String, value: String, index: Int): util.Map[String, util.List[String]] = {
    if (resultMap.containsKey(key)) {
      val values = resultMap.get(key)
      values.add(value)
      resultMap.put(key, values)
    }
    else {
      val values = new util.LinkedList[String]
      var count = 0
      while ( {
        count < index
      }) {
        values.add(null)
        count += 1
      }
      values.add(value)
      resultMap.put(key, values)
    }
    return resultMap
  }

  private def getElements(nList: NodeList, nodeValues: util.Map[String, String]): util.Map[String, String] = {
    for (
    var temp: Int = 0 temp < nList
    .getLength;
    {
      temp += 1;
      temp - 1
    }
    )
    {
      Node nNode = nList.item(temp);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) nNode;
        if (eElement.hasChildNodes()) {
          if (eElement.getChildNodes().getLength() == 1) nodeValues.put(getName(nNode), eElement.getTextContent())

          NodeList nList1 = eElement.getChildNodes();
          nodeValues = getElements(nList1, nodeValues);
        }
      }
      return nodeValues
    }
  }

  def getName(Node: nNode): String = return getfullHistoryOfName(nNode, nNode.getNodeName)

  def getfullHistoryOfName(nNode: Node, name: String): String = {
    if (nNode.getParentNode != null && nNode.getParentNode.getNodeType == Node.ELEMENT_NODE) {
      name = nNode.getParentNode.getNodeName + "_" + name
      name = getfullHistoryOfName(nNode.getParentNode, name)
    }
    return name
  }
}
