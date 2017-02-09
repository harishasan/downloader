package com.haris.downloader.scheduler.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.app.FtpDownloader;
import com.haris.downloader.app.HttpDownloader;
import com.haris.downloader.app.test.utils.FileUtils;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.scheduler.RunnableDownloader;

public class RunnableDownloaderTest {

	@Test(expected=IllegalStateException.class)
	public void getWithNullDownloader(){
		RunnableDownloader.getInstance(null, null);
	}
	
	@Test
	public void getInstance(){
		RunnableDownloader runnableDownloader = RunnableDownloader.getInstance(FtpDownloader.getInstance(), null);
		Assert.assertNotNull(runnableDownloader);
	}
	
	@Test
	public void successfulDownload() throws IOException, URISyntaxException{
		
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);

		try{
			String url = "http://speedtest.ftp.otenet.gr/files/test1Mb.db";
			DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
			RunnableDownloader runnableDownloader = RunnableDownloader.getInstance(HttpDownloader.getInstance(), parameters);
			runnableDownloader.run();
			Assert.assertTrue(null != runnableDownloader.getDownloadResult());
			Assert.assertTrue(null != runnableDownloader.getDownloadResult().getFilePath());
			Assert.assertTrue(new File(runnableDownloader.getDownloadResult().getFilePath()).exists());
		}
		finally{
			FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
		}
	}
}

