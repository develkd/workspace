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

package com.jgoodies.binding.extras;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jgoodies.common.base.Objects;
import com.jgoodies.common.base.Strings;
import com.jgoodies.common.format.AbstractWrappedDateFormat;


/**
 * In addition to its superclass, this formatter accepts dates 
 * encoded as DDMM, DDMMYY, and DDMMYYYY. For example:
 * <pre> 
 * "2412"     -> 24.12.<current year> 
 * "241205"   -> 24.12.2005 
 * "24122005" -> 24.12.2005
 * </pre>
 * 
 * To use a consistent parser semantics for the full and shorthand dates,
 * shorthands are expanded to the full format. In a second step, the
 * expanded shorthand date is then parsed by the superclass parser.<p>
 * 
 * <strong>Note:<strong> Unlike the DateFormatter and EmptyDateFormatter,
 * this Formatter is not internationalized; it assumes a fixed order of
 * day, month, and year.<p>
 * 
 * TODO: Consider internationalizing this formatter.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.1 $
 */

public class ShorthandDateFormatter extends AbstractWrappedDateFormat {

    // Instance Creation ******************************************************

    /**
	 * Erzeugt am 21.02.2009
	 */
	private static final long serialVersionUID = -4687336611636022829L;

	 private final Date emptyValue;
	/**
     * Constructs a ShorthandDateFormatter that accepts the superclass'
     * format strings as well as the following shorthands: DDMM, DDMMYY, 
     * and DDMMYYYY.
     */
     public ShorthandDateFormatter(DateFormat format, Date emptyValue) {
        super(format);
        this.emptyValue = emptyValue;
    }
    
    /**
     * Constructs a ShorthandDateFormatter configured with the given
     * DateFormat. It that accepts the superclass' format strings 
     * as well as the following shorthands: DDMM, DDMMYY, and DDMMYYYY.
     *
     * @param format Format used to dictate legal values
     */
    public ShorthandDateFormatter(DateFormat format) {
    	 this(format, null);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
            FieldPosition pos) {
        return Objects.equals(date, emptyValue)
                ? toAppendTo
                : delegate.format(date, toAppendTo, pos);
    }


    @Override
    public Date parse(String source, ParsePosition pos) {
        if (Strings.isBlank(source)) {
            // DateFormat#parse(String) throws a ParseException,
            // if the parse position is 0. We change it to 1.
            pos.setIndex(1);
            return emptyValue;
        }
        return delegate.parse(source, pos);
    }

    // Overriding Superclass Behavior *****************************************

    /**
     * Returns the <code>Object</code> representation of the
     * <code>String</code> <code>text</code>.<p>
     * 
     * First applies the super behavior, and if that fails, tries again
     * with shorthands expanded.
     * 
     * @param text   <code>String</code> to convert
     * @return <code>Object</code> representation of text
     * @throws ParseException
     *             if there is an error in the conversion
     */
//    public Object stringToValue(String text) throws ParseException {
//        // First, try to parse like the superclass.
//        try {
//            return super.stringToValue(text);
//        } catch (ParseException e) {
//            // Ignore; see below for the second try.
//        }
//        
//        // Now try to expand DDMM, DDMMYY, DDMMYYYY;, then parse the expansion.
//        try {
//            return super.stringToValue(expandShorthand(text.trim()));
//        } catch (Exception e) {
//            throw new ParseException("Shorthand must be one of DDMM, DDMMYY, DDMMYYYY.", 0);
//        }
//    }
    
    
    private String expandShorthand(String text) {
        int year = new GregorianCalendar().get(java.util.Calendar.YEAR);
        String yyyy = Integer.toString(year);

        StringBuffer buffer = new StringBuffer();
        buffer.append(text.substring(0, 2));
        buffer.append(separator());
        buffer.append(text.substring(2, 4));
        buffer.append(separator());
        if (text.length() == 4) {
            buffer.append(yyyy);
        } else if (text.length() == 6) {
            buffer.append(yyyy.substring(0, 2));
            buffer.append(text.substring(4, 6));
        } else if (text.length() == 8) {
            buffer.append(text.substring(4, 8));
        } else {
            throw new IllegalArgumentException(
                    "Shorthand must have length 4, 6, or 8.");
        }
        return buffer.toString();
    }
    
    
    /**
     * Returns the character used to separate days from months,
     * and months from the year. For example, the dot '.' for German dates.
     * Used to expand shorthands like DDMM to something like DD.MM.YYYY.
     * 
     * @return the day, month, year separator character
     */
    protected char separator() {
        return '.';
    }
    
}