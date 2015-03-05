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

package de.ev.iisin.common.stammdaten.member.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.common.stammdaten.kurs.domain.Kurs;
import de.ev.iisin.common.util.TimeDateUtil;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "mitgliedKey", referencedColumnName = "person_id")
public class Mitglied extends Person {

	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = 8258850643006970936L;

	public final static String PROPERTY_BAHRZAHLER = "bahrzahler";
	public final static String PROPERTY_ZAHLUNGSEINGANG = "zahlungseingang";
	public final static String PROPERTY_BEITRAG = "beitrag";
	public final static String PROPERTY_ZAHLUNGS_DATUM = "zahlungsdatum";
	public final static String PROPERTY_KONTO = "konto";
	public final static String PROPERTY_BEGIN_DATE = "beginDate";
	public final static String PROPERTY_END_DATE = "endDate";
	public final static String PROPERTY_KURSE = "kurse";

	private boolean isBahrzahler;
	private boolean hasZahlungeingang;
	private int beitrag;
	private Date zahlungsDatum;
	private Bankkonto konto;
	private Date beginDate;
	private Date endDate;
	private Collection<Kurs> kurse;
	


	public Mitglied() {
		isBahrzahler = false;
		setKonto(new Bankkonto());
		setKurse(new ArrayList<Kurs>());
		setBeginDate(TimeDateUtil.getDateOfNow());
		getPersonen().add(new Human(false));
		getPersonen().add(new Human(true));
	}
	
	
	public boolean isBahrzahler() {
		return isBahrzahler;
	}

	public void setBahrzahler(boolean isBahrzahler) {
		if (isBahrzahler == isBahrzahler())
			return;
		this.isBahrzahler = isBahrzahler;
		firePropertyChange(PROPERTY_BAHRZAHLER, !isBahrzahler, isBahrzahler);
	}

	/**
	 * @return the hasZahlungeingang
	 */
	public boolean hasZahlungeingang() {
		return hasZahlungeingang;
	}

	/**
	 * @param hasZahlungeingang
	 *            the hasZahlungeingang to set
	 */
	public void setZahlungeingang(boolean hasZahlungeingang) {
		if (hasZahlungeingang == hasZahlungeingang())
			return;
		this.hasZahlungeingang = hasZahlungeingang;
		firePropertyChange(PROPERTY_ZAHLUNGSEINGANG, !hasZahlungeingang,
				hasZahlungeingang);
	}

	public int getBeitrag() {
		return beitrag;
	}

	public void setBeitrag(int beitrag) {
		int old = getBeitrag();
		this.beitrag = beitrag;
		firePropertyChange(PROPERTY_BEITRAG, old, beitrag);
	}

	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<Kurs> getKurse() {
		return kurse;
	}

	public void setKurse(Collection<Kurs> kurse) {
		this.kurse = kurse;
		firePropertyChange(PROPERTY_KURSE, null, kurse);
	}

	/**
	 * @return the datum
	 */
	public Date getDatum() {
		return zahlungsDatum;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public Bankkonto getKonto() {
		return konto;
	}

	public void setKonto(Bankkonto konto) {
		Bankkonto old = getKonto();
		this.konto = konto;
		setBahrzahler(false);
		firePropertyChange(PROPERTY_KONTO, old, konto);
	}

	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(Date datum) {
		Date old = getDatum();
		this.zahlungsDatum = datum;
		firePropertyChange(PROPERTY_ZAHLUNGS_DATUM, old, datum);
	}


	@Temporal(TemporalType.DATE)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date date) {
		Date old = getBeginDate();
		this.beginDate = date;
		firePropertyChange(PROPERTY_BEGIN_DATE, old, date);
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date date) {
		Date old = getEndDate();
		this.endDate = date;
		firePropertyChange(PROPERTY_END_DATE, old, date);
	}
	
	@Transient
	public String getBeginDateAsString(){
		return getBeginDate() == null ?"" : DATE_FORMAT.format(getBeginDate());
	}

	
	@Transient
	public String getEndDateAsString(){
		return getEndDate() == null ?"" : DATE_FORMAT.format(getEndDate());
	}
}
