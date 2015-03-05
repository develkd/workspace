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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.CardPanel;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.application.model.AbstractMainModel;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractMainPageBuilder extends AbstractView {
	public static final ResourceBundle RESOURCE = ResourceUtil
			.getResourceBundle(AbstractMainPageBuilder.class);

	private final AbstractMainModel mainModule;
	private PanelBuilder builder;
	private JLabel emptyLabel;
	/**
	 * Holds all main pages: home, edit and other work pages.
	 */
	private CardPanel pages;

	// Instance Creation ******************************************************

	/**
	 * Constructs a MainPageBuilder for the given main module.
	 * 
	 * @param mainModule
	 *            provides high-level models
	 */
	public AbstractMainPageBuilder(AbstractMainModel mainModule) {
		this.mainModule = mainModule;
	}

	// Building ***************************************************************

	/**
	 * Creates and configures the UI components.
	 */
	private void initComponents() {
		emptyLabel = new JLabel(RESOURCE.getString("home.info.lable"));

		emptyLabel.setFont(emptyLabel.getFont().deriveFont(Font.BOLD, 14));
		emptyLabel.setForeground(Color.DARK_GRAY);

		pages = new CardPanel();
		mainModule.addPropertyChangeListener(
				AbstractMainModel.PROPERTY_CARDNAME,
				new MainCardChangeHandler());
	}

	protected abstract SimpleInternalFrame buildSideBar();

	protected CardPanel getCardPanel() {
		if (pages == null) {
			pages = new CardPanel();
		}
		return pages;
	}

	protected CardPanel buildPages() {
		getCardPanel().add(AbstractMainModel.CARDNAME_EMPTY, getEmptyView());
		return getCardPanel();
	}

	private JComponent getEmptyView() {
		SimpleInternalFrame emptyFrame = new SimpleInternalFrame("Aufgaben");
		FormLayout layout = new FormLayout("0:grow, pref, 0:grow",
				"0:grow, pref, 0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.getPanel().setBackground(Color.WHITE);
		builder.add(emptyLabel, new CellConstraints(2, 2));
		emptyFrame.setContent(builder.getPanel());
		return emptyFrame;
	}

	protected JPanel buildPanel() {
		initComponents();
		FormLayout layout = new FormLayout(
				"fill:[130dlu,p], 4dlu, fill:100dlu:grow", "fill:p:grow, p");

		builder = new PanelBuilder(layout);
		builder.setBorder(Borders.createEmptyBorder("6dlu, 4dlu, 6dlu, 4dlu"));

		CellConstraints cc = new CellConstraints();
		builder.add(buildSideBar(), cc.xy(1, 1));
		builder.add(buildPages(), cc.xy(3, 1));
		return builder.getPanel();
	}

	// Event Handling *********************************************************

	// Listens to main card changes and shows the new card.
	private class MainCardChangeHandler implements PropertyChangeListener {

		/**
		 * The main card has changed. Show this card in the card panel.
		 * 
		 * @param evt
		 *            describes the property change
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			String cardName = (String) evt.getNewValue();
			pages.showCard(cardName);
		}
	}

}
