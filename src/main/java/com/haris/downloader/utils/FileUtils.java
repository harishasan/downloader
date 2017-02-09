package com.haris.downloader.utils;

import java.io.File;

import org.apache.commons.io.FilenameUtils;


/**
 * Utility class containing file related handy functions
 */
public class FileUtils {
	
	/**
	 * @param filePath local disk path pointing to a file
	 * @return returns filePath as it is if a file doesn't already
	 * exists with same name, otherwise returns a unique file name. 
	 */
	public static String getUniqueFilePath(String filePath){

		String extension = FilenameUtils.getExtension(filePath);
		
		String uniqueFilePath = filePath;
		
		int index = 1;
		while(new File(uniqueFilePath).exists()){
			uniqueFilePath = String.format("%s(%s)", filePath, index);
			index ++;
		}
		
		//being extra careful here because when threads download same file at the same time it causes issues.
		
		String random = Long.toHexString(Double.doubleToLongBits(Math.random()));
		if(extension != null && extension.length() > 0)
			return uniqueFilePath.replace("." + extension, "") + "_" + random + "." + extension;
		
		return uniqueFilePath + random;
	}
}
