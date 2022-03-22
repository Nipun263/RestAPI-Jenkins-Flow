package com.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public  class PropertyFileReader {
	
	public static Properties ReadPropertyFile() throws IOException {
		
		String filePath = System.getProperty("user.dir") + "\\" + "URI.Properties";
		
		File file = new File(filePath);
		
		FileReader FR = new FileReader(file);
		
		Properties PRP = new Properties();
		
		PRP.load(FR);
	
		return PRP;
		
	}
	
	

}
