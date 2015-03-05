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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.jgoodies.binding.beans.Model;


/**
 * @author Kemal D�nmez
 *
 */
@Entity
@SequenceGenerator(name="ZEILE_SEQUENCE", sequenceName="ZEILE_SEQ")
public class Zeile extends Model {

	/**
	 * Erzeugt am 01.01.2009
	 */
	private static final long serialVersionUID = 1672385905410432323L;
	public final static String PROPERTY_SPALTEN ="spalten";
	public final static String PROPERTY_VERANSTALLTUNG ="veranstalltung";
	
	private long zeile_id;
	private Collection<Spalte> spalten;
	private Veranstalltung veranstalltung;

	public Zeile(){
		spalten = new ArrayList<Spalte>();
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ZEILE_SEQUENCE")
	public long getZeile_ID() {
		return zeile_id;
	}

	public void setZeile_ID(long id) {
		this.zeile_id = id;
	}

	/**
	 * @return the spalten
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="zeile")
	public Collection<Spalte> getSpalten() {
		return spalten;
	}

	/**
	 * @param spalten the spalten to set
	 */
	public void setSpalten(Collection<Spalte> spalten) {
		this.spalten = spalten;
		firePropertyChange(PROPERTY_SPALTEN, null, spalten);
	}
	
	@ManyToOne
	public Veranstalltung getVeranstalltung(){
		return veranstalltung;
	}
	
	public void setVeranstalltung(Veranstalltung veranstalltung){
		Veranstalltung old = getVeranstalltung();
		this.veranstalltung = veranstalltung;
		firePropertyChange(PROPERTY_VERANSTALLTUNG, old, veranstalltung);
	}

}
