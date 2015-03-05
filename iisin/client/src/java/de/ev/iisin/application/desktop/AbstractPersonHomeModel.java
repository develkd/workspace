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

package de.ev.iisin.application.desktop;

import javax.swing.ImageIcon;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractPersonHomeModel<P> extends AbstractHomeModel<P> {
	private PresentationModel<Person> pModel;
	private PresentationModel<Adresse> adressModel;
	private PresentationModel<BerufsTyp> berufsTypModel;
	private Person orgPerson;
	private ArrayListModel<Telefon> listModel;

	public AbstractPersonHomeModel(Person person) {
		orgPerson = person;
		pModel = new PresentationModel<Person>(person);
		adressModel = new PresentationModel<Adresse>(pModel.getBean()
				.getAdresse());
		berufsTypModel = new PresentationModel<BerufsTyp>(pModel.getBean()
				.getBeruf().getTyp());
		listModel = new ArrayListModel<Telefon>();
	}

	public PresentationModel<Person> getPersonModel() {
		return pModel;
	}

	public PresentationModel<Adresse> getAdressModel() {
		return adressModel;
	}

	public PresentationModel<BerufsTyp> getBerufsTypModel() {
		return berufsTypModel;
	}

	public ArrayListModel<Telefon> getTelefonListModel() {
		return listModel;
	}

	protected Person getOrgPerson() {
		return orgPerson;
	}

	protected ImageIcon setNewBeanAndGetIcon(Person person) {
		pModel.setBean(person);
		berufsTypModel.setBean(person.getBeruf().getTyp());
		adressModel.setBean(person.getAdresse());

		listModel.clear();
		listModel.addAll(Accessor.getAllTelefonFor(person));
		ImageIcon icon = person.getCreatedImage();
		return icon == null ? ResourceUtils.getImageIcon("pupil.image",
				AbstractPersonHomeModel.class) : icon;

	}

}
