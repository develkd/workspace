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

package de.ev.iisin.application.common.components.renderer;

import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;

/**
 * @author Kemal Dönmez
 * 
 */
public class BankView extends AbstractView {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(BankView.class);

	private JComponent bankNameComponent;
	private JComponent blzComponent;

	public BankView(PresentationModel<Bank> model) {
		this(model.getModel(Bank.PROPERTY_BANK_NAME), model
				.getModel(Bank.PROPERTY_BANK_LEIT_ZAHL));

	}

	public BankView(ValueModel valueName, ValueModel valueBlz) {

		this.bankNameComponent = ComponentFactory.createBordlessTextField(
				valueName, true);
		this.blzComponent = ComponentFactory
				.createBordlessNumberField(valueBlz);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 */
	@Override
	protected JComponent buildPanel() {
		FormLayout layout = new FormLayout("pref, 3dlu, fill:0:grow",
				"fill:p:grow, fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		
		builder.add(new JLabel(RESOURCE.getString("bank.name")), cc.xy(1, 1));
		builder.add(bankNameComponent, cc.xy(3, 1));
		builder.add(new JLabel(RESOURCE.getString("bank.blz")), cc.xy(1, 2));
		builder.add(blzComponent, cc.xy(3, 2));

		return builder.getPanel();
	}

}
