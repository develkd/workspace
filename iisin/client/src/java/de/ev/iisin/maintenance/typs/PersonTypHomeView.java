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

package de.ev.iisin.maintenance.typs;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.interfaces.Sortable;
import de.ev.iisin.application.desktop.AbstractHomeView;
import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.util.PersonenTypen;

/**
 * @author Kemal Dönmez
 * 
 */
public class PersonTypHomeView extends AbstractHomeView {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(PersonenTypen.class);

	private PersonTypHomeModel mainModel;

	public PersonTypHomeView(PersonTypHomeModel mainModel) {
		super(mainModel);
		this.mainModel = mainModel;
	}

	@Override
	@Sortable
	protected AbstractTableAdapter<?> getTableAdapter() {
		return new PersonTypTableAdptar(mainModel);
	}

	@SuppressWarnings("serial")
	private class PersonTypTableAdptar extends AbstractTableAdapter<PersonTyp> {

		public PersonTypTableAdptar(PersonTypHomeModel model) {
			super(model.getListModel(), model.getHeaderColumn());
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			PersonTyp typ = (PersonTyp) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				String value = typ.getBezeichnung();
				try {
					value = (value == null) ? "" : RESOURCE.getString(value);
				} catch (MissingResourceException e) {
					
				}

				return value;
			default:
				throw new IllegalStateException("Unknown column index: "
						+ columnIndex);
			}

		}
	}

}
