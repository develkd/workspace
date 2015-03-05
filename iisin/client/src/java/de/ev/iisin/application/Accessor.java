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

package de.ev.iisin.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.common.exceptions.ErrorTemplate;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.stammdaten.admin.remote.AdminRemote;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.common.stammdaten.human.remote.HumanRemote;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.common.stammdaten.member.remote.MitgliedRemote;
import de.ev.iisin.common.util.PersonenTypen;

/**
 * @author Kemal Dönmez
 * 
 */
public final class Accessor {
	private static Accessor ACCESSOR = new Accessor();

	private static final String CONTEXT_ROOT = "IISIN-Server/";

	private static Context context;
	private static List<PersonenTypen> personenTypenList;

	private Accessor() {

	}

	// PersonenTypen------------------------------------------------------
	// --------------------------------------------------------------------

	// ADMIN ---------------------------------------------------
	public static Admin login(String username, String password) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? null : remote.login(username, password);
	}

	public static RmiResponse addAdmin(Admin admin) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote.addAdmin(admin);
	}

	public static RmiResponse updateAdmin(Admin admin) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote.updateAdmin(admin);
	}

	public static boolean deleteAdmin(Admin admin) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		if (remote == null)
			return false;
		remote.deleteAdmin(admin);
		return true;
	}

	public static List<Admin> getAllAdmin() {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<Admin>() : remote.getAllAdmin();
	}

	public static RmiResponse addPersonTyp(PersonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote.addPersonTyp(typ);
	}

	public static RmiResponse updatePersonTyp(PersonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote.updatePersonTyp(typ);
	}

	public static boolean deletePersonTyp(PersonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		if (remote == null)
			return false;
		remote.deletePersonTyp(typ);
		return true;
	}

	public static List<PersonTyp> getAllPersonTyp() {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<PersonTyp>() : remote
				.getAllPersonTyp();
	}

	public static List<TelefonTyp> getAllTelefonTyp() {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<TelefonTyp>() : remote
				.getAllTelefonTyp();
	}

	public static List<Person> findPerson(PersonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<Person>() : remote
				.findPersonsOf(typ);
	}

	public static PersonTyp findPerson(int id) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? null : remote.findPersonTypById(id);
	}

	public static RmiResponse addTelefonTyp(TelefonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote.addTelefonTyp(typ);
	}

	public static RmiResponse updateTelefonTyp(TelefonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new RmiResponse() : remote
				.updateTelefonTyp(typ);
	}

	public static boolean deleteTelefonTyp(TelefonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		if (remote == null)
			return false;
		remote.deleteTelefonTyp(typ);
		return true;
	}

	public static List<Telefon> findTelefon(TelefonTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<Telefon>() : remote
				.findTelefonOf(typ);
	}

	public static List<Beruf> findBeruf(BerufsTyp typ) {
		AdminRemote remote = ACCESSOR.getAdminRemote();
		return remote == null ? new ArrayList<Beruf>() : remote
				.findBerufOf(typ);
	}

	public static List<PersonTyp> getPersontypenForMember() {
		List<PersonTyp> memberTypen = new ArrayList<PersonTyp>();
		for (PersonTyp typ : getAllPersonTyp()) {
			if (typ.getId() == 1)
				continue;
			memberTypen.add(typ);
		}
		return memberTypen;
	}

	public static List<PersonenTypen> getPersontypen() {
		if (personenTypenList == null) {
			personenTypenList = new ArrayList<PersonenTypen>();
			for (PersonenTypen typ : PersonenTypen.values()) {
				if (typ.ordinal() > 2)
					break;
				
					personenTypenList.add(typ);
				
			}
		}
		return personenTypenList;
	}

	// Mitglieder ---------------------------------------------------------
	public static RmiResponse addMitglied(Mitglied mitglied) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote
				.addMitglied(mitglied);
	}

	public static RmiResponse updateMitglied(Mitglied mitglied) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote
				.updateMitglied(mitglied);
	}

	public static boolean deleteMitglied(Mitglied mitglied) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		if (remote == null)
			return false;
		remote.deleteMitglied(mitglied);
		return true;
	}

	public static List<Mitglied> getAllMitglieder() {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new ArrayList<Mitglied>() : remote
				.getMitglieder();
	}

	// Personen ---------------------------------------------------------
	public static RmiResponse addHuman(Human human) {
		HumanRemote remote = ACCESSOR.getHumanRemote();
		return remote == null ? new RmiResponse() : remote.addHuman(human);
	}

	public static RmiResponse updateHuman(Human human) {
		HumanRemote remote = ACCESSOR.getHumanRemote();
		return remote == null ? new RmiResponse() : remote.updateHuman(human);
	}

	public static boolean deleteHuman(Human mitglied) {
		HumanRemote remote = ACCESSOR.getHumanRemote();
		if (remote == null)
			return false;
		remote.deleteHuman(mitglied);
		return true;
	}

	public static List<Human> getAllHumans() {
		HumanRemote remote = ACCESSOR.getHumanRemote();
		return remote == null ? new ArrayList<Human>() : remote.getHumans();
	}

	// -------------------- Bank -----------------------

	public static List<Bank> getAllBank() {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new ArrayList<Bank>() : remote.getAllBank();
	}

	public static RmiResponse addBank(Bank bank) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote.addBank(bank);
	}

	public static RmiResponse updateBank(Bank bank) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote.updateBank(bank);
	}

	public static boolean deleteBank(Bank bank) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? false : remote.deleteBank(bank);
	}

	// -------------------- BerufsTypen -----------------------
	public static List<BerufsTyp> getAllBerufsTyp() {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new ArrayList<BerufsTyp>() : remote
				.getAllBerufsTyp();
	}

	public static RmiResponse addBerufsTyp(BerufsTyp typ) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote.addBerufsTyp(typ);
	}

	public static RmiResponse updateBerufsTyp(BerufsTyp typ) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new RmiResponse() : remote.updateBerufsTyp(typ);
	}

	public static boolean deleteBerufsTyp(BerufsTyp typ) {
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? false : remote.deleteBerufsTyp(typ);
	}

	// -------------------- Misc -----------------------
	public static List<Telefon> getAllTelefonFor(Person person) {
		if (person.getPerson_ID() < 1) {
			ArrayList<Telefon> tList = new ArrayList<Telefon>();
			Collection<Telefon> cList = person.getTelefon();
			if (cList != null)
				tList.addAll(cList);
			return tList;
		}
		MitgliedRemote remote = ACCESSOR.getMitgliedRemote();
		return remote == null ? new ArrayList<Telefon>() : remote
				.getTelefon(person);
	}

	// -------------------- AdminRemote -----------------------
	private AdminRemote getAdminRemote() {
		try {
			return (AdminRemote) getContext(CONTEXT_ROOT
					+ AdminRemote.JNDI_REMOTE_NAME);

		} catch (NamingException e) {
			ClientMessageHandler.handle(ErrorTemplate.CONTEXT_ERROR, e
					.getMessage(), ClientMessageType.ERROR);
		}

		return null;

	}

	private MitgliedRemote getMitgliedRemote() {
		try {
			return (MitgliedRemote) getContext(CONTEXT_ROOT
					+ MitgliedRemote.JNDI_REMOTE_NAME);

		} catch (NamingException e) {
			ClientMessageHandler.handle(ErrorTemplate.CONTEXT_ERROR, e
					.getMessage(), ClientMessageType.ERROR);
		}

		return null;

	}

	private HumanRemote getHumanRemote() {
		try {
			return (HumanRemote) getContext(CONTEXT_ROOT
					+ HumanRemote.JNDI_REMOTE_NAME);

		} catch (NamingException e) {
			ClientMessageHandler.handle(ErrorTemplate.CONTEXT_ERROR, e
					.getMessage(), ClientMessageType.ERROR);
		}

		return null;

	}

	// ---------------- Contextloader ------------------------
	/**
	 * The setting of the properties are moved
	 * 
	 * @see de.vaberlin.vabnet.application.VABnetApplication.initializeContext();
	 */
	private Object getContext(String lookupName) throws NamingException {
		if (context == null) {
			Properties env = new Properties();
			env.setProperty(Context.SECURITY_PRINCIPAL, "IISIN-intern");
			env.setProperty(Context.SECURITY_CREDENTIALS, "IISIN-intern");
			env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.jboss.security.jndi.JndiLoginInitialContextFactory");
			context = new InitialContext(env);
		}
		return context.lookup(lookupName);
	}

}
