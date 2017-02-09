package com.haris.downloader.app;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;
import com.haris.downloader.utils.UrlUtils;


/**
 * Downloads Http and Https resources
 */
public class HttpDownloader extends BaseDownloader{

	private final static String SUPPORTED_SCHEME_HTTP = "http";
	private final static String SUPPORTED_SCHEME_HTTPS = "https";
	
	/**
	 * @return new instance of this class
	 */
	public static HttpDownloader getInstance() {
		return new HttpDownloader();
	}
	
	/**
	 * @return list of supported schemes
	 */
	public static List<String> getSupportedSchemes() {
		return Arrays.asList(new String[] {
			SUPPORTED_SCHEME_HTTP,
			SUPPORTED_SCHEME_HTTPS
		});
	}
	
	/** 
	 * Implementation of Download interface that handles Http and Https download.
	 */
	@Override
	public DownloadResult download(final DownloadParameters parameters) {
		
		boolean isSuccess = true;
		String diskFilePath = null;
		try {
			diskFilePath = com.haris.downloader.utils.FileUtils.getUniqueFilePath(parameters.getFilePath());
			//makes sure server file path is valid
			UrlUtils.extractServerFilePathFromUrl(parameters.getUrl());
			FileUtils.copyURLToFile(new URL(parameters.getUrl()), new File(diskFilePath), 
					parameters.getConnectionTimeoutValue(), parameters.getConnectionTimeoutValue());
			
			printSuccess(parameters, diskFilePath);
			return DownloadResult.getInstance(diskFilePath, null);
		}
		catch(Exception exception){
			printError(exception, parameters);
			isSuccess = false;
			return DownloadResult.getInstance(null, exception);
		}
		finally{
			if(!isSuccess)
				cleanup(diskFilePath);
		}
	}
}
