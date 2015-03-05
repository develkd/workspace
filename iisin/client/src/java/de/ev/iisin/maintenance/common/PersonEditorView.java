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

package de.ev.iisin.maintenance.common;

import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.AbstractEditorView;
import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.renderer.BerufsTypenListCellRenderer;
import de.ev.iisin.application.common.formatter.PositivNumberFormat;
import de.ev.iisin.application.view.TelefonEditView;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class PersonEditorView<M> extends AbstractEditorView<M> {
	
	/**
	 * Erzeugt am 04.08.2009
	 */
	private static final long serialVersionUID = 1224791714940732014L;

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(PersonEditorView.class);

	private JComponent imageComponent;

	private PersonEditorModel<Person> model;
	private JTabbedPane tabbedPane;
	private JComponent ageComponent;
	private JComponent emailComponent;
	private JComponent nameComponent;
	private JComponent lastnameComponent;
	private JComponent sexComponent;
	private JComponent strasseComponent;
	private JComponent platzComponent;
	private JComponent ortComponent;
	private JComboBox jobComponent;

	private PresentationModel<Adresse> adressModel;
	private PresentationModel<BerufsTyp> berufsTypModel;

	@SuppressWarnings("unchecked")
	public PersonEditorView(String title,
			PersonEditorModel<Person> model) {
		super(title, (M)model);
		this.model = model;
		adressModel = new PresentationModel<Adresse>(model.getBean()
				.getAdresse());
		berufsTypModel = model.getBerusTypEditModel();

	}

	protected void initComponents() {
		tabbedPane = new JTabbedPane();

		imageComponent = ComponentFactory.createImageButton(model
				.getModel(Person.PROPERTY_CREATE_IMAGE));
		nameComponent = ComponentFactory.createTextField(model
				.getModel(Person.PROPERTY_NAME));
		lastnameComponent = ComponentFactory.createTextField(model
				.getModel(Person.PROPERTY_LASTNAME));
		ageComponent = ComponentFactory.createDateField(model
				.getModel(Person.PROPERTY_AGE));
		emailComponent = ComponentFactory.createTextField(model
				.getModel(Person.PROPERTY_EMAIL));

		sexComponent = ComponentFactory.createCheckBox(model
				.getModel(Person.PROPERTY_WEIBLICH), RESOURCE
				.getString("edit.sex"));
		strasseComponent = ComponentFactory.createTextField(adressModel
				.getModel(Adresse.PROPERTY_STARSSE));
		ortComponent = ComponentFactory.createTextField(adressModel
				.getModel(Adresse.PROPERTY_ORT));
		platzComponent = ComponentFactory.createIntagerField(adressModel
				.getModel(Adresse.PROPERTY_PLZ), PositivNumberFormat.NORMAL);

		jobComponent = ComponentFactory.createEditableComboBox(model
				.getBerufsTypSelectionInList(), berufsTypModel
				.getModel(BerufsTyp.PROPERTY_BEZEICHNUNG),
				new BerufsTypenListCellRenderer());

	}

	public JTabbedPane getTappedPane() {
		if (tabbedPane == null)
			tabbedPane = new JTabbedPane();
		return tabbedPane;
	}
	
	protected PresentationModel<Adresse> getAdressModel(){
		if(adressModel == null)
			adressModel = new PresentationModel<Adresse>(model.getBean()
					.getAdresse());
		
		return adressModel;
	}

	public JComponent getComponent(){
		return buildView();
	}
	
	protected String getPersonTabName(){
		return RESOURCE.getString("tab.pupil");
	}
	
	@Override
	protected JComponent buildView() {
		initComponents();
		tabbedPane.addTab(RESOURCE.getString("tab.image"),
				buildBeginPanel());

		tabbedPane.addTab(getPersonTabName(),
				buildMainPersonPanel());
	
		return tabbedPane;

	}
	
	
	
	protected JComponent buildMainPersonPanel() {
		FormLayout layout = new FormLayout("fill:p:grow",
				"p, 10dlu, p,3dlu,p,10dlu, p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));
		CellConstraints cc = new CellConstraints();
		builder.add(buildPersonPanel(), cc.xy(1, 1));
		builder.addSeparator(RESOURCE.getString("sep.address"), cc.xy(1,
				3));
		builder.add(buildAdressPanel(), cc.xy(1, 5));
		builder.addSeparator(RESOURCE.getString("sep.phone"), cc
				.xy(1, 7));
		builder.add(buildTelefonPanel(), cc.xy(1, 9));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	protected JComponent buildPersonPanel() {
		FormLayout layout = new FormLayout(
				"p, 2dlu,60dlu, 10dlu, p, 2dlu, 80dlu, 1dlu, p,fill:p:grow",
				"p, 3dlu, p, 6dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("edit.name"), cc.xy(1, 1));
		builder.add(nameComponent, cc.xy(3, 1));
		builder.addLabel(RESOURCE.getString("edit.lastname"), cc
				.xy(5, 1));
		builder.add(lastnameComponent, cc.xy(7, 1));
		builder.add(sexComponent, cc.xy(9, 1));

		builder.addLabel(RESOURCE.getString("edit.birthday"), cc
				.xy(1, 3));
		builder.add(ageComponent, cc.xy(3, 3));
		builder.addLabel(RESOURCE.getString("edit.email"), cc.xy(5, 3));
		builder.add(emailComponent, cc.xyw(7, 3, 3));
		builder.add(buildJobComboPanel(), cc.xyw(1, 5, 9));


		builder.setOpaque(false);
		return builder.getPanel();
	}

	protected JComponent buildBeginPanel() {
		FormLayout layout = new FormLayout("fill:p:grow",
				"fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));

		CellConstraints cc = new CellConstraints();
		builder.add(buildImagePanel(), cc.xy(1, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}


	protected JComponent buildImagePanel() {
		FormLayout layout = new FormLayout(
				"fill:0:grow,fill:100dlu:grow,fill:0:grow",
				"fill:0:grow,fill:80dlu:grow,fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(imageComponent, cc.xy(2, 2));
		builder.setOpaque(false);
		return builder.getPanel();
	}


	
	protected JComponent buildAdressPanel() {
		FormLayout layout = new FormLayout(
				"p, 2dlu,80dlu, 6dlu, p, 2dlu, 80dlu, 6dlu, p, 2dlu, 80dlu",
				"p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("address.street"), cc.xy(1,
				1));
		builder.add(strasseComponent, cc.xy(3, 1));
		builder.addLabel(RESOURCE.getString("address.zip"), cc.xy(5, 1));
		builder.add(platzComponent, cc.xy(7, 1));
		builder.addLabel(RESOURCE.getString("address.place"), cc
				.xy(9, 1));
		builder.add(ortComponent, cc.xy(11, 1));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	protected JComponent buildJobComboPanel() {
		FormLayout layout = new FormLayout("p, 3dlu, fill:p:grow", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("work.name"), cc.xy(1, 1));
		builder.add(jobComponent, cc.xy(3, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	
	
	@SuppressWarnings("unchecked")
	private JComponent buildTelefonPanel() {
		TelefonEditView view = new TelefonEditView(((PersonEditorModel<Person>)getModel()).getTelefonEditModel());
		return view.getPanel();
	}

	
}
