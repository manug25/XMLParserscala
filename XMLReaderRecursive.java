package com.xmltodb.processor;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReaderRecursive {
	
	public static void main(String argv[]) {
		 extractData();
	    
	    
	  }



	public static Map<String,List<String>>  extractData() {
		Map<String,List<String>> resultMap = new TreeMap<String,List<String>>();
		resultMap = extractData("C:\\\\work\\\\logi\\\\employee.xml",resultMap,0);
		/*resultMap = extractData("C:\\\\work\\\\logi\\\\emp1.xml",resultMap,1);
		resultMap = extractData("C:\\\\work\\\\logi\\\\emp2.xml",resultMap,2);
		resultMap = extractData("C:\\\\work\\\\logi\\\\location.xml",resultMap,3);*/
		
		return resultMap;
	}
	

	private static Map<String,List<String>>  extractData(String filePath,Map<String,List<String>> resultMap,int index) {
		try {
			//resultMap=	resultMap);
	    File fXmlFile = new File(filePath);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();

	   // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    //NodeList nList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
	    
	  
	    
	    NodeList nList = doc.getDocumentElement().getChildNodes();
	   Map<String,String> nodeValues = new LinkedHashMap<String,String>();
	    
	    nodeValues =getElements(nList, nodeValues);
	    for (Map.Entry<String, String> m: nodeValues.entrySet()) {
			System.out.println(m.getKey()+"  "+m.getValue());
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
	    } catch (Exception e) {
	    e.printStackTrace();
	    }
		// resultMap= reSize(resultMap);
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
			            } /*else {
			            	nodeValues.put(nNode.getParentNode().getNodeName()+nNode.getNodeName(), eElement.getTextContent());	
			            }
		            */
		           
		            	NodeList nList1 = eElement.getChildNodes();
		            	nodeValues = getElements(nList1, nodeValues);
		            }
			  }
		  }
		
		return nodeValues;
		
	}
	
	
	
	public static String getName( Node nNode) {
		return getfullname(nNode, nNode.getNodeName());
	}
	
	public static String getfullname( Node nNode,String name) {
		if(nNode.getParentNode()!=null && nNode.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
			name = nNode.getParentNode().getNodeName()+"_"+name;
			name = getfullname(nNode.getParentNode(), name);
		}
		return name;
	}
}
