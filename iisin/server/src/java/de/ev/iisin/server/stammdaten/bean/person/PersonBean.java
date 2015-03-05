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

package de.ev.iisin.server.stammdaten.bean.person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.ImageIcon;

import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;

import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.person.remote.PersonRemote;
import de.ev.iisin.common.person.resources.Person_PersonTypMessageKey;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.bank.resources.BankMessageKey;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;
import de.ev.iisin.common.stammdaten.beruf.resources.BerufsTypMessageKey;

/**
 * @author Kemal D�nmez
 * 
 */
@Stateless
@RunAs("IISIN-intern")
@SecurityDomain("IisinAdminDB")
@RolesAllowed( { "IISIN-intern" })
public abstract class PersonBean implements PersonRemote {
	private static final Logger LOGGER = Logger.getLogger(PersonBean.class
			.getName());

	@PersistenceContext
	protected EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Person> findPersonsOf(PersonTyp typ) {
		Query query = manager
				.createQuery("from Person p where p.personTyp = :typ");
		query.setParameter("typ", typ);
		List<Person> list = query.getResultList();
		return list == null ? Collections.EMPTY_LIST : list;
	}

	public PersonTyp findPersonTypById(int id) {
		Query query = manager.createQuery("from PersonTyp p where p.id = :id");
		query.setParameter("id", id);
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Abfrage: " + query.toString());
		try {
			return (PersonTyp) query.getSingleResult();
		} catch (NoResultException notFound) {
			return createUnknownPerson(id);
		} catch (Exception notFound) {
			StringBuilder sb = new StringBuilder("PersonTyp ");
			sb.append(id);
			sb.append(" mehrmals vorhanden");
			LOGGER.error(sb);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<PersonTyp> getAllPersonTyp() {
		List<PersonTyp> list = manager.createQuery(
				"from PersonTyp p order by id").getResultList();

		return list == null ? Collections.EMPTY_LIST : list;
	}

	@SuppressWarnings("unchecked")
	public List<TelefonTyp> getAllTelefonTyp() {
		List<TelefonTyp> list = manager.createQuery(
				"from TelefonTyp p order by id").getResultList();

		return list == null ? Collections.EMPTY_LIST : list;
	}

	public RmiResponse addTelefonTyp(TelefonTyp typ) {
		RmiResponse response = new RmiResponse();
		manager.persist(typ);

		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addTelefonTyp: ");
			sb.append(typ);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(typ);
		return response;
	}

	public void deleteTelefonTyp(TelefonTyp typ) {
		TelefonTyp n = manager.find(TelefonTyp.class, typ.getTelefonTyp_ID());
		if (n != null) {
			manager.remove(n);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("removeTelefonTyp: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}

	}

	public RmiResponse updateTelefonTyp(TelefonTyp typ) {
		RmiResponse response = new RmiResponse();
		response.setObject(manager.merge(typ));
		response.setReturnCode(true);
		return response;
	}

	@SuppressWarnings("unchecked")
	public List<Telefon> findTelefonOf(TelefonTyp typ) {
		Query query = manager.createQuery("from Telefon t where t.typ = :typ");
		query.setParameter("typ", typ);
		List<Telefon> list = query.getResultList();
		return list == null ? Collections.EMPTY_LIST : list;
	}

	@SuppressWarnings("unchecked")
	public List<Beruf> findBerufOf(BerufsTyp typ) {
		Query query = manager.createQuery("from Beruf b where b.typ = :typ");
		query.setParameter("typ", typ);
		List<Beruf> list = query.getResultList();
		return list == null ? Collections.EMPTY_LIST : list;
	}

	public RmiResponse addPersonTyp(PersonTyp typ) {
		RmiResponse response = new RmiResponse();

		if (containsPersonTyp(typ.getBezeichnung())) {
			response.setObject(Person_PersonTypMessageKey.PERSON_EXIST);
			return response;
		}

		manager.persist(typ);

		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addPersonTyp: ");
			sb.append(typ);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(typ);
		return response;
	}

	public boolean deletePersonTyp(PersonTyp typ) {
		PersonTyp n = manager.find(PersonTyp.class, typ.getPersonTyp_ID());
		if (n != null) {
			manager.remove(n);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("removePersonTyp: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}

		return n != null;
	}

	public RmiResponse updatePersonTyp(PersonTyp typ) {
		RmiResponse response = new RmiResponse();
		response.setObject(manager.merge(typ));
		response.setReturnCode(true);
		return response;
	}

	public Adresse getAdresse(Person person) {
		return (Adresse) manager.createNamedQuery("Person.getAdresse")
				.setParameter("id", person.getAdresse()).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Bank> getAllBank() {
		List<Bank> list = manager
				.createQuery("from Bank order by bankLeitZahl").getResultList();

		return list == null ? Collections.EMPTY_LIST : list;
	}

	@SuppressWarnings("unchecked")
	public List<Telefon> getTelefon(Person person) {
		Query query = manager
				.createQuery("from Telefon t where t.person = :person");
		query.setParameter("person", person);
		List<Telefon> list = query.getResultList();
		return list == null ? Collections.EMPTY_LIST : list;

	}

	public RmiResponse addBank(Bank bank) {
		RmiResponse response = new RmiResponse();

		if (containsBankName(bank)) {
			response.setObject(BankMessageKey.BANK_NAME_EXIST);
			return response;
		}

		if (containsBLZ(bank)) {
			response.setObject(BankMessageKey.BANK_BLZ_EXIST);
			return response;
		}

		manager.persist(bank);

		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addBank: ");
			sb.append(bank);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(bank);
		return response;

	}

	public RmiResponse updateBank(Bank bank) {
		RmiResponse response = new RmiResponse();
		response.setObject(manager.merge(bank));
		response.setReturnCode(true);
		return response;
	}

	public boolean deleteBank(Bank bank) {
		Bank n = manager.find(Bank.class, bank.getBank_ID());
		if (n != null) {
			manager.remove(n);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("deleteBank: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}

		return n != null;
	}

	@SuppressWarnings("unchecked")
	public List<BerufsTyp> getAllBerufsTyp() {
		List<BerufsTyp> list = manager.createQuery(
				"from BerufsTyp p order by id").getResultList();

		return list == null ? Collections.EMPTY_LIST : list;
	}

	public RmiResponse addBerufsTyp(BerufsTyp typ) {
		RmiResponse response = new RmiResponse();

		if (containsBerufsTyp(typ.getBezeichnung())) {
			response.setObject(BerufsTypMessageKey.TYP_EXIST);
			return response;
		}

		manager.persist(typ);

		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addBerufsTyp: ");
			sb.append(typ);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(typ);
		return response;
	}

	public boolean deleteBerufsTyp(BerufsTyp typ) {
		BerufsTyp n = manager.find(BerufsTyp.class, typ.getBerufsTyp_ID());
		if (n != null) {
			manager.remove(n);
			return true;
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("deleteBerufsTyp: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}

		return false;
	}

	public RmiResponse updateBerufsTyp(BerufsTyp typ) {
		RmiResponse response = new RmiResponse();
		response.setObject(manager.merge(typ));
		response.setReturnCode(true);
		return response;
	}

	// ----- private helperMethods --------------------
	@SuppressWarnings("unchecked")
	protected boolean containsBankName(Bank bank) {
		Query query = manager
				.createQuery("from Bank n where n.bankName = :name");
		query.setParameter("name", bank.getBankName());
		List<Bank> n = query.getResultList();

		return !n.isEmpty();

	}

	@SuppressWarnings("unchecked")
	protected boolean containsBLZ(Bank bank) {
		Query query = manager
				.createQuery("from Bank n where n.bankLeitZahl = :blz");
		query.setParameter("blz", bank.getBankLeitZahl());
		List<Bank> n = query.getResultList();

		return !n.isEmpty();

	}

	private PersonTyp createUnknownPerson(int id) {
		PersonTyp p = new PersonTyp();
		p.setId(id);
		p.setBezeichnung("Change Name");
		return (PersonTyp) addPersonTyp(p).getObject();
	}

	
	@SuppressWarnings("unchecked")
	private boolean containsPersonTyp(String value) {
		Query query = manager
				.createQuery("from PersonTyp n where n.bezeichnung = :name");
		query.setParameter("name", value);
		List<PersonTyp> n = query.getResultList();

		return !n.isEmpty();

	}

	@SuppressWarnings("unchecked")
	private boolean containsBerufsTyp(String value) {
		Query query = manager
				.createQuery("from BerufsTyp n where n.bezeichnung = :name");
		query.setParameter("name", value);
		List<PersonTyp> n = query.getResultList();

		return !n.isEmpty();

	}

	protected RmiResponse setBeruf(Person person) {
		List<Person> p = new ArrayList<Person>();
		p.add(person);
		p.addAll(person.getPersonen());
		RmiResponse resp = new RmiResponse();
		for (Person fperson : p) {
			resp = setBerufsTyp(fperson);
			if (!resp.isReturnCode()) {
				return resp;
			}
		}
		return resp;
	}

	private RmiResponse setBerufsTyp(Person person) {
		Beruf beruf = person.getBeruf();
		BerufsTyp typ = beruf.getTyp();
		RmiResponse resp = new RmiResponse();
		resp.setReturnCode(true);
		if (typ == null)
			return resp;

		if (typ.getBerufsTyp_ID() > 0) {
			resp = updateBerufsTyp(typ);
		} else {
			resp = addBerufsTyp(typ);
			if (!resp.isReturnCode()) {
				return resp;
			}

		}
		return resp;
	}

	protected void updatePerson(Person p) {
		ImageIcon b = p.getCreatedImage();
		p.setBlobImage(null);
		p = manager.merge(p);
		p.setCreatedImage(b);
		makeBlob(p);
	}

	// --------- Helper -------------------------------
	public ImageIcon makeBlob(Person person) {
		ImageIcon icon = person.getCreatedImage();
		if (icon == null)
			return null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(person.getCreatedImage());
			oos.close();
//			person.setBlobImage(Hibernate.createBlob(baos.toByteArray()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return icon;
	}

	public void makeImage(Person person) {
		Blob blob = person.getBlobImage();
		if (blob == null)
			return;

		try {
			ByteArrayInputStream inos = new ByteArrayInputStream(blob.getBytes(
					1, (int) blob.length() + 1));
			ObjectInputStream ois = new ObjectInputStream(inos);
			person.setCreatedImage((ImageIcon) ois.readObject());
		} catch (Exception e) {
		}

	}

}
