package com.xmltodb.processor;

import java.util.List;
import java.util.Map;

public class AppGateway {

	/**
	 * Starting point of the application
	 * @param args
	 */
	public static void main(String[] args) {

		Map<String, List<String>> dataMap = XMLReader.extractData();// This will read xml files and extarct data 
		DataProcessor.processForInsert(dataMap);// this will make the final data list for insert into Database

	}

}
