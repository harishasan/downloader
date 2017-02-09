package com.haris.downloader.exceptions;

/**
 * Thrown when no matching downloader is found against a Url
 */
public class ProtocolNotSupportedException extends Exception{

	private static final long serialVersionUID = -2595728977039081516L;
	
	public ProtocolNotSupportedException(String message) {
        super(message);
    }
}
