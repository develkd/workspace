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

package de.ev.iisin.application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.UIManager;

import com.jgoodies.uif_lite.AbstractFrame;
import com.jgoodies.uif_lite.application.Application;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.common.TeeStream;
import de.ev.iisin.application.controller.MainController;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.application.model.MainModel;
import de.ev.iisin.common.exceptions.ErrorTemplate;
import de.ev.iisin.view.MainFrame;

/**
 * @author Kemal Dönmez
 * 
 */
public class Iisin {
	private static ResourceBundle RESOURCE = ResourceUtils.getBundlePath(
			Iisin.class, getInstalledLocal());

	public static final String FULL_VERSION = "1.0.0 (Aug-03-2008)";

	public static final String COPYRIGHT_TEXT = "\u00a9 2009 I-ISIN e.V.";

	private static boolean isInit = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Iisin().boot();
	}

	public static Locale getInstalledLocal() {
		if (!isInit) {
			Locale.setDefault(new Locale("de", "DE"));
			 //Locale.setDefault(new Locale("tr", "TR"));

			isInit = true;
		}
		return Locale.getDefault();
	}

	private void boot() {
		try {
			load();
		} catch (Throwable t) {
			System.err.print(t);
			// ClientMessageHandler.handle(ErrorTemplate.SYSTEM, "Load
			// failed..."
			// + '\n' + "Program will be closed", ClientMessageType.EXIT);

		} finally {
		}
	}

	private void load() {

		// ResourceUtils.setBundlePath(APPLICATION_HOME + RESOURCES_PATH);
		// ActionManager.setBundle(Actions.getBundel());

		configureUI();
		configureLogging();

		// Create the model that provides all high-level models.
		MainModel mainModule = new MainModel();

		// Create the controller that provides the major operations.
		MainController mainController = new MainController(mainModule);

		// Initialize all Actions
		Actions.initializeFor(mainModule, mainController);
		mainModule.registerLoginListener();

		// Create and build the main frame.
		AbstractFrame mainFrame = new MainFrame(mainModule);
		mainFrame.build();

		// Set the main frame as default parent for dialogs.
		Application.setDefaultParentFrame(mainFrame);

		// Show the main frame.
		mainFrame.setVisible(true);

		/**
		 * ThreadCheckingRepaintManager überwacht die Modifikation von
		 * SwingKomponenten. Wird diese in einem anderen Thread als dem
		 * Hauptthread ausgeführt, so generiert er eine Exception
		 */
		// RepaintManager.setCurrentManager(new ThreadCheckingRepaintManager());
	}

	private void configureUI() {

		UIManager.put("ClassLoader", Iisin.class.getClassLoader());
		try {
			// UIManager.setLookAndFeel(Options.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (Exception e) {
			ClientMessageHandler.handle(ErrorTemplate.SYSTEM,
					"Can't set look & feel", e, ClientMessageType.ERROR);
		}
	}

	private void configureLogging() {
		try {
			// ReLaLog.initialize(new Log4jAppender());

			// ResourceBundle bundle =
			// ResourceBundle.getBundle("resources/log4j");
			String logFile = RESOURCE.getString("log4j.appender.file.File");
			// Tee standard error
			PrintStream err = new PrintStream(new FileOutputStream(logFile));
			PrintStream tee = new TeeStream(System.err, err);

			System.setErr(tee);

			// } catch (FilerException e) {
			// ReLaClientExceptionHandler.handle(ErrorTemplates.LOG4J_ERROR,
			// "Schwerwiegender Fehler : Logdatei kann nicht gefunden werden."
			// + e, ClientErrorConstants.EXIT);
		} catch (FileNotFoundException e) {
			e.getMessage();
		}

	}

}
