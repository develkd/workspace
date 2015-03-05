/*
 * Copyright (c) 2008 Kemal D�nmez. All Rights Reserved.
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

package de.ev.iisin.common.veranstalltung.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.jgoodies.binding.beans.Model;


/**
 * @author Kemal D�nmez
 * 
 */
@Entity
@SequenceGenerator(name = "VERANSTALLTUNG_SEQUENCE", sequenceName = "VERANSTALLTUNG_SEQ")
public class Veranstalltung extends Model {

	/**
	 * Erzeugt am 01.01.2009
	 */
	private static final long serialVersionUID = -4062546565717466041L;

	public final static String PROPERTY_ZEILEN = "zeilen";
	public final static String PROPERTY_BEZEICHNUNG = "bezeichnung";
	
	
	private long veranstalltung_id;
	private Collection<Zeile> zeilen;
	private String bezeichnung;

	public Veranstalltung(){
		zeilen = new ArrayList<Zeile>();
	}
	/**
	 * @return the veranstalltung_id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERANSTALLTUNG_SEQUENCE")
	public long getVeranstalltung_ID() {
		return veranstalltung_id;
	}

	/**
	 * @param veranstalltung_id
	 *            the veranstalltung_id to set
	 */
	public void setVeranstalltung_ID(long veranstalltung_id) {
		this.veranstalltung_id = veranstalltung_id;
	}

	/**
	 * @return the zeilen
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="veranstalltung", fetch = FetchType.EAGER)
	public Collection<Zeile> getZeilen() {
		return zeilen;
	}

	/**
	 * @param zeilen
	 *            the zeilen to set
	 */
	public void setZeilen(Collection<Zeile> zeilen) {
		this.zeilen = zeilen;
		firePropertyChange(PROPERTY_ZEILEN, null, zeilen);
	}

	/**
	 * @return the bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * @param bezeichnung
	 *            the bezeichnung to set
	 */
	public void setBezeichnung(String bezeichnung) {
		String old = getBezeichnung();
		this.bezeichnung = bezeichnung;
		firePropertyChange(PROPERTY_BEZEICHNUNG, old, bezeichnung);
	}

}
