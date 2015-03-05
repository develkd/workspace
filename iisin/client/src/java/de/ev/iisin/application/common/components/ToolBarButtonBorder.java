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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.AbstractBorder;

/**
 * @author Kemal Dönmez
 *
 */
public class ToolBarButtonBorder extends AbstractBorder {
	
	/**
	 * Erzeugt am 02.03.2009
	 */
	private static final long serialVersionUID = -481199241968546801L;

	static final Insets INSETS = new Insets(4, 4, 4, 4);

	private final Image rolloverImage;
	private final int rolloverWidth;
	private final int rolloverHeight;
	private final Image pressedImage;
	private final int pressedWidth;
	private final int pressedHeight;

	protected ToolBarButtonBorder(Image rolloverImage, Image pressedImage) {
		this.rolloverImage = rolloverImage;
		this.rolloverWidth = rolloverImage.getWidth(null);
		this.rolloverHeight = rolloverImage.getHeight(null);
		this.pressedImage = pressedImage;
		this.pressedWidth = pressedImage.getWidth(null);
		this.pressedHeight = pressedImage.getHeight(null);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Image image;
		int width;
		int height;
		AbstractButton b = (AbstractButton) c;
		ButtonModel m = b.getModel();
		if (m.isPressed() && m.isArmed()) {
			image = pressedImage;
			width = pressedWidth;
			height = pressedHeight;
		} else if (m.isRollover()) {
			image = rolloverImage;
			width = rolloverWidth;
			height = rolloverHeight;
		} else {
			return;
		}
		g.translate(x, y);

		g.drawImage(image, 0, 0, 5, 4, 0, 0, 5, 4, null); // north west
		g.drawImage(image, 5, 0, w - 5, 4, 5, 0, width - 5, 4, null); // north
		g.drawImage(image, w - 5, 0, w, 4, width - 5, 0, width, 4, null); // north
																			// east

		g.drawImage(image, 0, 4, 4, h - 4, 0, 4, 4, height - 4, null); // west
		g.drawImage(image, w - 4, 4, w, h - 4, width - 4, 4, width, height - 4,
				null); // west

		g.drawImage(image, 0, h - 4, 4, h, 0, height - 4, 4, height, null); // west
		g.drawImage(image, 4, h - 4, w - 4, h, 4, height - 4, width - 4,
				height, null); // west
		g.drawImage(image, w - 4, h - 4, w, h, width - 4, height - 4, width,
				height, null); // west

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
