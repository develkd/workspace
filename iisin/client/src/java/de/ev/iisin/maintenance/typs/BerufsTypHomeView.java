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

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.ev.iisin.application.common.interfaces.Sortable;
import de.ev.iisin.application.desktop.AbstractHomeView;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;

/**
 * @author Kemal Dönmez
 *
 */
public class BerufsTypHomeView extends AbstractHomeView {

	private BerufsTypHomeModel mainModel;
	
	public BerufsTypHomeView(BerufsTypHomeModel mainModel) {
		super(mainModel);
		this.mainModel = mainModel;
	}

	/* (non-Javadoc)
	 * @see de.ev.iisin.application.desktop.AbstractHomeView#getTableAdapter()
	 */
	@Override
	@Sortable
	protected AbstractTableAdapter<?> getTableAdapter() {
		return new BerufsTypTableAdptar(mainModel);
	}

	@SuppressWarnings("serial")
	private class BerufsTypTableAdptar extends AbstractTableAdapter<BerufsTyp> {

		public BerufsTypTableAdptar(BerufsTypHomeModel model) {
			super(model.getListModel(), model.getHeaderColumn());
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			BerufsTyp typ = (BerufsTyp) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return typ.getBezeichnung();
			case 1:
				return typ.isPupil();
			default:
				throw new IllegalStateException("Unknown column index: "
						+ columnIndex);
			}

		}
		public Class<?> getColumnClass(int columnIndex){
			return (columnIndex > 0 ) ? Boolean.class : Object.class;
		}
	}

}
