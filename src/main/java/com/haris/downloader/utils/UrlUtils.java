package com.haris.downloader.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class containing Url related handy functions
 */
public class UrlUtils {
	
	/**
	 * @param url absolute path pointing to a resource
	 * @return last parts of path i.e. file name
	 * @throws URISyntaxException 
	 */
	public static String extractFileNameFromUrl(String url) throws URISyntaxException{
		String filePath = extractServerFilePathFromUrl(url);
		int index = filePath.lastIndexOf('/');
		if(index == -1)
			return filePath;
		
		return filePath.substring(index + 1);
	}
	
	/**
	 * @param url absolute path pointing to a resource
	 * @return server name extracted from url
	 * @throws URISyntaxException in case of invalid url
	 */
	public static String extractServerNameFromUrl(String url) throws URISyntaxException{
		URI uri = new URI(url);
		return uri.getHost();
	}
	
	/**
	 * @param url absolute path pointing to a resource
	 * @return resource path extracted from url 
	 * @throws URISyntaxException in case of invalid url
	 */
	public static String extractServerFilePathFromUrl(String url) throws URISyntaxException{
		if(url.endsWith("/"))
			throw new IllegalArgumentException("Url must end on a file");
		
		URI uri = new URI(url);
		String filePathOnServer = uri.getPath(); 
		if(filePathOnServer == null || filePathOnServer.length() == 0)
			throw new IllegalArgumentException("Could not resolve file path on server");
		
		return filePathOnServer;
	}
	
	
}
