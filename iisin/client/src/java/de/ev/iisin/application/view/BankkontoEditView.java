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

import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.renderer.BankComboBoxEditor;
import de.ev.iisin.application.common.components.renderer.BankListCellRenderer;
import de.ev.iisin.application.common.formatter.PositivLongFormat;
import de.ev.iisin.application.common.formatter.PositivNumberFormat;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.member.domain.Bankkonto;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

/**
 * @author Kemal Dönmez
 * 
 */
public class BankkontoEditView extends AbstractView {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(BankkontoEditView.class);

	private JComponent paymentComponent;
	private JComponent beitragComponent;
	private JComponent kontoNrComponent;
	private JComboBox bankComponent;
	private SelectionInList<Bank> bankSelectionInList;
	private PresentationModel<Bankkonto> model;
	private PresentationModel<Mitglied> mitgliedModel;

	public BankkontoEditView(Mitglied mitglied, ArrayListModel<Bank> bankListMode) {
		this.model = new PresentationModel<Bankkonto>(mitglied.getKonto());
		this.mitgliedModel = new PresentationModel<Mitglied>(mitglied);
		bankSelectionInList = new SelectionInList<Bank>(
				(ListModel) bankListMode);
	}

	private void initComponent() {
		bankComponent = ComponentFactory.createComboBox(bankSelectionInList,
				model.getModel(Bankkonto.PROPERTY_BANK),
				new BankListCellRenderer());
		bankComponent.setEditor(new BankComboBoxEditor(model.getBean()
				.getBank()));
		bankComponent.setEditable(true);
		kontoNrComponent = ComponentFactory.createLongField(model
				.getModel(Bankkonto.PROPERTY_KONTO_NR), PositivLongFormat.LONG);
		beitragComponent = ComponentFactory
				.createIntagerField(mitgliedModel
						.getModel(Mitglied.PROPERTY_BEITRAG),
						PositivNumberFormat.SHORT);
		paymentComponent = ComponentFactory.createCheckBox(mitgliedModel
				.getModel(Mitglied.PROPERTY_BAHRZAHLER), RESOURCE
				.getString("member.edit.payment"));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 * 
	 */
	@Override
	protected JComponent buildPanel() {
		initComponent();
		FormLayout layout = new FormLayout("p, 4dlu,fill:p:grow, 4dlu, p",
				" p, 3dlu, p, 3dlu, p, 3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("member.bank.value"), cc.xy(1, 1));
		builder.add(beitragComponent, cc.xy(3, 1));
		builder.add(paymentComponent, cc.xy(5, 1));

		builder.addLabel(RESOURCE.getString("member.bank.nr"), cc.xy(1, 3));
		builder.add(kontoNrComponent, cc.xyw(3, 3, 3));
		builder.addLabel(RESOURCE.getString("member.bank.name"), cc.xy(1, 5));
		builder.add(bankComponent, cc.xyw(3, 5,3));

		builder.setOpaque(false);
		return builder.getPanel();

	}

}
