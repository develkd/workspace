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

package de.ev.iisin.view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.Application;
import com.jgoodies.uif_lite.panel.CardPanel;

import de.ev.iisin.application.desktop.component.ImageBackgroundPanel;
import de.ev.iisin.application.model.MainModel;
import de.ev.iisin.maintenance.MaintenaceHomeModel;
import de.ev.iisin.maintenance.MaintenaceHomeView;
import de.ev.iisin.pupil.PupilMainModel;
import de.ev.iisin.pupil.PupilMainPageBuilder;

/**
 * @author Kemal Dönmez
 * 
 */
public class MainPageBuilder {
	/**
	 * Refers to the module that provides all high-level models and submodels.
	 */
	private final MainModel mainModule;

	/**
	 * Holds all main pages: home, edit and other work pages.
	 */
	private CardPanel pages;
	private JButton backButton; // header navigation
	private JButton nextButton;

	private JPanel contextActionContainer; // header center: toolbar
	private JButton searchButton; // header popup for quicksearch

	private JPanel navigationActionContainer; // side bar top
	private JPanel applicationActionContainer; // side bar middle
	private JPanel documentActionContainer; // side bar bottom

	private JPanel contentContainer; // content
	private JPanel statusContainer; // footer: details

	// Instance Creation ******************************************************

	/**
	 * Constructs a MainPageBuilder for the given main module.
	 * 
	 * @param mainModule
	 *            provides high-level models
	 */
	public MainPageBuilder(MainModel mainModule) {
		this.mainModule = mainModule;
	}

	// Building ***************************************************************

	/**
	 * Creates and configures the UI components.
	 */
	private void initComponents() {
		pages = new CardPanel();
		pages.add(PupilMainModel.CARDNAME_PUPIL, new PupilMainPageBuilder(
				mainModule.getPupilMainModel()).getPanel());
		pages.add(MaintenaceHomeModel.CARDNAME_MAINTENANCE,
				new MaintenaceHomeView(mainModule
						.getMaintenaceHomeModel()).getPanel());

	}

	private void initEvent() {
		mainModule.addPropertyChangeListener(MainModel.PROPERTY_CARDNAME,
				new MainCardChangeHandler());

	}

	/**
	 * Builds and returns the main page that consists only of a card panel to
	 * hold and switch the work pages: home, edit and others.
	 */
	public JComponent build() {
		initComponents();
		initEvent();
		FormLayout layout = new FormLayout(
				"fill:0:grow",
				"fill:36px, 2px, fill:pref:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.setOpaque(false);
		builder.add(buildHeader(), cc.xy(1, 1));
		builder.add(pages, cc.xy(1, 3));

		return builder.getPanel();
	}

	private JComponent buildHeader() {
		FormLayout layout = new FormLayout("fill:0:grow", "fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout, ImageBackgroundPanel
				.createFrom());
		CellConstraints cc = new CellConstraints();
		builder.add(new MainMenuBuilder().build(), cc.xy(1, 1));
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
