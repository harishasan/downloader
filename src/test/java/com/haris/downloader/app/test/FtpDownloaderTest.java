package com.haris.downloader.app.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.naming.AuthenticationException;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.app.FtpDownloader;
import com.haris.downloader.app.test.utils.FileUtils;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;

public class FtpDownloaderTest {

	@Test
	public void successfulDownload() throws URISyntaxException, IOException{
		
		FtpDownloader ftpDownloader = FtpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "ftp://speedtest.tele2.net/1MB.zip";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = ftpDownloader.download(parameters);
		Assert.assertTrue(downloadResult.isSuccess());
		FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
	}
	
	@Test
	public void downloadWithInvalidUrl() throws URISyntaxException, IOException{
		FtpDownloader ftpDownloader = FtpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "ftp://speedtest.tele2.net/invalid.zip";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = ftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(FileNotFoundException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void downloadWithInvalidCredentials() throws URISyntaxException, IOException{
		FtpDownloader ftpDownloader = FtpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "ftp://speedtest.tele2.net/invalid.zip";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		parameters.setFtpUsername("Hi");
		DownloadResult downloadResult = ftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(AuthenticationException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void downloadWithInvalidServerName() throws URISyntaxException, IOException{
		FtpDownloader ftpDownloader = FtpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "ftp://xxxxx.tele2.net/invalid.zip";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		DownloadResult downloadResult = ftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(UnknownHostException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void verifySupportedSchemes(){
		Assert.assertTrue(FtpDownloader.getSupportedSchemes().contains("ftp"));
	}
	
	@Test
	public void verifyNotSupportedSchemes(){
		Assert.assertFalse(FtpDownloader.getSupportedSchemes().contains("http"));
	}
}
