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

import java.io.Serializable;
import java.util.*;

/**
 * Describes a validation result as a list of <code>ValidationMessage</code>
 * instances. You can add single validation messages, single text messages, 
 * lists of messages, and all messages from another ValidationResult.<p>
 * 
 * The underlying {@link java.util.List} implementation is an instance of
 * {@link java.util.LinkedList}, so adding single or multiple message
 * and sequentially visiting messages performs well.
 * 
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 * 
 * @see     SimpleValidationMessage
 * @see     ValidationCapable
 */

@SuppressWarnings("all")
public final class SimpleValidationResult implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 852795716620860653L;
	/** 
     * Holds a <code>List</code> of <code>ValidationMessage</code> instances.
     */
    private final List messageList;
    
    
    // Instance Creation ******************************************************
    
    /**
     * Constructs an empty <code>ValidationResult</code> object.
     */
    public SimpleValidationResult() {
        this(new LinkedList());
    }
    
    /**
     * Constructs a <code>ValidationResult</code> on the given message list.
     * Used for constructing the <code>EMPTY</code> validation result.
     * 
     * @param messageList   an initial message list
     */
    private SimpleValidationResult(List messageList) {
        this.messageList = messageList;
    }
    

    // Adding Messages ********************************************************
    
    /**
     * Adds a new <code>ValidationMessage</code> to the list of messages.
     * 
     * @param validationMessage   the message to add
     * 
     * @throws NullPointerException if the message is <code>null</code>
     * 
     * @see #addError(String)
     * @see #addWarning(String)
     */
    private void add(SimpleValidationMessage validationMessage) {
        messageList.add(validationMessage);
    }
    
    
    /**
     * Creates and adds an error message to the list of validation messages 
     * using the given text.
     * 
     * @param text   the error text to add
     * 
     * @throws NullPointerException if the message text <code>null</code>
     * 
     * @see #add(ValidationMessage)
     * @see #addWarning(String)
     */
    public void addError(String text) {
        if (text == null)
            throw new NullPointerException("The message text must not be null.");
        
        messageList.add(new SimpleValidationMessage(text, 
                SimpleValidationMessage.SEVERITY_ERROR));
    }
    
    
    /**
     * Creates and adds a warning message to the list of validation messages 
     * using the given text
     * 
     * @param text   the warning text to add
     * 
     * @throws NullPointerException if the message text <code>null</code>
     * 
     * @see #add(ValidationMessage)
     * @see #addError(String)
     */
    public void addWarning(String text) {
        if (text == null)
            throw new NullPointerException("The message text must not be null.");
        
        messageList.add(new SimpleValidationMessage(text, 
                SimpleValidationMessage.SEVERITY_WARNING));
    }
    
    
    /**
     * Checks and answers whether this list contains no elements.
     *
     * @return true if this list contains no elements
     * 
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public boolean isEmpty() {
        return messageList.isEmpty();
    }
    
    
    /**
     * Returns the number of messages in this result. 
     *
     * @return the number of elements in this list
     */
    public int size() {
        return messageList.size();
    }
    

    /**
     * Returns the highest severity of this result's messages, 
     * <code>null</code> if there are no messages.
     * 
     * @return the highest severity of this result's messages,
     *     <code>null</code> if there are no messages
     * 
     * @see #hasMessages()
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    private int getSeverity() {
        return getSeverity(getMessages());
    }
        

    /**
     * Checks and answers whether this validation result has messages or not.
     * 
     * @return true if there are messages, false if not
     * 
     * @see #getSeverity()
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public boolean hasMessages() {
        return !isEmpty();
    }
    
    
    /**
     * Checks and answers whether this validation result has a message 
     * of type <code>ERROR</code>.
     * 
     * @return true if there are error messages, false if not
     * 
     * @see #getSeverity()
     * @see #hasMessages()
     * @see #hasWarnings()
     */
    public boolean hasErrors() {
        return hasSeverity(getMessages(), SimpleValidationMessage.SEVERITY_ERROR);
    }
    
    
    /**
     * Checks and answers whether this validation result has a message 
     * of type <code>WARNING</code>.
     * 
     * @return true if there are warnings, false if not
     * 
     * @see #getSeverity()
     * @see #hasMessages()
     * @see #hasErrors()
     */
    public boolean hasWarnings() {
        return hasSeverity(getMessages(), SimpleValidationMessage.SEVERITY_WARNING);
    }
    
    
    /**
     * Returns an unmodifiable <code>List</code> of all validation messages. 
     * 
     * @return the <code>List</code> of all validation messages
     */
    public List getMessages() {
        return Collections.unmodifiableList(messageList);
    }
    
    
    /**
     * Returns a string representation intended for debugging purposes.
     * 
     * @return a string representation intended for debugging
     * @see Object#toString()
     */
    public String toString() {
        if (isEmpty()) 
            return "Empty ValidationResult";
            
        StringBuffer buffer = new StringBuffer("ValidationResult:");
        for (Iterator it = messageList.iterator(); it.hasNext();) {
            buffer.append("\n\t").append(it.next());
        }

        return buffer.toString();
    }    
    
    
    // Helper Code ***********************************************************
    
    /**
     * Returns the highest severity of this result's messages, 
     * <code>Severity.OK</code> if there are no messages.
     * 
     * @param messages  the <code>List</code> of ValidationMessages to check
     * @return the highest severity of this result's messages
     */
    private static int getSeverity(List messages) {
        if (messages.isEmpty())
            return SimpleValidationMessage.SEVERITY_OK;
        for (Iterator it = messages.iterator(); it.hasNext();) {
            SimpleValidationMessage message = (SimpleValidationMessage) it.next();
            if (message.severity() == SimpleValidationMessage.SEVERITY_ERROR)
                return SimpleValidationMessage.SEVERITY_ERROR;
        }
        return SimpleValidationMessage.SEVERITY_WARNING;
    }
        
    /**
     * Checks and answers whether the given list of validation messages
     * includes message with the specified Severity.
     * 
     * @param messages  the <code>List</code> of ValidationMessages to check
     * @param severity  the <code>Severity</code> to check
     * @return true if the given messages list includes error messages, 
     *     false if not
     */
    private static boolean hasSeverity(List messages, int severity) {
        for (Iterator it = messages.iterator(); it.hasNext();) {
            SimpleValidationMessage message = (SimpleValidationMessage) it.next();
            if (message.severity() == severity)
                return true;
        }
        return false;
    }  
    
    
}