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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "BANKKONTO_SEQUENCE", sequenceName = "BANKKONTO_SEQ")
public class Bankkonto extends ValidableModel {

	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = 8930977616986422719L;

	public final static String PROPERTY_KONTO_NR = "kontoNr";
	public final static String PROPERTY_MITGLIED = "mitglied";
	public final static String PROPERTY_BANK = "bank";

	private long bankkonto_id;
	private Mitglied mitglied;
	private Bank bank;
	private long kontoNr;

	public Bankkonto() {
		setBank(new Bank());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANKKONTO_SEQUENCE")
	public long getBankkonto_ID() {
		return bankkonto_id;
	}

	public void setBankkonto_ID(long id) {
		this.bankkonto_id = id;
	}

	@OneToOne(mappedBy = "konto")
	public Mitglied getMitglied() {
		return mitglied;
	}

	public void setMitglied(Mitglied mitglied) {
		Mitglied old = getMitglied();
		this.mitglied = mitglied;
		firePropertyChange(PROPERTY_MITGLIED, old, mitglied);
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		Bank old = getBank();
		this.bank = bank;
		firePropertyChange(PROPERTY_BANK, old, bank);
	}

	public long getKontoNr() {
		return kontoNr;
	}

	public void setKontoNr(long kontoNr) {
		long old = getKontoNr();
		this.kontoNr = kontoNr;
		firePropertyChange(PROPERTY_KONTO_NR, old, kontoNr);
	}

	@Transient
	public String getBakkontoFormated() {
		StringBuilder builder = new StringBuilder();
		if (bank != null) {
			builder.append(kontoNr).append(", ").append(bank.getBankName())
					.append(" ").append(bank.getBankLeitZahl());
		}

		return builder.toString();
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {

		return result;
	}

}
