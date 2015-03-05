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

package de.ev.iisin.application.desktop.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * @author Kemal Dönmez
 * 
 */
public class HeaderBorder extends AbstractBorder {
	
	
	/**
	 * Erzeugt am 05.03.2009
	 */
	private static final long serialVersionUID = 6800590065998191743L;

	static final Insets INSETS = new Insets(1, 1, 2, 1);

	private final Color outerS;
	private final Color innerWuNuE;
	private final Color innerS;
	private final Color innerNWuNE;
	private final Color innerSWuSE;

	public HeaderBorder() {
		outerS    =new Color(0x0F0F14);
		innerWuNuE=new Color(0x858587);
		innerS    =new Color(0xAAB5D0);
		innerNWuNE=new Color(0x959597);
		innerSWuSE=new Color(0x434347);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		g.translate(x, y);

		g.setColor(outerS);
		g.fillRect(1, h - 1, w - 2, 1);

		g.setColor(innerWuNuE);
		g.fillRect(1, 0, w - 2, 1);
		g.fillRect(0, 1, 1, h - 2);
		g.fillRect(w - 1, 1, 1, h - 2);

		g.setColor(innerS);
		g.fillRect(1, h - 2, w - 2, 1);

		// Inner Corners
		g.setColor(innerNWuNE);
		g.fillRect(0, 0, 1, 1);
		g.fillRect(w - 1, 0, 1, 1);

		g.setColor(innerSWuSE);
		g.fillRect(0, h - 1, 1, 1);
		g.fillRect(w - 1, h - 1, 1, 1);

		g.translate(-x, -y);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return INSETS;
	}

	@Override
	public Insets getBorderInsets(Component c, Insets newInsets) {
		newInsets.top = INSETS.top;
		newInsets.left = INSETS.left;
		newInsets.bottom = INSETS.bottom;
		newInsets.right = INSETS.right;
		return newInsets;
	}
}
