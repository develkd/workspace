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

package de.ev.iisin.application.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.maintenance.member.MitgliedEditModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class HumanView extends AbstractView {

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(HumanView.class);

	private JComponent nameComponent;
	private JComponent lastNameComponent;
	private JComponent emailComponent;
	private JComponent workComponent;
	private PresentationModel<Human> model;
	private TelefonEditView telefonView;
	private MitgliedEditModel mitgliedEditModel;

	public HumanView(Human human) {
		this.model = new PresentationModel<Human>(human);
		Telefon telefon = new Telefon();
		telefon.setPerson(human);
		this.telefonView = new TelefonEditView(new TelefonEditModel(telefon));
	}

	private void initEventhandler() {
		mitgliedEditModel.getPersonenTypenSelectionInList()
				.addPropertyChangeListener(
						SelectionInList.PROPERTYNAME_SELECTION,
						new BeanChangedHandler());
	}

	private void initComponants() {
		initEventhandler();
		nameComponent = ComponentFactory.createTextField(model
				.getModel(Human.PROPERTY_NAME));
		lastNameComponent = ComponentFactory.createTextField(model
				.getModel(Human.PROPERTY_LASTNAME));
		emailComponent = ComponentFactory.createTextField(model
				.getModel(Human.PROPERTY_EMAIL));
		workComponent = new JTextField();
		// ComponentFactory.createTextField(model
		// .getModel(Human.PROPERTY_BERUF));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 */
	@Override
	protected JComponent buildPanel() {
		initComponants();
		FormLayout layout = new FormLayout("p, 4dlu,fill:200dlu:grow",
				"fill:0:grow, p, 3dlu, p, 3dlu, p, 3dlu, p,6dlu, fill:p:grow, fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("member.parent.name"), cc.xy(1, 2));
		builder.add(nameComponent, cc.xy(3, 2));
		builder.addLabel(RESOURCE.getString("member.parent.lastname"), cc.xy(1,
				4));
		builder.add(lastNameComponent, cc.xy(3, 4));
		builder.addLabel(RESOURCE.getString("member.parent.work"), cc.xy(1, 6));
		builder.add(workComponent, cc.xy(3, 6));
		builder
				.addLabel(RESOURCE.getString("member.parent.email"), cc
						.xy(1, 8));
		builder.add(emailComponent, cc.xy(3, 8));
		builder.add(telefonView.getPanel(), cc.xyw(1, 10, 3));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	private class BeanChangedHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			System.out.println("BeanChanged");

		}

	}

}
