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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListModel;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.binding.ValidablePresentationModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.common.util.PersonenTypen;
import de.ev.iisin.maintenance.common.PersonEditorModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class MitgliedEditModel extends ValidablePresentationModel<Person> {

	/**
	 * Erzeugt am 03.02.2009
	 */
	private static final long serialVersionUID = -517026684323610278L;

	private ArrayListModel<Bank> bankListMode;
	private SelectionInList<Bank> bankSelectionInList;
	private ArrayListModel<PersonenTypen> personListMode;
	private SelectionInList<PersonenTypen> personSelectionInList;
//	private ArrayListModel<BerufsTyp> berufsTypListMode;
//	private SelectionInList<BerufsTyp> berufsTypSelectionInList;
	// private TelefonEditModel telefonEditModel;
	private PersonEditorModel<Person> personEditorModel;
//	private PresentationModel<BerufsTyp> berufsTypModel;
	private Human mother;
	private Human father;
	private Mitglied mitglied;

	public MitgliedEditModel() {
		super(null);
	}

	public MitgliedEditModel(Mitglied mitglied,
			ArrayListModel<Bank> bankListMode) {
		super(mitglied);
		this.mitglied = mitglied;
		this.personEditorModel = new PersonEditorModel<Person>(mitglied);
		for (Person person : mitglied.getPersonen()) {
			if (person.isWeiblich()) {
				mother = (Human) person;
			} else {
				father = (Human) person;
			}
		}
		personListMode = new ArrayListModel<PersonenTypen>();
		personListMode.addAll(Accessor.getPersontypen());
		personSelectionInList = new SelectionInList<PersonenTypen>(
				(ListModel) personListMode);
		personSelectionInList.setSelectionIndex(0);

		this.bankListMode = bankListMode;
		bankSelectionInList = new SelectionInList<Bank>(
				(ListModel) bankListMode);

//		berufsTypSelectionInList = new SelectionInList<BerufsTyp>(
//				(ListModel) getBerufsTypListModel());
		initEventhandler();
	}

	private void initEventhandler() {
		getPersonenTypenSelectionInList().addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION,
				new BeanChangedHandler());
	}

	public ArrayListModel<Bank> getBankListModel() {
		return bankListMode;
	}

	public SelectionInList<Bank> getBankSelectionInList() {
		return bankSelectionInList;
	}

	public ArrayListModel<PersonenTypen> getPersonenTypenListModel() {
		return personListMode;
	}

	public SelectionInList<PersonenTypen> getPersonenTypenSelectionInList() {
		return personSelectionInList;
	}

//	public ArrayListModel<BerufsTyp> getBerufsTypListModel() {
//		if (berufsTypListMode == null) {
//			berufsTypListMode = new ArrayListModel<BerufsTyp>(Accessor
//					.getAllBerufsTyp());
//		}
//		return berufsTypListMode;
//	}
//
//	public SelectionInList<BerufsTyp> getBerufsTypSelectionInList() {
//		return berufsTypSelectionInList;
//	}

	void accept() {
		Collection<Person> humans = new ArrayList<Person>();
		humans.add(mother);
		humans.add(father);
		setBean(mitglied);
		personEditorModel.setBean(mitglied);
		mitglied.setPersonen(humans);

	}

//	PresentationModel<BerufsTyp> getBerusTypEditModel() {
//		if (berufsTypModel == null) {
//			berufsTypModel = new PresentationModel<BerufsTyp>(getBerufsTyp());
//		}
//		return berufsTypModel;
//	}

//	BerufsTyp getBerufsTyp() {
//		BerufsTyp typ = getBean().getBeruf().getTyp();
//		if (typ == null) {
//			typ = new BerufsTyp();
//			getBean().getBeruf().setTyp(typ);
//		}
//		return typ;
//	}

	PersonEditorModel<Person> getPersonEditorModel(){
		return personEditorModel;
	}
	public ValidationResult validate(ValidationResult result) {
		return getBean().validate(result);
	}

	private void changeBean(Person bean) {
		setBean(bean);
		personEditorModel.setBean(bean);
		Telefon telefon = personEditorModel.getTelefonEditModel().getBean();
		if (telefon != null)
			telefon.setPerson(bean);
		BerufsTyp typ = personEditorModel.getBerufsTyp();
		personEditorModel.getBerusTypEditModel().setBean(typ);

	}

	private class BeanChangedHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			PersonenTypen typ = (PersonenTypen) evt.getNewValue();
			int odinal = typ.ordinal();
			switch (odinal) {
			case 1:
				changeBean(father);
				break;

			case 2:
				changeBean(mother);
				break;
			default:
				changeBean(mitglied);
				break;
			}
		}

	}

}
