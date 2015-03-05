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

package de.ev.iisin.application.desktop;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractMainPageView<T> extends AbstractView {

	private SimpleInternalFrame sidebar;
	private JComponent mainPanel;
	private T t;
	private PanelBuilder builder;

	public AbstractMainPageView(T t) {
		this.t = t;
	}

	protected abstract SimpleInternalFrame buildSideBar();

	protected abstract JComponent buildMainPanel();

	protected JPanel buildPanel() {
		if (builder == null) {
			FormLayout layout = new FormLayout(
					"fill:130dlu, 4dlu, fill:100dlu:grow",
					"fill:100dlu:grow, p");

			builder = new PanelBuilder(layout);
			builder.setBorder(Borders
					.createEmptyBorder("6dlu, 4dlu, 6dlu, 4dlu"));

			CellConstraints cc = new CellConstraints();
			builder.add(buildSideBar(), cc.xy(1, 1));
			builder.add(buildMainPanel(), cc.xy(3, 1));
			// builder.add(buildButtonPanel(), cc.xy(3, 2));
		}
		return builder.getPanel();
	}

	protected SimpleInternalFrame getSideBar() {
		if (sidebar == null) {
			sidebar = new SimpleInternalFrame("");
		}
		return sidebar;
	}

	public JComponent getMainPanel() {
		if (mainPanel == null) {
			mainPanel = buildMainPanel();
		}
		return mainPanel;
	}

	public T getModel() {
		return t;
	}

	protected JComponent buildPupilPanel(JComponent component) {

		JScrollPane scrollPane = new JScrollPane(component,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		//scrollPane.setBorder(BorderFactory.createEmptyBorder());
		return scrollPane;
	}
}
