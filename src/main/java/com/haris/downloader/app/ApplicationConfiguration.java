package com.haris.downloader.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides access to configuration information. 
 */
public class ApplicationConfiguration {
	
	public static final int DEFAULT_CONCURRENT_DOWNLOAD_LIMIT = 2;
	public static final int DEFAULT_CONNECTION_TIMEOUT_VALUE = 10000;
	
	/**
	 * Name of the property in configuration file whose value
	 * points to local disk path for storing downloads  
	 */
	public static final String PROPERTY_DOWNLOAD_PATH = "download_path";
	
	/**
	 * Name of the property in configuration file whose value
	 * describes allowed number of concurrent downloads
	 */
	public static final String PROPERTY_CONCURRENT_DOWNLOAD_LIMIT = "concurrent_download_limit";
	
	/**
	 * Name of the property in configuration file whose value
	 * describes Ftp username
	 */
	public static final String PROPERTY_FTP_USERNAME = "ftp_username";
	
	/**
	 * Name of the property in configuration file whose value
	 * describes Ftp password
	 */
	public static final String PROPERTY_FTP_PASSWORD = "ftp_password";
	
	/**
	 * Name of the property in configuration file whose value
	 * points to  timeout in milliseconds to use when reading from the data connection.  
	 */
	public static final String PROPERTY_CONNECTION_TIMEOUT_VALUE = "connection_timeout_value";
	
	/**
	 * Name of the property in configuration file whose value
	 * points to sftp username  
	 */
	public static final String PROPERTY_SFTP_USERNAME = "sftp_username";
	
	/**
	 * Name of the property in configuration file whose value
	 * points to sftp password  
	 */
	public static final String PROPERTY_SFTP_PASSWORD = "sftp_password";
	
	private final Properties properties;
	
	private ApplicationConfiguration(){
		this.properties = new Properties();
	}
	
	/**
	 
	 * @throws IOException throws exception if it fails to read the file
	 */
	
	/**
	 * creates application configuration instance and fills it with values defined in configuration file
	 * @param path absolute path of configuration file 
	 * @return instance of application configuration containing
	 * @throws IOException in case of invalid configuration file path
	 */
	public static ApplicationConfiguration getInstance(String path) throws IOException{
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
			applicationConfiguration.properties.load(inputStream);
			return applicationConfiguration;
		} 
		catch (IOException exception) {
			throw new IOException("Error while loading application.properties configuration file. Please make sure it is present in config folder.");
		} 
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} 
				catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * @return value of download path property 
	 */
	public String getDownloadDirectoryPath(){
		return properties.getProperty(PROPERTY_DOWNLOAD_PATH);
	}
	
	/**
	 * @return value of ftp username property 
	 */
	public String getFtpUsername(){
		return properties.getProperty(PROPERTY_FTP_USERNAME);
	}
	
	/**
	 * @return value of ftp password property 
	 */
	public String getFtpPassword(){
		return properties.getProperty(PROPERTY_FTP_PASSWORD);
	}
	
	/**
	 * @return value of sftp username property 
	 */
	public String getSftpUsername(){
		return properties.getProperty(PROPERTY_SFTP_USERNAME);
	}
	
	/**
	 * @return value of sftp password property 
	 */
	public String getSftpPassword(){
		return properties.getProperty(PROPERTY_SFTP_PASSWORD);
	}
	
	
	/**
	 * @return value of concurrent downloads property or default (2) if invalid or absent
	 */
	public Integer getConcurrentDownlaodLimit(){
		if(properties.getProperty(PROPERTY_CONCURRENT_DOWNLOAD_LIMIT) == null)
			return DEFAULT_CONCURRENT_DOWNLOAD_LIMIT;
		
		try{
			return Integer.parseInt(properties.getProperty(PROPERTY_CONCURRENT_DOWNLOAD_LIMIT));
		}
		catch(NumberFormatException exception){
			exception.printStackTrace();
			return DEFAULT_CONCURRENT_DOWNLOAD_LIMIT;
		}
	}
	
	/**
	 * @return value of timeout in milliseconds to use when reading from the data connection.
	 */
	public Integer getConnectionTimeoutValue(){
		if(properties.getProperty(PROPERTY_CONNECTION_TIMEOUT_VALUE) == null)
			return DEFAULT_CONNECTION_TIMEOUT_VALUE;
		
		try{
			return Integer.parseInt(properties.getProperty(PROPERTY_CONNECTION_TIMEOUT_VALUE));
		}
		catch(NumberFormatException exception){
			exception.printStackTrace();
			return DEFAULT_CONNECTION_TIMEOUT_VALUE;
		}
	}
}
