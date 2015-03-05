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
public interface ResourceConverter {
	  /**
     * Checks and answers whether this converter supports the given type,
     * i.e. it can convert a String to the given type.
     *
     * @param type    the type to convert to
     * @return {@code true} if this converter can convert to the given type,
     *    {@code false} otherwise
     */
    boolean supportsType(Class<?> type);


    /**
     * Converts the given String value to the specified return type.
     * Implementations will typically just use the String value and
     * convert it to an instance of the return type. The key is useful
     * for conversion failure messages. The resource map can be used
     * to access the map's class loader (e.g. for loading resources) and
     * to resolve relative paths. Also, the resource map can be used
     * to request conversion configuration data, such as
     * a <code>Format</code> used to parse Strings.<p>
     *
     * The string conversion is invoked by the resource map object lookup
     * in {@link ResourceMap#getObject(String, Class)},
     * if a resource value is a String and requires conversion. For example
     * if a boolean is requested, but the ResourceBundle returns the
     * String {@code true}.<p>
     *
     * @param key         the resource key associated with the String value
     * @param value       the String value found for the resource key
     * @param returnType  the type of the converted object
     * @param r           the resource map that provides the class loader,
     *                    that resolves relative paths and potentially has
     *                    conversion configuration information
     * @return the converted value of type <code>returnType</code>
     *
     * @throws ResourceConversionException if the converter fails to convert
     *    the string to the specified return type, for example in case
     *    of a parse error
     *
     * @see ResourceMap#getObject(String, Class)
     */
    Object convert(String key, String value, Class<?> returnType, ResourceMap r);


    /**
     * A runtime exception used to describe string conversion failures,
     * e.g. a parse error.
     */
    public static final class ResourceConversionException extends RuntimeException {

        // Instance Creation ******************************************************

        /**
		 * Created at 02.11.2008
		 */
		private static final long serialVersionUID = 6858293136395520516L;


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

}
