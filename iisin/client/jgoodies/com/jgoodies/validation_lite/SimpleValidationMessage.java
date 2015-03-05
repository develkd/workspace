/*
 * Copyright (c) 2003-2005 JGoodies Karsten Lentzsch. All Rights Reserved.
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

package com.jgoodies.validation_lite;


/**
 * An abstract class that minimizes the effort required to implement the 
 * {@link SimpleValidationMessage} interface. Holds the severity, a text message,
 * and the association key.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 */

public final class SimpleValidationMessage {
    
    static final int SEVERITY_OK      = 0;
    static final int SEVERITY_WARNING = 1;
    static final int SEVERITY_ERROR   = 2;
    

    /**
     * Holds this message's severity, either error or warning.
     */
    private final int severity;
    
    /** 
     * Holds the text messages.
     */
    private final String text;
    

    // Instance Creation ******************************************************

    /**
     * Constructs an AbstractValidationMessage for the given text and Severity.
     * 
     * @param text       describes this message
     * @param severity   this message's severity, either error or warning
     * 
     * @throws IllegalArgumentException if severity is <code>Severity.OK</code>
     */
    public SimpleValidationMessage(String text, int severity) {
        this.text = text;
        this.severity = severity;
    }

    
    // Implementation of the ValidationMessage API ****************************

    /**
     * Returns this message's severity, either error or warning.
     * 
     * @return message's severity, either error or warning
     */
    int severity() {
        return severity;
    }
    
    
    /**
     * Returns a message description as formatted text. This default 
     * implementation just returns the message text. 
     * Subclasses may override to add information about the type and
     * other message related information.
     * 
     * @return a message description as formatted text
     */
    public String formattedText() {
        return text;
    }
    
    
    /**
     * Returns a string representation of this validation message.
     * Prints the class name and the formatted text.
     * 
     * @return a string representaiton of this validation message
     * @see Object#toString()
     */
    public String toString() {
        return getClass().getName() + ": " + formattedText();
    }

}