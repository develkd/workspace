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

package de.ev.iisin.common.person.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.descriptor.ComponentDeskriptor;
import de.ev.iisin.common.descriptor.ComponentTyp;
import de.ev.iisin.common.descriptor.EnumAttributes;
import de.ev.iisin.common.util.validator.ValidateUtil;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "PERSON_TYP_SEQUENCE", sequenceName = "PERSON_TYP_SEQ")
public class PersonTyp extends ValidableModel {


	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = -1587654795359554660L;

	public final static String PROPERTY_BEZEICHNUNG = "bezeichnung";

	private long personTyp_id;

	@ComponentDeskriptor(labelEnumText = EnumAttributes.DESCRIPTOR, component = ComponentTyp.TEXT, property = PROPERTY_BEZEICHNUNG)
	private String bezeichnung;
	private int id;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_TYP_SEQUENCE")
	public long getPersonTyp_ID() {
		return personTyp_id;
	}

	public void setPersonTyp_ID(long id) {
		this.personTyp_id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		String old = getBezeichnung();
		this.bezeichnung = bezeichnung;
		firePropertyChange(PROPERTY_BEZEICHNUNG, old, bezeichnung);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {
		if (ValidateUtil.isBlank(getBezeichnung())) {
			result.addError("Bezeichnung darf nicht leer!");
		}
		return result;
	}

}
