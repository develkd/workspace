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

package de.ev.iisin.application.common.components.renderer;

import java.awt.Component;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;

/**
 * @author doenmez
 * @version $Revision: 1.1 $
 */
public class ValueOrientationCellRenderer extends StripedTableCellRenderer {
	private static final long serialVersionUID = -6196775674800211255L;
	private boolean isSumHeader;
	
	public ValueOrientationCellRenderer(boolean isSumHeader){
		this.isSumHeader = isSumHeader;
	}

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        boolean isInteger = false;
        String text = label.getText();
       	try{
    		Integer.parseInt(text);
    		isInteger = true;
    	}catch(Exception e){
    		
    	}
        
    	if(isSumHeader){
        	label = new JLabel(text);
        }
 
        if(value instanceof Integer || value instanceof Double|| (isSumHeader && isInteger)){
        	label.setHorizontalAlignment(JLabel.RIGHT);
        }else
        if(value instanceof Date){
        	label.setHorizontalAlignment(JLabel.CENTER);
        }else{
        	if(text.endsWith("%")){
        		label.setHorizontalAlignment(JLabel.RIGHT);
        	}else{
        	label.setHorizontalAlignment(JLabel.LEFT);
        	}
        }
  
        return label;
    }
}
