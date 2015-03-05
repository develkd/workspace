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

package de.ev.iisin.server.stammdaten.bean.admin;

import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;

import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.stammdaten.admin.remote.AdminRemote;
import de.ev.iisin.common.stammdaten.admin.resources.AdminMessageKey;
import de.ev.iisin.common.util.PersonenTypen;
import de.ev.iisin.common.util.TimeDateUtil;
import de.ev.iisin.server.stammdaten.bean.person.PersonBean;

/**
 * @author Kemal D�nmez
 * 
 */
@Stateless
@RunAs("IISIN-intern")
@SecurityDomain("IisinAdminDB")
@RolesAllowed( { "IISIN-intern" })
public class AdminBean extends PersonBean implements AdminRemote {

	private static final Logger LOGGER = Logger.getLogger(AdminBean.class
			.getName());

	@PersistenceContext
	protected EntityManager manager;

	public AdminBean(){
		
	}
	// @EJB
	// private PersonLocal personLocal;

	public RmiResponse addAdmin(Admin admin) {
		RmiResponse response = new RmiResponse();
		if (containsAdmin(admin.getName())) {
			response.setObject(AdminMessageKey.ADMIN_EXIST);
			return response;
		}
		PersonTyp typ = findPersonTypById(PersonenTypen.TEACHER.ordinal());
		if (admin.isAdmin() && !admin.isTeacher()) {
			typ = findPersonTypById(PersonenTypen.ADMIN.ordinal());
		}
		admin.setPersonTyp(typ);
		response = setBeruf(admin);
		makeBlob(admin);
		manager.persist(admin);
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addAdmin: ");
			sb.append(admin);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(admin);

		return response;
	}

	@SuppressWarnings("unchecked")
	private boolean containsAdmin(String value) {
		Query query = manager.createQuery("from Admin n where deleted=false and n.name = :name");
		query.setParameter("name", value);
		List<Admin> n = query.getResultList();

		return !n.isEmpty();
	}

	public Admin changePassword(Admin admin) {
		Admin a = manager.merge(admin);
		manager.persist(a);
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("changePasswordAdmin: ");
			sb.append(a);
			LOGGER.debug(sb);
		}

		return a;
	}

	public void deleteAdmin(Admin admin) {
		Admin n = manager.find(Admin.class, admin.getPerson_ID());
		if (n != null) {
			admin.setDeleted(true);
			manager.merge(admin);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("removeAdmin: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Admin> getAllAdmin() {
		List<Admin> list = manager.createQuery("from Admin where deleted=false order by name")
				.getResultList();

		for (Admin admin : list) {
			makeImage(admin);
			if (!admin.getTelefon().isEmpty())
				admin.getTelefon().size();
		}

		return list == null ? Collections.EMPTY_LIST : list;
	}

	public Admin login(String name, String passwort) {
		Query query = manager
				.createQuery("from Admin n where n.admin = true and n.deleted=false and name = :name and n.passwort = :passwort");
		query.setParameter("name", name);
		query.setParameter("passwort", passwort);
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Abfrage: " + query.toString());
		try {
			Admin n = (Admin) query.getSingleResult();
			n.setLastLogin(TimeDateUtil.getDateOfNow());
			return updateAdminLastLogin(n);
		} catch (NoResultException notFound) {
			StringBuilder sb = new StringBuilder("Admin ");
			sb.append(name);
			sb.append(" nicht vorhanden");
			LOGGER.error(sb);

		} catch (Exception notFound) {
			StringBuilder sb = new StringBuilder("Admin ");
			sb.append(name);
			sb.append(" mehrmals vorhanden");
			LOGGER.error(sb);
		}
		return null;
	}

	private Admin updateAdminLastLogin(Admin admin) {
		return manager.merge(admin);
	}

	public RmiResponse updateAdmin(Admin admin) {
		RmiResponse response = new RmiResponse();
		updatePerson(admin);
		admin = manager.merge(admin);
		response.setObject(admin);
		response.setReturnCode(true);
		return response;

	}
}
