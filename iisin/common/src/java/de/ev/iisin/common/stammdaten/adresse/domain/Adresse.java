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

package de.ev.iisin.common.stammdaten.adresse.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.util.validator.ValidateUtil;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "ADRESSE_SEQUENCE", sequenceName = "ADRESSE_SEQ")
public class Adresse extends ValidableModel {

	/**
	 * Erzeugt am 21.12.2008
	 */
	private static final long serialVersionUID = -3927903652957307597L;

	public final static String PROPERTY_STARSSE = "strasse";
	public final static String PROPERTY_PLZ = "plz";
	public final static String PROPERTY_ORT = "ort";
	public final static String PROPERTY_PERSON = "person";

	private long adresse_id;
	private String strasse;
	private int plz;
	private String ort;
	private Person person;

	public Adresse() {
	}

	/**
	 * @return the adresse_id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADRESSE_SEQUENCE")
	public long getAdresse_id() {
		return adresse_id;
	}

	/**
	 * @param adresse_id
	 *            the adresse_id to set
	 */
	public void setAdresse_id(long adresse_id) {
		this.adresse_id = adresse_id;
	}

	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * @param strasse
	 *            the strasse to set
	 */
	public void setStrasse(String strasse) {
		String old = getStrasse();
		this.strasse = strasse;
		firePropertyChange(PROPERTY_STARSSE, old, strasse);
	}

	/**
	 * @return the plz
	 */
	public int getPlz() {
		return plz;
	}

	/**
	 * @param plz
	 *            the plz to set
	 */
	public void setPlz(int plz) {
		int old = getPlz();
		this.plz = plz;
		firePropertyChange(PROPERTY_PLZ, old, plz);
	}

	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * @param ort
	 *            the ort to set
	 */
	public void setOrt(String ort) {
		String old = getOrt();
		this.ort = ort;
		firePropertyChange(PROPERTY_ORT, old, ort);
	}


	@OneToOne(mappedBy = "adresse", cascade=CascadeType.ALL)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		Person old = getPerson();
		this.person = person;
		firePropertyChange(PROPERTY_PERSON, old, person);
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {
		if (ValidateUtil.isBlank(getStrasse())) {
			result.addError("Strasse darf nicht leer sein");
		}
		if (ValidateUtil.isBlank(getOrt())) {
			result.addError("Ort darf nicht leer sein");
		} 
		if (ValidateUtil.isNotPLZ(getPlz())) {
			result.addError("Plz ist nicht richtig");
		}

		return result;
	}

}
