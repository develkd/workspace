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

package de.ev.iisin.pupil;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.desktop.AbstractHomeBuilder;
import de.ev.iisin.common.person.domain.Person;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilHomeBuilder extends AbstractHomeBuilder<PupilMainModel> {

	private final PupilMainModel mainModule;

	private JLabel imageComponent;

	private PupilDetailView detailView;
	private JComponent kursNoteView;
	// Instance Creation ****************************************************

	public PupilHomeBuilder(PupilMainModel mainModule) {
		super(mainModule, EMPTY, SELECTED, INSTRUCTION);
		this.mainModule = mainModule;
		detailView = new PupilDetailView(getEditModule());

	}


	protected void initComponents() {
		imageComponent = new JLabel();
		imageComponent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		kursNoteView = new PupilKursHomeView(mainModule.getPupilKursHomeModule()).getPanel();
	}

	protected void initEvents() {
		mainModule.getVetoAbleSelectionInList().addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION,
				new UserSelectionChangedHandler());

		mainModule.getVetoAbleSelectionInList().addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY, getHandler());

	}
	protected JComponent getButtonBar(){
		return new JPanel();
	}

	// Building *************************************************************


	private PupilEditModel getEditModule() {
		return getModel().getPupilEditModule();
	}
	
	@Override
	protected JComponent buildMainPanel()  {
		initComponents();
		FormLayout layout = new FormLayout("fill:p:grow",
				"p, 10dlu, p, 10dlu, fill:p:grow");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(buidPupilPanel(), cc.xy(1, 1));
		builder.addSeparator("", cc.xy(1, 3));
		builder.add(buildTableComponent(), cc.xy(1, 5));
		return builder.getPanel();
	}

	private JComponent buidPupilPanel() {
		FormLayout layout = new FormLayout("r:p, fill:10:grow, l:p",
				"fill:p:grow");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(detailView.build(), cc.xy(1, 1));
		builder.add(buildImagepanel(), cc.xy(3, 1));
		return builder.getPanel();

	}

	private JComponent buildImagepanel() {
		FormLayout layout = new FormLayout("r:p:grow", "fill:p:grow");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(imageComponent, cc.xy(1, 1));
		return builder.getPanel();

	}

	private JComponent buildTableComponent() {
		FormLayout layout = new FormLayout("fill:0:grow", "fill:0:grow");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(kursNoteView, cc.xy(1, 1));
		return builder.getPanel();

	}

	private class UserSelectionChangedHandler implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			Person person = (Person) evt.getNewValue();
			if(person == null)
				return;
			ImageIcon icon = person.getCreatedImage();
			icon = icon == null ? ResourceUtils.getImageIcon("pupil.image", PupilHomeBuilder.class):icon;
			imageComponent.setIcon(icon);
			imageComponent.setPreferredSize(person.getPreferedImageSize());

		}

	}

}
