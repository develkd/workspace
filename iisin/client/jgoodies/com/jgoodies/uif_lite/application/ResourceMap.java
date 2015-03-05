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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

/**
 * @author Kemal Dönmez
 *
 */
public class ResourceMap {

	    // Shared Objects *********************************************************

	    /**
	     * A special value used to cache {@code null}.
	     *
	     * @see #getResource(ResourceBundle, String)
	     */
	    private static final Object NULL_RESOURCE = new String("Null resource");

	    private static final Logger LOGGER = Logger.getLogger(ResourceMap.class.getName());


	    // Instance Fields ********************************************************

	    /**
	     * The parent ResourceMap that is used to lookup resources,
	     * if this resource map misses a resource.
	     */
	    private final ResourceMap parent;

	    /**
	     * The class loader used to load icons, images, and other resources.
	     * Will be initialized with the loaderType's class loader,
	     * if no custom class loader has been specified.
	     */
	    private final ClassLoader classLoader;

	    /**
	     * Holds the base name of this map's resource bundle.
	     */
	    private final String bundleBaseName;

	    /**
	     * Holds the path to this map's resources package.
	     * Used for relative paths when converting and loading icons, images,
	     * resource URLs, and other resources.
	     */
	    private final String resourceParentPath;


	    // Fields for Caching *****************************************************

	    /**
	     * Holds the Locale that was used while caching data.
	     * If the current default locale differs from this stored locale,
	     * the cache is invalid.
	     */
	    private Locale cachedLocale;

	    /**
	     * The lazily created bundle that is used to lookup resources from.
	     */
	    private ResourceBundle bundle;

	    /**
	     * Describes whether this map has tried to lazily create the associated
	     * resource bundle before. If this succeeds, the bundle is stored.
	     */
	    private boolean bundleSearched;

	    /**
	     * Maps resource bundle keys to converted values.
	     * Used only for values that have been converted.
	     *
	     * @see #getObject(String, Class)
	     */
	    private final Map<String, Object> cache;


	    // Instance Creation ******************************************************

	    /**
	     * Constructs a resource map for the given class without a parent.
	     *
	     * Uses the class' class loader for loading
	     * icons, images, and other resources. The base name of the associated
	     * resource bundle is the class' package + ".resources." + its simple name.
	     * For example for class MyPackage.MyClass, the bundle base name is
	     * "MyPackage.resources.MyClass".
	     * Relative resource paths are resolved using the package portion
	     * of the resource bundle base name.
	     *
	     * @param type     used to load icons, images, and other resources
	     *
	     * @throws IllegalArgumentException   if the class is null
	     */
	    public ResourceMap(Class<?> type) {
	        this(null, type);
	    }


	    /**
	     * Constructs a resource map for the given parent and class.
	     *
	     * The optional parent resource map is used, if a resource is missing
	     * in this map's bundle. Uses the class' class loader for loading
	     * icons, images, and other resources. The base name of the associated
	     * resource bundle is the class' package + ".resources." + its simple name.
	     * For example for class MyPackage.MyClass, the bundle base name is
	     * "MyPackage.resources.MyClass".
	     * Relative resource paths are resolved using the package portion
	     * of the resource bundle base name.
	     *
	     * @param parent          an optional parent resource map that will be used
	     *                        if a resource is missing in this map
	     * @param type            used to load icons, images, and other resources
	     *
	     * @throws IllegalArgumentException   if the class is null
	     */
	    public ResourceMap(ResourceMap parent, Class<?> type) {
	        this(parent, type.getClassLoader(), classResourceBundleBaseName(type));
	    }


	    /**
	     * Constructs a resource map for the given class loader and resource bundle
	     * base name. The resource bundle - if any - will be created lazily.
	     *
	     * The optional parent resource map is used, if a resource is missing
	     * in this map's bundle. The specified class loader is used for loading
	     * icons, images, and other resources. Relative resource paths are resolved
	     * using the package portion of the resource bundle base name.
	     *
	     * @param parent          an optional parent resource map that will be used
	     *                        if a resource is missing in this map
	     * @param classLoader     used to load icons, images, and other resources
	     * @param bundleBaseName  this base name for map's underlying resource bundle
	     *
	     * @throws IllegalArgumentException   if the class loader or bundle base name is null
	     */
	    public ResourceMap(ResourceMap parent, ClassLoader classLoader, String bundleBaseName) {
	        if (classLoader == null) {
	            throw new IllegalArgumentException("The class loader must not be null.");
	        }
	        if (bundleBaseName == null) {
	            throw new IllegalArgumentException("The resource bundle base name must not be null.");
	        }
	        this.parent = parent;
	        this.classLoader = classLoader;
	        this.bundleBaseName = bundleBaseName;
	        this.resourceParentPath = resourceParentPath(bundleBaseName);
	        this.cache = new ConcurrentHashMap<String, Object>(20);
	        this.cachedLocale = Locale.getDefault();
	    }


	    private static String classResourceBundleBaseName(Class<?> type) {
	        String simpleClassName = type.getSimpleName();
	        String packageName = type.getPackage().getName();
	        return packageName + ".resources." + simpleClassName;
	    }


	    private static String resourceParentPath(String baseName) {
	        int index = baseName.lastIndexOf('.');
	        return index == -1
	            ? ""
	            : baseName.substring(0, index).replace('.', '/') + "/";
	    }


	    // Property Accessors *****************************************************

	    /**
	     * Returns this map's parent that is used to look up resources,
	     * if a resource is missing in this map's resource bundle.<p>
	     *
	     * Application's rarely need access to this parent. Instead
	     * they lookup a resource and the lookup mechanism will automatically
	     * continue to look for a resource in the parent map.
	     *
	     * @return the map that is used to lookup missing resources from
	     */
	    public ResourceMap getParent() {
	        return parent;
	    }


	    /**
	     * Returns the class loader that is used to load icons, images,
	     * and other resources.
	     *
	     * @return the ClassLoader used to read resources
	     */
	    public ClassLoader getClassLoader() {
	        return classLoader;
	    }


	    /**
	     * Returns the base name of this map's underlying resource bundle.
	     * The bundle may exist or not.
	     *
	     * @return the base name of this map's resource bundle
	     */
	    public String getBundleBaseName() {
	        return bundleBaseName;
	    }


	    /**
	     * Lazily creates and returns this map's underlying resource bundle,
	     * or {@code null} if there's no associated bundle.
	     * All bundle access must use this method.<p>
	     *
	     * Also useful for legacy APIs that haven't been designed for ResourceMap's.
	     *
	     * @return this map's underlying resource bundle
	     *     or {@code null} if there's no associated bundle.
	     */
	    public synchronized ResourceBundle getBundle() {
	        if (bundleSearched) {
	            return bundle;
	        }
	        try {
	            bundle = ResourceBundle.getBundle(
	                        bundleBaseName,
	                        Locale.getDefault(),
	                        getClassLoader());
	        } catch (MissingResourceException e) {
	            // We just tried to find the bundle and will never try again.
	        }
	        bundleSearched = true;
	        return bundle;
	    }


	    /**
	     * Returns the path to this map's resources package.
	     * Used for relative paths when converting and loading icons, images,
	     * resource URLs, and other resources.
	     *
	     * @return the parent path of relative resources
	     */
	    public String getResourceParentPath() {
	        return resourceParentPath;
	    }


	    // Accessing Resources ****************************************************

	    /**
	     * Looks up a resource value for the given resource key, converts it
	     * if necessary, and returns a value of the specified <code>type</code>.
	     * If type is a primitive type, the returned value is an instance
	     * of the associated object type.<p>
	     *
	     * First looks up the value from this map's resource bundle.
	     * If no value is found, the lookup continues in this map's parent.
	     * If the value found is of the expected return type, it is returned.
	     * If it's no String, a ClassCastException is thrown, because the
	     * following conversion can convert only strings.<p>
	     *
	     * Before the string value is converted, a cache is searched.
	     * In case of a cache hit, this cached converted value is returned.
	     *
	     * Otherwise, a converter will be chosen for the given return type.
	     * If no converter can be found, an IllegalArgumentException is thrown.
	     *
	     * The string value is converted, stored in the cache of converted values,
	     * and finally returned.
	     *
	     * @param <T>   the type of the returned result
	     * @param key   the resource bundle key to look for
	     * @param type  the type used for a conversion - if any - and the return type
	     * @return the resource bundle value in converted form - if necessary
	     *
	     * @throws MissingResourceException if the key is absent in this
	     *     map's bundle, as well in all parent map bundles - if any.
	     * @throws ClassCastException if the resource value is neither
	     *    of the return type, nor a String
	     * @throws IllegalArgumentException if the key or type is null,
	     *    or if the resource value needs
	     *    to be converted but has no associated converter
	     * @throws ResourceConverter.ResourceConversionException if the conversion
	     *    fails to convert the resource bundle string value to the expected return type
	     */
	    @SuppressWarnings("unchecked")
	    public <T> T getObject(String key, Class<T> type) {
	        ensureValidKeyAndType(key, type);
	        ensureValidCache();
	        Object value;
	        ResourceBundle rBundle = getBundle();
	        if (parent == null) {
	            if (rBundle == null) {
	                throw new MissingResourceException(
	                        "Missing resource bundle " + bundleBaseName, bundleBaseName, null);
	            }
	            value = getResource(rBundle, key);
	        } else {
	            if (rBundle == null) {
	                return parent.getObject(key, type);
	            }
	            try {
	                value = getResource(rBundle, key);
	            } catch(MissingResourceException e) {
	                return parent.getObject(key, type);
	            }
	        }
	        if (value instanceof String) {
	            String evaluatedString = evaluate((String) value);
	            if (evaluatedString != value) {
	                LOGGER.finer("Resource expression \"" + value + "\" expands to  \"" + evaluatedString + "\"");
	                putResource(key, evaluatedString);
	                value = evaluatedString;
	            }
	        }
	        if (value == null) {
	            return null;
	        }
	        if (type.isInstance(value)) {  // if (value.getClass().isAssignableFrom(type)) {
	            return type.cast(value);
	        }
	        if (value instanceof CachedResource) {
	            CachedResource cachedValue = (CachedResource) value;
	            if (type == String.class) {
	                return type.cast(cachedValue.evaluatedString);
	            } else if (type.isInstance(cachedValue.convertedObject)) {
	                return type.cast(cachedValue.convertedObject);
	            }
	            value = cachedValue.evaluatedString;
	        }
	        if (!(value instanceof String)) {
	            wrongType(key, value, type);
	        }
	        ResourceConverter converter = ResourceConverters.forType(type);
	        if (converter == null) {
	            throw new IllegalArgumentException("No resource converter found for type " + type);
	        }
	        Class<T> returnType = convertPrimitiveType(type);
	        T convertedValue = returnType.cast(converter.convert(key, (String) value, type, this));
	        putResource(key, (String) value, convertedValue);
	        return convertedValue;
	    }


	    /**
	     * Looks up and returns the boolean associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to boolean.
	     * This conversion accepts: <em>true, false, yes, no, on, off</em>
	     * ignoring the case.
	     *
	     * @param key  the key in the resource bundle
	     * @return the boolean associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public boolean getBoolean(String key) {
	        return getObject(key, Boolean.class).booleanValue();
	    }


	    /**
	     * Looks up and returns the byte associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a Byte.
	     * The conversion uses {@link Byte#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the byte associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public byte getByte(String key) {
	        return getObject(key, Byte.class).byteValue();
	    }


	    /**
	     * Looks up and returns a Calendar object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Calendar.
	     * The conversion first parses the string value using DateFormat's
	     * <code>MEDIUM</code> date instance in the English Locale.
	     * If this fails, the converter parses with DateFormat's <code>SHORT</code>
	     * date instance in the English Locale. If both attempts fail,
	     * a {@link ResourceConverter.ResourceConversionException} is thrown.<p>
	     *
	     * Examples:<pre>
	     * application.releaseDate=Dec 31, 2006  # month day, year
	     * application.buildDate=12/31/06        # month/day/year
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Calendar value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     * @see DateFormat#getDateInstance(int)
	     */
	    public Calendar getCalendar(String key) {
	        return getObject(key, Calendar.class);
	    }


	    /**
	     * Looks up and returns the char associated with the given resource key.
	     * If the resource bundle returns a String, the first character is returned.
	     * The conversion fails if the resource String is not of length 1.
	     *
	     * @param key  the key in the resource bundle
	     * @return the char associated with the given resource key,
	     *    often the first (and only) char of the resource value string
	     *
	     * @see #getObject(String, Class)
	     */
	    public char getCharacter(String key) {
	        return getObject(key, Character.class).charValue();
	    }


	    /**
	     * Looks up and returns a Color object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Color
	     * using {@link Color#decode(String)}. The decoder converts the string
	     * to an integer and returns the specified opaque Color; it handles
	     * string formats that are used to represent octal and hexadecimal numbers.<p>
	     *
	     * Examples:<pre>
	     * #R,G,B
	     * color1=1, 2, 3
	     *
	     * #RRGGBB
	     * navigation.foreground=#000000   # black
	     * navigation.background=#EEF3FA   # light blue
	     *
	     * #R,G,B,A
	     * translucent.1=17, 34, 51, 68
	     *
	     * #AARRGGBB
	     * translucent.2=0x44112233
	     * translucent.3=#44112233
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Color value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Color getColor(String key) {
	        return getObject(key, Color.class);
	    }


	    /**
	     * Looks up and returns a Date object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Date.
	     * The conversion first parses the string value using DateFormat's
	     * <code>MEDIUM</code> date instance in the English Locale.
	     * If this fails, the converter parses with DateFormat's <code>SHORT</code>
	     * date instance in the English Locale. If both attempts fail,
	     * a {@link ResourceConverter.ResourceConversionException} is thrown.<p>
	     *
	     * Examples:<pre>
	     * application.releaseDate=Dec 31, 2006  # month day, year
	     * application.buildDate=12/31/06        # month/day/year
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Date value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     * @see DateFormat#getDateInstance(int)
	     */
	    public Date getDate(String key) {
	        return getObject(key, Date.class);
	    }


	    /**
	     * Looks up and returns a Dimension object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Dimension.
	     * The parser expects two strings that represent an integer, separated
	     * by a comma.<p>
	     *
	     * Examples:<pre>
	     * mainPanel.size=400, 300    # width=400, height=300
	     * button.minSize=75, 21      # width= 75, height= 21
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Dimension value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Dimension getDimension(String key) {
	        return getObject(key, Dimension.class);
	    }


	    /**
	     * Looks up and returns the double associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a Double.
	     * The conversion uses {@link Double#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the double associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public double getDouble(String key) {
	        return getObject(key, Double.class).doubleValue();
	    }


	    /**
	     * Looks up and returns an EmptyBorder object associated with the given key.
	     * If the resource bundle returns a String, it is converted to an EmptyBorder
	     * object. The parser expects four strings that represent an integer,
	     * separated by a comma.<p>
	     *
	     * Example:<pre>
	     * editor.border=21, 12, 21, 12   # top, left, bottom, right
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return an EmptyBorder value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public EmptyBorder getEmptyBorder(String key) {
	        return getObject(key, EmptyBorder.class);
	    }


	    /**
	     * Looks up and return a File object associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a File
	     * using {@link File#File(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the File associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public File getFile(String key) {
	        return getObject(key, File.class);
	    }


	    /**
	     * Looks up and returns the float associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a Float.
	     * The conversion uses {@link Float#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the float associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public float getFloat(String key) {
	        return getObject(key, Float.class).floatValue();
	    }


	    /**
	     * Looks up and returns the Font value for the given resource key.
	     * If the resource bundle returns a String, it is converted to a Font
	     * using {@link Font#decode(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the Font associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Font getFont(String key) {
	        return getObject(key, Font.class);
	    }


	    /**
	     * Returns an icon that will be loaded from the URL specified
	     * by the property value of the given key. The URL can be absolute,
	     * or relative to this ResourceMap's loader type (the type it has been
	     * requested for). For example <tt>exit.icon=images/exit.png</tt>
	     * is a relative path,
	     * <tt>exit.icon=/com/jgoodies/myapp/resources/images/exit.png</tt>
	     * is an absolute path.
	     *
	     * @param key   the resource bundle key for the icon's URL
	     * @return an Icon loaded from the URL at the key's value
	     *
	     * @see #getObject(String, Class)
	     */
	    public Icon getIcon(String key) {
	        return getObject(key, Icon.class);
	    }


	    /**
	     * Returns an image that will be loaded from the URL specified
	     * by the property value of the given key. The URL can be absolute,
	     * or relative to this ResourceMap's loader type (the type it has been
	     * requested for). For example <tt>bar.image=images/bluebar.jpg</tt>
	     * is a relative path,
	     * <tt>bar.image=/com/jgoodies/myapp/resources/images/bluebar.jpg</tt>
	     * is an absolute path.
	     *
	     * @param key   the resource bundle key for the image's URL
	     * @return an Image loaded from the URL at the key's value
	     *
	     * @see #getObject(String, Class)
	     */
	    public Image getImage(String key) {
	        return getObject(key, Image.class);
	    }


	    /**
	     * Returns an ImageIcon that will be loaded from the URL specified
	     * by the property value of the given key. The URL can be absolute,
	     * or relative to this ResourceMap's loader type (the type it has been
	     * requested for). For example <tt>exit.icon=images/exit.png</tt>
	     * is a relative path,
	     * <tt>exit.icon=/com/jgoodies/myapp/resources/images/exit.png</tt>
	     * is an absolute path.
	     *
	     * @param key   the resource bundle key for the icon's URL
	     * @return an ImageIcon loaded from the URL at the key's value
	     *
	     * @see #getObject(String, Class)
	     */
	    public ImageIcon getImageIcon(String key) {
	        return getObject(key, ImageIcon.class);
	    }


	    /**
	     * Looks up and returns an Insets object associated with the given key.
	     * If the resource bundle returns a String, it is converted to an Insets
	     * object. The parser expects four strings that represent an integer,
	     * separated by a comma.<p>
	     *
	     * Example:<pre>
	     * mainPanel.insets=14, 21, 16, 21   # top, left, bottom, right
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return an Insets value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Insets getInsets(String key) {
	        return getObject(key, Insets.class);
	    }


	    /**
	     * Looks up and returns the int associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to an Integer.
	     * The conversion uses {@link Integer#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the int associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public int getInt(String key) {
	        return getObject(key, Integer.class).intValue();
	    }


	    /**
	     * Looks up and returns the KeyStroke associated with the given key.
	     * If the resource bundle returns a String, it is converted to a KeyStroke
	     * using {@link KeyStroke#getAWTKeyStroke(String)}.<p>
	     *
	     * Examples:<pre>
	     * print.Action.accelerator=ctrl P
	     * print.Action.accelerator.mac=meta P
	     * </pre>
	     *
	     * @param key  the resource bundle key
	     * @return the KeyStroke associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public KeyStroke getKeyStroke(String key) {
	        return getObject(key, KeyStroke.class);
	    }


	    /**
	     * Looks up and returns the long associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a Long.
	     * The conversion uses {@link Long#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the long associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public long getLong(String key) {
	        return getObject(key, Long.class).longValue();
	    }


	    /**
	     * Looks up and returns the MessageFormat associated with the given key.
	     * If the resource bundle returns a String, it is converted
	     * to a MessageFormat. The conversion uses
	     * {@link MessageFormat#MessageFormat(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the MessageFormat associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public MessageFormat getMessageFormat(String key) {
	        return getObject(key, MessageFormat.class);
	    }


	    /**
	     * Looks up and returns a Point object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Point.
	     * The parser expects two strings that represent an integer, separated
	     * by a comma.<p>
	     *
	     * Examples:<pre>
	     * mainPanel.origin=400, 300    # x=400, y=300
	     * chartPoint=05, 12            # x=  5, y= 12
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Point value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Point getPoint(String key) {
	        return getObject(key, Point.class);
	    }


	    /**
	     * Looks up and returns a Rectangle object associated with the given key.
	     * If the resource bundle returns a String, it is converted to a Rectangle
	     * object. The parser expects four strings that represent an integer,
	     * separated by a comma.<p>
	     *
	     * Example:<pre>
	     * outline.rectangle=14, 21, 16, 21   # x, y, width, height
	     * </pre>
	     *
	     * @param key  the key in the resource bundle
	     * @return a Rectangle value associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public Rectangle getRectangle(String key) {
	        return getObject(key, Rectangle.class);
	    }


	    /**
	     * Looks up and returns the short associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a Short.
	     * The conversion uses {@link Short#valueOf(String)}.
	     *
	     * @param key  the key in the resource bundle
	     * @return the short associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     */
	    public short getShort(String key) {
	        return getObject(key, Short.class).shortValue();
	    }


	    /**
	     * Looks up and returns a String associated with the given resource key.
	     * If no arguments are provided, the plain String is returned. Otherwise
	     * the string will be formatted using {@code String.format} with the
	     * given arguments.
	     *
	     * @param key   the key in the resource bundle
	     * @param args  optional format arguments forwarded to {@code String#format}
	     * @return the String value found for the given resource key,
	     *    formatted with the optional arguments - if any
	     *
	     * @see #getObject(String, Class)
	     * @see String#format(String, Object...)
	     */
	    public String getString(String key, Object... args) {
	        String str = getObject(key, String.class);
	        return args.length == 0
	            ? str
	            : String.format(str, args);
	    }


	    /**
	     * Looks up and returns the URL associated with the given resource key.
	     * If the resource bundle returns a String, it is converted to a URL.
	     * The conversion first checks whether the string represents a URL
	     * using {@link URL#URL(String)}. If that fails, the string is interpreted
	     * as an unresolved absolute or relative  resource path. The path
	     * is resolved by adding this map's resource parent path (for relative paths)
	     * or by removing a leading slash '/' (for absolute paths).
	     * Next, it finds resource for this path using
	     * {@link ClassLoader#getResource(String)} and returns its URL.
	     *
	     * @param key  the key in the resource bundle
	     * @return the URL or resource URL associated with the given resource key
	     *
	     * @see #getObject(String, Class)
	     * @see ClassLoader#getResource(String)
	     */
	    public URL getURL(String key) {
	        return getObject(key, URL.class);
	    }


	    // Convenience Accessors **************************************************

	    /**
	     * Adds this map's resource parent path, if the given path is relative,
	     * and removes the leading slash, if the path is absolute.<p>
	     *
	     * Useful for getting path information for resource values that are
	     * relative to this ResourceMap's path, for example when loading images
	     * or icons. Used by some resource converters to resolve relative paths.
	     *
	     * @param path  a relative or absolute path
	     * @return the absolute path
	     */
	    public String resolvePath(String path) {
	        if (path == null) {
	            return path;
	        }
	        return path.startsWith("/")
	            ? path.substring(1)
	            : getResourceParentPath() + path;
	    }


	    // Helper Code ************************************************************

	    /**
	     * Expands variables - if any - in the given expression and returns
	     * the expanded string. Variables are surrounded by "${" and "}";
	     * for example "${hello}", or "${world}" for the variables <tt>hello</tt>
	     * and <tt>world</tt>.
	     * The expression "${null}" is mapped to {@code null}.<p>
	     *
	     * If the following properties are defined,
	     * <pre>
	     * hello = Hello
	     * world = World
	     * place = ${world}
	     * </pre>
	     * then "${hello} ${place}") evaluates to "Hello World".
	     *
	     * @param expr   the expression
	     * @return the expression, if it contains no variables,
	     *    or {@code null} if the expression is "${null}",
	     *    or the expanded string where all variables are replaced
	     *     by their associated string values.
	     */
	    private String evaluate(String expr) {
	        int cursor = 0;
	        int start = expr.indexOf("${", cursor);
	        if (start == -1) { // No variables found
	            return expr;
	        }
	        if (expr.substring(start + 2).trim().equals("null}")) { // ${null}
	            return null;
	        }
	        StringBuilder result = new StringBuilder();
	        do {
	            if ((start > 0) && (expr.charAt(start - 1) == '\\')) {
	                // escaped variable - "\${"
	                result.append(expr.substring(cursor, start - 1));
	                result.append("${");
	                cursor = start + 2; // skip past "${"
	                continue;
	            }
	            int end = expr.indexOf("}", start);
	            if (end <= start + 2) {
	                String rest = expr.substring(cursor);
	                throw new IllegalStateException(
	                        "Missing closing brace in \"" + rest + "\"");
	            }
	            String variableName = expr.substring(start + 2, end);
	            String variableValue = getString(variableName);
	            if (variableValue == null) {
	                throw new IllegalStateException(
	                        "Missing value for \"" + variableName + "\" in \"" + expr + "\"");
	            }
	            result.append(expr.substring(cursor, start));
	            result.append(variableValue);
	            cursor = end + 1; // skip trailing "}"
	            start = expr.indexOf("${", cursor);
	        } while (start != -1);
	        result.append(expr.substring(cursor));
	        return result.toString();
	    }


	    @SuppressWarnings("unchecked")
	    private static Class convertPrimitiveType(Class<?> type) {
	        if (!type.isPrimitive()) { return type; }
	        if (type == boolean.class) {
	            return Boolean.class;
	        } else if (type == byte.class) {
	            return Byte.class;
	        } else if (type == char.class) {
	            return Character.class;
	        } else if (type == double.class) {
	            return Double.class;
	        } else if (type == float.class) {
	            return Float.class;
	        } else if (type == int.class) {
	            return Integer.class;
	        } else if (type == short.class) {
	            return Short.class;
	        }
	        return null;
	    }


	    private void ensureValidKeyAndType(String key, Class<?> type) {
	        if (key == null) {
	            throw new IllegalArgumentException("The key must not be null.");
	        }
	        if (type == null) {
	            throw new IllegalArgumentException("The type must not be null.");
	        }
	    }


	    private void wrongType(String key, Object value, Class<?> type) {
	        throw new ClassCastException("The resource value must either be a String or " +
	                type.getName() +
	                "\nkey=" + key +
	                "\nvalue=" + value +
	                "\nvalue type= " + (value == null ? "null" : value.getClass().getName()) +
	                "\nreturn type=" + type);
	    }


	    // Caching ****************************************************************

	    private synchronized void ensureValidCache() {
	        if (cachedLocale != Locale.getDefault()) {
	            bundleSearched = false;
	            cache.clear();
	            cachedLocale = Locale.getDefault();
	        }
	    }


	    private Object getResource(ResourceBundle rBundle, String key) {
	        Object value = cache.get(key);
	        if (value != null) {
	            LOGGER.finer("ResourceMap cache hit for key: " + key);
	            return value == NULL_RESOURCE ? null : value;
	        }
	        return rBundle.getObject(key);
	    }


	    private void putResource(String key, String evaluatedString, Object convertedObject) {
	        Object entry = convertedObject == null
	            ? NULL_RESOURCE
	            : new CachedResource(evaluatedString, convertedObject);
	        cache.put(key, entry);
	    }

	    private void putResource(String key, String evaluatedString) {
	        cache.put(key, new CachedResource(evaluatedString, null));
	    }


	    static final class CachedResource {

	        final String evaluatedString;
	        final Object convertedObject;

	        CachedResource(String evaluatedString, Object convertedObject) {
	            this.evaluatedString = evaluatedString;
	            this.convertedObject = convertedObject;
	        }

	    }


}
