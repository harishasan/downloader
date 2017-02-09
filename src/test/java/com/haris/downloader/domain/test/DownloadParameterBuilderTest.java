package com.haris.downloader.domain.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;
import com.haris.downloader.domain.DownloadParameterBuilder;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.utils.UrlUtils;

public class DownloadParameterBuilderTest {

	@Test
	public void build() throws URISyntaxException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		ApplicationConfiguration applicationConfiguration;
		try {
			applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
			String url = "google.com/file";
			DownloadParameters paramters = DownloadParameterBuilder.build(url, applicationConfiguration);
			Assert.assertEquals(applicationConfiguration.getDownloadDirectoryPath() + "/" + UrlUtils.extractFileNameFromUrl(url), paramters.getFilePath());
			Assert.assertEquals(applicationConfiguration.getFtpPassword(), paramters.getFtpPassword());
			Assert.assertEquals(applicationConfiguration.getFtpUsername(), paramters.getFtpUsername());
			Assert.assertEquals(applicationConfiguration.getSftpPassword(), paramters.getSftpPassword());
			Assert.assertEquals(applicationConfiguration.getSftpUsername(), paramters.getSftpUsername());
			Assert.assertEquals(paramters.getUrl(), url);
			Assert.assertEquals(applicationConfiguration.getConnectionTimeoutValue(), Integer.valueOf(paramters.getConnectionTimeoutValue()));
		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test(expected=URISyntaxException.class)
	public void buildWithInvalidUrl() throws URISyntaxException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		ApplicationConfiguration applicationConfiguration;
		try {
			applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
			String url = "www.google.com/^file";
			DownloadParameterBuilder.build(url, applicationConfiguration);
		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
		
	}
}
