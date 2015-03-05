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

package de.ev.iisin.application.common.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.jgoodies.binding.extras.ShorthandTimeFormatter;


import de.ev.iisin.application.common.components.WarningLabelHolder;

/**
 * @author Kemal Dönmez
 *
 */
public class TimeFormatter extends ShorthandTimeFormatter {
    /**
	 * Created at 15.10.2008
	 */
	private static final long serialVersionUID = 7720868888544103221L;
	private final int WITH_POINT_POS = 3;
    private final int NO_POINT_POS = 2;

     private WarningLabelHolder warningHolder;

    // Instance Creation ******************************************************

    /**
     * Constructs a ShorthandTimeFormatter that accepts the superclass' format
     * strings as well as the HHMM shorthands.
     * 
     * @param format
     *            DateFormat instance used for converting from/to Strings
     */
    public TimeFormatter(DateFormat format, WarningLabelHolder warningHolder) {
        super(format);
        this.warningHolder = warningHolder;
    }

    // Overriding Superclass Behavior *****************************************

    /**
     * Returns the <code>Object</code> representation of the
     * <code>String</code> <code>text</code>.
     * <p>
     * 
     * First applies the super behavior, and if that fails, tries again with
     * shorthands expanded.
     * 
     * @param text
     *            <code>String</code> to convert
     * @return <code>Object</code> representation of text
     * @throws ParseException
     *             if there is an error in the conversion
     */
    public Object stringToValue(String text) throws ParseException {

        warningHolder.getWarningLabel().setVisible(false);

        if (!hasTextOnlyNumber(text)) {
            try {
                return super.stringToValue(null);
            } catch (Exception e) {
            }
        }

        // Now try to expand DDMM, DDMMYY, DDMMYYYY;, then parse the expansion.
        try {
            return super.stringToValue(expandShorthand(text.trim()));
        } catch (Exception e) {
            throw new ParseException("Shorthand must be HHMM.", 0);
        }
    }

    private boolean hasTextOnlyNumber(String text) {
        if(text == null || text.equals(""))
            return false;
        
        String[] value = text.trim().split("[0-9:]");
        if (value.length > 0 || text.trim().startsWith(":")) {
            if (value.length == 1 && value[0].equals("")) {
                return true;
            }
            showErrorToUser("Unzulässiges Zeichen!");
            return false;
        }
        
        if (text.trim().length() > 5) {
            showErrorToUser("Keine gültige Zeitangabe!");
            return false;
        }
        return true;
    }

    private String expandShorthand(String text) {
        StringBuffer buffer = new StringBuffer();

        if (text.length() < 3) {

            String testString = text.substring(0, 1);
            int testInt = new Integer(testString).intValue();
            if (testInt > 2) {
                showErrorToUser("Stundenangabe ungültig!");
                return new StringBuffer().toString();
            }

            testString = text.substring(0, 2);
            testInt = new Integer(testString).intValue();
            if (testInt > 23) {
                showErrorToUser("Stundenangabe ungültig!");
                return new StringBuffer().toString();

            }
        } else if (text.length() > 2) {
            int offset = text.startsWith(":", NO_POINT_POS)
                    ? WITH_POINT_POS
                    : NO_POINT_POS;
            String subString = text.substring(offset, text.length());
            String minuteVal = subString.substring(0, subString.length());
            int minute = new Integer(minuteVal).intValue();
            if (minute > 59 || minute < 0) {
                showErrorToUser("Minutenangabe ungültig!");
                return new StringBuffer().toString();
            }
            buffer.append(parseForTime(text, offset));
        }
        return buffer.toString();
    }

    private String parseForTime(String text, int offset) {
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat datfo = (SimpleDateFormat) getFormat();

        buffer.append(text.substring(0, 2));
        if (offset == NO_POINT_POS)
            buffer.append(separator());
        buffer.append(text.substring(2, text.length()));
        try {
            datfo.parse(buffer.toString());
        } catch (ParseException e) {
            return new StringBuffer().toString();

        }
        return text;

    }

    private void showErrorToUser(String text) {
        warningHolder.setNewWarnText(text);
        warningHolder.getWarningLabel().setVisible(true);
    }


}
