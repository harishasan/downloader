package com.haris.downloader.app.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.vfs2.FileSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.app.SftpDownloader;
import com.haris.downloader.app.test.utils.FileUtils;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;

public class SftpDownloaderTest {
	
	@Before 
	public void disableCommonLogging() {
		//disable logs coming in because of Sftp via common-logging 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }
	
	@Test
	public void successfulDownload() throws URISyntaxException, IOException{
		SftpDownloader sftpDownloader = SftpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "sftp://test.rebex.net/readme.txt";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = sftpDownloader.download(parameters);
		Assert.assertTrue(downloadResult.isSuccess());
		FileUtils.deleteDirectory(new File(applicationConfiguration.getDownloadDirectoryPath()));
	}
	
	@Test
	public void downloadWithInvalidUrl() throws URISyntaxException, IOException{
		SftpDownloader sftpDownloader = SftpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "sftp://test.rebex.net/404.txt";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration); 
		DownloadResult downloadResult = sftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(FileSystemException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void downloadWithInvalidCredentials() throws URISyntaxException, IOException{
		SftpDownloader sftpDownloader = SftpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "sftp://test.rebex.net/readme.txt";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		parameters.setSftpUsername("Hi");
		parameters.setSftpPassword("Hi");
		DownloadResult downloadResult = sftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(FileSystemException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void downloadWithInvalidServerName() throws URISyntaxException, IOException{
		SftpDownloader sftpDownloader = SftpDownloader.getInstance();
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		String url = "sftp://xxxx.rebex.net/readme.txt";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		DownloadParameters parameters = DownloadParameterBuilder.build(url, applicationConfiguration);
		DownloadResult downloadResult = sftpDownloader.download(parameters);
		Assert.assertFalse(downloadResult.isSuccess());
		Assert.assertEquals(FileSystemException.class, downloadResult.getException().getClass());
	}
	
	@Test
	public void verifySupportedSchemes(){
		Assert.assertTrue(SftpDownloader.getSupportedSchemes().contains("sftp"));
	}
	
	@Test
	public void verifyNotSupportedSchemes(){
		Assert.assertFalse(SftpDownloader.getSupportedSchemes().contains("http"));
	}
}
