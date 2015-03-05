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

package de.ev.iisin.application.handler;

import java.awt.Component;

import javax.annotation.processing.FilerException;
import javax.swing.JOptionPane;

import com.jgoodies.uif_lite.application.Application;


import de.ev.iisin.common.exceptions.ErrorTemplate;

/**
 * @author Kemal Dönmez
 * 
 */
public class ClientMessageHandler {
	
	
	
	/**
	 * Handle the user input error
	 * 
	 * @param error the occurred
	 * @param message the occurred error message
	 * @param id the occurred error id
	 */
	public static void handle(ErrorTemplate error, ClientMessageType id) {
		showMessageDialog(error.getMessage(), id);
	}

	/**
	 * Constructor for error handling without the throwen exception
	 * 
	 * @param error
	 *            id of the rised exception
	 * @param message
	 *            the errormessage
	 * @param id
	 *            id to supervise the behave of the program
	 */
	public static void handle(ErrorTemplate error, String message, ClientMessageType id) {
		// ReLaLog.error("Fehlernummer: " + error.getId() + "\nMeldung: "
		// + message);
		showMessageDialog("Fehlernummer: " + error.getId() + "\nMeldung: "
				+ message, id);
	}

	/**
	 * @param error
	 *            id of the rised exception
	 * @param message
	 *            the errormessage
	 * @param nested
	 *            the rised exception
	 * @param id
	 *            id to supervise the behave of the program after an error
	 */
	public static void handle(ErrorTemplate error, String message,
			Exception exception, ClientMessageType id) {
		// ReLaLog.error("Fehlernummer: " + error.getId() + "\nMeldung: "
		// + exception.toString() + " " + message);
		// ReLaLog.error(exception);
		showMessageDialog("Fehlernummer: " + error.getId() + "\nMeldung: "
				+ message, id);
	}

	/**
	 * Constructor for only one error. The ReLaLog could not be init.
	 * 
	 */
	public static void initReLaLogFaild(FilerException exception) {
		String message = "Initialisierung der Logdatei fehlgeschlagen!\n"
				+ "Eventuell auftretende Fehlermeldungen können nicht protokolliert werden!\n"
				+ "Bitte informieren Sie die LA-Zentrale!";
		showMessageDialog(message, ClientMessageType.EXIT);
	}

	public static void showInfoDialog(String message) {
		showMessageDialog(message, ClientMessageType.INFORMATION);
	}

	public static boolean showConfirmDialog(String title,String message) {
		Component frame = Application.getDefaultParentFrame();
		return  JOptionPane.showConfirmDialog(frame, message, title,
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	/**
	 * Constructor for only one error. The ReLaLog could not be init.
	 * 
	 */
	public static void unexpectedError() {
		String message = "Es ist ein unerwarteter Fehler aufgetreten.\n"
				.concat("Bitte informieren Sie den Administrator!\n").concat(
						"Das Programm wird beendet!");
		showMessageDialog(message, ClientMessageType.ERROR);

	}

	private static void showMessageDialog(String message,
			ClientMessageType id) {
		Component frame = Application.getDefaultParentFrame();
		if (ClientMessageType.EXIT == id) {
			message += "\nDas Programm wird beendet!";
			JOptionPane.showMessageDialog(frame, message, "Fehlernachricht ",
					JOptionPane.ERROR_MESSAGE);

			System.gc();
			System.exit(0);
		}

		if (ClientMessageType.WARNING == id) {
			JOptionPane.showMessageDialog(frame, message, "Warnung ",
					JOptionPane.WARNING_MESSAGE);
		}

		if (ClientMessageType.INFORMATION == id) {
			JOptionPane.showMessageDialog(frame, message, "Info ",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (ClientMessageType.ERROR == id) {
			JOptionPane.showMessageDialog(frame, message, "Fehler ",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
