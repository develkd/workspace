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

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;

import de.ev.iisin.common.stammdaten.bank.domain.Bank;

/**
 * @author Kemal Dönmez
 * 
 */
public class BankComboBoxEditor extends BasicComboBoxEditor {
	
	
	private JComponent editor;
	private PresentationModel<Bank> model;
	private ValueModel valueName;
	private ValueModel valueBlz;

	public BankComboBoxEditor(Bank bank) {
		this.model = new PresentationModel<Bank>(bank);
		valueName = model.getModel(Bank.PROPERTY_BANK_NAME);
		valueBlz = model.getModel(Bank.PROPERTY_BANK_LEIT_ZAHL);

	}

	@Override
	public Component getEditorComponent() {
		if (editor == null) {
			editor = new BankView(valueName, valueBlz).buildPanel();
		}
		return editor;
	}

	@Override
	public Object getItem() {

		return model.getBean();
	}

	@Override
	public void addActionListener(ActionListener l) {
		super.addActionListener(l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		super.removeActionListener(l);

	}

	@Override
	public void selectAll() {
		super.selectAll();
	}

	@Override
	public void setItem(Object anObject) {
		if (anObject != null) {
			Bank bank = (Bank) anObject;
			valueBlz.setValue(bank.getBankLeitZahl());
			valueName.setValue(bank.getBankName());
		} else {
			valueBlz.setValue(null);
			valueName.setValue(null);
		}
	}

}
