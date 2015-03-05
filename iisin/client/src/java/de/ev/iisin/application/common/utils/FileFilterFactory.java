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

import javax.swing.filechooser.FileFilter;

/**
 * @author Kemal Dönmez
 * 
 */
public class FileFilterFactory {


	private FileFilterFactory() {
	}

	public static Filter createFileFilter(FileFiltererType filter) {
		if (FileFiltererType.CSV == filter) {
			return new Filter(".csv");
		}

		if (FileFiltererType.ALL == filter) {
			return null;
		}
		if (FileFiltererType.PDF == filter) {
			return new Filter(".pdf");
		}
		if (FileFiltererType.DOC == filter) {
			return new Filter(".doc");
		}
		if (FileFiltererType.TXT == filter) {
			return new Filter(".txt");
		}

		if (FileFiltererType.IMAGE == filter) {
			return new Filter(".gif");
		}
		return new Filter("");
	}

	private static class Filter extends FileFilter {

		private String filter;

		public Filter(String filter) {
			this.filter = filter;
		}

		@Override
		public boolean accept(File file) {
			if (file.isDirectory())
				return true;
			return file.getName().endsWith(filter);
		}

		@Override
		public String getDescription() {
			return "*" + filter;
		}
	}
}
