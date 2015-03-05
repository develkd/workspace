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

package de.ev.iisin.server.stammdaten.bean.mitglied;

import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;

import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.member.domain.Bankkonto;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.common.stammdaten.member.remote.MitgliedRemote;
import de.ev.iisin.server.stammdaten.bean.person.PersonBean;

/**
 * @author Kemal D�nmez
 * 
 */
@Stateless
@RunAs("IISIN-intern")
@SecurityDomain("IisinAdminDB")
@RolesAllowed( { "IISIN-intern" })
public class MitgliedBean extends PersonBean implements MitgliedRemote {

	private static final Logger LOGGER = Logger.getLogger(MitgliedBean.class
			.getName());

	@PersistenceContext
	protected EntityManager manager;

	// @EJB
	// private PersonLocal personLocal;
	//
	public RmiResponse addMitglied(Mitglied mitglied) {
		RmiResponse response = new RmiResponse();
		RmiResponse resp = setBank(mitglied);
		if (!resp.isReturnCode())
			return resp;
		resp = setBeruf(mitglied);
		makeBlob(mitglied);
		manager.persist(mitglied);
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addMitglied: ");
			sb.append(mitglied);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(mitglied);
		response.setMessage("Mitglied bereits vorhanden");

		return response;
	}

	public void deleteMitglied(Mitglied mitglied) {
		Mitglied n = manager.find(Mitglied.class, mitglied.getPerson_ID());
		if (n != null) {
			n.setDeleted(true);
			n = manager.merge(n);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("removeMitglied: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Mitglied> getMitglieder() {
		List<Mitglied> list = manager
				.createQuery("from Mitglied where deleted=false order by name").getResultList();
		for (Mitglied mitglied : list) {
			makeImage(mitglied);
			if (!mitglied.getPersonen().isEmpty()) {
				mitglied.getPersonen().size();

				for (Person person : mitglied.getPersonen()) {
					if (!person.getTelefon().isEmpty()) {
						person.getTelefon().size();
					}
				}
			}

			if (!mitglied.getTelefon().isEmpty())
				mitglied.getTelefon().size();
			if (!mitglied.getKurse().isEmpty())
				mitglied.getKurse().size();
		}

		return list == null ? Collections.EMPTY_LIST : list;
	}

	public RmiResponse updateMitglied(Mitglied mitglied) {
		RmiResponse response = new RmiResponse();
		updatePerson(mitglied);
		mitglied = manager.merge(mitglied);
		response.setObject(mitglied);
		response.setReturnCode(true);
		return response;
	}

	// // --------- Helper -------------------------------
	// public void makeBlob(Mitglied mitglied) {
	// ImageIcon icon = mitglied.getCreatedImage();
	// if (icon == null)
	// return;
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// try {
	// ObjectOutputStream oos = new ObjectOutputStream(baos);
	// oos.writeObject(mitglied.getCreatedImage());
	// oos.close();
	// mitglied.setBlobImage(Hibernate.createBlob(baos.toByteArray()));
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	//
	// }
	//
	// public void makeImage(Mitglied mitglied) {
	// Blob blob = mitglied.getBlobImage();
	// if (blob == null)
	// return;
	//
	// try {
	// ByteArrayInputStream inos = new ByteArrayInputStream(blob.getBytes(
	// 1, (int) blob.length() + 1));
	// ObjectInputStream ois = new ObjectInputStream(inos);
	// mitglied.setCreatedImage((ImageIcon) ois.readObject());
	// } catch (Exception e) {
	// }
	//
	// }

	private RmiResponse setBank(Mitglied mitglied) {
		Bankkonto konto = mitglied.getKonto();
		Bank bank = konto.getBank();
		RmiResponse resp = addBank(bank);
		if (!resp.isReturnCode()) {
			return resp;
		}

		konto.setBank(bank);
		return resp;
	}

}
