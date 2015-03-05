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

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.ResourceBundle;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.application.desktop.AbstractHomeModel;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.common.exceptions.DeleteValueException;
import de.ev.iisin.common.exceptions.InsertValueException;
import de.ev.iisin.common.exceptions.UpdateValueException;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.stammdaten.bank.resources.BankMessageKey;
import de.ev.iisin.common.stammdaten.beruf.resources.BerufsTypMessageKey;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.common.util.PersonenTypen;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public class MitgliedHomeModel extends AbstractHomeModel<Mitglied> {
	/**
	 * Erzeugt am 31.01.2009
	 */
	private static final long serialVersionUID = -8716741236953051629L;

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(MitgliedHomeModel.class);

	public static final String CARDNAME_MEMBER = "member";

	private ArrayListModel<Mitglied> listModel;
	private ArrayListModel<Bank> listModelBank;

	private final String[] cols = new String[] {
			RESOURCE.getString("member.table.name"),
			RESOURCE.getString("member.table.lastname"),
			RESOURCE.getString("member.table.adge"),
			RESOURCE.getString("member.table.sex"),
			RESOURCE.getString("member.table.email"),
			RESOURCE.getString("member.table.adress"),
			RESOURCE.getString("member.table.tip"),
			RESOURCE.getString("member.table.konto"),
			RESOURCE.getString("member.table.begin"),
			RESOURCE.getString("member.table.end") };

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#getListModel()
	 */
	@Override
	public ArrayListModel<Mitglied> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<Mitglied>();
			listModel.addAll(Accessor.getAllMitglieder());
			listModelBank = new ArrayListModel<Bank>();
		}
		return listModel;
	}

	String[] getHeaderColumn() {
		return cols;
	}

	private ArrayListModel<Bank> getBankListModel() {
		listModelBank.clear();
		listModelBank.addAll(Accessor.getAllBank());
		return listModelBank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#delete(java.lang.Object)
	 */
	@Override
	protected void delete(Mitglied value) throws DeleteValueException {
		if (!Accessor.deleteMitglied(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#performDelete(java.awt.event.ActionEvent)
	 */
	@Override
	protected void performDelete(ActionEvent e) {
		Mitglied value = getSelection();
		if (value != null)
			if (!ClientMessageHandler.showConfirmDialog(RESOURCE
					.getString("member.delete"), ResourceUtil.getString(
					RESOURCE.getString("member.delete.message"), value.getName()))) {
				return;
			}
		save__(value, true);
	}

	protected Object createDialog(Mitglied value, String title, EventObject e) {
		MitgliedEditModel model = new MitgliedEditModel(value, getBankListModel());
		MitgliedEditView view = new MitgliedEditView(title, model);
		view.open();
		return view.hasBeenCanceled() ? null : value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#performEdit(java.awt.event.ActionEvent)
	 */
	@Override
	protected void performEdit(ActionEvent e) {
		Mitglied value = (Mitglied) getDeepCopy(getSelection());
		if ((value = (Mitglied) createDialog(value, RESOURCE
				.getString("member.edit"), e)) == null)
			return;
		save__(value, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#performNew(java.awt.event.ActionEvent)
	 */
	@Override
	protected void performNew(ActionEvent e) {
		Mitglied value = new Mitglied();
		value.setPersonTyp(Accessor.findPerson(PersonenTypen.MITGLIED.ordinal()));
		for (Person person : value.getPersonen()) {
			if (person.isWeiblich()) {
				person.setPersonTyp(Accessor.findPerson(PersonenTypen.MUTTER
						.ordinal()));
			} else {
				person.setPersonTyp(Accessor.findPerson(PersonenTypen.VATER
						.ordinal()));
			}

		}
		if ((value = (Mitglied) createDialog(value, RESOURCE
				.getString("member.create"), e)) == null)
			return;
		save__(value, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#insertOrUpdate(java.lang.Object)
	 */
	@Override
	protected void insertOrUpdate(Mitglied value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		if (value.getPerson_ID() > 0) {
			response = Accessor.updateMitglied(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addMitglied(value);
			if (!response.isReturnCode()){
				Object ob = response.getObject();
				String val = value.getName();
				if(ob instanceof BankMessageKey){
					val = value.getKonto().getBank().getBankName();
				}
				if(ob instanceof BerufsTypMessageKey){
					val = value.getBeruf().getTyp().getBezeichnung();
				}

				throw new InsertValueException(ob, val);
			}
		}

		SelectType tye = value.getPerson_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (Mitglied) response.getObject());

	}

}
