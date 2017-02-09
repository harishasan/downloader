package com.haris.downloader.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import javax.naming.AuthenticationException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;
import com.haris.downloader.utils.UrlUtils;

/**
 * Downloads FTP resources
 */
public class FtpDownloader extends BaseDownloader{

	private final static String SUPPORTED_SCHEME_FTP = "ftp";
	
	/**
	 * @return list of supported protocols
	 */
	public static List<String> getSupportedSchemes() {
		return Arrays.asList(new String[]{SUPPORTED_SCHEME_FTP});
	}
	
	/**
	 * @return new instance of this class
	 */
	public static FtpDownloader getInstance() {
		return new FtpDownloader();
	}
	
	/** 
	 * Implementation of Download interface that handles a Ftp download.
	 */
	@Override
	public DownloadResult download(final DownloadParameters parameters){
		
		boolean isSuccess = true;
		FTPClient ftp = new FTPClient();
		String diskFilePath = null;
		
		try {
			String serverName = UrlUtils.extractServerNameFromUrl(parameters.getUrl());
			ftp.connect(serverName);
		    //kill if server stop responding, hard coding for now, can be made configurable. 
		    ftp.setDataTimeout(parameters.getConnectionTimeoutValue());
		    //make sure connection is ok
		    int reply = ftp.getReplyCode();		  
		    if (!FTPReply.isPositiveCompletion(reply))
		    	throw new ConnectException("Exception in connecting to FTP Server");
		  
		    ftp.login(parameters.getFtpUsername(), parameters.getFtpPassword());
		    reply = ftp.getReplyCode();		  
		    if (!FTPReply.isPositiveCompletion(reply))
		    	throw new AuthenticationException("Exception in logging into FTP Server");
		  
		    String filePathOnServer = UrlUtils.extractServerFilePathFromUrl(parameters.getUrl());
		    diskFilePath = com.haris.downloader.utils.FileUtils.getUniqueFilePath(parameters.getFilePath());
		    //ensure parent directory exists
		    new File(new File(diskFilePath).getParent()).mkdirs();
		    FileOutputStream fos =  new FileOutputStream(diskFilePath);
		    ftp.retrieveFile(filePathOnServer, fos);
		    //make sure file is present on the server
		    reply = ftp.getReplyCode();
		    if(!FTPReply.isPositiveCompletion(reply))
		    	throw new FileNotFoundException("File not found on server");
		  
		    fos.flush();
		    fos.close();
		    ftp.logout();
		    printSuccess(parameters, diskFilePath);
		    return DownloadResult.getInstance(diskFilePath, null);
		} 
		catch(Exception exception) {
			printError(exception, parameters);
			isSuccess = false;
			return DownloadResult.getInstance(null, exception);
		}
		finally {
		  if(ftp.isConnected())
		    try {
		      ftp.disconnect();
		    } 
		    catch(IOException io) {
		    }
		  if(!isSuccess)
			  cleanup(diskFilePath);
		}
	}
}
