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
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class TelefonTypBoxEditor extends BasicComboBoxEditor {

	private JComponent typComponent;
	private ValueModel valueTyp;
	private JComponent editor;
	private PresentationModel<TelefonTyp> model;

	public TelefonTypBoxEditor(TelefonTyp telefon) {
		if(telefon == null)
			telefon = new TelefonTyp();
		
		this.model = new PresentationModel<TelefonTyp>(telefon);
		valueTyp = model.getModel(TelefonTyp.PROPERTY_BEZEICHNUNG);
		typComponent = ComponentFactory.createBordlessTextField(valueTyp, true);
	}


	@Override
	public Component getEditorComponent() {
		if (editor == null) {
			FormLayout layout = new FormLayout(" fill:p:grow", "fill:p:grow");
			PanelBuilder builder = new PanelBuilder(layout);
			CellConstraints cc = new CellConstraints();
			builder.add(typComponent, cc.xy(1, 1));
			editor = builder.getPanel();
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
			TelefonTyp telefon = (TelefonTyp) anObject;
			model.setBean(telefon);
			// valueTyp.setValue(telefon.getBezeichnung());
		} else {
			valueTyp.setValue(null);
		}
	}

}
