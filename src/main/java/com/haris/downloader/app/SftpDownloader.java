package com.haris.downloader.app;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.DownloadParameters;
import com.haris.downloader.domain.DownloadResult;
import com.haris.downloader.utils.FileUtils;
import com.haris.downloader.utils.UrlUtils;

public class SftpDownloader extends BaseDownloader{

	private final static String SUPPORTED_SCHEME_SFTP = "sftp";
	
	/**
	 * @return list of supported protocols
	 */
	public static List<String> getSupportedSchemes() {
		return Arrays.asList(new String[]{SUPPORTED_SCHEME_SFTP});
	}
	
	/**
	 * @return new instance of this class
	 */
	public static SftpDownloader getInstance() {
		return new SftpDownloader();
	}
	
	
	/** 
	 * Implementation of Download interface that handles Sftp download.
	 */
	@Override
	public DownloadResult download(DownloadParameters parameters) {
		
		StandardFileSystemManager manager = new StandardFileSystemManager();
		String filePath = null;
		
		boolean isSuccess = true;
		try {
			String serverAddress = UrlUtils.extractServerNameFromUrl(parameters.getUrl());
			String serverFilePath = UrlUtils.extractServerFilePathFromUrl(parameters.getUrl());
			String username = parameters.getSftpUsername();
			String password = parameters.getSftpPassword();

			manager.init();
			FileSystemOptions fileSystemOptions = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking( fileSystemOptions, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, false);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, parameters.getConnectionTimeoutValue());
			//Create Sftp uri
			String sftpUri = String.format("sftp://%s:%s@%s/%s", username, password, serverAddress, serverFilePath);
			// Create local file object
			filePath = FileUtils.getUniqueFilePath(parameters.getFilePath());
			File file = new File(filePath);
			FileObject localFile = manager.resolveFile(file.getAbsolutePath());
			// Create remote file object
			FileObject remoteFile = manager.resolveFile(sftpUri, fileSystemOptions);
				
			localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
			printSuccess(parameters, filePath);
			return DownloadResult.getInstance(filePath, null);
		} 
		catch (URISyntaxException | org.apache.commons.vfs2.FileSystemException exception) {
			isSuccess = false;
			printError(exception, parameters);
			return DownloadResult.getInstance(null, exception);
		}
		finally {
			if(!isSuccess)
				cleanup(filePath);
			
			manager.close();
		}

	}
}
