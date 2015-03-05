/*
 * Copyright (c) 2002-2005 JGoodies Karsten Lentzsch. All Rights Reserved.
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
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
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

import java.awt.Image;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * A light version of the JGoodies <code>ResourceUtils</code> class; lacks
 * some method to fetch resource types that are not used in Skeleton.
 * <p>
 * 
 * A singleton that provides access to localized application resources: strings,
 * icons, file paths, URLs, and text file contents. It therefore uses a
 * <code>ResourceBundle</code> to get localized paths to the resources which
 * it should fetch and answer.
 * <p>
 * 
 * Note: When loading image resources, you must often specify a
 * <code>ClassLoader</code>, e.g. when using JavaWebStart. The default
 * mechanism assumes, that all resources are loaded using the same class loader,
 * this <code>ResourceUtils</code> class has been loaded with. You can either
 * set a different default <code>ClassLoader</code>, or call #getIcons and
 * provide an individual <code>ClassLoader</code>.
 * 
 * @author Karsten Lentzsch
 */

public final class ResourceUtils {

	private static final ResourceUtils INSTANCE = new ResourceUtils();

	private ResourceBundle bundle;

	private ClassLoader defaultClassLoader = ResourceUtils.class
			.getClassLoader();

	private ResourceUtils() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	 /**
     * Laden der Resourcen ausserhabl der Client-Applikationsumgebung. 
     * Sollte benutzt werden, wenn eine Klasse aus dem Commen-Projekt auf Resourcen zugreifen muss.
     * 
     * @param clazz, Die Klassen, dessen Resourcen geladen werden sollen. Darf nicht null sein.
     *
     * @return die ResourceBundel der Klasse
     */
    public static ResourceBundle getResourceBundle(Class<?> clazz) {
    	return getResourceBundle(clazz, null);
    }

    public static ResourceBundle getResourceBundle(Class<?> clazz, Locale local) {
        ClassLoader loader = null;
        try {
            loader = Class.forName(clazz.getName()).getClassLoader();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lookupBundle(clazz.getPackage().getName()
                + ".resources.Package", null, loader);
    }

    /**
     * Loads a resource bundle by name, locale and class loader.
     * 
     * @param bundleName
     *            The base name of the resource bundle that should be looked up.
     * @param locale
     *            The target locale. If it is <code>null</code>, the default
     *            locale of the platform is taken (as returned by
     *            <code>Locale.getDefault()</code>.
     * @param loader
     *            The class loader with which the resource bundle should be
     *            looked up. If <code>null</code> the class loader that loaded
     *            the <code>ResourceManager</code> class will be used.
     * @return The resource bundle with the provided bundle name and locale.
     * @throws MissingResourceException
     *             If the resource bundle could not be found.
     */
    private static ResourceBundle lookupBundle(String bundleName, Locale locale,
            ClassLoader loader) {
        // this methode may be enhanced to include resource bundle caching
        ResourceBundle bundle = null;
        
        if (locale == null) locale = Locale.getDefault();
        
        if (loader != null) bundle = ResourceBundle.getBundle(bundleName,
                locale, loader);
        else bundle = ResourceBundle.getBundle(bundleName, locale);
        return bundle;
    }

	/**
	 * Sets the path to the <code>ResourceBundle</code> that will be used to
	 * fetch string resources and path resources.
	 */
	public static void setBundlePath(String path) {
		INSTANCE.bundle = ResourceBundle.getBundle(path);
	}

	public static ResourceBundle getBundlePath(Class<?> clazz) {
		return ResourceBundle.getBundle(getResourcePathFor(clazz) + "Package", Locale.getDefault());
	}

	public static ResourceBundle getBundlePath(Class<?> clazz, Locale local) {
		return ResourceBundle.getBundle(getResourcePathFor(clazz) + "Package",local);
	}

	private static String getResourcePathFor(Class<?> clazz) {
		return clazz.getPackage().getName() + ".resources.";
	}

	/**
	 * Retrieves and answers a <code>String</code> for the given key from the
	 * bundle.
	 */
	public static String getString(String key) {
		try {
			return INSTANCE.bundle.getString(key);
		} catch (MissingResourceException e) {
			System.err.println("Missing resource for key:" + key);
			return null;
		}
	}

	public static String getString(String key, Class<?> clazz) {
		try {
			return getBundlePath(clazz).getString(key);
		} catch (MissingResourceException e) {
			System.err.println("Missing resource for key:" + key);
			return null;
		}
	}

	/**
	 * Retrieves and answers an <code>ImageIcon</code> for the given key from
	 * the bundle.
	 */
	public static ImageIcon getIcon(String key) {
		String path = getString(key);
		return null == path ? null : readImageIcon(path);
	}

	/**
	 * Answers a <code>URL</code> for the given path using the default
	 * <code>ClassLoader</code>.
	 */
	private static URL getURL(String path) {
		return getURL(path, INSTANCE.defaultClassLoader);
	}

	private static String bundlePathToFilePath(String namespacePrefix) {
		String replacedString = namespacePrefix.replace(".", "/").toLowerCase();
		int index = replacedString.lastIndexOf('/');
		StringBuilder builder = new StringBuilder();
		builder.append(replacedString.substring(0, index)).append(".").append(
				replacedString.substring(index + 1));

		return builder.toString();
	}

	/**
	 * Answers a <code>URL</code> for the given path and the given
	 * <code>ClassLoader</code>.
	 */
	private static URL getURL(String path, ClassLoader classLoader) {
		URL url = classLoader.getResource(path);
		if (null == url)
			System.err.println(path + " not found.");
		return url;
	}

	/**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static ImageIcon readImageIcon(String path) {
		URL url = getURL(path);
		return null == url ? null : new ImageIcon(url);
	}
	public static ImageIcon readImageIcon(ResourceBundle bundel,String path) {
		URL url = getURL(path);
		return null == url ? null : new ImageIcon(url);
	}

	/**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static Image getImage(String path) {
		URL url = getURL(path);
		return null == url ? null : new ImageIcon(url).getImage();
	}
	
	/**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static Image getImage(String path, Class<?> clazz) {
		URL url = null;
		try{
		 url = getURL(bundlePathToFilePath(getResourcePathFor(clazz)+path), clazz.getClassLoader());
		}catch(MissingResourceException mg ){
			
		}
		return null == url ? null : new ImageIcon(url).getImage();
	}

	public static ImageIcon getImageIcon(String path, Class<?> clazz, ResourceBundle bundle) {
		URL url = null;
		try{
			String a = getResourcePathFor(clazz)+path;
		 url = getURL(bundlePathToFilePath(a), clazz.getClassLoader());
		}catch(MissingResourceException mg ){
			System.err.println(mg.getMessage());
		}
		return null == url ? null : new ImageIcon(url);
	}

	/**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static ImageIcon getImageIcon(String path, Class<?> clazz) {
		ResourceBundle bundle = getBundlePath(clazz);
		URL url = getURL(bundlePathToFilePath(getResourcePathFor(clazz)
				+ bundle.getString(path)), clazz.getClassLoader());
		return null == url ? null : new ImageIcon(url);
	}
	
}