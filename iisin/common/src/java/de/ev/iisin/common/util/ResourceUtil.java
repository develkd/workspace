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

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * @author Kemal Dönmez
 *
 */
public class ResourceUtil {
	   /**
     * Hidden constructor to prevent instantiation.
     */
    private ResourceUtil() {
        
    }
    
    /**
     * Transforms an (enum) constant name to a key name that may be used for
     * direct lookup in a resource bundle.
     * <p>
     * Example: The method invocation
     * <code>constantToBundleKey(&quot;error.validation&quot;, &quot;NAME_REQUIRED&quot;)</code>
     * will return a result of <code>error.validation.name.required</code>.
     * </p>
     * 
     * @param namespacePrefix
     *            The namespace prefix that should be prepended to the actual
     *            bundle key without a trailing dot. If <code>null</code>, no
     *            prefix will be prepended.
     * @param constantName
     *            The (enum) constant name to be transformed.
     * @return A bundle key made up of the namespace prefix and the transformed
     *         constant name.
     */
    public static String constantToBundleKey(String namespacePrefix,
            String constantName) {
        String convertedName = constantName.replaceAll("_", ".").toLowerCase();
        
        if (namespacePrefix != null) return namespacePrefix + "."
                + convertedName;
        else return convertedName;
    }
    
    public static String bundlePathToFilePath(String namespacePrefix) {
        return namespacePrefix.replace(".", "/").toLowerCase();
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
        bundle.getLocale();
        return bundle;
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
    public static ResourceBundle getResourceBundle(Enum<?> enumm) {
    	return getResourceBundle(enumm.getClass(), null);
    }

   

    public static ResourceBundle getResourceBundle(Class<?> clazz, Locale local) {
        ClassLoader loader = null;
        try {
            loader = Class.forName(clazz.getName()).getClassLoader();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lookupBundle(clazz.getPackage().getName()
                + ".resources.Package", local, loader);
    }

    /**
     * Looks up a String in the associated resource bundle with the provided
     * locale and the class loader of <code>key</code>.
     * 
     * @param key
     *            The key of the resource to be looked up.
     * @param locale
     *            The target locale. If it is <code>null</code>, the default
     *            locale of the platform is taken (as returned by
     *            <code>Locale.getDefault()</code>.
     * @return The value associated with the provided key or the key itself if
     *         the bundle and/or the key could not be found.
     */
    public static String getString(ResourceKey key, Locale locale) {
        return getString(key, locale, null);
    }
    
    public static String getString(ResourceKey key) {
        return getString(key, null, null);
    }
    
    public static String getString(ResourceKey key, ClassLoader loader) {
        return getString(key, null, loader);
    }
    
    public static URL getURL(ResourceKey key) {
        try {
            return new URL(getString(key, null, null));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static String packageResourceBundleBaseName(Class<?> type) {
        return type.getPackage().getName() + ".resources.Package";
    }
    
    /**
     * Looks up a string resource in the associated resource bundle with the
     * provided locale and class loader.
     * 
     * @param key
     *            The key of the resource to be looked up.
     * @param locale
     *            The target locale. If it is <code>null</code>, the default
     *            locale of the platform is taken (as returned by
     *            <code>Locale.getDefault()</code>.
     * @param loader
     *            The class loader with which the resource bundle should be
     *            looked up. If <code>null</code>, uses the class loader of
     *            <code>key</code>.
     * @return The value associated with the provided key or the key itself if
     *         the bundle and/or the key could not be found.
     */
    public static String getString(ResourceKey key, Locale locale,
            ClassLoader loader) {
        try {
            ResourceBundle bundle = lookupBundle(key.getBundleName(), locale,
                    loader);
            String value = bundle.getString(key.getBundleKey());
            return value;
        } catch (MissingResourceException e) {
            return key.toString();
        }
    }
    
    /**
     * Looks up a message String in the associated resource bundle with the
     * provided locale and the class loader of <code>key</code>. Furthermore,
     * applies the {@link MessageFormat} patterns using the provided message
     * arguments.
     * 
     * @param key
     *            The key of the message resource to be looked up.
     * @param locale
     *            The target locale. If it is <code>null</code>, the default
     *            locale of the platform is taken (as returned by
     *            <code>Locale.getDefault()</code>.
     * @param arguments
     *            The message arguments that should be applied.
     * @return The plain message with interpolated arguments or the key itself
     *         if the bundle and/or the key could not be found.
     */
    public static String getMessage(ResourceKey key, Locale locale,
            Object... arguments) {
        return getMessage(key, locale, null, arguments);
    }
    
    /**
     * Looks up a message String in the associated resource bundle with the
     * provided locale and the class loader of <code>key</code>. Furthermore,
     * applies the {@link MessageFormat} patterns using the provided message
     * arguments.
     * 
     * @param key
     *            The key of the message resource to be looked up.
     * @param arguments
     *            The message arguments that should be applied.
     * @return The plain message with interpolated arguments or the key itself
     *         if the bundle and/or the key could not be found.
     */
    public static String getMessage(ResourceKey key, Object... arguments) {
        return getMessage(key, Locale.getDefault(), null, arguments);
    }
    
    /**
     * Looks up a message String in the associated resource bundle with the
     * provided locale and class loader. Furthermore, applies the
     * {@link MessageFormat} patterns using the provided message arguments.
     * 
     * @param key
     *            The key of the message resource to be looked up.
     * @param locale
     *            The target locale. If it is <code>null</code>, the default
     *            locale of the platform is taken (as returned by
     *            <code>Locale.getDefault()</code>.
     * @param loader
     *            The class loader with which the resource bundle should be
     *            looked up. If <code>null</code>, uses the class loader of
     *            <code>key</code>.
     * @param arguments
     *            The message arguments that should be applied. If an argument
     *            is of type {@link ResourceKey}, it will be translated using
     *            {@link #getString(ResourceKey, Locale, ClassLoader)}.
     * @return The plain message with interpolated arguments or the key itself
     *         if the bundle and/or the key could not be found.
     */
    public static String getMessage(ResourceKey key, Locale locale,
            ClassLoader loader, Object... arguments) {
        try {
            ResourceBundle bundle = lookupBundle(key.getBundleName(), locale,
                    loader == null ? key.getClass().getClassLoader() : loader);
            String value = bundle.getString(key.getBundleKey());
            
            if (arguments != null && arguments.length > 0) {
                // Object[] argumentsCopy = new Object[arguments.length];
                //
                // for (int i = 0; i < arguments.length; i++) {
                // if (arguments[i] instanceof ResourceKey) {
                // // argument is a single ResourceKey
                // }
                // }
                
                String interpolated = arguments != null ? MessageFormat.format(
                        value, arguments) : value;
                
                return interpolated;
            } else {
                return value;
            }
        } catch (MissingResourceException e) {
            return key.toString();
        }
    }

    public static  String getString(String resouceBundleValue, Object...arguments){
        return arguments != null ? MessageFormat.format(
        		resouceBundleValue, arguments) : resouceBundleValue;
    }
    /**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static ImageIcon readImageIcon(String path, ClassLoader loader) {
		URL url = getURL(path,loader);
		return null == url ? null : new ImageIcon(url);
	}

	/**
	 * Reads and answers an <code>ImageIcon</code> for the given path using
	 * the default <code>ClassLoader</code>.
	 */
	public static Image getImage(String path, ClassLoader loader) {
		URL url = getURL(path,loader);
		return null == url ? null : new ImageIcon(url).getImage();
	}
	
	public static ImageIcon getImageIcon(String path, Class<?> clazz, ResourceBundle bundle) {
		URL url = null;
		try{
			int dotIndex = path.indexOf('.');
			String dot = path.substring(dotIndex+1);
			path = path.substring(0, dotIndex);
			String a = getResourcePathFor(clazz)+path;
		 url = getURL(bundlePathToFilePath(a)+"."+dot, clazz.getClassLoader());
		}catch(MissingResourceException mg ){
			System.err.println(mg.getMessage());
		}
		return null == url ? null : new ImageIcon(url);
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

	private static URL getURL(String path, ClassLoader classLoader) {
		URL url = classLoader.getResource(path);
		if (null == url)
			System.err.println(path + " not found.");
		return url;
	}
	
	private static String getResourcePathFor(Class<?> clazz) {
		return clazz.getPackage().getName() + ".resources.";
	}
	public static ResourceBundle getBundlePath(Class<?> clazz) {
		return ResourceBundle.getBundle(getResourcePathFor(clazz) + "Package");
	}
	
	public static ResourceBundle getBundlePath(Class<?> clazz, Locale local) {
		return ResourceBundle.getBundle(getResourcePathFor(clazz) + "Package", local);
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
