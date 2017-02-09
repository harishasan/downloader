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
import com.haris.downloader.scheduler.DownloadExecutor;

public class DownloadExecutorTest {

	@Test(expected=NullPointerException.class)
	public void initializeWithNullApplicationConfiguration() throws IOException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		List<String> jobs = Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"});
		DownloadExecutor.getInstance(null, jobs, availableDownloaders);
	}
	
	@Test(expected=NullPointerException.class)
	public void initializeWithNullJobs() throws IOException{
		
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		DownloadExecutor.getInstance(applicationConfiguration, null, availableDownloaders);
	}
	
	@Test
	public void initializeWithNullDownloaders() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		List<String> jobs = Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"});
		DownloadExecutor.getInstance(applicationConfiguration, jobs, null);
	}
	
	@Test
	public void successfulExecution() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		final ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		
		try{
			final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
			availableDownloaders.add(HttpDownloader.class);
			List<String> jobs = Arrays.asList(new String[]{"http://speedtest.ftp.otenet.gr/files/test1Mb.db"});
			DownloadExecutor downloadExecutor = DownloadExecutor.getInstance(applicationConfiguration, jobs, availableDownloaders);
			downloadExecutor.run();
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
