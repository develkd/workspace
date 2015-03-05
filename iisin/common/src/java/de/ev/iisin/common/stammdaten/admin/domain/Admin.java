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

package de.ev.iisin.common.stammdaten.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.common.descriptor.ComponentDeskriptor;
import de.ev.iisin.common.descriptor.ComponentTyp;
import de.ev.iisin.common.descriptor.EnumAttributes;
import de.ev.iisin.common.person.domain.Person;

/**
 * @author Kemal Dönmez
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "adminKey", referencedColumnName = "person_id")
public class Admin extends Person {

	/**
	 * Erzeugt am 24.12.2008
	 */
	private static final long serialVersionUID = 6040587989664001587L;

	public final static String PROPERTY_PASSWORT = "passwort";
	public final static String PROPERTY_PASSWORT_REF = "passwortRef";
	public final static String PROPERTY_ADMIN = "admin";
	public final static String PROPERTY_TEACHER = "teacher";
	public final static String PROPERTY_MENTOR = "mentor";

	@ComponentDeskriptor(labelEnumText = EnumAttributes.PASSWD, component = ComponentTyp.PASSWORT, property = PROPERTY_PASSWORT)
	private String passwort;

	@ComponentDeskriptor(labelEnumText = EnumAttributes.PASSWD_REF, component = ComponentTyp.PASSWORT, property = PROPERTY_PASSWORT_REF)
	private String passwortref;

	private boolean isAdmin;
	private boolean isTeacher;
	private boolean isMentor;
	private Date lastLogin;

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		String old = getPasswort();
		this.passwort = passwort;
		firePropertyChange(PROPERTY_PASSWORT, old, passwort);
	}

	@Transient
	public String getPasswortRef() {
		return passwortref;
	}

	public void setPasswortRef(String passwort) {
		String old = getPasswortRef();
		this.passwortref = passwort;
		firePropertyChange(PROPERTY_PASSWORT_REF, old, passwort);
	}

	
	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		if(isAdmin==isAdmin())
			return;
		
		this.isAdmin = isAdmin;
		firePropertyChange(PROPERTY_ADMIN, !isAdmin, isAdmin);
	}

	/**
	 * @return the isTeacher
	 */
	public boolean isTeacher() {
		return isTeacher;
	}

	/**
	 * @param isTeacher the isTeacher to set
	 */
	public void setTeacher(boolean isTeacher) {
		if(isTeacher == isTeacher())
			return;
		this.isTeacher = isTeacher;
		firePropertyChange(PROPERTY_TEACHER, !isTeacher, isTeacher);
	}

	
	
	/**
	 * @return the isMentor
	 */
	public boolean isMentor() {
		return isMentor;
	}

	/**
	 * @param isMentor the isMentor to set
	 */
	public void setMentor(boolean isMentor) {
		if(isMentor == isMentor())
			return;
		this.isMentor = isMentor;
		firePropertyChange(PROPERTY_MENTOR, !isMentor, isMentor);
	}

	@Temporal(TemporalType.DATE)
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date date) {
		this.lastLogin = date;
	}

	@Transient
	public ValidationResult validate(ValidationResult result) {
		 result = super.validate(result);
//		if (ValidateUtil.isBlank(getPasswort())) {
//			result.addError("Passwort darf nicht leer sein");
//		} else if (ValidateUtil.isBlank(getPasswortRef())) {
//			result.addError("Passwort darf nicht leer sein");
//		} else if (!getPasswort().equals(getPasswortRef())) {
//			result.addError("Passwort stimmt nicht überein");
//		}
		return result;
	}
}
