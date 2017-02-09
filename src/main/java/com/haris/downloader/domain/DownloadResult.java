package com.haris.downloader.domain;

import java.io.File;

/**
 * Holds download result information and provide methods to check download status.
 *
 */
public class DownloadResult {

	private String filePath;
	private Exception exception;
	
	private DownloadResult(){
	}
	
	/**
	 * @param filePath absolute disk path where download is placed or null in case of failure
	 * @param exception in case of any errors or null otherwise
	 * @return instance filled with provided details
	 */
	public static DownloadResult getInstance(String filePath, Exception exception){
		DownloadResult result = new DownloadResult();
		result.filePath = filePath;
		result.exception = exception;
		return result;
	}
	
	/**
	 * @return download status, true in case of success, false otherwise
	 */
	public boolean isSuccess(){
		return null != filePath && new File(filePath).exists() && exception == null;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public Exception getException() {
		return exception;
	}
}
