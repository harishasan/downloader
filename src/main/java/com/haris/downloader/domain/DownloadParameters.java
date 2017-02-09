package com.haris.downloader.domain;

/**
 * Holds various configuration properties that may be used during download. 
 */
public class DownloadParameters {
	
	private String url;
	private String filePath;
	private String ftpUsername;
	private String ftpPassword;
	private int connectionTimeoutValue;
	private String sftpUsername;
	private String sftpPassword;
	
	public String getSftpUsername() {
		return sftpUsername;
	}

	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public int getConnectionTimeoutValue() {
		return connectionTimeoutValue;
	}
	
	public void setConnectionTimeoutValue(int connectionTimeoutValue) {
		this.connectionTimeoutValue = connectionTimeoutValue;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
}
