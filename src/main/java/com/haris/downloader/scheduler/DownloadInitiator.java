package com.haris.downloader.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.domain.BaseDownloader;

/**
 * Initiates the download process on a separate thread. 
 */
public class DownloadInitiator{
	
	private final List<String> jobs;
	private Thread downloadingThread;
	private ApplicationConfiguration applicationConfiguration;
	private List<Class<? extends BaseDownloader>> availableDownloaders;
	
	private DownloadInitiator(){
		this.jobs = new ArrayList<String>();
	}
	
	/**
	 * @param applicationConfiguration containing download related configurations
	 * @param jobs list of urls to download
	 * @param availableDownloaders list of available download implementations
	 * @return new instance of class based on passed parameters
	 */
	public static DownloadInitiator getInstance(final ApplicationConfiguration applicationConfiguration, final List<String> jobs, 
			final List<Class<? extends BaseDownloader>> availableDownloaders) {
		
		if(jobs == null || jobs.size() == 0)
			throw new IllegalStateException("There must exist atleast one downloader job to execute");
		
		if(availableDownloaders == null || availableDownloaders.size() == 0)
			throw new IllegalStateException("There must exist atleast one downloader to execute download jobs");
		
		DownloadInitiator downloadInitiator = new DownloadInitiator();
		downloadInitiator.jobs.addAll(jobs);
		downloadInitiator.applicationConfiguration = applicationConfiguration;
		downloadInitiator.availableDownloaders = availableDownloaders;
		return downloadInitiator;
	}
	
	/**
	 * @return returns true if download is in progress by checking download thread, false otherwise
	 */
	public boolean isDownloading(){
		return downloadingThread != null && downloadingThread.isAlive();
	}
	
	/**
	 * Begins the downloading process on a separate thread if it is not already started. 
	 * @throws IOException if IO related error occurs during download execution.
	 */
	public void beginDownloading() throws IOException{
		
		if(downloadingThread == null){
			DownloadExecutor downloadExecutor = DownloadExecutor.getInstance(applicationConfiguration, jobs, availableDownloaders);
			downloadingThread = new Thread(downloadExecutor);
			downloadingThread.start();
		}
	}
}
