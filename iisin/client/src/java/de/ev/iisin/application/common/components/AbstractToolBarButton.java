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
import java.awt.Graphics;
import java.awt.Image;
import java.util.ResourceBundle;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.uif_lite.application.ResourceUtils;

/**
 * @author Kemal Dönmez
 *
 */
public abstract class AbstractToolBarButton extends JButton {
  private static final ResourceBundle RESOURCE_MAP = ResourceUtils
			.getResourceBundle(AbstractToolBarButton.class);
	    // Instance Fields ********************************************************

	    private final Image rolloverBackground;
	    private final Image pressedBackground;


	    // Instance Creation ******************************************************

	    AbstractToolBarButton() {
	        setFocusPainted(false);
	        setContentAreaFilled(false);
	        rolloverBackground = ResourceUtils.getImageIcon("ToolBarButton.rollover",AbstractToolBarButton.class).getImage();
	        pressedBackground  = ResourceUtils.getImageIcon("ToolBarButton.pressed",AbstractToolBarButton.class).getImage();
	        setBorder(new CompoundBorder(
	                new ToolBarButtonBorder(
	                        rolloverBackground,
	                        pressedBackground),
	                getInsetsBorder()));
	        setForeground(getCurrentColor());
	        getModel().addChangeListener(new ChangeListener() {
	            public void stateChanged(ChangeEvent e) {
	                setForeground(getCurrentColor());
	            }
	        });
	    }


	    public Image getBackgroundImage(){
	    	return rolloverBackground;
	    }
	    // Misc *******************************************************************

	    protected Border getInsetsBorder() {
	        return new EmptyBorder(2, 4, 2, 4);
	    }


	    @Override
	    protected void paintComponent(Graphics g) {
	        int width = getWidth();
	        int height = getHeight();
	        if (getModel().isPressed() && getModel().isArmed()) {
	            g.drawImage(pressedBackground, 4, 4, width-4, height-4, 4, 4, 22, 26, null);
	        } else if (getModel().isRollover()) {
	            g.drawImage(rolloverBackground, 4, 4, width-4, height-4, 4, 4, 32, 26, null);
	        }
	        super.paintComponent(g);
	    }


	    private Color getCurrentColor() {
	        ButtonModel m = getModel();
	        String foregroundKey = m.isEnabled()
	            ? "ToolBarButton.foreground"
	            : "ToolBarButton.disabledForeground";
	        String hex = RESOURCE_MAP.getString(foregroundKey);
	       String subHex = hex.substring(2);
	       int value = Integer.parseInt(subHex, 16);
	        return new Color(value);
	    }
}
