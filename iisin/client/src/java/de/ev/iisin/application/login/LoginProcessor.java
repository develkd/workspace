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

package de.ev.iisin.application.login;

import com.jgoodies.binding.beans.Model;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.common.exceptions.ErrorTemplate;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.util.crypt.Password;

/**
 * @author Kemal Dönmez
 * 
 */
public class LoginProcessor extends Model {

	// Names of bound bean properties *****************************************

	/**
	 * Created at 24.09.2008
	 */
	private static final long serialVersionUID = -8277113745386134819L;
	public final static String PROPERTYNAME_STATE = "loginState";
	public final static String PROPERTYNAME_MESSAGE = "loginMessage";

	// Instance Fields ********************************************************

	/**
	 * Holds the login state that can be used to switch presentations.
	 * 
	 * @see #getLoginState()
	 * @see #setLoginState(LoginState)
	 */
	private int loginState;

	private String loginMessage;

	// Instance Creation ******************************************************

	public LoginProcessor() {
		this.loginState = LoginModel.START;
	}

	// Implementing the LoginProcessor API ************************************

	/**
	 * Returns the current login state.
	 * 
	 * @return the current login state.
	 * 
	 * @see #setLoginState(LoginState)
	 */
	private int getLoginState() {
		return loginState;
	}

	/**
	 * Returns the latest login message.
	 * 
	 * @return the latest login message.
	 */
	private String getLoginMessage() {
		return loginMessage;
	}

	/**
	 * Sets a new login state and notifies all registered listeners about the
	 * change.
	 * 
	 * @param newState
	 *            the login state to set
	 */
	private void setLoginState(int newState) {
		int oldState = getLoginState();
		loginState = newState;
		firePropertyChange(PROPERTYNAME_STATE, oldState, newState);
	}

	/**
	 * Sets a new login message and notifies all registered listeners about the
	 * change.
	 * 
	 * @param newMessage
	 *            the login message to set
	 */
	private void setLoginMessage(String newMessage) {
		String oldMessage = getLoginMessage();
		loginMessage = newMessage;
		firePropertyChange(PROPERTYNAME_MESSAGE, oldMessage, newMessage);
	}

	/**
	 * Logs in the user with the specified name and password. Updates the login
	 * state during this process.
	 * 
	 * @param username
	 *            the name used to login the user
	 * @param password
	 *            the user's password used to login
	 */
	public void login(final String username, final String password) {
		
		setLoginState(LoginModel.LOGGING_IN);

		Runnable loginRunnable = new Runnable() {
			public void run() {
				loginInBackgroundThread(username, password);
			}
		};
		new Thread(loginRunnable).start();
	}

	/**
	 * Logs out the user from the system. Updates the login state during this
	 * process. The logout process is performed in a background thread.
	 */
	public void logout() {
		setLoginState(LoginModel.LOGGING_OUT);

		Runnable logoutRunnable = new Runnable() {
			public void run() {
				logoutInBackgroundThread();
			}
		};
		new Thread(logoutRunnable).start();

	}

	public void loginWithoutGUI(String username, String password) {
		loginInBackgroundThread(username, password);
	}

	// Background Activities **************************************************

	private void loginInBackgroundThread(String username, String password) {
		setLoginMessage("Verbinde mit Server...");

		 String passwordMd5 = Password.encryptMD5(password);
		
		Admin admin = Accessor.login(username, passwordMd5);

		if (admin == null) {
			ClientMessageHandler.handle(ErrorTemplate.UNKNOWN_USER,
					"Benutzerdaten konnten nicht geladen werden.\n"
							+ "Es wird automatisch ausgeloggt!",
					ClientMessageType.ERROR);
			setLoginState(LoginModel.LOGIN_FAILED);
			setLoginState(LoginModel.LOGGED_OUT);
			return;
		}

		setLoginMessage("");
		setLoginState(LoginModel.LOGGED_IN);
	}

	private void logoutInBackgroundThread() {
		setLoginMessage("Speichere Benutzerdaten...");
		setLoginMessage("Melde Benutzer ab...");
		setLoginMessage("");
		setLoginState(LoginModel.LOGGED_OUT);
	}

	// Helper Code ************************************************************
//
//	private void showMessage(String text) {
//		ClientMessageHandler.showInfoDialog(text);
//	}

}
