package com.haris.downloader.scheduler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.haris.downloader.domain.BaseDownloader;
import com.haris.downloader.domain.Downloader;
import com.haris.downloader.exceptions.ProtocolNotSupportedException;

/**
 * Initializes and returns corresponding download implementation based on passed Url and available download implementations. 
 */
public class DownloaderFactory {
	
	/**
	 * A Download implementation is expected to have a static method with this name returning 
	 * list of protocols it supports.  
	 */
	
	private static final String METHOD_GET_SCHEMES = "getSupportedSchemes";
	/**
	 * A download implementation is expected to have a static method with this name returning 
	 * new instance of its type.   
	 */
	private static final String METHOD_GET_INSTANCE = "getInstance";
	
	/**
	 * @param url absolute path to resource to download
	 * @param availableDownlaoders list of available download implementation
	 * @return instance of corresponding download implementation
	 * @throws ProtocolNotSupportedException in case url scheme do not match any available schemes
	 * @throws URISyntaxException in case passed url is invalid
	 * @throws NoSuchMethodException if download implementation do not have required static methods
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Downloader getDownloader(String url, final List<Class<? extends BaseDownloader>> availableDownlaoders) 
			throws ProtocolNotSupportedException, URISyntaxException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		URI uri = null;
		uri = new URI(url);			
		
		for(Class<? extends BaseDownloader> availableDownloader: availableDownlaoders){
			
			//find static method to get supported schemes
			Method method = availableDownloader.getDeclaredMethod(METHOD_GET_SCHEMES);
			if(method == null)
				continue;
			
			@SuppressWarnings("unchecked")
			List<String> supportedSchemes = (List<String>)method.invoke(null);
			if(supportedSchemes == null)
				continue;
			
			//check if any available scheme matches with url scheme
			if(supportedSchemes.contains(uri.getScheme())){
				
				//create instance of matching downloader and return it
				method = availableDownloader.getDeclaredMethod(METHOD_GET_INSTANCE);
				if(method == null)
					continue;
				
				return (Downloader) method.invoke(null);
			}
		}
		
		throw new ProtocolNotSupportedException("No matching protocol found");
	}
}
