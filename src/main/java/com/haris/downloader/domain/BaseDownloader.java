package com.haris.downloader.domain;

import java.io.File;

/**
 * Abstract base class that provides a class, which is implementing Download interface, few handy features.
 * Provide simple way to print success and error messages.  
 */
public abstract class BaseDownloader implements Downloader {

	/**
	 * Prints an error that occurred during download along with its stack trace.  
	 * @param exception object containing error information.
	 * @param parameters object containing download parameters
	 */
	public void printError(Exception exception, DownloadParameters parameters){
		System.out.println(String.format("Error occured in %s while downloading %s: %s", this.getClass().getSimpleName(),
				parameters.getUrl(), exception.getMessage()));
		
		exception.printStackTrace();
	}
	
	/**
	 * including downloader type, url, and filePath. 
	 */
	
	
	/**
	 * Prints success message on download complete.
	 * @param parameters object containing download parameters
	 * @param filePath disk path of downloaded resource
	 */
	public void printSuccess(DownloadParameters parameters,String filePath){
		System.out.println(String.format("%s successfully downloaded %s: %s", this.getClass().getSimpleName(), parameters.getUrl(), filePath));
	}
	
	
	/**
	 * Clean-ups the partial download in case of failure
	 * @param filePath absolute path where download was going to be placed. 
	 */
	public void cleanup(String filePath){
		if(null != filePath && new File(filePath).exists())
			new File(filePath).delete();
	}
}
