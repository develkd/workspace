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

package de.ev.iisin.common.stammdaten.bank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.common.util.validator.ValidateUtil;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "BANK_SEQUENCE", sequenceName = "BANK_SEQ")
public class Bank extends ValidableModel {

	/**
	 * Erzeugt am 27.12.2008
	 */
	private static final long serialVersionUID = 8421853317625509339L;
	public final static String PROPERTY_BANK_LEIT_ZAHL = "bankLeitZahl";
	public final static String PROPERTY_BANK_NAME = "bankName";

	private long bank_id;
	private int bankLeitZahl;
	private String bankName;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANK_SEQUENCE")
	public long getBank_ID() {
		return bank_id;
	}

	public void setBank_ID(long id) {
		this.bank_id = id;
	}

	@Column(unique = true)
	public int getBankLeitZahl() {
		return bankLeitZahl;
	}

	public void setBankLeitZahl(int blz) {
		int old = getBankLeitZahl();
		this.bankLeitZahl = blz;
		firePropertyChange(PROPERTY_BANK_LEIT_ZAHL, old, blz);
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String name) {
		String old = getBankName();
		this.bankName = name;
		firePropertyChange(PROPERTY_BANK_NAME, old, name);
	}
	
	@Transient
	public ValidationResult validate(ValidationResult result) {
		if (ValidateUtil.isBlank(getBankName())) {
			result.addError("Bankname darf nicht leer!");
		}
		if (getBankLeitZahl() < 0 && getBankLeitZahl()> 999999999) {
			result.addError("BLZ ist nich richtig!");
		}

		return result;
	}


}
