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

import java.text.Format;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

import com.jgoodies.uif_lite.util.Worker;


/**
 * @author Kemal Dönmez
 *
 */
public class PositiveNumberFormatter extends NumberFormatter {
    
	/**
	 * Created at 07.09.2008
	 */
	private static final long serialVersionUID = -7617187774360547677L;
	
	private boolean hasErrorShown = false;
    
	
    public PositiveNumberFormatter(Format format, Class<?> valueCalss){
		setFormat(format);
		setValueClass(valueCalss);

    }


    private synchronized void setErrorShownStatus(boolean status){
        hasErrorShown = status;
    }
    private synchronized boolean getErrorShownStatus(){
        return hasErrorShown;
    }

 
    /**
     * Overrides the super-methode to show the user an information on
     * a ParseException.
     * 
     */
    public Object stringToValue(String text) throws ParseException {
        if(!hasOnlyPositivNumber(text)){
            text = null;
            if(!getErrorShownStatus()){
                ThreadErrorMessage thread = new ThreadErrorMessage();
                thread.start();
            }
            setErrorShownStatus(true);
            return  super.stringToValue(text);
        }
        setErrorShownStatus(false);
        return super.stringToValue(text);
    }

    private boolean hasOnlyPositivNumber(String text){
        if(text == null)
            return true;
        //Alle anderen Zeichen werden im Array abgelegt
        String[] value = text.trim().split("[0-9.]");
        if(value.length == 1){
            if(!value[0].equals(""))
                return false;
        }

        if(value.length > 1){
           return false;
        }
         return true;
    }

    
    private class ThreadErrorMessage extends Worker{

        public Object construct() {
            return null;
        }

        public void finished() {
            setErrorShownStatus(false);
        }

         
    }
}
