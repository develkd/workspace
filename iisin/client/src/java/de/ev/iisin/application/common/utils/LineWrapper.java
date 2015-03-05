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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Doenmez
 * @version $Revision: 1.2 $
 */
public final class LineWrapper {
	private LineWrapper() {
		// Suppresses default constructor, ensuring non-instantiability.
	}
    

    // Public API *************************************************************
    
	  public static String[] getZeilen(String regex) {
		  return getZeilen(regex,1);
	  }
	  
	  public static String[] getZeilen(String regex, int maxLinelength) {
	        if (regex == null || regex.length() == 0) {
	            return new String[0];   
	        } else {
	            try {
	                return wrap(regex, maxLinelength);
	            } catch (IllegalArgumentException e) {
	                return new String[0];
	            }
	        }
	    }

    /**
     * Scans the given source String to break it into lines. A new line
     * is started either if the given maximum line length would be exceeded,
     * or if a newline character has been found in the source. 
     * 
     * @param source          the text to wrap and break into lines
     * @param maxLineLength   the maximum number of characters in a line
     * @return an array of Strings the represent the lines
     * 
     * @throws IllegalArgumentException if a word exceeds the maximum line length
     */
    private static String[] wrap(String source, int maxLineLength) {
        StringTokenizer tokenizer = new StringTokenizer(source, "-=\t\n\r\f", true);
        List<String> lines = new ArrayList<String>();
        StringBuffer currentLine = new StringBuffer();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            boolean isLineBreak = token.equals("\n");
            boolean isSpace     = token.equals(" ");
            boolean isMinus     = token.equals("-");
            boolean exceedsLine = currentLine.length() + token.length() > maxLineLength;
            if ((isLineBreak || exceedsLine) /*&& currentLine.length() > 0*/ ) {
                if (isMinus) currentLine.append(token);
                String lineString = currentLine.toString();
                if(!lineString.equalsIgnoreCase("")){
                lines.add(lineString);
                currentLine = new StringBuffer();
                }
            }
            if (!isLineBreak && !(exceedsLine && (isSpace || isMinus))) {
                currentLine.append(token);
            }
        }
        lines.add(currentLine.toString());    
        return (String[]) lines.toArray(new String[lines.size()]);
    }
    
   
}
