package com.xmltodb.processor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class processes the data , which was extracted from xml and produce a 
 * list which can be used to store as data frame 
 * @author Debasish.chakrabarty
 *
 */
public class DataProcessor {
	
	
	public static void processForInsert(Map<String,List<String>> resultMap) {
		
		
		Set<String> keyset =  resultMap.keySet(); // get the keyset  they will be used as column names
		
		List<String> headerList = getHeaderList(keyset);
		
		
		// get the length of any list in resultmap 
		List<List<String>> valueList = getDataList(resultMap, keyset, headerList);
		
		
		////  print part 
		
		for (String header : headerList) {
			System.out.print(header+" , ");
		}
		System.out.println("-------------------------------------------------------------");
		
		for (List<String> values : valueList) {
			// each row 
			for (String value : values) {
				System.out.print(value+" , ");
			}
			System.out.println("");
			
			
		}
		
		
		
	}



	private static List<List<String>> getDataList(Map<String, List<String>> resultMap, Set<String> keyset,
			List<String> headerList) {
		List<List<String>> valueList = new LinkedList<List<String>>();
		
		int length = getLengthOfData(headerList,resultMap);
		for(int i=0;i<length;i++) {
			List<String> row_data= new LinkedList<String>();
			for (String key : keyset) {
				
				List<String> values = resultMap.get(key);
				row_data.add(values.get(i));
			}
			valueList.add(row_data);
		}
		return valueList;
	}
	
	
	public static int getLengthOfData(List<String> headerList,Map<String,List<String>> resultMap) {
		
		// get the first element of the keyset 
		//find the length of the value list
		List<String> value = resultMap.get(headerList.get(0));
		
		return value.size();
	}
	
	
	public static List<String> getHeaderList(Set<String> keyset) {
		List<String> headerList = new LinkedList<String>();
		
		for (String key : keyset) {
			headerList.add(key);
		}
		

		
		return headerList;
	}
	
	
	
}
