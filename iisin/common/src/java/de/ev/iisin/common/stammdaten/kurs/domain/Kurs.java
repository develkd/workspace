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

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "KURS_SEQUENCE", sequenceName = "KURS_SEQ")
public class Kurs extends ValidableModel {

	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = 488223001488049342L;

	public final static String PROPERTY_TYP = "typ";
	public final static String PROPERTY_BEMERKUNG = "bemerkung";
	public final static String PROPERTY_KLASSE = "klasse";
	public final static String PROPERTY_LEHRER = "lehrer";
	public final static String PROPERTY_MITGLIEDER = "teilnehmer";
	public final static String PROPERTY_NOTEN = "noten";

	private long kurs_id;
	private KursTyp typ;
	private String bemerkung;
	private String klasse;
	private Person lehrer;
	private Collection<Mitglied> mitglieder;
	private Collection<Kursnote> noten;

	public Kurs() {
		mitglieder = new ArrayList<Mitglied>();
		noten = new ArrayList<Kursnote>();
	}

	/**
	 * @return the kurs_id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KURS_SEQUENCE")
	public long getKurs_id() {
		return kurs_id;
	}

	/**
	 * @param kurs_id
	 *            the kurs_id to set
	 */
	public void setKurs_id(long kurs_id) {
		this.kurs_id = kurs_id;
	}

	/**
	 * @return the typ
	 */
	public KursTyp getTyp() {
		return typ;
	}

	/**
	 * @param typ
	 *            the typ to set
	 */
	public void setTyp(KursTyp typ) {
		KursTyp old = getTyp();
		this.typ = typ;
		firePropertyChange(PROPERTY_TYP, old, typ);
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

	/**
	 * @return the klasse
	 */
	public String getKlasse() {
		return klasse;
	}

	/**
	 * @param klasse
	 *            the klasse to set
	 */
	public void setKlasse(String klasse) {
		String old = getKlasse();
		this.klasse = klasse;
		firePropertyChange(PROPERTY_KLASSE, old, klasse);
	}

	/**
	 * @return the leherer
	 */
	public Person getLehrer() {
		return lehrer;
	}

	/**
	 * @param leherer
	 *            the leherer to set
	 */
	public void setLehrer(Person leherer) {
		Person old = getLehrer();
		this.lehrer = leherer;
		firePropertyChange(PROPERTY_LEHRER, old, leherer);
	}

	/**
	 * @return the mitglieder
	 */
	@ManyToMany(mappedBy = "kurse")
	public Collection<Mitglied> getMitglieder() {
		return mitglieder;
	}

	/**
	 * @param collection
	 *            the collection to set
	 */
	public void setMitglieder(Collection<Mitglied> mitglieder) {
		this.mitglieder = mitglieder;
		firePropertyChange(PROPERTY_MITGLIEDER, null, mitglieder);
	}

	/**
	 * @return the mitglieder
	 */
	@OneToMany(mappedBy = "kurs", cascade = CascadeType.ALL)
	public Collection<Kursnote> getNoten() {
		return noten;
	}

	/**
	 * @param collection
	 *            the collection to set
	 */
	public void setNoten(Collection<Kursnote> noten) {
		this.noten = noten;
		firePropertyChange(PROPERTY_NOTEN, null, noten);
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {

		return result;
	}

}
