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

package de.ev.iisin.maintenance.admin;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.ValidablePresentationModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.util.crypt.Password;
import de.ev.iisin.maintenance.common.PersonEditorModel;

/**
 * @author Kemal Dönmez
 *
 */
public class AdminEditModel extends ValidablePresentationModel<Admin> {

	public final static String PROPERTY_PASSWORD ="password";
	public final static String PROPERTY_PASSWORD_REF ="passwordRef";
	
	/**
	 * Erzeugt am 04.08.2009
	 */
	private static final long serialVersionUID = -6747219116693227518L;
	private PersonEditorModel<Person> personEditorModel;
	private String password;
	private String passwordRef;
	
	public AdminEditModel(Admin bean) {
		super(bean);
		this.personEditorModel = new PersonEditorModel<Person>(bean);
	}

	
	PersonEditorModel<Person> getPersonEditorModel(){
		return personEditorModel;
	}

	
	/**
	 * @return the password
	 */
	public String getPassword() {
		password = getBean().getPasswort();
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		String old = getPassword();
		this.password = Password.encryptMD5(password);
		getBean().setPasswort(this.password);
		firePropertyChange(PROPERTY_PASSWORD, old, this.password);
	}


	/**
	 * @return the passwordRef
	 */
	public String getPasswordRef() {
		passwordRef = getBean().getPasswortRef();
		return passwordRef;
	}


	/**
	 * @param passwordRef the passwordRef to set
	 */
	public void setPasswordRef(String passwordRef) {
		String old = getPasswordRef();
		this.passwordRef = Password.encryptMD5(passwordRef);
		getBean().setPasswortRef(this.password);
		firePropertyChange(PROPERTY_PASSWORD_REF, old, this.passwordRef);
	}


	public ValidationResult validate(ValidationResult result) {
		// TODO Auto-generated method stub
		return result;
	}

}
