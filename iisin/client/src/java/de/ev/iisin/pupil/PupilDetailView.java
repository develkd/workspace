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

package de.ev.iisin.pupil;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.ActionLink;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilDetailView {
	private PupilEditModel model;

	private JComponent nameComponent;

	private JComponent lastNameComponent;

	private JComponent adgeComponent;

	private ActionLink fatherLink;
	private ActionLink motherLink;

	PupilDetailView(PupilEditModel model) {
		this.model = model;
	}

	private void initComponents() {
		nameComponent = ComponentFactory.createLabel(model
				.getModel(Person.PROPERTY_NAME));
		lastNameComponent = ComponentFactory.createLabel(model
				.getModel(Person.PROPERTY_LASTNAME));

		adgeComponent = ComponentFactory.createLabel(model
				.getModel(Mitglied.PROPERTY_ADGE_AS_STRING));

		fatherLink = new ActionLink("Osman Gazi", Actions.EDIT_FATHER_ID);
		motherLink = new ActionLink("Hatice Gazi", Actions.EDIT_MOTHER_ID);
	}

	public JPanel build() {
		initComponents();
		FormLayout layout = new FormLayout(
				"fill:p:grow, 40dlu, fill:p:grow,40dlu, fill:p:grow",
				"top:p, 6dlu,p");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(buildPupil(), cc.xy(1, 1));
		builder.add(buildAdress(), cc.xy(3, 1));
		builder.add(buildIsinPanel(), cc.xy(5, 1));
		return builder.getPanel();
	}

	private JPanel buildPupil() {
		FormLayout layout = new FormLayout("p,3dlu, fill:p:grow",
				"p, 10dlu,p, 3dlu, p, 3dlu, p,  3dlu, p, 3dlu, p,  3dlu, p, 3dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addSeparator("Schüler ", cc.xyw(1, 1, 3));
		builder.addLabel("Name :", cc.xy(1, 3));
		builder.add(nameComponent, cc.xy(3, 3));
		builder.addLabel("Nachname :", cc.xy(1, 5));
		builder.add(lastNameComponent, cc.xy(3, 5));
		builder.addLabel("Vater :", cc.xy(1, 7));
		builder.add(fatherLink, cc.xy(3, 7));
		builder.addLabel("Mutter :", cc.xy(1, 9));
		builder.add(motherLink, cc.xy(3, 9));
		builder.addLabel("Alter :", cc.xy(1, 11));
		builder.add(adgeComponent, cc.xy(3, 11));
		return builder.getPanel();
	}

	private JPanel buildAdress() {
		FormLayout layout = new FormLayout("p,3dlu, fill:p:grow",
				"p, 10dlu,p, 3dlu, p, 3dlu, p, 3dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addSeparator("Adresse ", cc.xyw(1, 1, 3));
		builder.addLabel("Strasse :", cc.xy(1, 3));
		builder.addLabel("Winkelsteingasse 8", cc.xy(3, 3));
		builder.addLabel("PLZ :", cc.xy(1, 5));
		builder.addLabel("120993", cc.xy(3, 5));
		builder.addLabel("Telefon :", cc.xy(1, 7));
		builder.addLabel("555 999 33", cc.xy(3, 7));
		builder.addLabel("Mobil:", cc.xy(1, 9));
		builder.addLabel("0170 134 532 12", cc.xy(3, 9));
		return builder.getPanel();
	}

	private JPanel buildIsinPanel() {
		FormLayout layout = new FormLayout("p,3dlu, fill:p:grow",
				"p, 10dlu,p, 3dlu, p, 3dlu, p, 3dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addSeparator("I-ISIN ", cc.xyw(1, 1, 3));
		builder.addLabel("Lehrer :", cc.xy(1, 3));
		builder.addLabel("Ibrahim Hoca", cc.xy(3, 3));
		builder.addLabel("Klasse :", cc.xy(1, 5));
		builder.addLabel("1a", cc.xy(3, 5));
		builder.addLabel("Tag :", cc.xy(1, 7));
		builder.addLabel("Montags", cc.xy(3, 7));
		builder.addLabel("Zeiten :", cc.xy(1, 9));
		builder.addLabel("15:30", cc.xy(3, 9));
		return builder.getPanel();
	}

}
