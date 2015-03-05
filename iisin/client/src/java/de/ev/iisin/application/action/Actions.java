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

package de.ev.iisin.application.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.uif_lite.application.ActionManager;

import de.ev.iisin.application.controller.MainController;
import de.ev.iisin.application.controller.MainManager;
import de.ev.iisin.application.model.MainModel;
import de.ev.iisin.common.util.ResourceUtil;
import de.ev.iisin.maintenance.MaintenaceHomeModel;
import de.ev.iisin.maintenance.admin.AdminMainModel;
import de.ev.iisin.maintenance.bank.BankHomeModel;
import de.ev.iisin.maintenance.human.HumanHomeModel;
import de.ev.iisin.maintenance.member.MitgliedHomeModel;
import de.ev.iisin.maintenance.typs.BerufsTypHomeModel;
import de.ev.iisin.maintenance.typs.PersonTypHomeModel;
import de.ev.iisin.maintenance.typs.TelefonTypHomeModel;
import de.ev.iisin.pupil.PupilMainModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class Actions {
	public static final ResourceBundle RESOURCE = ResourceUtil
			.getResourceBundle(Actions.class);

	// Action IDs *************************************************************
	public static final String ABOUT_ID = "about";

	// Datei
	public static final String SAVE_ID = "save";
	public static final String PRINT_ID = "print";
	public static final String EXIT_ID = "exit";
	public static final String CANCLE_ID = "cancle";
	public static final String CREATE_ID = "create";
	public static final String EDIT_ID = "edit";
	public static final String DELETE_ID = "delete";
	public static final String EDIT_FATHER_ID = "editFather";
	public static final String EDIT_MOTHER_ID = "editMother";

	public static final String UPDATE_PASSWORD = "updatePassword";

	public static final String OPEN_MAINTENANCE = "openMaintenance";
	public static final String OPEN_PUPIL_VIEW = "openPupil";
	public static final String OPEN_ADMIN_VIEW = "openAdminView";
	public static final String OPEN_MEMBER_VIEW = "openMember";
	public static final String OPEN_BANK_VIEW = "openBank";
	public static final String OPEN_PERSON_TYP_VIEW = "openPersonTyp";
	public static final String OPEN_TELEFON_TYP_VIEW = "openTelefonTyp";
	public static final String OPEN_BERUFS_TYP_VIEW = "openBerufsTyp";
	public static final String OPEN_HUMAN_VIEW = "openHuman";
	// Instance Fields ********************************************************

	private final MainModel mainModule;

	/**
	 * Refers to the controller that is used to forward all action behavior to.
	 * 
	 * @see #getController()
	 */
	private final MainController controller;
	private static Actions instance;

	// Instance Creation ******************************************************

	/**
	 * Initializes the actions used in this application. Registers all actions
	 * with the <code>ActionManager</code> and observes changes in the main
	 * module's selection and project to update the enablement of some actions.
	 * 
	 * @param mainModule
	 *            provides bound properties for the selection and project
	 * @param controller
	 *            used to forward all action behavior
	 */
	private Actions(MainModel mainModule, MainController controller) {

		this.mainModule = mainModule;
		this.controller = controller;
		registerActions();
		// mainModule.addPropertyChangeListener(new ModuleChangeHandler());
		updateActionEnablement();
		instance = this;
	}

	public static ResourceBundle getBundel() {
		return RESOURCE;
	}

	/**
	 * Initializes the actions used in this application. Registers all actions
	 * with the <code>ActionManager</code> and observes changes in the main
	 * module's selection and project to update the enablement of some actions.
	 * 
	 * @param mainModule
	 *            provides bound properties for the selection and project
	 * @param controller
	 *            used to forward all action behavior
	 */
	public static void initializeFor(MainModel mainModule,
			MainController controller) {
		new Actions(mainModule, controller);
	}

	/**
	 * Registers <code>Action</code> s at the <code>ActionManager</code>
	 * using three different styles for demoing purposes, see class comment.
	 */
	private void registerActions() {
		// Allgemein Actions
		register(ABOUT_ID);
		register(SAVE_ID);
		register(PRINT_ID);
		register(EXIT_ID);
		register(CANCLE_ID);
		register(CREATE_ID);
		register(DELETE_ID);
		register(EDIT_ID);
		register(UPDATE_PASSWORD);
		register(EDIT_FATHER_ID);
		register(EDIT_MOTHER_ID);
		register(OPEN_MAINTENANCE);
		register(OPEN_PUPIL_VIEW);
		register(OPEN_ADMIN_VIEW);
		register(OPEN_MEMBER_VIEW);
		register(OPEN_BANK_VIEW);
		register(OPEN_PERSON_TYP_VIEW);
		register(OPEN_TELEFON_TYP_VIEW);
		register(OPEN_BERUFS_TYP_VIEW);
		register(OPEN_HUMAN_VIEW);

	}

	public static void Exit() {
		instance.mainModule.aboutToExitApplication();
	}

	// ***********************************************************************

	/*
	 * An implementation of the <code> Action </code> interface that dispatches
	 * using an ID.
	 */
	@SuppressWarnings("serial")
	private class DispatchingAction extends AbstractAction {

		private final String id;

		/*
		 * Constructs a <code> DispatchingAction </code> .
		 */
		private DispatchingAction(String id) {
			this.id = id;
		}

		/**
		 * Dispatches using the stored id, then delegates to the appropriate
		 * method of the application controller, that finally does the real
		 * work.
		 * <p>
		 * 
		 */

		public void actionPerformed(ActionEvent event) {
			// Erfassungsstelle
			// Datenbank
			if (id.equals(UPDATE_PASSWORD)) {
				MainManager.openPasswordDialog();
			} else if (id.equals(CREATE_ID)) {

			} else if (id.equals(EXIT_ID)) {
				Exit();
			} else if (id.equals(EDIT_FATHER_ID)) {
				mainModule.getPupilMainModel().getPupilEditModule()
						.openFatherDialog();
			} else if (id.equals(EDIT_MOTHER_ID)) {
				mainModule.getPupilMainModel().getPupilEditModule()
						.openMotherDialog();
			} else if (id.equals(ABOUT_ID)) {
				mainModule.openAboutDialog();
			} else if (id.equals(OPEN_MAINTENANCE)) {
				mainModule
						.setMainCard(MaintenaceHomeModel.CARDNAME_MAINTENANCE);
			} else if (id.equals(OPEN_PUPIL_VIEW)) {
				mainModule.setMainCard(PupilMainModel.CARDNAME_PUPIL);
			} else if (id.equals(OPEN_ADMIN_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						AdminMainModel.CARDNAME_ADMIN);
			} else if (id.equals(OPEN_MEMBER_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						MitgliedHomeModel.CARDNAME_MEMBER);
			} else if (id.equals(OPEN_BANK_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						BankHomeModel.CARDNAME_BANK);
			} else if (id.equals(OPEN_PERSON_TYP_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						PersonTypHomeModel.CARDNAME_PERSON_TYP);
			} else if (id.equals(OPEN_TELEFON_TYP_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						TelefonTypHomeModel.CARDNAME_TELEFON_TYP);
			} else if (id.equals(OPEN_BERUFS_TYP_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						BerufsTypHomeModel.CARDNAME_BERUFS_TYP);
			} else if (id.equals(OPEN_HUMAN_VIEW)) {
				mainModule.getMaintenaceHomeModel().setMainCard(
						HumanHomeModel.CARDNAME_HUMAN);
			} else {
				Logger.getLogger("Actions").warning("Unknown action: " + id);
			}
		}

	}

	// Updating the Action Enablement *****************************************

	/**
	 * Updates the enablement state of actions whenever the main card changes.
	 * Can be used to enable or disable actions that are only valid if the home
	 * panel or edit panel is visible.
	 */
	private void updateActionEnablement() {
		//
	}

	// // Listens to main card changes and updates enablements.
	// private class ModuleChangeHandler implements PropertyChangeListener {
	//
	// /**
	// * The main card has changed. Updates the action enablement.
	// *
	// * @param evt
	// * describes the property change
	// */
	// public void propertyChange(PropertyChangeEvent evt) {
	// String propertyName = evt.getPropertyName();
	// // if (ErfstMainModule.PROPERTYNAME_MAINCARD.equals(propertyName)) {
	// // updateActionEnablement();
	// // }
	// }
	// }

	// Helper Code **********************************************************

	/**
	 * Registers a <code>DispatchingAction</code> with the
	 * <code>ActionManager</code> using the given id.
	 */
	private void register(String id) {
		register(id, true);
	}

	/**
	 * Registers a <code>DispatchingAction</code> with the
	 * <code>ActionManager</code> using the given id and enablement.
	 */
	private void register(String id, boolean enabled) {
		Action action = new DispatchingAction(id);
		ActionManager.register(id, action);
		action.setEnabled(enabled);
	}

	// Accessing Collaborators **********************************************

	MainController getController() {
		return controller;
	}

}
