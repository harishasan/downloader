package com.haris.downloader.domain;

/**
 * Define method(s) that must be implemented by a class providing download implementation.
 * This is Core of this application. Implementing class can expect all parameters
 * required for successful download in parameters object. 
 */
public interface Downloader {
	
	/**
	 * @param parameters object containing download configuration and details
	 * @return object containing result of download operation
	 */
	DownloadResult download(final DownloadParameters parameters);
}
