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

package de.ev.iisin.maintenance.human;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidablePresentationModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.maintenance.common.PersonEditorModel;

/**
 * @author Kemal D�nmez
 *
 */
public class HumanEditModel extends ValidablePresentationModel<Human> {

	
	/**
	 * Erzeugt am 04.04.2009
	 */
	private static final long serialVersionUID = -7046701856274500544L;
	private PersonEditorModel<Person> personEditorModel;

	public HumanEditModel(Human bean) {
		super(bean);
		this.personEditorModel = new PersonEditorModel<Person>(bean);
	}

	
	PersonEditorModel<Person> getPersonEditorModel(){
		return personEditorModel;
	}
	

	public ValidationResult validate(ValidationResult result) {
		// TODO Auto-generated method stub
		return result;
	}

}
