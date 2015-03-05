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

package com.jgoodies.uif_lite.application;

/**
 * @author Kemal Dönmez
 *
 */
public class ResourceConversionException extends RuntimeException {

    /**
	 * Created at 02.11.2008
	 */
	private static final long serialVersionUID = 3385797028669206820L;


	// Instance Creation ******************************************************

    /**
     * Constructs a new exception instance with the specified detail message.
     * The cause is not initialized.
     *
     * @param message  the detail message which is saved for later
     *        retrieval by the {@link #getMessage()} method.
     * @param key     the resource bundle key used to lookup the value
     *        that could not be converted
     * @param value   the value that couldn't be converted
     */
    public ResourceConversionException(String message, String key, String value) {
        this(message, key, value, null);
    }


    /**
     * Constructs a new exception instance with the specified detail message
     * and cause.
     *
     * @param message  the detail message which is saved for later
     *        retrieval by the {@link #getMessage()} method.
     * @param key      the resource bundle key used to lookup the value
     *        that could not be converted
     * @param value    the value that couldn't be converted
     * @param cause    the cause which is saved for later retrieval by the
     *        {@link #getCause()} method. A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.
     */
    public ResourceConversionException(String message, String key, String value, Throwable cause) {
        super(message + "\nKey=" + key + "\nValue=" + value, cause);
    }

}
