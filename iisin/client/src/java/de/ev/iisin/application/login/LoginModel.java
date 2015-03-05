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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.common.exceptions.ErrorTemplate;
import de.ev.iisin.common.util.validator.ValidateUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public final class LoginModel extends Model {

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(LoginModel.class);
	// State Constants ********************************************************

	/**
	 * Created at 24.09.2008
	 */
	private static final long serialVersionUID = -2458861655207050113L;
	public static final int START = 0;
	public static final int LOGGING_IN = 1;
	public static final int LOGGED_IN = 2;
	public static final int LOGIN_FAILED = 3;
	public static final int LOGGING_OUT = 4;
	public static final int LOGGED_OUT = 5;
	public static final int LOGOUT_FAILED = 6;
	public static final int ONLY_IMPORT_LOGGED = 7;

	// Names of Bound Bean Properties *****************************************

	public final static String PROPERTYNAME_USERNAME = "username";
	public final static String PROPERTYNAME_PASSWORD = "password";
	public final static String PROPERTYNAME_LOGIN_STATE = "loginState";
	public final static String PROPERTYNAME_LOGIN_MESSAGE = "loginMessage";

	// Instance Fields ********************************************************

	/**
	 * The action used to login. This can be specified as custom action in one
	 * of the constructors, or it is an instance of DefaultLoginAction that uses
	 * a LoginProcessor to perform the login.
	 * 
	 * @see #getLoginAction()
	 */
	private final Action loginAction;

	/**
	 * The action used to logout. This can be specified as custom action in one
	 * of the constructors, or it is an instance of DefaultLoginAction that uses
	 * a LoginProcessor to perform the logout.
	 * 
	 * @see #getLogoutAction()
	 */
	private final Action logoutAction;

	/**
	 * An action used to shut down the application.
	 * 
	 * @see #getExitAction()
	 */
	private final Action exitAction;

	/**
	 * Holds the user's name that is intended to be displayed in a login panel.
	 * 
	 * @see #getUsername()
	 * @see #setUsername(String)
	 */
	private String username;

	/**
	 * Holds the password that is intended to be displayed in a login panel.
	 * 
	 * @see #getPassword()
	 * @see #setPassword(String)
	 */
	private String password;

	/**
	 * Holds the login state that can be used to switch presentations.
	 * 
	 * @see #getLoginState()
	 */
	private int loginState;

	/**
	 * Holds a text message that is intended to be displayed during the login
	 * process and logout process. It can described the steps performed during
	 * the login/logout.
	 * 
	 * @see #getLoginMessage()
	 * @see #setLoginMessage(String)
	 */
	private String loginMessage;

	// Instance Creation ******************************************************

	/**
	 * Constructs a <code>LoginModel</code> with the given actions for login,
	 * logout and exit. The login and logout actions are responsible to set this
	 * module's login state during the login and logout process.
	 * <p>
	 * 
	 * The username and password will be set to empty strings, the login state
	 * is initialized to <code>START</code>.
	 * 
	 * @param loginProcessor
	 *            performs the login and logout
	 * @param exitAction
	 *            the Action that shuts down the applictaion
	 */
	public LoginModel(LoginProcessor loginProcessor) {
		this.loginAction = new DefaultLoginAction(RESOURCE
				.getString("login.label"), loginProcessor);
		this.logoutAction = new DefaultLogoutAction("Abmelden", loginProcessor);
		this.exitAction = new DefaultExitAction(RESOURCE
				.getString("exit.label"));

		this.loginState = LoginModel.START;

		loginProcessor
				.addPropertyChangeListener(new LoginProcessChangeHandler());
	}

	// Accessors **************************************************************

	/**
	 * Returns the action that performs the login operation. If this action is
	 * performed, it'll invoke the <code>#login</code> method of this module's
	 * LoginProcessor.
	 * 
	 * @return the action that performs the login using the LoginProcessor
	 */
	public Action getLoginAction() {
		return loginAction;
	}

	/**
	 * Returns the action for the logout operation. If this action is performed,
	 * it'll invoke the <code>#logout</code> method of this module's
	 * LoginProcessor.
	 * 
	 * @return the action that performs the logout using the LoginProcessor
	 */
	public Action getLogoutAction() {
		return logoutAction;
	}

	/**
	 * Returns an action for the exit operation. If this action is performed,
	 * it'll shutdown the application as specified in by the exit Action that
	 * has been used to construct this module.
	 * 
	 * @return an Action that shuts down the application
	 */
	public Action getExitAction() {
		return exitAction;
	}

	/**
	 * Returns the current login state.
	 * 
	 * @return the current login state.
	 * 
	 * @see #setLoginState(LoginState)
	 */
	public int getLoginState() {
		return loginState;
	}

	/**
	 * Sets a new login state and notifies all registered listeners about the
	 * change.
	 * <p>
	 * 
	 * <strong>Note:</strong> This method can be executed from any thread. It
	 * will ensure that the setter is invoked in the EventDispatchThread.
	 * 
	 * @param newState
	 *            the login state to set
	 */
	void setLoginState(final int newState) {
		if (!SwingUtilities.isEventDispatchThread()) {
			Runnable runnable = new Runnable() {
				public void run() {
					setLoginState(newState);
				}
			};
			SwingUtilities.invokeLater(runnable);
			return;
		}
		int oldState = getLoginState();
		loginState = newState;
		firePropertyChange(PROPERTYNAME_LOGIN_STATE, oldState, newState);
	}

	/**
	 * Returns the name used to login the user. It is intended to be entered in
	 * a login panel; and it can be displayed during the login/logout process.
	 * 
	 * @return the name usedto login the user
	 * 
	 * @see #setUsername(String)
	 * @see #getPassword()
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the password used to login the user. It is intended to be entered
	 * in a login panel using a password field.
	 * 
	 * @return the password used to login the user
	 * 
	 * @see #setPassword(String)
	 * @see #getUsername()
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns a text loginMessage that is intended to be displayed during the
	 * login/logout process. It shall describe the current step performed during
	 * the login/logout.
	 * 
	 * @return a text that describes the step performed during login/logout
	 * 
	 * @see #setLoginMessage(String)
	 * @see #getLoginState()
	 */
	public String getLoginMessage() {
		return loginMessage;
	}

	/**
	 * Sets the name that is used to login the user.
	 * 
	 * @param newUsername
	 *            the username to set
	 * 
	 * @see #getUsername()
	 */
	public void setUsername(String newUsername) {
		String oldUsername = getUsername();
		username = newUsername;
		firePropertyChange(PROPERTYNAME_USERNAME, oldUsername, newUsername);
	}

	/**
	 * Sets the password used to login the user.
	 * 
	 * @param newPassword
	 *            the password to set
	 * 
	 * @see #getPassword()
	 */
	public void setPassword(String newPassword) {
		String oldPassword = getPassword();
		password = newPassword;
		firePropertyChange(PROPERTYNAME_PASSWORD, oldPassword, newPassword);
	}

	/**
	 * Sets the the text message that describes the login/logout process.
	 * <p>
	 * 
	 * <strong>Note:</strong> This method can be executed from any thread. It
	 * will ensure that the setter is invoked in the EventDispatchThread.
	 * 
	 * @param newMessage
	 *            the message text to set
	 * 
	 * @see #getLoginMessage()
	 */
	void setLoginMessage(final String newMessage) {
		if (!SwingUtilities.isEventDispatchThread()) {
			Runnable runnable = new Runnable() {
				public void run() {
					setLoginMessage(newMessage);
				}
			};
			SwingUtilities.invokeLater(runnable);
			return;
		}
		String oldMessage = getLoginMessage();
		loginMessage = newMessage;
		firePropertyChange(PROPERTYNAME_LOGIN_MESSAGE, oldMessage, newMessage);
	}


	private void tryToLogin(LoginProcessor loginProcessor) {
		if (ValidateUtil.isBlank(getPassword())) {
			ClientMessageHandler.handle(ErrorTemplate.LOGIN_PASSWORD,
					ClientMessageType.ERROR);
			return;

		}
		if (ValidateUtil.isBlank(getUsername())) {
			ClientMessageHandler.handle(ErrorTemplate.LOGIN_USERNAME,
					ClientMessageType.ERROR);
			return;

		}

		loginProcessor.login(getUsername(), getPassword());
	}

	// Helper Classes
	// ***********************************************************

	@SuppressWarnings("serial")
	private class DefaultLoginAction extends AbstractAction {

		private final LoginProcessor loginProcessor;

		private DefaultLoginAction(String name, LoginProcessor loginProcessor) {
			super(name);
			this.loginProcessor = loginProcessor;
		}

		/**
		 * Invokes the login operation using this module's LoginProcessor.
		 */
		public void actionPerformed(ActionEvent evt) {
			tryToLogin(loginProcessor);
		}
	}

	@SuppressWarnings("serial")
	private class DefaultExitAction extends AbstractAction {


		private DefaultExitAction(String name) {
			super(name);
		}

		/**
		 * Invokes the login operation using this module's LoginProcessor.
		 */
		public void actionPerformed(ActionEvent evt) {
			Actions.Exit();
		}
	}

	@SuppressWarnings("serial")
	private class DefaultLogoutAction extends AbstractAction {

		private final LoginProcessor loginProcessor;

		private DefaultLogoutAction(String name, LoginProcessor loginProcessor) {
			super(name);
			this.loginProcessor = loginProcessor;

		}

		/**
		 * Invokes the logout operation using this module's LoginProcessor.
		 */
		public void actionPerformed(ActionEvent evt) {
			loginProcessor.logout();
		}
	}

	private class LoginProcessChangeHandler implements PropertyChangeListener {

		/**
		 * The observed LoginProcessor has changed. Updates this module's login
		 * state or login message to reflect this change. That in turn will fire
		 * a property change event.
		 * 
		 * @param evt
		 *            the event that describes the property change
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(LoginProcessor.PROPERTYNAME_STATE)) {
				int newState = ((Integer) evt.getNewValue()).intValue();
				setLoginState(newState);
			} else if (evt.getPropertyName().equals(
					LoginProcessor.PROPERTYNAME_MESSAGE)) {
				String newMessage = (String) evt.getNewValue();
				setLoginMessage(newMessage);
			}
		}
	}
}
