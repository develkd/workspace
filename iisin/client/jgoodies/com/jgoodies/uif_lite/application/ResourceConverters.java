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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

/**
 * @author Kemal Dönmez
 *
 */
public class ResourceConverters {

    private static final Logger LOGGER = Logger.getLogger(ResourceConverters.class.getName());

    /**
     * Holds all converters; used to lookup a converter for a given type
     * in case it has not been cached.
     */
    private static final List<ResourceConverter> CONVERTERS =
        new CopyOnWriteArrayList<ResourceConverter>();


    /**
     * Maps classes to resource converters. Used to lookup the converter
     * associated with a given type in {@link ResourceMap#getObject}.
     */
    private static final Map<Class<?>, ResourceConverter> CONVERTER_MAP =
        new ConcurrentHashMap<Class<?>, ResourceConverter>();


    static {
        ResourceConverter defaultConverter = new DefaultConverter();
        register(defaultConverter);
    }

    private ResourceConverters() {
        // Overrides default constructor prevents instantiation.
    }


    // Converters *************************************************************

    /**
     * Registers a ResourceConverter for the given type. Overrides all
     * previously registered converters for its supported types.<p>
     *
     * Invalidates the cached converter map that will be re-filled
     * in {@link #forType(Class)}.
     *
     * @param converter     the converter to be associated with the given type;
     *     converts Strings to the given type
     *
     * @throws IllegalArgumentException  if the converter is null
     */
    public static void register(ResourceConverter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("The converter must not be null.");
        }
        LOGGER.fine("Registering a resource converter:" + converter);
        CONVERTERS.add(0, converter);
        invalidateCache();
    }


    /**
     * Returns the ResourceConverter associated with the given type.
     * Accesses the converter cache first, and iterates over all converters
     * only if the converter has not been cached before.
     *
     * @param type   the class the result is associated with
     * @return a ResourceConverter that can convert strings to the given type
     *
     * @throws IllegalArgumentException  if the type is null
     */
    public static ResourceConverter forType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null.");
        }
        ResourceConverter converter = CONVERTER_MAP.get(type);
        if (converter == null) {
            converter = lookupConverter(type);
            CONVERTER_MAP.put(type, converter);
        }
        return converter;
    }


    private static ResourceConverter lookupConverter(Class<?> type) {
        for (ResourceConverter converter : CONVERTERS) {
            if (converter.supportsType(type)) {
                return converter;
            }
        }
        return null;
    }


    // Cache Management *******************************************************

    private static void invalidateCache() {
        CONVERTER_MAP.clear();
    }


    // Default Converter ******************************************************

    private static final class DefaultConverter implements ResourceConverter {

        private static final Class<?>[] SUPPORTED_TYPES = new Class[]{
              Boolean.class, boolean.class,
              Byte.class, byte.class,
              Calendar.class,
              Character.class, char.class,
              Color.class,
              Date.class,
              Dimension.class,
              Double.class, double.class,
              EmptyBorder.class,
              File.class,
              Float.class, float.class,
              Font.class,
              Icon.class, ImageIcon.class,
              Image.class,
              Insets.class,
              Integer.class, int.class,
              KeyStroke.class,
              Long.class, long.class,
              MessageFormat.class,
              Point.class,
              Rectangle.class,
              Short.class, short.class,
              URL.class
        };


        /**
         * {@inheritDoc}
         */
        public boolean supportsType(Class<?> type) {
            for (Class<?> supportedType : SUPPORTED_TYPES) {
                if (type == supportedType)
                    return true;
            }
            return false;
        }


        /**
         * {@inheritDoc}<p>
         *
         * This implementation converts a bunch of types.
         */
        public Object convert(String key, String value, Class<?> type, ResourceMap r) {
            try {
                if (type == Boolean.class || type == boolean.class) {
                    return convertBoolean(key, value, r);
                } else if (type == Byte.class || type == byte.class) {
                    return Byte.valueOf(Byte.parseByte(value));
                } else if (type == Calendar.class) {
                    return convertCalendar(key, value, r);
                } else if (type == Character.class || type == char.class) {
                    if (value.length() != 1) {
                        throw new ResourceConversionException(
                                "Character value too long.", key, value);
                    }
                    return Character.valueOf(value.charAt(0));
                } else if (type == Color.class) {
                    return convertColor(key, value, r);
                } else if (type == Date.class) {
                    return convertDate(key, value, r);
                } else if (type == Dimension.class) {
                    return convertDimension(key, value, r);
                } else if (type == Double.class || type == double.class) {
                    return Double.valueOf(value);
                } else if (type == EmptyBorder.class) {
                    return convertEmptyBorder(key, value, r);
                } else if (type == File.class) {
                    return new File(value);
                } else if (type == Float.class  || type == float.class) {
                    return Float.valueOf(value);
                } else if (type == Font.class) {
                    return Font.decode(value);
                } else if (type == Icon.class || type == ImageIcon.class) {
                    return new ImageIcon(convertImage(key, value, r));
                } else if (type == Image.class) {
                    return convertImage(key, value, r);
                } else if (type == Insets.class) {
                    return convertInsets(key, value, r);
                } else if (type == Integer.class || type == int.class) {
                    return Integer.valueOf(Integer.parseInt(value));
                } else if (type == KeyStroke.class) {
                    return KeyStroke.getKeyStroke(value);
                } else if (type == Long.class || type == long.class) {
                    return Long.valueOf(Long.parseLong(value));
                } else if (type == MessageFormat.class) {
                    return new MessageFormat(value);
                } else if (type == Point.class) {
                    return convertPoint(key, value, r);
                } else if (type == Rectangle.class) {
                    return convertRectangle(key, value, r);
                } else if (type == Short.class || type == short.class) {
                    return Short.valueOf(Short.parseShort(value));
                } else if (type == URL.class) {
                    return convertURL(key, value, r);
                } else
                    throw new IllegalStateException("Unknown type " + type);
            } catch (ResourceConversionException e) {
                throw e;
            } catch (Throwable t) {
                throw new ResourceConversionException("Invalid " + type + " format.", key, value, t);
            }
        }


        private Boolean convertBoolean(String key, String value, ResourceMap r) {
            if (value.equalsIgnoreCase("true")) {
                return Boolean.TRUE;
            } else if (value.equalsIgnoreCase("false")) {
                return Boolean.FALSE;
            } else if (   value.equalsIgnoreCase("on")
                       || value.equalsIgnoreCase("yes")) {
                return Boolean.TRUE;
            } else if (   value.equalsIgnoreCase("off")
                       || value.equalsIgnoreCase("no")) {
                return Boolean.FALSE;
            }
            throw new ResourceConversionException(
                    "Illegal boolean format. Must be one of: true, false, yes, no, on, off", key, value);
        }


        private Calendar convertCalendar(String key, String value, ResourceMap r) {
            Date date = convertDate(key, value, r);
            if (date == null) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }


        private Color convertColor(String key, String value, ResourceMap r) {
            String generalFormatError = "Invalid Color format. Must be one of: AARRGGBB or R, G, B [, A].";
            String[] token = value.split(",");
            switch (token.length) {
                case 1 :   // #RRGGBB
                    if (value.startsWith("#")) {
                        return decodeAARRGGBBColor(key, value, r, 1);
                    }
                    if (value.startsWith("0x") || value.startsWith("0X")) {
                        return decodeAARRGGBBColor(key, value, r, 2);
                    }
                    throw new ResourceConversionException(generalFormatError, key, value, null);
                case 3 :   // R, G, B
                case 4 :   // R, G, B, A
                    try {
                        int[] ints = parseInts(token);
                        return ints.length == 3
                            ? new Color(ints[0], ints[1], ints[2])
                            : new Color(ints[0], ints[1], ints[2], ints[3]);
                    } catch (NumberFormatException e) {
                        throw new ResourceConversionException(generalFormatError, key, value, e);
                    }
                default :
                    throw new ResourceConversionException(generalFormatError, key, value, null);
            }
        }

        private Color decodeAARRGGBBColor(String key, String value, ResourceMap r, int offset) {
            switch (value.length() - offset) {
                case 6 :
                    return Color.decode(value);
                case 8 :
                    return new Color(Long.decode(value).intValue(), true);
                default :
                    throw new ResourceConversionException("Invalid [AA]RRGGBB Color format.", key, value, null);
            }
        }

        private Date convertDate(String key, String value, ResourceMap r) {
            try {
                Format mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
                return (Date) mediumFormat.parseObject(value);
            } catch (ParseException e1) {
                Format shortFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
                try {
                    return (Date) shortFormat.parseObject(value);
                } catch (Exception e2) {
                    throw new ResourceConversionException("Invalid date format.", key, value, e2);
                }
            }
        }


        private Dimension convertDimension(String key, String value, ResourceMap r) {
            try {
                int[] ints = parseInts(value);
                if (ints.length != 2) {
                    throw new ResourceConversionException("Invalid Dimension format. Must be: <int>, <int>.", key, value, null);
                }
                return new Dimension(ints[0], ints[1]);
            } catch (NumberFormatException e) {
                throw new ResourceConversionException("Invalid Dimension format. Must be: <int>, <int>.", key, value, null);
            }
        }


        private EmptyBorder convertEmptyBorder(String key, String value, ResourceMap r) {
            try {
                int[] ints = parseInts(value);
                if (ints.length != 4) {
                    throw new ResourceConversionException("Invalid EmptyBorder format. Must be: <int>, <int>.", key, value, null);
                }
                return new EmptyBorder(ints[0], ints[1], ints[2], ints[3]);
            } catch (NumberFormatException e) {
                throw new ResourceConversionException(
                        "Invalid EmptyBorder format. Must be: <int>, <int>, <int>, <int>.", key, value, null);
            }
        }


        private Image convertImage(String key, String value, ResourceMap r) {
            if (isBlank(value)) {
                throw new ResourceConversionException(
                        "Missing image path.", key, value, null);
            }
            String path = r.resolvePath(value);
            URL url = r.getClassLoader().getResource(path);
            if (url == null) {
                throw new ResourceConversionException(
                        "Invalid image path.\nParent path=" + r.getResourceParentPath(), key, value, null);
            }
            try {
                return ImageIO.read(url);
            } catch (IOException e) {
                throw new ResourceConversionException(
                        "Can't read image.", key, value, e);
            }
        }


        private Insets convertInsets(String key, String value, ResourceMap r) {
            try {
                int[] ints = parseInts(value);
                if (ints.length != 4) {
                    throw new ResourceConversionException("Invalid Insets format. Must be: <int>, <int>.", key, value, null);
                }
                return new Insets(ints[0], ints[1], ints[2], ints[3]);
            } catch (NumberFormatException e) {
                throw new ResourceConversionException(
                        "Invalid Insets format. Must be: <int>, <int>, <int>, <int>.", key, value, null);
            }
        }


        private Point convertPoint(String key, String value, ResourceMap r) {
            try {
                int[] ints = parseInts(value);
                if (ints.length != 2) {
                    throw new ResourceConversionException("Invalid Point format. Must be: <int>, <int>.", key, value, null);
                }
                return new Point(ints[0], ints[1]);
            } catch (NumberFormatException e) {
                throw new ResourceConversionException("Invalid Point format. Must be: <int>, <int>.", key, value, null);
            }
        }


        private Rectangle convertRectangle(String key, String value, ResourceMap r) {
            try {
                int[] ints = parseInts(value);
                if (ints.length != 4) {
                    throw new ResourceConversionException("Invalid Rectangle format. Must be: <int>, <int>.", key, value, null);
                }
                return new Rectangle(ints[0], ints[1], ints[2], ints[3]);
            } catch (NumberFormatException e) {
                throw new ResourceConversionException(
                        "Invalid Rectangle format. Must be: <int>, <int>, <int>, <int>.", key, value, null);
            }
        }


        private URL convertURL(String key, String value, ResourceMap r) {
            try {
                return new URL(value);
            } catch (MalformedURLException e) {
                URL url = r.getClassLoader().getResource(r.resolvePath(value));
                if (url != null) {
                    return url;
                }
                throw new ResourceConversionException(
                        "Invalid URL.", key, value, e);
            }
        }


        // Helper Code -------------------------------------------------------

        private int[] parseInts(String value) {
            return parseInts(value.split(","));
        }

        private int[] parseInts(String[] token) {
            int[] result = new int[token.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Integer.parseInt(token[i].trim());
            }
            return result;
        }

        /**
         * Checks and answers if the given string is whitespace,
         * empty ("") or {@code null}.
         *
         * <pre>
         * isBlank(null)    == true
         * isBlank("")      == true
         * isBlank(" ")     == true
         * isBlank(" abc")  == false
         * isBlank("abc ")  == false
         * isBlank(" abc ") == false
         * </pre>
         *
         * @param str   the string to check, may be {@code null}
         * @return {@code true} if the string is whitespace, empty
         *    or {@code null}
         */
        private boolean isBlank(String str) {
            int length;
            if ((str == null) || ((length = str.length()) == 0))
                return true;
            for (int i = length - 1; i >= 0; i--) {
                if (!Character.isWhitespace(str.charAt(i)))
                    return false;
            }
            return true;
        }

    }

}
