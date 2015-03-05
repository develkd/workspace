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

package de.ev.iisin.common.rmi;

import java.io.Serializable;

/**
 * @author Kemal Dönmez
 * 
 */
public class RmiResponse implements Serializable {

	/**
	 * Erzeugt am 02.01.2009
	 */
	private static final long serialVersionUID = 149278764640641888L;

	/**
	 * true/false
	 */
	private boolean returnCode;

	/**
	 * Object, dass als Ergebnis des RMI-Aufrufs geliefert wird null = kein
	 * Ergebnis
	 */
	private Object object;

	/**
	 * Message zu RMI-Aufruf bevorzugt Fehlermeldung
	 */
	private String message;

	/**
	 * 
	 */
	public RmiResponse() {
		returnCode = false;
		object = null;
		message = "";
	}

	/**
	 * @param returnCode
	 * @param object
	 * @param message
	 */
	public RmiResponse(boolean returnCode, Object object, String message) {
		this.returnCode = returnCode;
		this.object = object;
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the returnCode
	 */
	public boolean isReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode
	 *            the returnCode to set
	 */
	public void setReturnCode(boolean returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public String toString() {
		return "Response: Returncode=" + returnCode + " Message=" + message;
	}

}
