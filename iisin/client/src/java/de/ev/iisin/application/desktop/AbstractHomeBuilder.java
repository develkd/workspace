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

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.CardPanel;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractHomeBuilder<M> extends AbstractView {
	private static final ResourceBundle RESOURCE = ResourceUtil
			.getResourceBundle(AbstractHomeBuilder.class);

	protected static final String INSTRUCTION = RESOURCE.getString("home.instruction");
	protected static final String EMPTY = RESOURCE.getString("home.title");
	protected static final String SELECTED = RESOURCE.getString("home.selection");

	public static final String CARDNAME_EMPTY = "empty";

	public static final String CARDNAME_MAIN = "main";

	private SimpleInternalFrame mainPanel;

	private JLabel noSelectionLabel;
	private String selectedTitle;
	private String emptyTitle;
	private CardPanel cardPanel;
	private M m;

	public AbstractHomeBuilder(M m, String emptyTitle, String selectedTitle,
			String instruction) {
		this.m = m;
		this.selectedTitle = selectedTitle;
		this.emptyTitle = emptyTitle;
		initView(instruction);
	}

	protected abstract JComponent buildMainPanel();

	protected abstract void initComponents();

	protected abstract void initEvents();

	protected CardSelectionChangedHandler getHandler() {
		return new CardSelectionChangedHandler();
	}

	protected M getModel() {
		return m;
	}

	protected void initView(String instruction) {
		noSelectionLabel = new JLabel(instruction);

		noSelectionLabel.setFont(noSelectionLabel.getFont().deriveFont(
				Font.BOLD, 14));
		noSelectionLabel.setForeground(Color.DARK_GRAY);
		mainPanel = new SimpleInternalFrame(emptyTitle);
		mainPanel.setEnabled(true);
		cardPanel = new CardPanel();
	}

	protected CardPanel getCardPanel() {
		return cardPanel;
	}

	public JPanel buildPanel() {
		mainPanel.setContent(buildCardPanel());
		return mainPanel;
	}

	@SuppressWarnings("unchecked")
	protected JComponent getButtonBar() {
		ButtonBarBuilder2 builder = ButtonBarBuilder2
				.createLeftToRightBuilder();
		builder.addButton(((AbstractHomeModel) getModel()).getActions());
		return builder.getPanel();
	}

	protected JComponent buildCardPanel() {
		cardPanel.add(buildInstructionPanel(), CARDNAME_EMPTY);
		cardPanel.add(buildViewPanel(), CARDNAME_MAIN);
		return cardPanel;
	}

	protected JComponent buildViewPanel() {
		initComponents();
		initEvents();
		FormLayout layout = new FormLayout("fill:p:grow",
				"fill:p:grow, 10dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setBorder(Borders.DLU14_BORDER);

		CellConstraints cc = new CellConstraints();
		builder.add(buildMainPanel(), cc.xy(1, 1));
		builder.add(getButtonBar(), cc.xy(1, 3));
		return builder.getPanel();
	}

	private JComponent buildInstructionPanel() {
		FormLayout layout = new FormLayout("0:grow, pref, 0:grow",
				"0:grow, pref, 0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.getPanel().setBackground(Color.WHITE);
		builder.add(noSelectionLabel, new CellConstraints(2, 2));
		return builder.getPanel();
	}

	private class CardSelectionChangedHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			boolean selectionEmpty = Boolean.TRUE.equals(evt.getNewValue());
			mainPanel.setTitle(selectionEmpty ? emptyTitle : selectedTitle);
			getCardPanel().showCard(
					selectionEmpty ? CARDNAME_EMPTY : CARDNAME_MAIN);
		}
	}
}
