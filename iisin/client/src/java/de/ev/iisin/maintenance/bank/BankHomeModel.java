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

package de.ev.iisin.maintenance.bank;

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
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.bank.domain.Bank;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public class BankHomeModel extends AbstractHomeModel<Bank> {

	/**
	 * Erzeugt am 24.07.2009
	 */
	private static final long serialVersionUID = -5434335063524920148L;

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(BankHomeModel.class);

	public static final String CARDNAME_BANK = "bank";

	private ArrayListModel<Bank> listModel;

	private final String[] cols = new String[] {
			RESOURCE.getString("bank.blz"),
			RESOURCE.getString("bank.name") };

	String[] getHeaderColumn() {
		return cols;
	}

	@Override
	public ArrayListModel<Bank> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<Bank>();
			listModel.addAll(Accessor.getAllBank());
		}
		return listModel;
	}

	@Override
	protected void performNew(ActionEvent e) {
		Bank value = new Bank();
		if ((value = (Bank) createDialog(value, RESOURCE
				.getString("edit.new"), e)) == null)
			return;
		save__(value, false);
	}

	@Override
	protected void performEdit(ActionEvent e) {
		Bank value = (Bank) getDeepCopy(getSelection());
		if ((value = (Bank) createDialog(value, RESOURCE
				.getString("edit.update"), e)) == null)
			return;
		save__(value, false);
	}

	@Override
	protected void performDelete(ActionEvent e) {
		Bank value = getSelection();
		if (value == null)
			return;

		if (!ClientMessageHandler.showConfirmDialog(ResourceUtil.getString(
				RESOURCE.getString("delete.message"), value
						.getBankName()), ResourceUtil.getString(RESOURCE
				.getString("delete.confirm"), value.getBankName()))) {
			return;
		}
//		 List<Telefon> telefone = Accessor.findTelefon(value);
//		if (!telefone.isEmpty()) {
//			ClientMessageHandler.showInfoDialog(ResourceUtil.getString(RESOURCE
//					.getString("delete.error"), value
//					.getBankName()));
//			return;
//		}



		save__(value, true);
	}

	@Override
	protected Object createDialog(Bank value, String title, EventObject e) {
		BankEditModel model = new BankEditModel(value);
		BankEditView view = new BankEditView(title, model);
		view.open();
		return view.hasBeenCanceled() ? null : value;
	}

	@Override
	protected void insertOrUpdate(Bank value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		if (value.getBank_ID() > 0) {
			response = Accessor.updateBank(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addBank(value);
			if (!response.isReturnCode())
				throw new InsertValueException(response.getObject(), value
						.getBankName());
		}

		SelectType tye = value.getBank_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (Bank) response.getObject());
	}

	@Override
	protected void delete(Bank value) throws DeleteValueException {
		if (!Accessor.deleteBank(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);
	}

}
