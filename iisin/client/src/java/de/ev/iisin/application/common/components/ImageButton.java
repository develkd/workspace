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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.utils.Dialogs;
import de.ev.iisin.application.common.utils.FileFiltererType;

/**
 * @author Kemal Dönmez
 * 
 */
public class ImageButton extends JButton {

	/**
	 * Erzeugt am 07.02.2009
	 */
	private static final long serialVersionUID = 4039668224271940015L;
	private int width, height;
	//private ImageIcon valueIcon;
	private ValueModel valueModel;

	public ImageButton(ValueModel valueModel) {
		this.valueModel = valueModel;
		setBorder(null);
		addActionListener(new Open());
		addAncestorListener(new AncestorHandler());
	}

	private void scaleImage(ImageIcon icon) {
		Dimension dim = getSize();
		width = dim.width - 10;
		height = dim.height - 10;
		Image image = icon.getImage().getScaledInstance(width, height,
				Image.SCALE_FAST);
		setIcon(new ImageIcon(image));
		
	}
	
	private void setImage(ImageIcon imageIcon){
		if(imageIcon == null)
			imageIcon = ResourceUtils.getImageIcon("empty_user.png",
					ImageButton.class);

		scaleImage(imageIcon);
		
	}


	private class Open implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File file = Dialogs.openFileChooser("Image", "",
					FileFiltererType.IMAGE);
			if (file == null)
				return;
			try {
				BufferedImage image = ImageIO.read(file);
				ImageIcon icon = new ImageIcon(image);
				setImage( icon);
				valueModel.setValue(icon);
			} catch (IOException e1) {
				e1.getMessage();
			}

		}

	}

	private class AncestorHandler implements AncestorListener {

		@Override
		public void ancestorAdded(AncestorEvent event) {
			setImage((ImageIcon)valueModel.getValue());
		}

		@Override
		public void ancestorMoved(AncestorEvent event) {
		}

		@Override
		public void ancestorRemoved(AncestorEvent event) {
		}

	}
}
