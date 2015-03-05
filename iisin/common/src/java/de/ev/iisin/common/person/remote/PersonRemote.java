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

package de.ev.iisin.common.person.remote;

import java.util.List;

import javax.ejb.Remote;

import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.adresse.domain.Adresse;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 * 
 */
@Remote
public interface PersonRemote {
	static final String JNDI_REMOTE_NAME = "PersonBean/remote";

	List<Bank> getAllBank();

	RmiResponse addBank(Bank bank);

	RmiResponse updateBank(Bank bank);

	boolean deleteBank(Bank bank);

	List<PersonTyp> getAllPersonTyp();

	List<TelefonTyp> getAllTelefonTyp();

	RmiResponse addTelefonTyp(TelefonTyp typ);

	List<Telefon> findTelefonOf(TelefonTyp typ);

	List<Beruf> findBerufOf(BerufsTyp typ);

	RmiResponse updateTelefonTyp(TelefonTyp typ);

	void deleteTelefonTyp(TelefonTyp typ);

	List<Person> findPersonsOf(PersonTyp typ);

	PersonTyp findPersonTypById(int id);

	RmiResponse addPersonTyp(PersonTyp typ);

	RmiResponse updatePersonTyp(PersonTyp typ);

	boolean deletePersonTyp(PersonTyp typ);

	Adresse getAdresse(Person person);

	List<Telefon> getTelefon(Person person);

	List<BerufsTyp> getAllBerufsTyp();

	RmiResponse addBerufsTyp(BerufsTyp typ);

	RmiResponse updateBerufsTyp(BerufsTyp typ);

	boolean deleteBerufsTyp(BerufsTyp typ);

}
