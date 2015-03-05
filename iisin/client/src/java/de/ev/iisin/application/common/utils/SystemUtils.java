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

package de.ev.iisin.application.common.utils;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.logging.Logger;

/**
 * @author Kemal Dönmez
 *
 */
public class SystemUtils {
	   // Requesting the OS and OS Version ***************************************

    /**
     * The <code>os.name</code> System Property. Operating system name.<p>
     *
     * Defaults to {@code null} if the runtime does not have security
     * access to read this property or the property does not exist.
     */
    private static final String OS_NAME = getSystemProperty("os.name");

    /**
     * The <code>os.version</code> System Property. Operating system version.<p>
     *
     * Defaults to {@code null} if the runtime does not have security
     * access to read this property or the property does not exist.
     */
    private static final String OS_VERSION = getSystemProperty("os.version");

    /**
     * Is true if this is Linux.
     */
    public static final boolean IS_OS_LINUX = startsWith(OS_NAME, "Linux")
                                           || startsWith(OS_NAME, "LINUX");

    /**
     * Is true if this is the Mac OS.
     */
    public static final boolean IS_OS_MAC = startsWith(OS_NAME, "Mac OS");

    /**
     * Is true if this is the Windows OS.
     */
    public static final boolean IS_OS_WINDOWS = startsWith(OS_NAME, "Windows");

    /**
     * Is true if this is the Windows 2000 OS.
     */
    public static final boolean IS_OS_WINDOWS_2000 = startsWith(OS_NAME, "Windows 2000");

    /**
     * Is true if this is the Windows XP OS.
     */
    public static final boolean IS_OS_WINDOWS_XP  =
        IS_OS_WINDOWS && startsWith(OS_VERSION, "5.1");


    // Requesting the Java Version ********************************************

    /**
     * The <code>os.name</code> System Property. Operating system name.<p>
     *
     * Defaults to {@code null} if the runtime does not have security
     * access to read this property or the property does not exist.
     */
    private static final String JAVA_VERSION = getSystemProperty("java.version");

    /**
     * True if this is Java 5.x. We check for a prefix of 1.5.
     */
    public static final boolean IS_JAVA_5 =
        startsWith(JAVA_VERSION, "1.5");

    /**
     * True if this is Java 6. We check for a prefix of 1.6.
     */
    public static final boolean IS_JAVA_6 =
        startsWith(JAVA_VERSION, "1.6");

    /**
     * True if this is Java 6.x or later. Since we don't support Java 1.4,
     * we can check that it's neither 1.4 nor 1.5.
     */
    public static final boolean IS_JAVA_6_OR_LATER =
        !IS_JAVA_5;


    // User Interface Requests ************************************************

    /**
     * Is true if this environment's default toolkit reports a screen resolution
     * below 120 dpi.
     */
    public static final boolean IS_LOW_RES = isLowRes();


    private SystemUtils() {
        // Override default constructor; prevents instantiation.
    }


    /**
     * Tries to look up the System property for the given key.
     * In untrusted environments this may throw a SecurityException.
     * In this case we catch the exception and answer an empty string.
     *
     * @param key   the name of the system property
     * @return the system property's String value, or {@code null} if there's
     *     no such value, or an empty String when
     *     a SecurityException has been caught
     */
    private static String getSystemProperty(String key) {
        try {
            return System.getProperty(key);
        } catch (SecurityException e) {
            Logger.getLogger(SystemUtils.class.getName()).warning(
                  "Can't access the System property " + key + ".");
            return "";
        }
    }


    private static boolean startsWith(String str, String prefix) {
        return str != null && str.startsWith(prefix);
    }


    private static boolean isLowRes() {
        try {
            return Toolkit.getDefaultToolkit().getScreenResolution() < 120;
        } catch (HeadlessException e) {
            return true;
        }
    }

}
