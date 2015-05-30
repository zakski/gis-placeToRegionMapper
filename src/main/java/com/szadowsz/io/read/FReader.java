package com.szadowsz.io.read;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;

public class FReader {

	protected String encoding; // encoding of the file to be read
	protected String file; // the path of the file to be read
	protected BufferedReader bReader; // buffered reader to do the reading

	/**
	 * Simple Constructor that sets key variables of the reader
	 * 
	 * @param nFile the filepath to read
	 * @param encode the encoding of the file
	 */
	public FReader(String nFile, String encode) {
		encoding = encode;
		file = nFile;
	}

	/**
	 * Convenience Method to get all the lines from a file
	 * 
	 * @param path - the path of the file
	 * @param encoding - the encoding of the file
	 * @param collection - the collection to store the lines in
	 * @return the updated collection
	 */
	public static Collection<String> getLines(String path, String encoding, Collection<String> collection) {
		if (path != null && path.length() > 0) {

			FReader read = new FReader(path, encoding);
			try {
				read.init();

				String line = null;
				while ((line = read.readLine()) != null) {
					collection.add(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return collection;
	}
	
	/**
	 * Initialises the file reader with the set encoding and file path
	 * 
	 * @throws FileNotFoundException
	 */
	public void init() throws FileNotFoundException {
		bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName(encoding)));
	}

	/**
	 * Reads the next line of the file
	 * 
	 * @return the line that was read
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return bReader.readLine();
	}

	/**
	 * Closes the file reader connection
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (bReader != null) {
			bReader.close();
		}
	}
}
