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
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.uif_lite.application.ResourceUtils;

/**
 * @author Kemal Dönmez
 * 
 */
public class ToolBarMenuButton extends AbstractToolBarButton {

	/**
	 * Erzeugt am 02.03.2009
	 */
	private static final long serialVersionUID = -3250623958005387430L;
	private static final ResourceBundle RESOURCE_MAP = ResourceUtils
			.getResourceBundle(ToolBarMenuButton.class);
	private static ActionListener showMenuListener;

	private final Icon downIcon;
	private final int downIconTextGap;

	private JPopupMenu popupMenu;

	// Instance Creation ******************************************************

	public ToolBarMenuButton(Action action, JPopupMenu popupMenu) {
		setAction(action);
		downIcon = ResourceUtils.getImageIcon("ToolBarMenuButton.downIcon",ToolBarMenuButton.class);
		downIconTextGap = new Integer(RESOURCE_MAP
				.getString("ToolBarMenuButton.downIconTextGap")).intValue();
		setMenu(popupMenu);
		if (showMenuListener == null) {
			showMenuListener = new ShowMenuActionListener();
		}
		addActionListener(showMenuListener);
	}

	// Accessors **************************************************************

	public JPopupMenu getMenu() {
		return popupMenu;
	}

	public void setMenu(JPopupMenu popupMenu) {
		if (popupMenu == null) {
			throw new IllegalArgumentException(
					"The popup menu must not be null.");
		}
		this.popupMenu = popupMenu;
	}

	// Overriding Superclass Behavior *****************************************

	@Override
	protected Border getInsetsBorder() {
		return new EmptyBorder(2, 0, 2, 8);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Insets insets = getInsets();
		int width = getWidth();
		int height = getHeight();
		int x = width - (insets.right - 3) - downIcon.getIconWidth();
		int y = insets.top
				+ 1
				+ (height - insets.top - insets.bottom - downIcon
						.getIconHeight()) / 2;
		downIcon.paintIcon(this, g, x, y);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension prefSize = super.getPreferredSize();
		int downIconWidth = downIcon.getIconWidth();
		return new Dimension(prefSize.width + downIconWidth + 2
				* downIconTextGap, prefSize.height);
	}

	private static final class ShowMenuActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ToolBarMenuButton b = (ToolBarMenuButton) e.getSource();
			b.getMenu().show(b, 0, b.getHeight() + 1);
		}
	}

}
