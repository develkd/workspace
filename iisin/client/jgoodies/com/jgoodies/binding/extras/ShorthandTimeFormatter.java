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
import java.text.ParseException;

import com.jgoodies.binding.formatter.EmptyDateFormatter;

/**
 * In addition to its superclass, this formatter accepts times 
 * encoded as HHMM. For example:
 * <pre> 
 * "2412"   -> 24:12. 
 * "0005"   -> 00:05 
 * </pre>
 * 
 * To use a consistent parser semantics for the full and shorthand times,
 * shorthands are expanded to the full format. In a second step, the
 * expanded shorthand time is then parsed by the superclass parser.<p>
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

public class ShorthandTimeFormatter extends EmptyDateFormatter {

    // Instance Creation ******************************************************

    /**
     * Constructs a ShorthandTimeFormatter that accepts the superclass'
     * format strings as well as the HHMM shorthands.
     */
    public ShorthandTimeFormatter() {
        super(DateFormat.getTimeInstance(DateFormat.SHORT));
    }

    /**
     * Constructs a ShorthandTimeFormatter that accepts the superclass'
     * format strings as well as the HHMM shorthands.
     * 
     * @param format DateFormat instance used for converting from/to Strings
     */
    public ShorthandTimeFormatter(DateFormat format) {
        super(format);
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
    public Object stringToValue(String text) throws ParseException {
        // First, try to parse like the superclass.
        try {
            return super.stringToValue(text);
        } catch (ParseException e) {
            // Ignore; see below for the second try.
        }
        
        // Now try to expand DDMM, DDMMYY, DDMMYYYY;, then parse the expansion.
        try {
            return super.stringToValue(expandShorthand(text.trim()));
        } catch (Exception e) {
            throw new ParseException("Shorthand must be HHMM.", 0);
        }
    }
    
    
    private String expandShorthand(String text) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(text.substring(0, 2));
        buffer.append(separator());
        buffer.append(text.substring(2, 4));
        return buffer.toString();
    }
    
    
    /**
     * Returns the character used to separate hours from minutes. 
     * For example, the colon ':' for German times.
     * Used to expand the shorthand HHMM to HH:MM.
     * 
     * @return the hour-minutes separator character
     */
    protected char separator() {
        return ':';
    }
    
}