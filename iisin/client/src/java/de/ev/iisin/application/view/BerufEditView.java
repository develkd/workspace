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
import de.ev.iisin.application.common.components.renderer.BerufsTypenListCellRenderer;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class BerufEditView extends AbstractView {
	private static final ResourceBundle RESOURCE = ResourceUtils
	.getResourceBundle(BerufEditView.class);

	private JComboBox berufsTypComponent;
	private SelectionInList<BerufsTyp> berufsTypSelectionInList;
	private PresentationModel<Beruf> model;

	public BerufEditView(Person person, ArrayListModel<BerufsTyp> berufsTypListMode) {
		this.model = new PresentationModel<Beruf>(person.getBeruf());
		berufsTypSelectionInList = new SelectionInList<BerufsTyp>(
				(ListModel) berufsTypListMode);
	}

	private void initComponent() {
		berufsTypComponent = ComponentFactory.createComboBox(berufsTypSelectionInList,
				model.getModel(Beruf.PROPERTY_BERUFS_TYP),
				new BerufsTypenListCellRenderer());
		berufsTypComponent.setEditable(true);
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
		FormLayout layout = new FormLayout("p, 4dlu,fill:p:grow",
				" p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE.getString("member.edit.work"), cc.xy(1, 1));
		builder.add(berufsTypComponent, cc.xy(3, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

}
