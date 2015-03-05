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

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.common.person.domain.Person;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilCellRenderer extends DefaultListCellRenderer {

	/**
	 * Created at 04.10.2008
	 */
	private static final long serialVersionUID = -8014653367453034861L;

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);

		Person benutzer = (Person) value;
		return createImagePanel(benutzer);
	}

	private JPanel createImagePanel(Person benutzer) {
		ImageIcon icon = benutzer.getMinimizedImageIcon();
		icon = icon == null ? ResourceUtils.getImageIcon("empty_user.png",
				PupilCellRenderer.class) : icon;
		JLabel label = new JLabel(icon);
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		FormLayout layout = new FormLayout("2dlu,30dlu, 5dlu, fill:p:grow",
				"1dlu,fill:p:grow, 1dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(label, cc.xy(2,2));
		builder.add(createNamePanel(benutzer), cc.xy(4, 2));
		JPanel panel = builder.getPanel();
		panel.setForeground(getForeground());
		panel.setBackground(getBackground());
		return panel;

	}

	private JPanel createNamePanel(Person benutzer) {
		FormLayout layout = new FormLayout("p ", "fill:0:grow,p, 1dlu, p,fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel(benutzer.getName(), cc.xy(1, 2));
		builder.addLabel(benutzer.getLastName(), cc.xy(1, 4));
		JPanel panel = builder.getPanel();
		panel.setOpaque(false);
		return panel;
	}

}
