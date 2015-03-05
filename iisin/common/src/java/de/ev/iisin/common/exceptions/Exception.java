/*
 * Copyright (c) 2008 Kemal Dönmez. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *          
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package de.ev.iisin.common.exceptions;

/**
 * @author Kemal Dönmez
 *
 */
public class Exception extends java.lang.Exception {

	/**
	 * Erzeugt am 25.12.2008
	 */
	private static final long serialVersionUID = 3828206135019482364L;

	
	private final ErrorTemplate error;

	/**
	 * Constructor for Exception.
	 * @param error The error code which identifies the error.
	 * @param detail The details used to describe the error or null.
	 */
	protected Exception(ErrorTemplate error, String detail) {
		super(getMessage(error, detail, null));
		this.error = error;
	}

	/**
	 * Constructor for Exception.
	 * @param error The error code which identifies the error.
	 * @param detail The details used to describe the error or null.
	 * @param cause The nested exception which caused the error or null.
	 */
	protected Exception(ErrorTemplate error, String detail, Throwable cause) {
		super(getMessage(error, detail, cause), cause);
		this.error = error;
	}

	/**
	 * Constructor for VAB_Exception.
	 * @param msg The message which describes the problem.
	 * @param error The error code which identifies the error.
	 * @param cause The nested exception which caused the error or null.
	 */
	protected Exception(String msg, ErrorTemplate error, Throwable cause) {
		super(getMessage(msg, error, cause), cause);
		this.error = error;
	}

	/**
	 * Returns the occurred error
	 * 
	 * @return the occurred error
	 */
	public ErrorTemplate getError() {
		return error;
	}

	private static String getMessage(String msg, ErrorTemplate error, Throwable cause) {
		StringBuffer buffer = new StringBuffer(1000);
		buffer.append(msg);
		if (error != null) {
			buffer.append(" (Error ");
			buffer.append(error.getId());
			buffer.append(")");
		}
		if (cause != null) {
			buffer.append(" ");
			buffer.append(cause.getMessage());
		}
		return buffer.toString();
	}

	
	
	private static String getMessage(ErrorTemplate error, Object detail, Throwable cause) {
		StringBuffer buffer = new StringBuffer(1000);
		if (error != null) {
			buffer.append(error.getName());
			buffer.append(" (Error ");
			buffer.append(error.getId());
			buffer.append(")");
		} else {
			buffer.append("Unknown error.");
		}
		if (detail != null) {
			buffer.append(" (");
			buffer.append(detail);
			buffer.append(")");
		}
		if (cause != null) {
			buffer.append(" ");
			buffer.append(cause.getMessage());
		}
		return buffer.toString();
	}

}
