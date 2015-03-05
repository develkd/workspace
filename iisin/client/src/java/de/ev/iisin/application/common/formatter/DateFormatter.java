/*
 * Copyright (c) 2008 Kemal D�nmez. All Rights Reserved.
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
import java.util.GregorianCalendar;

import com.jgoodies.binding.extras.ShorthandDateFormatter;


import de.ev.iisin.application.common.components.WarningLabelHolder;

/**
 * @author Kemal D�nmez
 *
 */
public class DateFormatter extends ShorthandDateFormatter {
	   /**
	 * Created at 15.10.2008
	 */
	private static final long serialVersionUID = 6775416598418492663L;
	private WarningLabelHolder warningHolder;
	    private final int WITH_POINT_POS = 3;
	    private final int NO_POINT_POS = 2;

	    public DateFormatter(DateFormat format, WarningLabelHolder warningHolder) {
	        super(format);
	        this.warningHolder = warningHolder;
	    }

	    /**
	     * Overrides the super-methode to show the user an information on a
	     * ParseException.
	     *  
	     */
	    public Object stringToValue(String text) throws ParseException {
	        warningHolder.getWarningLabel().setVisible(false);

	        if (!hasTextOnlyNumber(text)) {
	            try {
	                return super.parse(null);
	            } catch (Exception e) {
	            }
	        }

	        // Now try to expand DDMM, DDMMYY, DDMMYYYY;, then parse the expansion.
	        try {
	            return super.parse(expandShorthand(text.trim()));
	        } catch (Exception e) {
	            throw new ParseException(
	                    "Shorthand must be one of DDMM, DDMMYY, DDMMYYYY.", 0);
	        }
	    }

	    private boolean hasTextOnlyNumber(String text) {
	        if(text == null || text.equals(""))
	            return false;

	        String[] value = text.trim().split("[0-9.]");
	        if (value.length > 0 || text.trim().startsWith(".")) {
	            if (value.length == 1 && value[0].equals("")) {
	                return true;
	            }
	            showErrorToUser("Unzul�ssiges Zeichen!");
	            return false;
	        }
	        return true;

	    }

	    private String expandShorthand(String text) {
	        StringBuffer buffer = new StringBuffer();

	        if (text.length() < 3) {

	            String testString = text.substring(0, 1);
	            int testInt = new Integer(testString).intValue();
	            if (testInt > 3) {
	                showErrorToUser("Kein g�ltiger Tag!");
	                return new StringBuffer().toString();
	            }

	            testString = text.substring(0, 2);
	            testInt = new Integer(testString).intValue();
	            if (testInt > 31) {
	                showErrorToUser("Kein g�ltiger Tag!");
	                return new StringBuffer().toString();
	            }
	        }else if (text.length()  == 3) {
	            showErrorToUser("Kein g�ltiges Datum!");
	            return new StringBuffer().toString();
	        } 
	        else if (text.length() > 2 && text.length() < 6) {
	            int offset = text.startsWith(".", NO_POINT_POS)
	            ? WITH_POINT_POS
	            : NO_POINT_POS;

	           String subString = text.substring(offset,  2 + offset);

	            String[] temp = text.split("[.]");
	            if(temp.length > 1){
	                subString = temp[1];
	            }

	            String monthVal = subString.substring(0, subString.length());
	            int month = new Integer(monthVal).intValue();
	            if (month > 12 || month < 1) {
	                showErrorToUser("Kein g�ltiger Monat!");
	                return new StringBuffer().toString();
	            }
	            buffer.append(parseForDate(text));
	        } else {
	            buffer.append(parseForDate(text));
	        }
	        return buffer.toString();

	    }

	    private String parseForDate(String text) {
	        StringBuffer buffer = new StringBuffer();
	        int year = new GregorianCalendar().get(java.util.Calendar.YEAR);
	        String yyyy = Integer.toString(year);
	        
//	        SimpleDateFormat datfo = (SimpleDateFormat) getFormat();
	        String[] temp = text.split("[.]");
	        //Punktnotation
	        if(temp.length > 1){
	            buffer.append(text);
	            if(temp.length > 2){
	                String tmpYear = temp[2];
	               if(tmpYear.length() == 2){
	                   buffer = new StringBuffer();
	                   String substring = text.substring(0, text.length()-2);
	                   buffer.append(substring);
	                   buffer.append(yyyy.substring(0,2));
	                   buffer.append(tmpYear);
	               }
	                
	            }else{
	                buffer.append(yyyy);
	            }
	            
	        }else{
	            buffer.append(text.substring(0,2));
	            buffer.append(separator());
	            buffer.append(text.substring(2,4));
	  
	           if (text.length() == 4) {
	                buffer.append(separator());
	                buffer.append(yyyy);
	            } else if (text.length() == 6) {
	                buffer.append(separator());
	                buffer.append(yyyy.substring(0,2));
	                buffer.append(text.substring(4,6));
	            }
	        }

	   
	        try {
	           // datfo.parse(buffer.toString());
	           parse(buffer.toString());
	        } catch (ParseException e) {
	            return new StringBuffer().toString();

	        }
	        return buffer.toString();

	    }

	    private void showErrorToUser(String text) {
	        warningHolder.setNewWarnText(text);
	        warningHolder.getWarningLabel().setVisible(true);
	    }


}
