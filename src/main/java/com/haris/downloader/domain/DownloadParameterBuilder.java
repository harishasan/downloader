package com.haris.downloader.domain;

import java.io.File;
import java.net.URISyntaxException;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.utils.UrlUtils;

/**
 * Builder class for building DownloadParamter instance fill with available details.
 */
public class DownloadParameterBuilder {
	
	private DownloadParameterBuilder(){
	};
	
	/**
	 * Creates and returns DownloadParamter object filled with information present in application configuration. 
	 * @param url absolute path to resource to download
	 * @param applicationConfiguration object containing download configuration details
	 * @return newly created DownloadParamter object.
	 * @throws URISyntaxException 
	 */
	public static DownloadParameters build(String url, ApplicationConfiguration applicationConfiguration) throws URISyntaxException{
		
		String fileName = UrlUtils.extractFileNameFromUrl(url);
		String downloadFilePath = new File(applicationConfiguration.getDownloadDirectoryPath(), fileName).getAbsolutePath();
		
		DownloadParameters paramters = new DownloadParameters();
		paramters.setFilePath(downloadFilePath);
		paramters.setUrl(url);
		paramters.setFtpUsername(applicationConfiguration.getFtpUsername());
		paramters.setFtpPassword(applicationConfiguration.getFtpPassword());
		paramters.setSftpUsername(applicationConfiguration.getSftpUsername());
		paramters.setSftpPassword(applicationConfiguration.getSftpPassword());
		paramters.setConnectionTimeoutValue(applicationConfiguration.getConnectionTimeoutValue());
		return paramters;
	}
}
