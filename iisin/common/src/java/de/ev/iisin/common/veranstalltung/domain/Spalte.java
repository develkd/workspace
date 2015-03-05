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

package de.ev.iisin.common.veranstalltung.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.jgoodies.binding.beans.Model;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@SequenceGenerator(name = "SPALTE_SEQUENCE", sequenceName = "SPALTE_SEQ")
public class Spalte extends Model {

	/**
	 * Erzeugt am 01.01.2009
	 */
	private static final long serialVersionUID = -477455354604584640L;
	public final static String PROPERTY_SPALTEN_NAME = "spaltenname";
	public final static String PROPERTY_SPALTEN_VALUE = "spaltenvalue";
	public final static String PROPERTY_ZEILE = "zeile";
	public final static String PROPERTY_SPALTEN_INDEX = "spaltenIndex";

	private long spalten_id;
	private String spaltenname;
	private SpaltenValue spaltenValue;
	private Zeile zeile;
	private int spaltenIndex;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPALTE_SEQUENCE")
	public long getSpalten_ID() {
		return spalten_id;
	}

	public void setSpalten_ID(long id) {
		this.spalten_id = id;
	}

	public String getSpaltenName() {
		return spaltenname;
	}

	public void setSpaltenName(String spaltenname) {
		String old = getSpaltenName();
		this.spaltenname = spaltenname;
		firePropertyChange(PROPERTY_SPALTEN_NAME, old, spaltenname);
	}

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public SpaltenValue getSpaltenValue() {
		return spaltenValue;
	}

	public void setSpaltenValue(SpaltenValue object) {
		SpaltenValue old = getSpaltenValue();
		spaltenValue = object;
		firePropertyChange(PROPERTY_SPALTEN_VALUE, old, object);

	}
	
	@ManyToOne
	public Zeile getZeile(){
		return zeile;
	}
	
	public void setZeile(Zeile zeile){
		Zeile old = getZeile();
		this.zeile = zeile;
		firePropertyChange(PROPERTY_ZEILE, old, zeile);
	}
	
	
	public int getSpaltenIndex(){
		return spaltenIndex;
	}
	
	public void setSpaltenIndex(int index){
		int old = getSpaltenIndex();
		this.spaltenIndex = index;
		firePropertyChange(PROPERTY_SPALTEN_INDEX, old, index);
	}
}
