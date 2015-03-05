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

package de.ev.iisin.maintenance.admin;

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
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public class AdminMainModel extends AbstractHomeModel<Admin> {

	/**
	 * Erzeugt am 31.01.2009
	 */
	private static final long serialVersionUID = -2137339932782410853L;

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(AdminMainModel.class);

	public static final String CARDNAME_ADMIN = "admin";

	private final String[] cols = new String[] {
			RESOURCE.getString("table.name"),
			RESOURCE.getString("table.lastname"),
			RESOURCE.getString("table.admin"),
			RESOURCE.getString("table.teacher"),
			RESOURCE.getString("table.mentor")};

	private ArrayListModel<Admin> listModel;

	String[] getHeaderColumn() {
		return cols;
	}

	@Override
	public ArrayListModel<Admin> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<Admin>();
			listModel.addAll(Accessor.getAllAdmin());
		}
		return listModel;
	}

	@Override
	protected void performDelete(ActionEvent e) {
		Admin value = getSelection();
		
		if (value != null)
			if (!ClientMessageHandler.showConfirmDialog(RESOURCE
					.getString("delete"), ResourceUtil.getString(
					RESOURCE.getString("delete.message"), value.getName()))) {
				return;
			}

		save__(value, true);
	}

	protected Object createDialog(Admin value, String title, EventObject e) {
		AdminEditModel model = new AdminEditModel(value);
		AdminEditView view = new AdminEditView(title, model);
		view.open();
		return view.hasBeenCanceled() ? null : value;

		
	}

	@Override
	protected void performEdit(ActionEvent e) {
		Admin value = (Admin) getDeepCopy(getSelection());
		if ((value = (Admin) createDialog(value, RESOURCE
				.getString("edit"), e)) == null)
			return;
		
		save__(value, false);
	}

	@Override
	protected void performNew(ActionEvent e) {
		Admin value = new Admin();
		if ((value = (Admin) createDialog(value, RESOURCE
				.getString("create"), e)) == null)
			return;

		save__(value, false);

	}

	@Override
	protected void delete(Admin value) throws DeleteValueException {
		if (!Accessor.deleteAdmin(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);

	}

	@Override
	protected void insertOrUpdate(Admin value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		
		if (value.getPerson_ID() > 0) {
			response = Accessor.updateAdmin(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addAdmin(value);
			if (!response.isReturnCode())
				throw new InsertValueException(response.getObject(), value
						.getName());
		}

		SelectType tye = value.getPerson_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (Admin) response.getObject());

	}
}
