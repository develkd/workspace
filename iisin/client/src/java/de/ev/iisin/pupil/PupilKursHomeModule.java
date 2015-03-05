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

package de.ev.iisin.pupil;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import com.jgoodies.binding.list.ArrayListModel;

import de.ev.iisin.application.desktop.AbstractHomeModel;
import de.ev.iisin.common.exceptions.DeleteValueException;
import de.ev.iisin.common.exceptions.InsertValueException;
import de.ev.iisin.common.exceptions.UpdateValueException;
import de.ev.iisin.common.stammdaten.kurs.domain.Kursnote;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilKursHomeModule extends  AbstractHomeModel<Kursnote>{

	/**
	 * Erzeugt am 22.02.2009
	 */
	private static final long serialVersionUID = -1725784791221048762L;
	private final String[] cols = new String[] {
			"Kurs",
			"Note",
			"Bemerkung"};
//	RESOURCE.getString("member.table.name"),
//	RESOURCE.getString("member.table.lastname")};

	private PupilMainModel mainModel;
	public PupilKursHomeModule(PupilMainModel mainModel){
		this.mainModel = mainModel;
	}
	
	String[] getHeaderColumn() {
		return cols;
	}



	@Override
	public ArrayListModel<Kursnote> getListModel() {
		// TODO Auto-generated method stub
		return new ArrayListModel<Kursnote>();
	}



	@Override
	protected void delete(Kursnote value) throws DeleteValueException {
		// TODO Auto-generated method stub
		
	}






	@Override
	protected void insertOrUpdate(Kursnote value) throws InsertValueException,
			UpdateValueException {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void performDelete(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void performEdit(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	protected void performNew(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object createDialog(Kursnote value, String title, EventObject e) {
		// TODO Auto-generated method stub
		return null;
	}


}
