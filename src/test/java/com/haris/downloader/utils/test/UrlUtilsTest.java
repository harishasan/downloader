package com.haris.downloader.utils.test;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.utils.UrlUtils;

public class UrlUtilsTest {
	
	@Test
	public void extractFileNameFromUrlRootFile() throws URISyntaxException {
		String returnedFileName = UrlUtils.extractFileNameFromUrl("http://google.com/hello.txt");
		Assert.assertEquals("hello.txt", returnedFileName);
	}
	
	@Test
	public void extractFileNameFromUrlSubDirFile() throws URISyntaxException {
		String result = UrlUtils.extractFileNameFromUrl("http://google.com/hello/world.txt");
		Assert.assertEquals("world.txt", result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extractFileNameFromUrlPointingToNoFile() throws URISyntaxException {
		UrlUtils.extractFileNameFromUrl("http://google.com");
	}
	
	@Test(expected=NullPointerException.class)
	public void extractFileNameFromNullUrl() throws URISyntaxException {
		UrlUtils.extractFileNameFromUrl(null);
	}
	
	@Test(expected=URISyntaxException.class)
	public void extractFileNameFromInvalidUrl() throws URISyntaxException {
		UrlUtils.extractServerFilePathFromUrl("www.google.com/@#$%^");
	}
	
	@Test
	public void extractServerNameUrl() throws URISyntaxException {
		String result = UrlUtils.extractServerNameFromUrl("http://google.com");
		Assert.assertEquals("google.com", result);
	}
	
	@Test(expected=NullPointerException.class)
	public void extractServerNameFromNullUrl() throws URISyntaxException {
		UrlUtils.extractServerNameFromUrl(null);
	}
	
	@Test(expected=URISyntaxException.class)
	public void extractServerNameFromInvalidUrl() throws URISyntaxException {
		UrlUtils.extractServerFilePathFromUrl("www.google.com/@#$%^");
	}
	
	@Test
	public void extractServerFilePathFromUrlRootFile() throws URISyntaxException {
		String result = UrlUtils.extractServerFilePathFromUrl("http://google.com/hello");
		Assert.assertEquals("/hello", result);
	}
	
	@Test
	public void extractServerFilePathFromUrlSubDirFile() throws URISyntaxException {
		String result = UrlUtils.extractServerFilePathFromUrl("http://google.com/hello/world");
		Assert.assertEquals("/hello/world", result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extractFilePathFromUrlPointingToNoFile() throws URISyntaxException {
		UrlUtils.extractServerFilePathFromUrl("http://google.com/");
	}
	
	@Test(expected=NullPointerException.class)
	public void extractServerFilePathFromNullUrl() throws URISyntaxException {
		UrlUtils.extractServerFilePathFromUrl(null);
	}
	
	@Test(expected=URISyntaxException.class)
	public void extractServerFilePathFromInvalidUrl() throws URISyntaxException {
		UrlUtils.extractServerFilePathFromUrl("www.google.com/@#$%^");
	}
}
