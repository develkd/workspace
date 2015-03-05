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

package de.ev.iisin.common.exceptions;

import java.io.InvalidObjectException;

/**
 * @author Kemal Dönmez
 * 
 */
public class ErrorTemplate extends AbstractErrorTemplate {

	/**
	 * Erzeugt am 25.12.2008
	 */
	private static final long serialVersionUID = 2894200728435132780L;

	private ErrorTemplate(int id, String name) {
		super(id, name);
	}

	/**
	 * Get the error message. The message format is '<code>Error [id]: [name]</code>'.
	 * 
	 * @return Returns the error message.
	 */
	public String getMessage() {
		// return "Error " + getId() + "\n" + getName();
		return getName();
	}

	protected Object readResolve() throws InvalidObjectException {
		switch (getId()) {
		case 1:
			return SYSTEM;
		case 2:
			return VALUE_NULL;
		case 3:
			return VALUE_PARSE;
		case 4:
			return VALUE_SIZES;
		case 5:
			return CONFIG_FIND;
		case 6:
			return CONFIG_PARSE;
		case 7:
			return FILE_PARSE;
		case 8:
			return FILE_NOT_FOUND;
		case 9:
			return FILE_SECURITY;
		case 10:
			return FILE_READ;
		case 11:
			return FILE_WRITE;
		case 12:
			return FILE_RANGE;
		case 13:
			return FILE_IO;
		case 14:
			return FONT_NOT_FOUND;
		case 15:
			return VALUE_INSERT;
		case 16:
			return VALUE_UPDATE;
		case 17:
			return VALUE_DELETE;
		case 18:
			return DEEPCOPY_FILE;
		case 19:
			return DEEPCOPY_CLASS;

		case 202:
			return CONTEXT_ERROR;
		case 203:
			return LOGIN_USERNAME;
		case 204:
			return LOGIN_PASSWORD;
		case 205:
			return PRINT;
		case 206:
			return EXPORT;
		case 207:
			return CREATE_DIRECTORY;
		case 208:
			return UNDEFINED_ERROR;
		case 209:
			return FILE_EXIST;
		case 210:
			return TIMETABLE_IMPORT;
		case 211:
			return DELETE;
		case 212:
			return VALID;
		case 213:
			return VALUE_EMPTY;
		case 214:
			return PASSWORD_CRYPT;
		case 215:
			return TABLE_PROVIDER;
		case 216:
			return UNKNOWN_USER;
		}

		throw new InvalidObjectException("Can't find enum for id " + getId());
	}

	// -------------------- Common Error Codes (1 to 100) --------------------
	/**
	 * The constant identifies the system error.
	 */
	public final static ErrorTemplate SYSTEM = new ErrorTemplate(1,
			getResourceName("SYSTEM"));

	/**
	 * The constant identifies the value is null error.
	 */
	public final static ErrorTemplate VALUE_NULL = new ErrorTemplate(2,
			getResourceName("VALUE_NULL"));

	/**
	 * The constant identifies the value parse error.
	 */
	public final static ErrorTemplate VALUE_PARSE = new ErrorTemplate(3,
			getResourceName("VALUE_PARSE"));

	/**
	 * The constant identifies the value size error.
	 */
	public final static ErrorTemplate VALUE_SIZES = new ErrorTemplate(4,
			getResourceName("VALUE_SIZES"));

	/**
	 * The constant identifies the configuration value find error.
	 */
	public final static ErrorTemplate CONFIG_FIND = new ErrorTemplate(5,
			getResourceName("CONFIG_FIND"));

	/**
	 * The constant identifies the configuration value parse error.
	 */
	public final static ErrorTemplate CONFIG_PARSE = new ErrorTemplate(6,
			getResourceName("CONFIG_PARSE"));

	/**
	 * The constant identifies the file parse error.
	 */
	public final static ErrorTemplate FILE_PARSE = new ErrorTemplate(7,
			getResourceName("FILE_PARSE"));

	/**
	 * The constant identifies the file not found error.
	 */
	public final static ErrorTemplate FILE_NOT_FOUND = new ErrorTemplate(8,
			getResourceName("FILE_NOT_FOUND"));

	/**
	 * The constant identifies the file security error.
	 */
	public final static ErrorTemplate FILE_SECURITY = new ErrorTemplate(9,
			getResourceName("FILE_SECURITY"));

	/**
	 * The constant identifies the file read error.
	 */
	public final static ErrorTemplate FILE_READ = new ErrorTemplate(10,
			getResourceName("FILE_READ"));

	/**
	 * The constant identifies the file write error.
	 */
	public final static ErrorTemplate FILE_WRITE = new ErrorTemplate(11,
			getResourceName("FILE_WRITE"));
	/**
	 * The constant identifies the file range error.
	 */
	public final static ErrorTemplate FILE_RANGE = new ErrorTemplate(12,
			getResourceName("FILE_RANGE"));

	/**
	 * The constant identifies the file io.
	 */
	public final static ErrorTemplate FILE_IO = new ErrorTemplate(13,
			getResourceName("FILE_IO"));

	/**
	 * The constant identifies the font not found.
	 */
	public final static ErrorTemplate FONT_NOT_FOUND = new ErrorTemplate(14,
			getResourceName("FONT_NOT_FOUND"));

	/**
	 * The constant identifies the value insert not found.
	 */
	public final static ErrorTemplate VALUE_INSERT = new ErrorTemplate(15,
			getResourceName("VALUE_INSERT"));

	/**
	 * The constant identifies the value update not found.
	 */
	public final static ErrorTemplate VALUE_UPDATE = new ErrorTemplate(16,
			getResourceName("VALUE_UPDATE"));

	/**
	 * The constant identifies the value delete not found.
	 */
	public final static ErrorTemplate VALUE_DELETE = new ErrorTemplate(17,
			getResourceName("VALUE_DELETE"));

	/**
	 * The constant identifies the serialized object file not found.
	 */
	public final static ErrorTemplate DEEPCOPY_FILE = new ErrorTemplate(18,
			getResourceName("DEEPCOPY_FILE"));
	/**
	 * The constant identifies the serialized object class not found.
	 */
	public final static ErrorTemplate DEEPCOPY_CLASS = new ErrorTemplate(19,
			getResourceName("DEEPCOPY_CLASS"));

	/**
	 * The constant identifies a context error
	 */
	public final static ErrorTemplate CONTEXT_ERROR = new ErrorTemplate(202,
			getResourceName("CONTEXT_ERROR"));
	/**
	 * The constant identifies the login error.
	 */
	public final static ErrorTemplate LOGIN_USERNAME = new ErrorTemplate(203,
			getResourceName("LOGIN_USERNAME"));

	/**
	 * The constant identifies the login error.
	 */
	public final static ErrorTemplate LOGIN_PASSWORD = new ErrorTemplate(204,
			getResourceName("LOGIN_PASSWORD"));

	/**
	 * The constant identifies the print error.
	 */
	public final static ErrorTemplate PRINT = new ErrorTemplate(205,
			getResourceName("PRINT"));

	/**
	 * The constant identifies the print error.
	 */
	public final static ErrorTemplate EXPORT = new ErrorTemplate(206,
			getResourceName("EXPORT"));

	/**
	 * The constant identifies the create error.
	 */
	public final static ErrorTemplate CREATE_DIRECTORY = new ErrorTemplate(207,
			getResourceName("CREATE_DIRECTORY"));

	/**
	 * The constant identifies the undefined error.
	 */
	public final static ErrorTemplate UNDEFINED_ERROR = new ErrorTemplate(208,
			getResourceName("UNDEFINED_ERROR"));

	/**
	 * The constant identifies the file exists error.
	 */
	public final static ErrorTemplate FILE_EXIST = new ErrorTemplate(209,
			getResourceName("FILE_EXIST"));

	/**
	 * The constant identifies the timetable exists error.
	 */
	public final static ErrorTemplate TIMETABLE_IMPORT = new ErrorTemplate(210,
			getResourceName("TIMETABLE_IMPORT"));

	/**
	 * The constant identifies the delete error.
	 */
	public final static ErrorTemplate DELETE = new ErrorTemplate(211,
			getResourceName("DELETE"));

	/**
	 * The constant identifies the valid error.
	 */
	public final static ErrorTemplate VALID = new ErrorTemplate(212,
			getResourceName("VALID"));

	/**
	 * The constant identifies the empty value filed error.
	 */
	public final static ErrorTemplate VALUE_EMPTY = new ErrorTemplate(213,
			getResourceName("VALUE_EMPTY"));

	/**
	 * The constant identifies the cryptography error.
	 */
	public final static ErrorTemplate PASSWORD_CRYPT = new ErrorTemplate(214,
			getResourceName("PASSWORD_CRYPT"));

	/**
	 * The constant identifies the table provider error.
	 */
	public final static ErrorTemplate TABLE_PROVIDER = new ErrorTemplate(215,
			getResourceName("TABLE_PROVIDER"));

	/**
	 * The constant identifies the table provider error.
	 */
	public final static ErrorTemplate UNKNOWN_USER = new ErrorTemplate(216,
			getResourceName("UNKNOWN_USER"));

}
