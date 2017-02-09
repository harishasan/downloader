package com.haris.downloader.scheduler.test;

import java.util.Arrays;
import java.util.List;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;

public class DownloaderWithoutGetInstance extends BaseDownloader{
	
	private final static String SUPPORTED_SCHEME_HTTP = "http";
	
	/**
	 * @return list of supported schemes
	 */
	public static List<String> getSupportedSchemes() {
		return Arrays.asList(new String[] {
			SUPPORTED_SCHEME_HTTP
		});
	}
	
	@Override
	public DownloadResult download(final DownloadParameters parameters) {
		return null;
	}
}
