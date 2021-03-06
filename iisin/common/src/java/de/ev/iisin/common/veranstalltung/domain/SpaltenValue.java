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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.jgoodies.binding.beans.Model;

/**
 * @author Kemal D�nmez
 *
 */
@Entity
@SequenceGenerator(name="SPALTEN_VALUE_SEQUENCE",sequenceName="SPALTEN_VALUE_SEQ")
public class SpaltenValue extends Model{

	/**
	 * Erzeugt am 02.01.2009
	 */
	private static final long serialVersionUID = 1559833465818415290L;
	
	private long id;
	private Spalte spalte;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SPALTEN_VALUE_SEQUENCE")
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	@OneToOne(mappedBy="spaltenValue")
	public Spalte getSpalte(){
		return spalte;
	}
	
	public void setSpalte(Spalte spalte){
		this.spalte = spalte;
	}

}
