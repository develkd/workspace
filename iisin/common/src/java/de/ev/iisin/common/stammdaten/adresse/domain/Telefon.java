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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.person.domain.Person;


/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "TELEFON_SEQUENCE", sequenceName = "TELEFON_SEQ")
public class Telefon extends ValidableModel {
	/**
	 * Erzeugt am 21.12.2008
	 */
	private static final long serialVersionUID = 1055276693895040062L;
	public final static String PROPERTY_TYP = "typ";
	public final static String PROPERTY_NUMMER = "nummer";
	public final static String PROPERTY_PERSON = "person";

	private long telefon_id;
	private Person person;
	private TelefonTyp typ;
	private String nummer;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEFON_SEQUENCE")
	public long getTelefon_ID() {
		return telefon_id;
	}

	public void setTelefon_ID(long id) {
		this.telefon_id = id;
	}

	@ManyToOne
	public TelefonTyp getTyp() {
		return typ;
	}

	public void setTyp(TelefonTyp typ) {
		TelefonTyp oldTyp = getTyp();
		this.typ = typ;
		firePropertyChange(PROPERTY_TYP, oldTyp, typ);

	}
	

	@ManyToOne
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		Person old = getPerson();
		this.person = person;
		firePropertyChange(PROPERTY_PERSON, old, person);
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		String old = getNummer();
		this.nummer = nummer;
		firePropertyChange(PROPERTY_NUMMER, old, nummer);
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {
	
		return result;
	}

}
