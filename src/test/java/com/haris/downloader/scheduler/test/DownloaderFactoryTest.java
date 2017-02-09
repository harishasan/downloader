package com.haris.downloader.scheduler.test;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.app.FtpDownloader;
import com.haris.downloader.app.HttpDownloader;
import com.haris.downloader.app.SftpDownloader;
import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.Downloader;
import com.haris.downloader.exceptions.ProtocolNotSupportedException;
import com.haris.downloader.scheduler.DownloaderFactory;


public class DownloaderFactoryTest {

	@Test(expected=URISyntaxException.class)
	public void getDownloaderWithInvalidUri() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
		InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		DownloaderFactory.getDownloader("www.google.com/!@#$%^abc", availableDownloaders);
	}
	
	@Test(expected=NullPointerException.class)
	public void getDownloaderWithNullDownloaders() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		DownloaderFactory.getDownloader("www.google.com/abc", null);
	}
	
	@Test
	public void getFtpDownloader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		availableDownloaders.add(FtpDownloader.class);
		availableDownloaders.add(SftpDownloader.class);
		Downloader downloader = DownloaderFactory.getDownloader("ftp://speedtest.tele2.net/10MB.zip", availableDownloaders);
		Assert.assertEquals(FtpDownloader.class, downloader.getClass());
	}
	
	@Test
	public void getHttpDownloader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		availableDownloaders.add(FtpDownloader.class);
		availableDownloaders.add(SftpDownloader.class);
		Downloader downloader = DownloaderFactory.getDownloader("http://speedtest.ftp.otenet.gr/files/test1Mb.db", availableDownloaders);
		Assert.assertEquals(HttpDownloader.class, downloader.getClass());
	}
	
	@Test
	public void getSftpDownloader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(HttpDownloader.class);
		availableDownloaders.add(FtpDownloader.class);
		availableDownloaders.add(SftpDownloader.class);
		Downloader downloader = DownloaderFactory.getDownloader("sftp://test.rebex.net/readme.txt", availableDownloaders);
		Assert.assertEquals(SftpDownloader.class, downloader.getClass());
	}
	
	@Test(expected=ProtocolNotSupportedException.class)
	public void getDownloaderWithNoMatchingDownloader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(SftpDownloader.class);
		DownloaderFactory.getDownloader("ssh://speedtest.ftp.otenet.gr/files/test1Mb.db", availableDownloaders);
	}
	
	@Test(expected=NullPointerException.class)
	public void getDownloaderWithNullUrl() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(SftpDownloader.class);
		DownloaderFactory.getDownloader(null, availableDownloaders);
	}
	
	@Test(expected=NoSuchMethodException.class)
	public void getDownloaderWithoutGetSchemesMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{
		
		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(DownloaderWithoutGetSchemes.class);
		DownloaderFactory.getDownloader("http://speedtest.ftp.otenet.gr/files/test1Mb.db", availableDownloaders);
	}
	
	@Test(expected=NoSuchMethodException.class)
	public void getDownloaderWithoutGetInstanceMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, ProtocolNotSupportedException, URISyntaxException{

		final List<Class<? extends BaseDownloader>> availableDownloaders = new ArrayList<>();
		availableDownloaders.add(DownloaderWithoutGetInstance.class);
		DownloaderFactory.getDownloader("http://speedtest.ftp.otenet.gr/files/test1Mb.db", availableDownloaders);
	}
}
