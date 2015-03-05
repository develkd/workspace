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

package de.ev.iisin.common.stammdaten.kurs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "KURSNOTE_SEQUENCE", sequenceName = "KURSNOTE_SEQ")
public class Kursnote extends ValidableModel {

	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = 6799211406899660783L;

	public final static String PROPERTY_MITGLIED = "mitglied";
	public final static String PROPERTY_KURS = "kurs";
	public final static String PROPERTY_NOTE = "note";
	public final static String PROPERTY_BEMERKUNG = "bemerkung";

	private long kursnote_id;
	private Mitglied mitglied;
	private Kurs kurs;
	private String note;
	private String bemerkung;

	/**
	 * @return the kursnote_id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KURSNOTE_SEQUENCE")
	public long getKursnote_id() {
		return kursnote_id;
	}

	/**
	 * @param kursnote_id
	 *            the kursnote_id to set
	 */
	public void setKursnote_id(long kursnote_id) {
		this.kursnote_id = kursnote_id;
	}

	/**
	 * @return the mitglied
	 */
	public Mitglied getMitglied() {
		return mitglied;
	}

	/**
	 * @param mitglied
	 *            the mitglied to set
	 */
	public void setMitglied(Mitglied mitglied) {
		Mitglied old = getMitglied();
		this.mitglied = mitglied;
		firePropertyChange(PROPERTY_MITGLIED, old, mitglied);
	}

	/**
	 * @return the kurs
	 */
	@ManyToOne
	public Kurs getKurs() {
		return kurs;
	}

	/**
	 * @param kurs
	 *            the kurs to set
	 */
	public void setKurs(Kurs kurs) {
		Kurs old = getKurs();
		this.kurs = kurs;
		firePropertyChange(PROPERTY_KURS, old, kurs);
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		String old = getNote();
		this.note = note;
		firePropertyChange(PROPERTY_NOTE, old, note);
	}

	/**
	 * @return the bemerkung
	 */
	public String getBemerkung() {
		return bemerkung;
	}

	/**
	 * @param bemerkung
	 *            the bemerkung to set
	 */
	public void setBemerkung(String bemerkung) {
		String old = getBemerkung();
		this.bemerkung = bemerkung;
		firePropertyChange(PROPERTY_BEMERKUNG, old, bemerkung);
	}

	@Transient
	public String getBezeichnung() {
		String value = "";
		value = getKurs() == null ? value : getKurs().getTyp() == null ? value
				: getKurs().getTyp().getBezeichnung();
		return value;
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {

		return result;
	}

}
