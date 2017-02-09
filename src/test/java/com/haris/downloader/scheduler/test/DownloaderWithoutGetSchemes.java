package com.haris.downloader.scheduler.test;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;

public class DownloaderWithoutGetSchemes extends BaseDownloader{
	
	/**
	 * @return new instance of this class
	 */
	public static DownloaderWithoutGetSchemes getInstance() {
		return new DownloaderWithoutGetSchemes();
	}

	@Override
	public DownloadResult download(final DownloadParameters parameters) {
		return null;
	}
}

