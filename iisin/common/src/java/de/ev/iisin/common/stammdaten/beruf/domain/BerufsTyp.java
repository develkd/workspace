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

package de.ev.iisin.common.stammdaten.beruf.domain;

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

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "BERUFS_TYP_SEQUENCE", sequenceName = "BERUFS_TYP_SEQ")
public class BerufsTyp extends ValidableModel {

	/**
	 * Erzeugt am 21.12.2008
	 */
	private static final long serialVersionUID = 8964837925178394522L;

	public final static String PROPERTY_BEZEICHNUNG = "bezeichnung";
	public final static String PROPERTY_PUPIL = "pupil";

	private long berufstyp_id;

	@ComponentDeskriptor(labelEnumText = EnumAttributes.PUPIL, component = ComponentTyp.CHECKBOX, property = PROPERTY_PUPIL)
	private boolean isPupil;

	@ComponentDeskriptor(labelEnumText = EnumAttributes.DESCRIPTOR, component = ComponentTyp.COMBOBOX, listCellRender = "de.ev.iisin.application.common.components.renderer.BerufsTypenListCellRenderer", listModel = "de.ev.iisin.maintenance.typs.BerufsTypHomeModel", property = PROPERTY_BEZEICHNUNG)
	private String bezeichnung;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BERUFS_TYP_SEQUENCE")
	public long getBerufsTyp_ID() {
		return berufstyp_id;
	}

	public void setBerufsTyp_ID(long id) {
		this.berufstyp_id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		String old = getBezeichnung();
		this.bezeichnung = bezeichnung;
		firePropertyChange(PROPERTY_BEZEICHNUNG, old, bezeichnung);
	}

	public boolean isPupil() {
		return isPupil;
	}

	public void setPupil(boolean isPupil) {
		if (isPupil == isPupil())
			return;
		this.isPupil = isPupil;
		firePropertyChange(PROPERTY_PUPIL, !isPupil, isPupil);
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {
//		if (ValidateUtil.isBlank(getBezeichnung())) {
//			result.addError("Berufsbezeichnung darf nicht leer sein");
//		}
		return result;
	}

	@Transient
	@Override
	public String toString(){
		return bezeichnung != null ?  bezeichnung : "";
	}
}
