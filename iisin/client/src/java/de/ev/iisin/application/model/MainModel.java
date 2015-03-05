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

package de.ev.iisin.application.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.utils.Dialogs;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.login.LoginModel;
import de.ev.iisin.application.login.LoginProcessor;
import de.ev.iisin.maintenance.MaintenaceHomeModel;
import de.ev.iisin.pupil.PupilMainModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class MainModel extends AbstractMainModel {

	// Lazily Created References to Submodules *******************************
	private static final ResourceBundle BUNDLE = ResourceUtils
			.getResourceBundle(MainModel.class);
	/**
	 * Created at 01.10.2008
	 */
	private static final long serialVersionUID = -545551106465034593L;
	/**
	 * Provides models and actions for the login page, login process, logout and
	 * logout process.
	 */
	private LoginModel loginModule;

	private PupilMainModel pupilMainModel;
	private MaintenaceHomeModel maintenanceHomeModel;

	// Instance Creation ******************************************************

	public void registerLoginListener() {
		getLoginModule().addPropertyChangeListener(
				LoginModel.PROPERTYNAME_LOGIN_STATE, new LoggedInHandler());

	}

	// Exposing Submodules ****************************************************

	/**
	 * Lazily creates and returns the login module that provides all models and
	 * actions required to present a login panel, and the login/logout process
	 * panels.
	 * 
	 * @return the login module
	 */
	public LoginModel getLoginModule() {
		if (loginModule == null) {
			LoginProcessor loginProcessor = new LoginProcessor();
			loginModule = new LoginModel(loginProcessor);
		}
		return loginModule;
	}

	public PupilMainModel getPupilMainModel() {
		if (pupilMainModel == null) {
			pupilMainModel = new PupilMainModel(getMaintenaceHomeModel().getMitgliedMainModel());
		}
		return pupilMainModel;
	}

	public MaintenaceHomeModel getMaintenaceHomeModel() {
		if (maintenanceHomeModel == null) {
			maintenanceHomeModel = new MaintenaceHomeModel();
		}
		return maintenanceHomeModel;
	}

	// Application Shutdown *************************************************

	public void openAboutDialog() {
		Dialogs.openAboutDialog();
	}

	/**
	 * Leaves the application if nobody vetos against the close request.
	 */
	public void aboutToExitApplication() {
		if (requestForWindowClose())
			exitApplication();
	}

	/**
	 * Checks and answers whether we accept a close request. The default
	 * behavior just says: true = OK to close. Some application may ask "Do you
	 * really want to close the application?".
	 * 
	 * @return true to accept the window close, false to reject it
	 */
	private boolean requestForWindowClose() {
		// TODO: Check for pending edits.
		return true;
	}

	/**
	 * Shuts down the application, in this case just exit the System. Larger
	 * application may need to perform additional operations before exiting the
	 * System, for example store state, save preferences, etc.
	 */
	public void exitApplication() {
		if (!ClientMessageHandler.showConfirmDialog("Exit",BUNDLE
				.getString("exit")))
			return;
		System.exit(0);
	}

	// Event Handling ********************************************************

	private class LoggedInHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			int state = ((Integer) evt.getNewValue()).intValue();
			if (state == LoginModel.LOGGED_OUT) {
				// cleanModuleReferenzen();
			}
			if (state != LoginModel.LOGGED_IN)
				return;
			//getPupilMainModel().prepareCoreData();
		}

	}

}
