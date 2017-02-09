package com.haris.downloader.scheduler.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.app.HttpDownloader;
import com.haris.downloader.app.test.utils.FileUtils;
import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.scheduler.DownloadInitiator;

public class DownloadInitiatorTest {

	@Test(expected=IllegalStateException.class)
	public void InitializeWithNoJob() throws IOException{
		
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		DownloadInitiator.getInstance(applicationConfiguration, new ArrayList<String>(), availableDownloaders);
	}
	
	@Test(expected=IllegalStateException.class)
	public void InitializeWithNullJob() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		DownloadInitiator.getInstance(applicationConfiguration, null, availableDownloaders);
	}
	
	@Test(expected=IllegalStateException.class)
	public void InitializeWithNullDownloader() throws IOException{
		
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		DownloadInitiator.getInstance(applicationConfiguration, Arrays.asList(new String[]{"Hello"}), availableDownloaders);
	}
	
	@Test(expected=IllegalStateException.class)
	public void InitializeWithNoDownloader() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadInitiator.getInstance(applicationConfiguration, Arrays.asList(new String[]{"Hello"}), null);
	}
	
	@Test
	public void IsDownloadingWithoutStart() throws IOException{
		
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		DownloadInitiator downloadInitiator = DownloadInitiator.getInstance(applicationConfiguration, 
				Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"}), availableDownloaders);
		
		Assert.assertFalse(downloadInitiator.isDownloading());
	}
	
	@Test
	public void IsDownloadingAfterStart() throws IOException{
	
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		
		try{
			final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
			availableDownloaders.add(HttpDownloader.class);
			DownloadInitiator downloadInitiator = DownloadInitiator.getInstance(applicationConfiguration, 
					Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"}), availableDownloaders);
			
			downloadInitiator.beginDownloading();
			Assert.assertTrue(downloadInitiator.isDownloading());
			//make sure downloainding completes to delete the folder afterwards
			while(downloadInitiator.isDownloading())
				Thread.sleep(1000);
		}
		catch(Exception ex){
			
		}
		finally{
			FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
		}
	}
	
	@Test
	public void SuccessfulDownloading() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		
		try{
			final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
			availableDownloaders.add(HttpDownloader.class);
			DownloadInitiator downloadInitiator = DownloadInitiator.getInstance(applicationConfiguration, 
					Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"}), availableDownloaders);
			
			downloadInitiator.beginDownloading();
			Assert.assertTrue(downloadInitiator.isDownloading());
			//make sure downloainding completes to delete the folder afterwards
			while(downloadInitiator.isDownloading())
				Thread.sleep(1000);
			
			List<File> files = Arrays.asList(new File(applicationConfiguration.getDownloadDirectoryPath()).listFiles());
			boolean foundFile = false;
			for(File file : files)
				if(file.getName().contains("test1Mb"))
					foundFile = true;
			
			Assert.assertTrue(foundFile);
		}
		catch(Exception ex){
			
		}
		finally{
			FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
		}
	}
}
