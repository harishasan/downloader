package com.haris.downloader.scheduler;

import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;
import com.haris.downloader.domain.Downloader;

/**
 * Helper class for running download on a separate thread. 
 */
public class RunnableDownloader implements Runnable{

	private Downloader downloader;
	private DownloadParameters parameters;
	private DownloadResult downloadResult;
	
	private RunnableDownloader(){
	}
	
	/**
	 * @param downloader instance containing download execution details 
	 * @param parameters containing download configuration
	 * @return new instance of class
	 */
	public static RunnableDownloader getInstance(Downloader downloader, DownloadParameters parameters){
		if(null == downloader)
			throw new IllegalStateException("Downloader cannot be null");
		
		RunnableDownloader runnableDownloader = new RunnableDownloader();
		runnableDownloader .downloader = downloader;
		runnableDownloader .parameters = parameters;
		return runnableDownloader;
	}
	
	public DownloadResult getDownloadResult(){
		return this.downloadResult;
	}
	
	@Override
	public void run(){
		this.downloadResult = downloader.download(parameters);
	}
	
}
