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

package de.ev.iisin.application.common.components;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.uif_lite.application.ResourceUtils;


/**
 * @author Kemal Dönmez
 *
 */
public class WarningLabelHolder extends JComponent {
	   /**
	 * Created at 15.10.2008
	 */
	private static final long serialVersionUID = 6908371310854015923L;
	private String WARN_ICON = "resources/images/warn_tsk.gif";
	    private String WARN_TEXT = "Zeilenanzahl";
	    private JLabel     warnungLabel;
	    
	    
	    public WarningLabelHolder(){
	        super();
	        initWaringLabel();
	    }
	    
	    protected void initWaringLabel(){
	        Icon warnIcon = ResourceUtils.readImageIcon(WARN_ICON);
	        warnungLabel = new JLabel(WARN_TEXT, warnIcon, JLabel.LEFT);
	        warnungLabel.setIcon(warnIcon);
	        warnungLabel.setText(WARN_TEXT);
	        warnungLabel.setAlignmentY(JLabel.LEFT);
	        warnungLabel.setForeground(Color.red);
	        warnungLabel.setVisible(false);

	    }
	   
	    //**** public API **************************************************************

	    public JLabel getWarningLabel(){
	        return warnungLabel;
	    }
	    
	    public void setNewWarnText(String text){
	        warnungLabel.setText(text);
	    }
	    
	    public void setNewWarnIcon(Icon icon){
	        warnungLabel.setIcon(icon);
	    }
	    
	    public void setFontColor(Color color){
	        warnungLabel.setForeground(color);
	    }

}
