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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.formatter.PositivNumberFormat;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractPersonHomeView extends AbstractHomeView {

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(AbstractPersonHomeView.class);

	private final String[] cols = new String[] {
			RESOURCE.getString("phone.type"),
			RESOURCE.getString("phone.number") };

	private JLabel imageComponent;
	private JComponent nameComponent;
	private JComponent lastnameComponent;
	private JComponent strasseComponent;
	private JComponent platzComponent;
	private JComponent ortComponent;
	private JComponent jobComponent;
	private JScrollPane telePane;
	private JComponent telefonTable;

	private PresentationModel<Person> pModel;
	private PresentationModel<Adresse> adressModel;
	private PresentationModel<BerufsTyp> berufsTypModel;

	private ArrayListModel<Telefon> listModel;
	private Person orgPerson;
	private AbstractPersonHomeModel<?> model;

	public AbstractPersonHomeView(AbstractPersonHomeModel<?> model) {
		// public AbstractPersonHomeView(AbstractHomeModel<?> model, Person
		// person) {
		super(model);
		this.model = model;
		// orgPerson = person;
		// pModel = new PresentationModel<Person>(person);
		// adressModel = new PresentationModel<Adresse>(pModel.getBean()
		// .getAdresse());
		// berufsTypModel = new PresentationModel<BerufsTyp>(pModel.getBean()
		// .getBeruf().getTyp());
		// listModel = new ArrayListModel<Telefon>();

		orgPerson = model.getOrgPerson();
		pModel = model.getPersonModel();
		adressModel = model.getAdressModel();
		berufsTypModel = model.getBerufsTypModel();
		listModel = model.getTelefonListModel();

	}

	protected void initEvent() {
		super.initEvent();
		getModel().getSelectionInList().addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION,
				new UserSelectionChangedHandler());

	}

	protected void initComponents() {
		super.initComponents();
		imageComponent = new JLabel();
		imageComponent.setBorder(BorderFactory.createLineBorder(
				Color.LIGHT_GRAY, 2));
		nameComponent = ComponentFactory.createLabelField(pModel
				.getModel(Person.PROPERTY_NAME));
		lastnameComponent = ComponentFactory.createLabelField(pModel
				.getModel(Person.PROPERTY_LASTNAME));
		strasseComponent = ComponentFactory.createLabelField(adressModel
				.getModel(Adresse.PROPERTY_STARSSE));
		ortComponent = ComponentFactory.createLabelField(adressModel
				.getModel(Adresse.PROPERTY_ORT));
		// platzComponent = ComponentFactory.createIntagerField(adressModel
		// .getModel(Adresse.PROPERTY_PLZ), PositivNumberFormat.NORMAL);
		// platzComponent.setEnabled(false);
		platzComponent = ComponentFactory.createLabel(adressModel
				.getModel(Adresse.PROPERTY_PLZ), PositivNumberFormat.NORMAL);
		jobComponent = ComponentFactory.createLabelField(berufsTypModel
				.getModel(BerufsTyp.PROPERTY_BEZEICHNUNG));
		telefonTable = ComponentFactory.createTable(getTelefonTableAdapter(),
				null);
		telePane = new JScrollPane(telefonTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	}

	@Override
	protected JComponent buildView() {
		FormLayout layout = new FormLayout("fill:p:grow",
				"fill:p:grow,3dlu,p,10dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setBorder(Borders.DLU14_BORDER);
		CellConstraints cc = new CellConstraints();
		builder.add(getScrollPane(), cc.xy(1, 1));
		builder.add(buildDetailView(), cc.xy(1, 3));
		builder.add(getButtonBar(), cc.xy(1, 5));
		return builder.getPanel();
	}

	protected JComponent buildDetailView() {
		FormLayout layout = new FormLayout(
				"fill:p:grow, 10dlu, fill:p:grow,10dlu, fill:0:grow,10dlu, fill:p:grow",
				"top:p");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(buildTelefonView(), cc.xy(1, 1));
		builder.add(buildNameView(), cc.xy(3, 1));
		builder.add(buildImagepanel(), cc.xy(7, 1));
		return builder.getPanel();
	}

	private JPanel buildNameView() {
		FormLayout layout = new FormLayout("p, 3dlu,180dlu",
				"p, 3dlu, p, 3dlu, p,3dlu,p, 3dlu, p,3dlu,p, 3dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("edit.name"), cc.xy(1, 1));
		builder.add(nameComponent, cc.xy(3, 1));
		builder.addLabel(RESOURCE.getString("edit.lastname"), cc.xy(1, 3));
		builder.add(lastnameComponent, cc.xy(3, 3));
		builder.addLabel(RESOURCE.getString("address.street"), cc.xy(1, 5));
		builder.add(strasseComponent, cc.xy(3, 5));
		builder.addLabel(RESOURCE.getString("address.zip"), cc.xy(1, 7));
		builder.add(platzComponent, cc.xy(3, 7));
		builder.addLabel(RESOURCE.getString("address.place"), cc.xy(1, 9));
		builder.add(ortComponent, cc.xy(3, 9));
		builder.addLabel(RESOURCE.getString("edit.work"), cc.xy(1, 11));
		builder.add(jobComponent, cc.xy(3, 11));

		builder.setOpaque(false);
		return builder.getPanel();
	}

	private JPanel buildTelefonView() {
		FormLayout layout = new FormLayout("250dlu", "80dlu");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(telePane, cc.xy(1, 1));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	private JComponent buildImagepanel() {
		FormLayout layout = new FormLayout("r:p:grow", "80dlu");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(imageComponent, cc.xy(1, 1));
		return builder.getPanel();

	}

	private void setPersonBean(Person person) {
		ImageIcon icon = model.setNewBeanAndGetIcon(person);
		imageComponent.setIcon(icon);
		imageComponent.setPreferredSize(person.getPreferedImageSize());

	}

	private AbstractTableAdapter<Telefon> getTelefonTableAdapter() {
		return new TelefonTableAdptar();
	}

	@SuppressWarnings("serial")
	private class TelefonTableAdptar extends AbstractTableAdapter<Telefon> {

		public TelefonTableAdptar() {
			super(listModel, cols);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Telefon t = (Telefon) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				TelefonTyp typ = t.getTyp();
				return typ == null ? "??" : t.getTyp().getBezeichnung();
			case 1:
				return t.getNummer();
			default:
				throw new IllegalStateException("Unknown column index: "
						+ columnIndex);
			}

		}

	}

	private class UserSelectionChangedHandler implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			Person person = (Person) evt.getNewValue();
			if (person == null)
				person = orgPerson;

			setPersonBean(person);
		}

	}

}
