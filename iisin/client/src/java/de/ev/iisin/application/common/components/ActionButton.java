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
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Kemal Dönmez
 * 
 */
public class ActionButton extends JPanel {
	/**
	 * Erzeugt am 29.01.2009
	 */
	private static final long serialVersionUID = -1399247656336877242L;

	private static final Color DARK_BLUE = new Color(0, 51, 153);
	private static final Color PALE_BLUE = new Color(122, 187, 255);

	public ActionButton(Action action) {
		addComponents(createActionComponent(action));

	}

	private void addComponents(JComponent comp) {

		FormLayout layout = new FormLayout("fill:p:grow", "p,4dlu");
		setLayout(layout);
		CellConstraints cc = new CellConstraints();
		add(comp, cc.xy(1, 1));
	}

	private JComponent createActionComponent(Action action) {

		final JButton button = new JButton(action);
		Font font = button.getFont();
		font = font.deriveFont(font.getSize2D()+2);
		button.setFont(font);

		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setIcon(getIconFor(action));
		button.setToolTipText(null);
		
		button.setHorizontalAlignment(SwingConstants.LEFT);
		final Border myBorder = BorderFactory.createCompoundBorder(
				new LineBorder(DARK_BLUE), new EmptyBorder(2, 4, 2, 4));
		button.setBorder(myBorder);

		button.setBackground(PALE_BLUE);
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				button.setContentAreaFilled(true);
				button.setBorderPainted(true);
			}

			public void mouseExited(MouseEvent event) {
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
			}
		});
		return button;
	}

	/**
	 * Looks up the icon from the given action and returns it, or the default
	 * icon, if the action has no individual icon set.
	 */
	private Icon getIconFor(Action action) {
		Icon icon = (Icon) action.getValue(Action.SMALL_ICON);
		return icon;
	}

}
