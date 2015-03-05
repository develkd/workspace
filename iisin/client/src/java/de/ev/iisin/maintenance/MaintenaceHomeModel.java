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

package de.ev.iisin.maintenance;

import javax.swing.Action;

import com.jgoodies.uif_lite.application.ActionManager;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.desktop.AbstractMainPageModel;
import de.ev.iisin.maintenance.admin.AdminMainModel;
import de.ev.iisin.maintenance.bank.BankHomeModel;
import de.ev.iisin.maintenance.human.HumanHomeModel;
import de.ev.iisin.maintenance.member.MitgliedHomeModel;
import de.ev.iisin.maintenance.typs.BerufsTypHomeModel;
import de.ev.iisin.maintenance.typs.PersonTypHomeModel;
import de.ev.iisin.maintenance.typs.TelefonTypHomeModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class MaintenaceHomeModel extends AbstractMainPageModel {
	public static final String CARDNAME_MAINTENANCE = "maintenace";

	/**
	 * Erzeugt am 25.01.2009
	 */
	private static final long serialVersionUID = -3745348467433528588L;
	private Action adminAction;
	private Action memberAction;
	private Action bankAction;
	private Action personAction;
	private Action telefonTypAction;
	private Action berufsTypAction;
	private Action humanAction;

	private AdminMainModel adminMainModel;
	private MitgliedHomeModel mitgliedMainModel;
	private PersonTypHomeModel personTypMainModel;
	private TelefonTypHomeModel telefonTypMainModel;
	private BerufsTypHomeModel berufsTypHomeModel;
	private BankHomeModel bankHomeModel;
	private HumanHomeModel humanHomeModel;
	
	public MaintenaceHomeModel() {
		adminAction = ActionManager.get(Actions.OPEN_ADMIN_VIEW);
		memberAction = ActionManager.get(Actions.OPEN_MEMBER_VIEW);
		bankAction = ActionManager.get(Actions.OPEN_BANK_VIEW);
		personAction = ActionManager.get(Actions.OPEN_PERSON_TYP_VIEW);
		telefonTypAction = ActionManager.get(Actions.OPEN_TELEFON_TYP_VIEW);
		berufsTypAction= ActionManager.get(Actions.OPEN_BERUFS_TYP_VIEW);
		humanAction= ActionManager.get(Actions.OPEN_HUMAN_VIEW);
	}

	public Action[] getSidbarActions() {
		return new Action[] { adminAction, memberAction, humanAction, bankAction };
	}

	public Action[] getSidbarTypActions() {
		return new Action[] { personAction, telefonTypAction, berufsTypAction };
	}

	// Models --------------------------------------
	public AdminMainModel getAdminMainModel() {
		if (adminMainModel == null) {
			adminMainModel = new AdminMainModel();
		}
		return adminMainModel;
	}

	public MitgliedHomeModel getMitgliedMainModel() {
		if (mitgliedMainModel == null) {
			mitgliedMainModel = new MitgliedHomeModel();
		}
		return mitgliedMainModel;
	}

	public HumanHomeModel getHumanHomeModel() {
		if (humanHomeModel == null) {
			humanHomeModel = new HumanHomeModel();
		}
		return humanHomeModel;
	}

	public PersonTypHomeModel getPersonTypMainModel() {
		if (personTypMainModel == null) {
			personTypMainModel = new PersonTypHomeModel();
		}
		return personTypMainModel;
	}

	public TelefonTypHomeModel getTelefonTypMainModel() {
		if (telefonTypMainModel == null) {
			telefonTypMainModel = new TelefonTypHomeModel();
		}
		return telefonTypMainModel;
	}

	public BerufsTypHomeModel getBerufsTypHomeModel() {
		if (berufsTypHomeModel == null) {
			berufsTypHomeModel = new BerufsTypHomeModel();
		}
		return berufsTypHomeModel;
	}

	public BankHomeModel getBankHomeModel() {
		if (bankHomeModel == null) {
			bankHomeModel = new BankHomeModel();
		}
		return bankHomeModel;
	}

}
