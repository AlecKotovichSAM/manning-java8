/**
 * 
 */
package com.alec.java8;

import java.io.File;
import java.io.FileFilter;

/**
 * @author okoto
 *
 */
public class Functions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		listFiles();

	}

	private static void listFiles() {
		FileFilter filter = File::isHidden;
		File[] hiddenFiles = new File(".").listFiles(filter);
		System.out.println(hiddenFiles.toString());
	}

}
