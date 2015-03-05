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

package de.ev.iisin.server.stammdaten.bean.human;

import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.jboss.security.annotation.SecurityDomain;

import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.common.stammdaten.human.remote.HumanRemote;
import de.ev.iisin.common.util.PersonenTypen;
import de.ev.iisin.server.stammdaten.bean.mitglied.MitgliedBean;
import de.ev.iisin.server.stammdaten.bean.person.PersonBean;

/**
 * @author Kemal D�nmez
 * 
 */
@Stateless
@RunAs("IISIN-intern")
@SecurityDomain("IisinAdminDB")
@RolesAllowed( { "IISIN-intern" })
public class HumanBean extends PersonBean implements HumanRemote {

	private static final Logger LOGGER = Logger.getLogger(MitgliedBean.class
			.getName());

	@PersistenceContext
	protected EntityManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.common.stammdaten.human.remote.HumanRemote#addHuman(de.ev.iisin.common.stammdaten.human.domain.Human)
	 */
	public RmiResponse addHuman(Human human) {
		RmiResponse response = new RmiResponse();
		PersonTyp typ = findPersonTypById(PersonenTypen.HUMAN.ordinal());
		human.setPersonTyp(typ);
		response = setBeruf(human);
		makeBlob(human);
		manager.persist(human);
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("addHuman: ");
			sb.append(human);
			LOGGER.debug(sb);
		}
		response.setReturnCode(true);
		response.setObject(human);
		response.setMessage("Human bereits vorhanden");

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.common.stammdaten.human.remote.HumanRemote#deleteHuman(de.ev.iisin.common.stammdaten.human.domain.Human)
	 */
	public void deleteHuman(Human human) {
		Human n = manager.find(Human.class, human.getPerson_ID());
		if (n != null) {
			n.setDeleted(true);
			manager.merge(n);
		}
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("removeHuman: ");
			sb.append(n == null ? "null" : n);
			LOGGER.debug(sb);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.common.stammdaten.human.remote.HumanRemote#getHumans()
	 */
	@SuppressWarnings("unchecked")
	public List<Human> getHumans() {
		List<Human> list = manager.createQuery("from Human where deleted=false order by name")
				.getResultList();
		for (Human human : list) {
			makeImage(human);
			if (!human.getTelefon().isEmpty())
				human.getTelefon().size();
		}

		return list == null ? Collections.EMPTY_LIST : list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.common.stammdaten.human.remote.HumanRemote#updateHuman(de.ev.iisin.common.stammdaten.human.domain.Human)
	 */
	public RmiResponse updateHuman(Human human) {
		RmiResponse response = new RmiResponse();
		updatePerson(human);
		human = manager.merge(human);
		response.setObject(human);
		response.setReturnCode(true);
		return response;
	}

}
