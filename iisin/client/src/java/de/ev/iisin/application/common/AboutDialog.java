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

package de.ev.iisin.application.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.AbstractDialog;
import com.jgoodies.uif_lite.application.ResourceUtils;

/**
 * @author Kemal Dönmez
 * 
 */
public class AboutDialog extends AbstractDialog {
	/**
	 * Created at 07.09.2008
	 */
	private static final long serialVersionUID = 4149098492207613643L;

	private final String VERSION = "1.0.0";

	private JLabel logoLabel;
	private JLabel versionLabel;
	private JLabel copyRightLabel;
	private JLabel byLabel;
	private JLabel supportLabel;
	private Font font;

	/**
	 * @param owner
	 */
	public AboutDialog(Frame owner) {
		super(owner, "I-ISIN e.V.");
	}

	private void init() {
		Icon logoIcon = ResourceUtils.getImageIcon("about.png", AboutDialog.class);
		logoLabel = new JLabel(logoIcon);
		Font defFont = getFont();
		font = new Font(defFont.getName(), defFont.getStyle(), defFont
				.getSize() + 5);
		versionLabel = new JLabel("Version:" + VERSION);
		versionLabel.setFont(font);
		copyRightLabel = new JLabel("\u00a9 2009 I-ISIN e.V.");
		copyRightLabel.setFont(font);
		byLabel = new JLabel("Kemal D\u00f6nmez");
		supportLabel = new JLabel("email: kemaldoenmez@googlemail.com");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jgoodies.uif_lite.AbstractDialog#buildContent()
	 */
	protected JComponent buildContent() {
		init();
		FormLayout layout = new FormLayout("fill:pref",
				"fill:pref, fill:pref,8dlu, pref");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.getPanel().setBorder(new EmptyBorder(12, 10, 10, 10));
		CellConstraints cc = new CellConstraints();
		builder.add(buildAboutPanel(), cc.xy(1, 1));
		builder.add(buildVersionPanel(), cc.xy(1, 2));
		builder.add(buildButtonBarWithClose(), cc.xy(1, 4));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.WHITE);
		return panel;
	}

	/**
	 * Builds and returns the main panel.
	 */
	private JComponent buildAboutPanel() {
		FormLayout layout = new FormLayout("fill:pref:grow",
				"fill:pref:grow, 10dlu, fill:pref:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(logoLabel, cc.xy(1, 1));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.WHITE);
		return panel;
	}

	private JComponent buildVersionPanel() {
		FormLayout layout = new FormLayout("p, 10dlu,p:grow",
				"p, 10dlu, p,2dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(versionLabel, cc.xy(1, 1));
		builder.add(copyRightLabel, cc.xy(3, 1));
		builder.add(byLabel, cc.xyw(1, 3, 3));
		builder.add(supportLabel, cc.xyw(1, 5, 3));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.WHITE);
		return panel;
	}

	protected JComponent buildButtonBarWithClose() {
		JComponent bar = super.buildButtonBarWithClose();
		bar.setBackground(Color.WHITE);
		return bar;
	}
}
