/*
 * Copyright (c) 2002-2005 JGoodies Karsten Lentzsch. All Rights Reserved.
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
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
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

package com.jgoodies.uif_lite.application;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.common.util.ResourceUtil;

public final class ActionManager {

	// private static final ResourceBundle RESOURCE =
	// ResourceUtil.getBundlePath(Actions.class);
//	private static final ResourceBundle RESOURCE = ResourceUtils
//	.getBundlePath(Actions.class);
	

	private static final ActionManager INSTANCE = new ActionManager();

	private final Map<String, Action> actions;

	// Suppresses default constructor, ensuring non-instantiability.
	private ActionManager() {
		this.actions = new HashMap<String, Action>(50);
	}

	// /**
	// * Sets the path to the <code>ResourceBundle</code> that
	// * is used to read <code>Action</code> values.
	// *
	// * @param path the path to the ResourceBundle
	// */
	// public static void setBundlePath(String path) {
	// INSTANCE.bundle = ResourceBundle.getBundle(path);
	// }
	// public static void setBundle(ResourceBundle bundle) {
	// INSTANCE.bundle = bundle;
	// }
	//
	/**
	 * Registers an <code>Action</code> for the given <code>id</code>.
	 * 
	 * @param id
	 *            the key used to store and retrieve the Action
	 * @param action
	 *            the Action to be registered
	 * @return the registered Action
	 */
	public static Action register(String id, Action action) {
		if (action == null)
			throw new NullPointerException(
					"Registered actions must not be null.");

		ActionReader.readAndPutValues(action, Actions.RESOURCE, id);
		Object oldValue = INSTANCE.actions.put(id, action);
		if (oldValue != null)
			System.err.println("Duplicate action id: " + id);

		return action;
	}

	/**
	 * Looks up and returns the <code>Action</code> for the given id.
	 * 
	 * @param id
	 *            the key used to lookup the Action
	 * @return the Action for the given id, null if none was registered for that
	 *         id
	 */
	public static Action get(String id) {
		Action action = (Action) INSTANCE.actions.get(id);
		if (null == action) {
			System.err.println("No action found for id: " + id);
			return null;
		}
		return action;
	}

	/**
	 * Looks up and returns the small icon for the Action with the given
	 * <code>actionId</code>. The Action must be registered with this
	 * ActionManager, otherwise this method returns null.
	 * 
	 * @param actionId
	 *            the id used to lookup the action
	 * @return the action's small icon, or null if no action could be found
	 */
	public static Icon getIcon(String actionId) {
		Action action = get(actionId);
		if (action == null)
			return null;
		return (Icon) action.getValue(Action.SMALL_ICON);
	}

	/**
	 * The ActionReader reads action values for label, descriptions, etc. from a
	 * resource bundle for a given id, and puts these values into an action.
	 */
	private static class ActionReader {

		private static final String LABEL = "label";
		private static final char MNEMONIC_MARKER = '&';
		private static final char ELLIPSIS = '\u2026';
		private static final String ELLIPSIS_STRING = "...";
		private static final String SHORT_DESCRIPTION = "tooltip";
		private static final String LONG_DESCRIPTION = "helptext";
		private static final String ICON = "icon";
		private static final String ACCELERATOR = "accelerator";

		private static final String MNEMONIC_INDEX_KEY = "MnemonicIndexKey";

		private final String id;
		private final String name;
		private final Integer mnemonic;
		private final Integer aMnemonicIndex;
		private final String shortDescription;
		private final String longDescription;
		private final ImageIcon icon;
		private final KeyStroke accellerator;

		/**
		 * Reads properties for <code>id</code> in <code>bundle</code> and
		 * sets the approriate values in the given <code>action</code>.
		 */
		static void readAndPutValues(Action action, ResourceBundle bundle,
				String id) {
			ActionReader reader = new ActionReader(bundle, id);
			reader.putValues(action);
		}

		private ActionReader(ResourceBundle bundle, String id) {
			
			this.id = id;
			String nameWithMnemonic = getString(Actions.RESOURCE, id + "." + LABEL, id);
			int index = mnemonicIndex(nameWithMnemonic);
			name = stripName(nameWithMnemonic, index);
			mnemonic = stripMnemonic(nameWithMnemonic, index);
			aMnemonicIndex = new Integer(index);

			shortDescription = getString(Actions.RESOURCE,
					id + '.' + SHORT_DESCRIPTION, stripEllipsis(name));
			longDescription = getString(Actions.RESOURCE, id + '.' + LONG_DESCRIPTION,
					name);

			String iconPath = getString(Actions.RESOURCE, id + '.' + ICON, null);
			icon = null == iconPath ? null : ResourceUtil.getImageIcon(
					iconPath, Actions.class, Actions.RESOURCE);

			String shortcut = getString(Actions.RESOURCE, id + '.' + ACCELERATOR, null);
			accellerator = getKeyStroke(shortcut);
		}

		/**
		 * Put the ActionReader's properties as values in the Action.
		 */
		private void putValues(Action action) {
			action.putValue(Action.NAME, name);
			action.putValue(Action.SHORT_DESCRIPTION, shortDescription);
			action.putValue(Action.LONG_DESCRIPTION, longDescription);
			action.putValue(Action.SMALL_ICON, icon);
			action.putValue(Action.ACCELERATOR_KEY, accellerator);
			action.putValue(Action.MNEMONIC_KEY, mnemonic);
			action.putValue(MNEMONIC_INDEX_KEY, aMnemonicIndex);
		}

		private int mnemonicIndex(String nameWithMnemonic) {
			return nameWithMnemonic.indexOf(MNEMONIC_MARKER);
		}

		private String stripName(String nameWithMnemonic, int mnemonicIndex) {
			return mnemonicIndex == -1 ? nameWithMnemonic : nameWithMnemonic
					.substring(0, mnemonicIndex)
					+ nameWithMnemonic.substring(mnemonicIndex + 1);
		}

		private Integer stripMnemonic(String nameWithMnemonic, int mnemonicIndex) {
			return mnemonicIndex == -1 ? null : new Integer(nameWithMnemonic
					.charAt(mnemonicIndex + 1));
		}

		/**
		 * Strips a trailing ellipsis - if any - from the given string and
		 * returns the stripped string. The ellipsis can be described by three
		 * dots "..." or by the ellipsis character &hellip;.
		 * <p>
		 * 
		 * Examples: "Print..." -> "Print", "Print\u2026" -> "Print", "About" ->
		 * "About"
		 * 
		 * @param withEllipsis
		 *            the String with optional ellipsis
		 * @return the String without ellipsis
		 */
		private static String stripEllipsis(String withEllipsis) {
			int length = withEllipsis.length();
			if (withEllipsis.charAt(length - 1) == ELLIPSIS)
				return withEllipsis.substring(0, length - 1);
			else if (withEllipsis.endsWith(ELLIPSIS_STRING))
				return withEllipsis.substring(0, length
						- ELLIPSIS_STRING.length());
			else
				return withEllipsis;
		}

		private KeyStroke getKeyStroke(String accelleratorString) {
			if (accelleratorString == null) {
				return null;
			} else {
				KeyStroke keyStroke = KeyStroke
						.getKeyStroke(accelleratorString);
				if (keyStroke == null)
					System.err.println("Action " + id
							+ " has an invalid accellerator "
							+ accelleratorString);
				return keyStroke;
			}
		}

		private String getString(ResourceBundle bundle, String key,
				String defaultString) {
			try {
				return bundle.getString(key);
			} catch (MissingResourceException e) {
				return defaultString;
			}
		}

	}

}