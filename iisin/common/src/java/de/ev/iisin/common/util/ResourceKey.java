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

package de.ev.iisin.common.util;

/**
 * @author Kemal Dönmez
 *
 */
public interface ResourceKey {
	/**
	 * The full key that can be found in the resource bundle referenced by {@link #getBundleName()}.
	 * <p>
	 * By convention, a enum type implementing this method is supposed to transform constant name to
	 * lowercase and replace underscores (<code>_</code>) by dots (<code>.</code>). Apart
	 * from that, a namespace prefix may be prepended. This transformation can be performed
	 * conveniently by {@link ResourceUtil#constantToBundleKey(String, String)}.
	 * <p>
	 * Example: The enum constant name <code>NAME_REQUIRED</code> might be transformed to the
	 * bundle key <code>error.validation.name.required</code> where <code>error.validation</code>
	 * is a namespace prefix.
	 * </p>
	 */
	String getBundleKey();

	/**
	 * @return The fully qualified name of the resource bundle in which this key can be found.
	 */
	String getBundleName();

}
