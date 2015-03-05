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

package de.ev.iisin.wizard.provider;

import java.lang.reflect.Method;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.AbstractValueModel;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.common.descriptor.ComponentDeskriptor;
import de.ev.iisin.common.descriptor.ComponentTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class ComponentPair {
	private String labelText;

	private JComponent component;

	private String property;
	private PresentationModel<Object> model;
	private DefaultListCellRenderer listCellRenderer;
	private SelectionInList<Object> selectionInList;;
	private ComponentDeskriptor deskriptor;

	public ComponentPair(ComponentDeskriptor deskriptor, Object ob) {
		this.deskriptor = deskriptor;
		this.model = new PresentationModel<Object>(ob);
		this.property = deskriptor.property();
		labelText = deskriptor.labelEnumText().getBezeichnung();
		this.component = createComponent(deskriptor.component());

	}

	private void initListCellRenderClasses(ComponentDeskriptor deskriptor) {
		try {
			String className = deskriptor.listCellRender();
			if (className == null || className.equals(""))
				return;

			Class<?> clazz = Class.forName(className);
			Object o = clazz.newInstance();
			listCellRenderer = (DefaultListCellRenderer) o;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initSelectinInListClasses(ComponentDeskriptor deskriptor) {
		try {
			String className = deskriptor.listModel();
			if (className == null || className.equals(""))
				return;

			Class<?> clazz = Class.forName(className);
			Method m = clazz.getMethod("getBerufsTypListModel");
			Object o = clazz.newInstance();
			Object mO = m.invoke(o);
			ListModel ml = (ListModel) mO;
			selectionInList = new SelectionInList<Object>(ml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLabelText() {
		return labelText;
	}

	public JComponent getComponent() {
		return component;
	}

	@SuppressWarnings("all")
	private JComponent createComponent(ComponentTyp typ) {
		switch (typ) {
		case LABEL:
			return ComponentFactory.createLabel(model.getModel(property));
		case TEXT:
			return ComponentFactory.createTextField(model.getModel(property));
		case CHECKBOX:
			String checkBoxTex = labelText;
			labelText = "";
			return ComponentFactory.createCheckBox(model.getModel(property),
					checkBoxTex);
		case COMBOBOX:
			initListCellRenderClasses(deskriptor);
			initSelectinInListClasses(deskriptor);
			JComboBox cb = ComponentFactory.createComboBox(selectionInList,
					model.getModel(property), listCellRenderer);
			cb.setEditable(true);
			return cb;
		case PASSWORT:
			AbstractValueModel valueModel = EventCreator.createPasswordHandler(
					property, model);
			return ComponentFactory.createPasswordField(valueModel, true);
		default:
			return new JPanel();
		}
	}

}
