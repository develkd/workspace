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

import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.application.view.TelefonEditModel;
import de.ev.iisin.binding.ValidablePresentationModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 *
 */
public class PersonEditorModel<B> extends ValidablePresentationModel<B> {

	/**
	 * Erzeugt am 01.08.2009
	 */
	private static final long serialVersionUID = -2937068260359519508L;
	private TelefonEditModel telefonEditModel;
	private ArrayListModel<BerufsTyp> berufsTypListMode;
	private SelectionInList<BerufsTyp> berufsTypSelectionInList;
	private PresentationModel<BerufsTyp> berufsTypModel;

	public PersonEditorModel(B bean) {
		super(bean);
		berufsTypSelectionInList = new SelectionInList<BerufsTyp>(
				(ListModel) getBerufsTypListModel());

	}

	
	public BerufsTyp getBerufsTyp() {
		BerufsTyp typ =((Person) getBean()).getBeruf().getTyp();
		if (typ == null) {
			typ = new BerufsTyp();
			((Person) getBean()).getBeruf().setTyp(typ);
		}
		return typ;
	}

	public PresentationModel<BerufsTyp> getBerusTypEditModel() {
		if (berufsTypModel == null) {
			berufsTypModel = new PresentationModel<BerufsTyp>(getBerufsTyp());
		}
		return berufsTypModel;
	}

	public ArrayListModel<BerufsTyp> getBerufsTypListModel() {
		if (berufsTypListMode == null) {
			berufsTypListMode = new ArrayListModel<BerufsTyp>(Accessor
					.getAllBerufsTyp());
		}
		return berufsTypListMode;
	}

	public SelectionInList<BerufsTyp> getBerufsTypSelectionInList() {
		return berufsTypSelectionInList;
	}

	public TelefonEditModel getTelefonEditModel() {
		if (telefonEditModel == null) {
			Telefon telefon = new Telefon();
			telefon.setPerson((Person)getBean());
			telefonEditModel = new TelefonEditModel(telefon);
		}
		return telefonEditModel;
	}

	public ValidationResult validate(ValidationResult result) {
		// TODO Auto-generated method stub
		return result;
	}

}
