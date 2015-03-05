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

package de.ev.iisin.application.common.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.jgoodies.uif_lite.AbstractDialog;
import com.jgoodies.uif_lite.application.Application;

import de.ev.iisin.application.common.AboutDialog;

/**
 * @author Kemal Dönmez
 *
 */
public class Dialogs {

	   private Dialogs() {
	        // Overrides default constructor; prevents instantiation.
	    }

	   public static File openFileChooser(String title, String fileName,
	            FileFiltererType typ) {
	        JFrame parent = Application
			.getDefaultParentFrame();
	        
	        JFileChooser fileChooser = null;
	        if (fileName == null || fileName.trim().equalsIgnoreCase("")) {
	            fileChooser = new JFileChooser();
	        } else {
	            File file = new File(fileName);
	            fileChooser = new JFileChooser();
	            fileChooser.setSelectedFile(file);
	        }
	        
	        fileChooser.addChoosableFileFilter(FileFilterFactory
	                .createFileFilter(typ));
	        fileChooser.setMultiSelectionEnabled(false);
	        int result = fileChooser.showDialog(parent, title);
	        return result == JFileChooser.APPROVE_OPTION ? fileChooser
	                .getSelectedFile() : null;
	        
	    }

	   
		public static void openAboutDialog() {
			AbstractDialog dialog = new AboutDialog(Application
					.getDefaultParentFrame());
			dialog.open();

		}

}
