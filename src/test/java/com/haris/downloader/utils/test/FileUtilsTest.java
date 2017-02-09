package com.haris.downloader.utils.test;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.haris.downloader.utils.FileUtils;

public class FileUtilsTest {
	
	@Test
	public void getNewFilePathContainingOldFileName() {
		String fileName = UUID.randomUUID().toString();
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		String returnedFilePath = FileUtils.getUniqueFilePath(filePath);
		Assert.assertTrue(returnedFilePath.contains(fileName));
	}
	
	@Test
	public void getNewFilePathEndingWithSameExtension() {
		String fileName = UUID.randomUUID().toString() + ".txt";
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		String returnedFilePath = FileUtils.getUniqueFilePath(filePath);
		Assert.assertTrue(returnedFilePath.endsWith(".txt"));
	}
	
	@Test
	public void getNewFilePathWithAddedNumbering() {
		
		//create a temp file
		String fileName = UUID.randomUUID().toString() + ".txt";
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			writer.close();
			String returnedFilePath = FileUtils.getUniqueFilePath(filePath);
			Assert.assertTrue(returnedFilePath.contains("(1)"));
		} 
		catch (Exception exception){
			exception.printStackTrace();
		}
		finally{
			new File(filePath).delete();
		}
		
	}
	
	@Test
	public void getAllUniqueFilePathsIn500Attemps() {
		
		Set<String> set = new HashSet<>();
		String fileName = UUID.randomUUID().toString();
		String filePath = System.getProperty("user.dir") + "/" + fileName;
		boolean duplicate = false;
		
		for(int index = 0; index < 500; index ++){
			String returnedFilePath = FileUtils.getUniqueFilePath(filePath);
			
			if(set.contains(returnedFilePath))
				duplicate = true;
			
			set.add(returnedFilePath);
		}
		
		Assert.assertFalse(duplicate);
	}
	
	@Test(expected=NullPointerException.class)
	public void getUniqueFileNameFromNullPath() {
		FileUtils.getUniqueFilePath(null);
	}
}
