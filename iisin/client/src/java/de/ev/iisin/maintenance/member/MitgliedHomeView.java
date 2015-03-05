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

package de.ev.iisin.maintenance.member;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

import de.ev.iisin.application.common.interfaces.Sortable;
import de.ev.iisin.application.desktop.AbstractHomeView;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

/**
 * @author Kemal Dönmez
 *
 */
public class MitgliedHomeView extends AbstractHomeView {

	
	private final MitgliedHomeModel mainModel;

	public MitgliedHomeView(MitgliedHomeModel mainModel) {
		super(mainModel);
		this.mainModel = mainModel;
	}


	
	@Override
	@Sortable
	protected AbstractTableAdapter<Mitglied> getTableAdapter() {
		return new MitgliedTableAdptar(mainModel);
	}

	@SuppressWarnings("serial")
	private class MitgliedTableAdptar extends AbstractTableAdapter<Mitglied> {

		public MitgliedTableAdptar(MitgliedHomeModel model) {
			super(model.getListModel(),  model.getHeaderColumn());
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Mitglied p = (Mitglied) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return p.getName();
			case 1:
				return p.getLastName();
			case 2:
				return p.getAdgeAsString();
			case 3:
				return p.isWeiblich()? "w" : "m";
			case 4:
				return p.getEmail() ;
			case 5:
				return p.getAdresseFormated();
			case 6:
				return p.getBeitrag();
			case 7:
				return p.getKonto() == null ? p.isBahrzahler() : p.getKonto().getBakkontoFormated();
			case 8:
				return p.getBeginDateAsString();
			case 9:
				return p.getEndDateAsString();
			default:
				throw new IllegalStateException("Unknown column index: " + columnIndex);
			}
		
		}

	}

}
