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

package de.ev.iisin.maintenance.human;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.ResourceBundle;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.application.desktop.AbstractPersonHomeModel;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.common.exceptions.DeleteValueException;
import de.ev.iisin.common.exceptions.InsertValueException;
import de.ev.iisin.common.exceptions.UpdateValueException;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.human.domain.Human;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author Kemal Dönmez
 * 
 */
public class HumanHomeModel extends AbstractPersonHomeModel<Human> {

	/**
	 * Erzeugt am 04.04.2009
	 */
	private static final long serialVersionUID = 6664145897019676201L;
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(HumanHomeModel.class);
	public static final String CARDNAME_HUMAN = "human";

	private final String[] cols = new String[] {
			RESOURCE.getString("human.table.name"),
			RESOURCE.getString("human.table.lastname"),
			RESOURCE.getString("human.table.adge"),
			RESOURCE.getString("human.table.sex"),
			RESOURCE.getString("human.table.email")};

	private ArrayListModel<Human> listModel;

	
	String[] getHeaderColumn() {
		return cols;
	}
	
	public HumanHomeModel(){
		super(new Human());
	}

	@Override
	public ArrayListModel<Human> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<Human>();
			listModel.addAll(Accessor.getAllHumans());
		}
		return listModel;
	}

	@Override
	protected void performNew(ActionEvent e) {
		Human value = new Human();
		if ((value = (Human) createDialog(value, RESOURCE
				.getString("edit.new"), e)) == null)
			return;
		save__(value, false);

	}

	@Override
	protected void performEdit(ActionEvent e) {
		Human value = (Human) getDeepCopy(getSelection());
		if ((value = (Human) createDialog(value, ResourceUtil.getString(
				RESOURCE.getString("edit.update"), value.getName()),
				e)) == null)
			return;
		save__(value, false);
	}

	@Override
	protected void performDelete(ActionEvent e) {
		Human value = getSelection();
		if (value == null)
			return;

		if (!ClientMessageHandler.showConfirmDialog(ResourceUtil.getString(
				RESOURCE.getString("delete.message"), value
						.getName()), ResourceUtil.getString(RESOURCE
				.getString("delete.confirm"), value.getName()))) {
			return;
		}
//		 List<Telefon> telefone = Accessor.findTelefon(value);
//		if (!telefone.isEmpty()) {
//			ClientMessageHandler.showInfoDialog(ResourceUtil.getString(RESOURCE
//					.getString("delete.error"), value
//					.getName()));
//			return;
//		}

		save__(value, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object createDialog(Human value, String title, EventObject e) {
		HumanEditModel  model = new HumanEditModel(value);
		HumanEditView view = new HumanEditView(title, model);
		view.open();
		return view.hasBeenCanceled() ? null : value;
	}

	@Override
	protected void insertOrUpdate(Human value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		
		if (value.getPerson_ID() > 0) {
			response = Accessor.updateHuman(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addHuman(value);
			if (!response.isReturnCode())
				throw new InsertValueException(response.getObject(), value
						.getName());
		}

		SelectType tye = value.getPerson_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (Human) response.getObject());
	}

	@Override
	protected void delete(Human value) throws DeleteValueException {
		if (!Accessor.deleteHuman(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);
	}

}
