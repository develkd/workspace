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

package de.ev.iisin.maintenance.member;

import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.renderer.PersonenTypenListCellRender;
import de.ev.iisin.application.view.BankkontoEditView;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.maintenance.common.PersonEditorView;

/**
 * @author Kemal Dönmez
 * 
 */
public class MitgliedEditView extends PersonEditorView<Person> {// AbstractEditorView<Person>
																		// {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(MitgliedEditView.class);

	/**
	 * Erzeugt am 03.02.2009
	 */
	private static final long serialVersionUID = 8692714445486331805L;

	private JComponent beginnDatum;
//	private JComboBox jobComponent;
	private PresentationModel<Mitglied> mitgliedModel;
//	private PresentationModel<BerufsTyp> berufsTypModel;

	private MitgliedEditModel model;
	private Mitglied mitglied;
	private ValueModel valueModelDate;
	private JComponent personCombo;

	public MitgliedEditView(String title, MitgliedEditModel model) {
		super(title, model.getPersonEditorModel());
		this.model = model;
		mitglied = (Mitglied) model.getBean();
		mitgliedModel = new PresentationModel<Mitglied>(mitglied);
//		berufsTypModel = model.getBerusTypEditModel();
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		valueModelDate = mitgliedModel.getModel(Mitglied.PROPERTY_BEGIN_DATE);
		beginnDatum = ComponentFactory.createJXMonthView(Calendar.MONDAY,
				valueModelDate);
		personCombo = ComponentFactory.createComboBox(model
				.getPersonenTypenSelectionInList(),
				new PersonenTypenListCellRender());

//		jobComponent = ComponentFactory.createComboBox(model
//				.getBerufsTypSelectionInList(), berufsTypModel
//				.getModel(BerufsTyp.PROPERTY_BEZEICHNUNG),
//				new BerufsTypenListCellRenderer());
//		jobComponent.setEditable(true);
	}

	@Override
	protected JComponent buildView() {
		super.buildView();
		getTappedPane().addTab(RESOURCE.getString("member.tab.bank"),
				buildBankPanel());

		return getTappedPane();

	}

	@Override
	protected JComponent buildPersonPanel() {
		FormLayout layout = new FormLayout("p", "p,10dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(buildPersonCompboPanel(), cc.xy(1, 1));
		builder.add(super.buildPersonPanel(), cc.xy(1, 3));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	private JComponent buildPersonCompboPanel() {
		FormLayout layout = new FormLayout("p, 3dlu, fill:p:grow", "p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("member.edit.person"), cc.xy(1, 1));
		builder.add(personCombo, cc.xy(3, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

//	private JComponent buildJobComboPanel() {
//		FormLayout layout = new FormLayout("p, 3dlu, fill:p:grow", "p");
//		PanelBuilder builder = new PanelBuilder(layout);
//		CellConstraints cc = new CellConstraints();
//		builder.addLabel(RESOURCE.getString("member.edit.work"), cc.xy(1, 1));
//		builder.add(jobComponent, cc.xy(3, 1));
//		builder.setOpaque(false);
//		return builder.getPanel();
//
//	}

	@Override
	protected JComponent buildBeginPanel() {
		FormLayout layout = new FormLayout("fill:p:grow,20dlu,fill:p:grow",
				"fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));

		CellConstraints cc = new CellConstraints();
		builder.add(buildDatePanel(), cc.xy(1, 1));
		builder.add(buildImagePanel(), cc.xy(3, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	private JComponent buildDatePanel() {
		FormLayout layout = new FormLayout("c:p",
				"fill:0:grow,c:p, 2dlu,p,fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("member.edit.begin"), cc.xy(1, 2));
		builder.add(beginnDatum, cc.xy(1, 4));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	private JComponent buildBankView() {
		BankkontoEditView view = new BankkontoEditView(mitglied, model
				.getBankListModel());
		return view.getPanel();
	}


	private JComponent buildBankPanel() {
		FormLayout layout = new FormLayout("fill:p:grow",
				" fill:0:grow,p,fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));

		CellConstraints cc = new CellConstraints();
		builder.add(buildBankView(), cc.xy(1, 2));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	@Override
	public void doAccept() {
		model.accept();
		super.doAccept();
	}
}
