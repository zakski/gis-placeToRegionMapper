package com.szadowsz.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Convenience Class to read and save properties from/to files
 * 
 * @author zakski
 *
 */
public class PropertiesHandler {

	
	/**
	 *  Method to create and load properties from a given file
	 * 
	 * @param filePath - the path of the properties file
	 * @return the loaded properties
	 */
	public static Properties loadProperties(String filePath) {
		Properties properties = new Properties();
		FileInputStream in = null;
		try {
		
			in = new FileInputStream(filePath);
			properties.load(in);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 *  Method to save properties to a given file
	 * 
	 * @param filePath - the path of the properties file
	 * @param properties - the properties to save
	 */
	public static void saveProperties(String filePath, Properties properties) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			properties.store(out, "---No Comment---");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
