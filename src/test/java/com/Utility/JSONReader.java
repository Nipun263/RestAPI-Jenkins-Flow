package com.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONTokener;

public class JSONReader {
	
	
	public  static String ReadJSONFile(String JSONFileName , String FolderName) throws FileNotFoundException {
		
		String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\com\\" +  FolderName  + JSONFileName;
		
		File file = new File(filePath);
		
		FileReader FR = new FileReader(file);
		
		JSONTokener JT = new JSONTokener(FR);
		
		Object Obj = JT.nextValue();
		
		return Obj.toString();
		
	}
	
	
}
