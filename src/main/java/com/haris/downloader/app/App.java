package com.haris.downloader.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.scheduler.DownloadInitiator;

/**
 * Downloads various type of resources and persist them on local disk.
 * Expects the absolute path to resources in arguments.
 * A valid local disk path must be present in config/application.properties file where resources will be persisted.
 * An optional concurrent download limit can be defined in config/application.properties to limit the number of concurrent downloads. 
 */
public class App {
	
	private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/config/application.properties";
	
	/**
	 * @param args an array of absolute paths pointing to resources to download
	 */
	public static void main(String[] args){
		
		if(args.length == 0){
			System.out.println("Ending application: no urls to process.");
			return;
		}
			
		try {
			//load application configuration file
			final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(CONFIG_FILE_PATH);
			System.out.println("Application configuration loaded");
			
			//create download scheduler
			final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
			availableDownloaders.add(FtpDownloader.class);
			availableDownloaders.add(HttpDownloader.class);
			availableDownloaders.add(SftpDownloader.class);
			final DownloadInitiator downloadScheduler = DownloadInitiator.getInstance(applicationConfiguration, Arrays.asList(args), availableDownloaders);
			
			//disable logs coming in because of Sftp via common-logging 
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
			
			downloadScheduler.beginDownloading();
			
			//wait until all the downloads are completed
			while(downloadScheduler.isDownloading())
				Thread.sleep(3000);
			
			System.out.println("Ending application: downlading completed");
			
		} 
		catch (Exception exception) {
			System.out.println("Program quit due to unexpected error: " + exception.getMessage());
			exception.printStackTrace();
		}
	}
}
