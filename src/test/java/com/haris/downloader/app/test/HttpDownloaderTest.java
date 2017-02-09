package com.haris.downloader.app.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.app.HttpDownloader;
import com.haris.downloader.app.test.utils.FileUtils;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;

public class HttpDownloaderTest {
	@Test
	public void successfulDownload() throws URISyntaxException, IOException{
		HttpDownloader httpDownloader = HttpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "http://speedtest.ftp.otenet.gr/files/test1Mb.db";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = httpDownloader.download(parameters);
		Assert.assertTrue(downloadResult.isSuccess());
		FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
	}
	
	@Test
	public void downloadWithInvalidUrl() throws URISyntaxException, IOException{
		HttpDownloader httpDownloader = HttpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "http://speedtest.ftp.otenet.gr/files/invalid.db";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = httpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(FileNotFoundException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void downloadWithInvalidServerName() throws URISyntaxException, IOException{
		HttpDownloader httpDownloader = HttpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "http://xxxxxxx.ftp.otenet.gr/files/test1Mb.db";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		DownloadResult downloadResult = httpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(UnknownHostException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void verifySupportedSchemes(){
		Assert.assertTrue(HttpDownloader.getSupportedSchemes().contains("http"));
		Assert.assertTrue(HttpDownloader.getSupportedSchemes().contains("https"));
	}
	
	@Test
	public void verifyNotSupportedSchemes(){
		Assert.assertFalse(HttpDownloader.getSupportedSchemes().contains("ftp"));
	}
}
