package com.haris.downloader.app.test;

import java.io.IOException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.ApplicationConfiguration;

public class ApplicationConfigurationTest {

	@Test
	public void loadApplicationConfiguration() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application.properties";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		Assert.assertEquals("/Users/haris/Desktop/downloads", applicationConfiguration.getDownloadDirectoryPath());
		Assert.assertEquals("", applicationConfiguration.getFtpPassword());
		Assert.assertEquals("anonymous", applicationConfiguration.getFtpUsername());
		Assert.assertEquals("password", applicationConfiguration.getSftpPassword());
		Assert.assertEquals("demo", applicationConfiguration.getSftpUsername());
		Assert.assertEquals(new Integer(2), applicationConfiguration.getConcurrentDownlaodLimit());
		Assert.assertEquals(new Integer(10000), applicationConfiguration.getConnectionTimeoutValue());
	}
	
	@Test(expected=IOException.class)
	public void loadApplicationConfigurationWithInvalidFilePath() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/" + UUID.randomUUID();
		ApplicationConfiguration.getInstance(configFilePath);
	}
	
	@Test(expected=NullPointerException.class)
	public void loadApplicationConfigurationWithNullFilePath() throws IOException{
		ApplicationConfiguration.getInstance(null);
	}
	
	@Test
	public void loadApplicationConfigurationWithMissingPropertyValues() throws IOException{
		String configFilePath = System.getProperty("user.dir") + "/test-config/application_2.properties";
		ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance(configFilePath);
		Assert.assertEquals("", applicationConfiguration.getDownloadDirectoryPath());
		Assert.assertNull(applicationConfiguration.getFtpPassword());
		Assert.assertNull(applicationConfiguration.getFtpUsername());
		Assert.assertNull(applicationConfiguration.getSftpPassword());
		Assert.assertNull(applicationConfiguration.getSftpUsername());
		Assert.assertEquals(new Integer(2), applicationConfiguration.getConcurrentDownlaodLimit());
		Assert.assertEquals(new Integer(10000), applicationConfiguration.getConnectionTimeoutValue());
	}
}
