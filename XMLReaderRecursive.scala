package scala

/**
  * Created by Manu Gupta on 11/27/2017.
  */
import java.io.File
import java.util
import javax.xml.parsers.DocumentBuilderFactory

import org.w3c.dom.{Element, Node, NodeList}

class XMLReaderRecursive {

  def main(argv: Array[String]) = extractData

  def extractData: util.Map[String, util.List[String]] = {
    var resultMap = new util.TreeMap[String, util.List[String]]
    resultMap = extractData("C:\\\\work\\\\logi\\\\employee.xml", resultMap, 0)
    /*resultMap = extractData("C:\\\\work\\\\logi\\\\emp1.xml",resultMap,1);
    resultMap = extractData("C:\\\\work\\\\logi\\\\emp2.xml",resultMap,2);
    resultMap = extractData("C:\\\\work\\\\logi\\\\location.xml",resultMap,3);*/

    return resultMap
  }

  private def extractData(filePath: String, resultMap: util.Map[String, util.List[String]], index: Int): util.Map[String, util.List[String]] = {
    try { //resultMap=	resultMap);
      val fXmlFile = new File(filePath)
      val dbFactory = DocumentBuilderFactory.newInstance
      val dBuilder = dbFactory.newDocumentBuilder
      val doc = dBuilder.parse(fXmlFile)
      doc.getDocumentElement.normalize()
      // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      //NodeList nList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
      val nList = doc.getDocumentElement.getChildNodes
      var nodeValues = new util.LinkedHashMap[String, String]
      nodeValues = getElements(nList, nodeValues)
      import scala.collection.JavaConversions._
      for (m <- nodeValues.entrySet) {
        System.out.println(m.getKey + "  " + m.getValue)
      }
      /*//System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                   System.out.println("\nCurrent Element :" + nNode.getNodeName());
                   System.out.println("____"+eElement.getChildNodes());

                    System.out.println("...."+eElement.getTextContent());

                    if(eElement.hasChildNodes()) {
                      NodeList nList1 = eElement.getChildNodes();
                      for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                        Node nNode1 = nList1.item(temp1);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                          Element eElement1 = (Element) nNode1;
                           System.out.println("\nCurrent Element :" + nNode1.getNodeName());
                               System.out.println("____"+eElement.getChildNodes());

                                System.out.println("...."+eElement1.getTextContent());
                        }

                      }

                    }


                  //  resultMap = appendData(resultMap, nNode.getNodeName(), eElement.getTextContent(),index);

                    if(eElement.hasChildNodes()) {
                      NodeList nList1 = eElement.getChildNodes();
                    for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                         Element eElement1 = (Element) nNode;
                      String name1 = nList1.item(temp1).getNodeName();
                      System.out.println("child"+name1);
                      System.out.println("...."+eElement1.getTextContent());
                      }
                    }
                    }


                }else if (nNode.getNodeType() == Node.ATTRIBUTE_NODE) {
                  String name = nNode.getNodeName();
                  System.out.println(name);
                  if(nNode.hasChildNodes()) {
                    NodeList nList1 = nNode.getChildNodes();
                    for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                      String name1 = nList1.item(temp1).getNodeName();
                      System.out.println(""+name1);
                    }
                  }

                }
            }*/
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    // resultMap= reSize(resultMap);
    return resultMap
  }

  private def getElements(nList: NodeList, nodeValues: util.Map[String, String]): util.Map[String, String] = {
    var temp = 0
    while ( {
      temp < nList.getLength
    }) {
      val nNode = nList.item(temp)
      if (nNode.getNodeType == Node.ELEMENT_NODE) {
        val eElement = nNode.asInstanceOf[Element]
        if (eElement.hasChildNodes) {
          if (eElement.getChildNodes.getLength == 1) {
            nodeValues.put(getName(nNode), eElement.getTextContent)
            /*else {
                                nodeValues.put(nNode.getParentNode().getNodeName()+nNode.getNodeName(), eElement.getTextContent());
                              }
                            */
          }
          val nList1 = eElement.getChildNodes
          nodeValues = getElements(nList1, nodeValues)
        }
      }

      {
        temp += 1; temp - 1
      }
    }

    return nodeValues
  }

  def getName(nNode: Node): String = return getfullname(nNode, nNode.getNodeName)

  def getfullname(nNode: Node, name: String): String = {
    if (nNode.getParentNode != null && nNode.getParentNode.getNodeType == Node.ELEMENT_NODE) {
      name = nNode.getParentNode.getNodeName + "_" + name
      name = getfullname(nNode.getParentNode, name)
    }
    return name
  }
}
