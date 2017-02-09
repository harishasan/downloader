package com.haris.downloader.domain.test;

import java.io.File;
import java.io.PrintWriter;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.domain.DownloadResult;

public class DownloadResultTest {

	@Test
	public void getDownloadResultWithFilePath(){
		DownloadResult result = DownloadResult.getInstance("somePath", null);
		Assert.assertEquals("somePath", result.getFilePath());
		Assert.assertNull(result.getException());
	}
	
	@Test
	public void getDownloadResultWithException(){
		DownloadResult result = DownloadResult.getInstance(null, new Exception("HelloWorld"));
		Assert.assertEquals("HelloWorld", result.getException().getMessage());
		Assert.assertNull(result.getFilePath());
	}
	
	@Test
	public void getSuccessfulDownloadResult(){
		
		String fileName = UUID.randomUUID().toString() + ".txt";
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		PrintWriter writer;
		try {
			//create a temp file
			writer = new PrintWriter(filePath, "UTF-8");
			writer.close();
			
			DownloadResult result = DownloadResult.getInstance(filePath, null);
			Assert.assertTrue(result.isSuccess());
		} 
		catch (Exception exception){
			exception.printStackTrace();
		}
		finally{
			new File(filePath).delete();
		}
	}
	

	@Test
	public void getFailedDownloadResultWithNoFile(){
		String fileName = UUID.randomUUID().toString() + ".txt";
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		DownloadResult result = DownloadResult.getInstance(filePath, null);
		Assert.assertFalse(result.isSuccess());
	}
	
	@Test
	public void getFailedDownloadResultWithException(){
		String fileName = UUID.randomUUID().toString() + ".txt";
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		PrintWriter writer;
		try {
			//create a temp file
			writer = new PrintWriter(filePath, "UTF-8");
			writer.close();
			
			DownloadResult result = DownloadResult.getInstance(filePath, new Exception());
			Assert.assertFalse(result.isSuccess());
		} 
		catch (Exception exception){
			exception.printStackTrace();
		}
		finally{
			new File(filePath).delete();
		}
		
	}
}
