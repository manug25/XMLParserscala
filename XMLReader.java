package com.xmltodb.processor;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class can be used to read data from xml file. 
 * It will read multiple xml file and store data in a map  
 * the output map will contain key as unique node name and value as list of items against the key
 * if same node name contains in multiple xmls then values will be added in the corresponding list
 * if the node does not contain in a xml , a null will be added 
 *  @author Debasish.chakrabarty
 *
 */
public class XMLReader {
	/**starting point of the xml 
	 * @return
	 */
	public static Map<String,List<String>>  extractData() {
		Map<String,List<String>> resultMap = new TreeMap<String,List<String>>();
		resultMap = extractData("C:\\\\work\\\\logi\\\\employee.xml",resultMap,0);
		resultMap = extractData("C:\\\\work\\\\logi\\\\emp1.xml",resultMap,1);
		resultMap = extractData("C:\\\\work\\\\logi\\\\emp2.xml",resultMap,2);
		resultMap = extractData("C:\\\\work\\\\logi\\\\location.xml",resultMap,3);
		resultMap = extractData("C:\\\\work\\\\logi\\\\employee3.xml",resultMap,4);
		
		return resultMap;
	}
	
	

	/**This will read data from XML and produce a map with node as key and node value as corresponding list element 
	 * @param filePath
	 * @param resultMap
	 * @param index
	 * @return
	 */
	private static Map<String,List<String>>  extractData(String filePath,Map<String,List<String>> resultMap,int index) {
		try {
		File fXmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();

	  	    
	    NodeList nList = doc.getDocumentElement().getChildNodes(); // get child node for the root
		Map<String,String> nodeAndValues = new LinkedHashMap<String,String>(); 
		    
		nodeAndValues =getElements(nList, nodeAndValues); // call Recursively to fetch values from all the child nodes
		
		    for (Map.Entry<String, String> m: nodeAndValues.entrySet()) {
				 resultMap = appendData(resultMap, m.getKey(), m.getValue(),index); // arrange the data 
			}
	    
	    
	    } catch (Exception e) {
	    e.printStackTrace();
	    }
		 resultMap= reSize(resultMap); // fill up the empty with null 
		return resultMap;
	}
	
	
	/** This will fill up the empty with null
	 * @param resultMap
	 * @return
	 */
	private static   Map<String,List<String>>  reSize(Map<String,List<String>> resultMap) {
		int size =0;
		for (Map.Entry<String, List<String>> pair : resultMap.entrySet()) {
			if(size<pair.getValue().size()) {
				size = pair.getValue().size();
			}
		}
		
		for (Map.Entry<String, List<String>> pair : resultMap.entrySet()) {
			if(pair.getValue().size()<size) {
				pair.getValue().add(null);
			}
		}
		return resultMap;
		
	}
	
	static Map<String,List<String>> appendData(Map<String,List<String>> resultMap,String key, String value,int index){
		if(resultMap.containsKey(key)){
			List<String> values = resultMap.get(key);
			values.add(value);
			resultMap.put(key,values);
		
			
		} else {
			List<String> values = new LinkedList<String>();
			int count =0;
			while(count<index ) {
				values.add(null);
				count++;
			}
			values.add(value);
			resultMap.put(key,values);
		}
		
		return resultMap;
	}
	
	private static Map<String,String> getElements(NodeList nList,Map<String,String> nodeValues ) {
		
		  for (int temp = 0; temp < nList.getLength(); temp++) {
			  Node nNode = nList.item(temp);
			  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				    Element eElement = (Element) nNode;
				    if(eElement.hasChildNodes() ) {
			            if(eElement.getChildNodes().getLength()==1) {
			            	nodeValues.put(getName(nNode), eElement.getTextContent());	
			            } 
		           
		            	NodeList nList1 = eElement.getChildNodes();
		            	nodeValues = getElements(nList1, nodeValues);
		            }
			  }
		  }
		
		return nodeValues;
		
	}
	
	
	
	public static String getName( Node nNode) {
		return getfullHistoryOfName(nNode, nNode.getNodeName());
	}
	
	public static String getfullHistoryOfName( Node nNode,String name) {
		if(nNode.getParentNode()!=null && nNode.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
			name = nNode.getParentNode().getNodeName()+"_"+name;
			name = getfullHistoryOfName(nNode.getParentNode(), name);
		}
		return name;
	}
	

}