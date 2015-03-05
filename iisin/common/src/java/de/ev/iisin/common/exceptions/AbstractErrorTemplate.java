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

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * @author Kemal Dönmez
 *
 */
public abstract class AbstractErrorTemplate implements Serializable {
	static final ResourceBundle RESOURCE_MAP = ResourceBundle.getBundle("de.ev.iisin.common.exceptions.resources/Error");

	private final int id;
	private transient final String name;

	protected AbstractErrorTemplate(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the errornumber
	 * @return the errornumber
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Gets the name of the error
	 * @return errorname
	 */
	public final String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static final String getResourceName(String key){
		key = key.toLowerCase();
		return RESOURCE_MAP.getString(key);
	}
	
	protected abstract Object readResolve() throws InvalidObjectException ;


}
