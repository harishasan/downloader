package com.haris.downloader.scheduler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.Downloader;
import com.haris.downloader.exceptions.ProtocolNotSupportedException;

/**
 *	Initiates and executes the download process on a separate thread.
 */
public class DownloadExecutor implements Runnable{
	
	private final List<Thread> inProgress;
	private final List<String> pending;
	private ApplicationConfiguration applicationConfiguration;
	private List<Class<? extends BaseDownloader>> availableDownloaders;
	
	private DownloadExecutor(){
		inProgress = new ArrayList<Thread>();
		pending = new ArrayList<String>();
	}

	/**
	 * @param applicationConfiguration configuration containing download related information
	 * @param jobs list of urls to download
	 * @param availableDownloaders list of available download implementations
	 * @return new instance of this class
	 * @throws IOException in case of failure to create download directory
	 */
	public static DownloadExecutor getInstance(final ApplicationConfiguration applicationConfiguration, final List<String> jobs,
			final List<Class<? extends BaseDownloader>> availableDownloaders) throws IOException{
		
		DownloadExecutor downloadExecutor = new DownloadExecutor();
		downloadExecutor.pending.addAll(jobs);
		downloadExecutor.availableDownloaders = availableDownloaders;
		downloadExecutor.applicationConfiguration = applicationConfiguration;
		System.out.println(String.format("Initializing download executor with %s as concurrent limit", applicationConfiguration.getConcurrentDownlaodLimit()));
		
		//ensure download directory exists
		new File(applicationConfiguration.getDownloadDirectoryPath()).mkdirs();
		if(!new File(applicationConfiguration.getDownloadDirectoryPath()).exists())
			throw new IOException(String.format("Couldn't create download direcotry: %s", applicationConfiguration.getDownloadDirectoryPath()));
		
		System.out.println("Download directory created: " + applicationConfiguration.getDownloadDirectoryPath());
		
		return downloadExecutor;
	}
	
	/**
	 * Monitors download progress and schedules pending download jobs.
	 */
	@Override
	public void run(){
		
		while(inProgress.size() > 0 || pending.size() > 0){
			
			try {
				while(inProgress.size() < applicationConfiguration.getConcurrentDownlaodLimit() && pending.size() > 0){
					String url = pending.remove(0);
					download(url);
				}
					
				//re-check time period 
				Thread.sleep(3000);
				System.out.println(String.format("%s download(s) are in progress and %s download(s) are pending", inProgress.size(), pending.size()));
				clearCompletedThreads();
			} 
			catch (Exception exception) {
				System.out.println("Quitting download scheduler due to unexpected error: " + exception.getMessage());
				exception.printStackTrace();
				inProgress.clear();
				pending.clear();
			}
		}
	}
	

	/**
	 * kick offs download on a separate thread
	 * @param url absolute path to resource to download
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws URISyntaxException in case of invalid url 
	 */
	private void download(String url) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
		InvocationTargetException, URISyntaxException{
		
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		Downloader downloader = null;
		try {
			downloader = DownloaderFactory.getDownloader(url, availableDownloaders);
		} 
		catch (ProtocolNotSupportedException e) {
			e.printStackTrace();
			return;
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}
		
		RunnableDownloader runnableDownload = RunnableDownloader.getInstance(downloader, parameters);
		Thread thread = new Thread(runnableDownload);
		inProgress.add(thread) ;
		thread.start();		
	}
	
	/**
	 * Check for completed jobs and remove them from list
	 */
	private void clearCompletedThreads() {
		
		boolean isIterationComplete = false;
		while(!isIterationComplete){
		
			Thread threadToClear = null;
			
			for(int index = 0; index < inProgress.size(); index ++){
				if(!inProgress.get(index).isAlive()){
					threadToClear = inProgress.get(index);
					break;
				}
			}
			
			if(threadToClear == null)
				isIterationComplete = true;
			else
				inProgress.remove(threadToClear);
		}
	}
}
