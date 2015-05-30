package com.szadowsz.io.find;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to perform file finding drudgery tasks
 * 
 * @author zakski
 * 
 */
public final class FileFinder {

	private FileFinder(){}
	
	/**
	 * Method to recursively get the list of files under the specified path
	 * 
	 * @param directoryPath - the file path directory string to look at
	 * @return the list of found files
	 */
	public static List<File> getFiles(String directoryPath) {
		return getFiles(new File(directoryPath), null); // a null filter lets us accept all file types
	}

	/**
	 * Method to recursively get the list of files under the specified path
	 * 
	 * @param directory - the file directory to look at
	 * @return the list of found files
	 */
	public static List<File> getFiles(File directory) {
		return getFiles(directory, null); // a null filter lets us accept all file types
	}

	/**
	 * Method to get the list of files under the specified path, recursively if possible
	 * 
	 * @param path - the file path directory string to look at
	 * @param filter - the file filter
	 * @return the list of found files
	 */
	public static List<File> getFiles(String directoryPath, FilenameFilter filter) {
		return getFiles(new File(directoryPath), filter);
	}

	/**
	 * Method to get the list of files under the specified path, recursively if possible
	 * 
	 * @param directory - the file directory to look at
	 * @param filter - the file filter
	 * @return the list of found files
	 */
	public static List<File> getFiles(File directory, FilenameFilter filter) {
		List<File> files = new ArrayList<File>(); // the files found so far in our search at this level

		File[] list = directory.listFiles(filter); // the files at the current depth

		if (list == null)
			return files;
		
		for (File f : list) {
			if (f.isDirectory()) { //
				files.addAll(getFiles(f, filter));
			} else {
				files.add(f);
			}
		}
		return files;
	}
}
