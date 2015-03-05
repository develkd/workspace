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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;

import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public class ImageBackgroundPanel extends JPanel {
	/**
	 * Erzeugt am 05.03.2009
	 */
	private static final long serialVersionUID = -8628666562795189535L;
	
	
	private final Image image;
	private final int sx1;
	private final int sy1;
	private final int sx2;
	private final int sy2;

	// Instance Creation
	// ******************************************************

	public ImageBackgroundPanel(Image image, int sx1, int sy1, int sx2, int sy2) {
		this.image = image;
		this.sx1 = sx1;
		this.sy1 = sy1;
		this.sx2 = sx2;
		this.sy2 = sy2;
	}

	public static ImageBackgroundPanel createFrom() {
		Image image = ResourceUtils.getImageIcon("header.background",ImageBackgroundPanel.class).getImage();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new ImageBackgroundPanel(image, 0, 0, width, height);
	}

	// Painting
	// ***************************************************************

	@Override
	protected void paintComponent(Graphics g) {
		Insets insets = getInsets();
		int x1 = insets.left;
		int y1 = insets.top;
		int x2 = getWidth() - insets.right;
		int y2 = getHeight() - insets.bottom;
		g.drawImage(image, x1, y1, x2, y2, sx1, sy1, sx2, sy2, null);
	}

}
