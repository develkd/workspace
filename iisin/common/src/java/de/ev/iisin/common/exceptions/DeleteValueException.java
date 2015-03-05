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

import de.ev.iisin.common.util.ResourceKey;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * This class
 * 
 * 
 * @author Kemal Dönmez
 * @version $Revision: $
 */
public class DeleteValueException extends Exception {

	/**
	 * Generated at 22.10.2008
	 */
	private static final long serialVersionUID = 6268158696609499908L;
	private String responseMessage;

	/**
	 * Constructor for UpdateValueException.
	 * 
	 * @param error
	 *            The error code which identifies the error.
	 * @param detail
	 *            The details used to describe the error or null.
	 */
	public DeleteValueException(String detail) {
		super(ErrorTemplate.VALUE_DELETE, detail);
	}

	public DeleteValueException() {
		this("");
	}

	public DeleteValueException(Object object, Object... arguments) {
		this("");
		ResourceKey key = (ResourceKey) object;
		responseMessage = ResourceUtil.getMessage(key, arguments);
	}

	public String getResponseMessage() {
		return responseMessage;
	}
}
