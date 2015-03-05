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

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.List;
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
import de.ev.iisin.common.person.domain.PersonTyp;
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.util.ResourceUtil;
import de.ev.iisin.wizard.gui.GuiBuilder;

/**
 * @author Kemal Dönmez
 * 
 */
public class PersonTypHomeModel extends AbstractHomeModel<PersonTyp> {

	public static final String CARDNAME_PERSON_TYP = "personTyp";

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(PersonTypHomeModel.class);
	/**
	 * Erzeugt am 14.02.2009
	 */
	private static final long serialVersionUID = 4808120639244401264L;

	private ArrayListModel<PersonTyp> listModel;
	private final String[] cols = new String[] { RESOURCE
			.getString("typ.table.name")};
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#getListModel()
	 */
	@Override
	public ArrayListModel<PersonTyp> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<PersonTyp>();
			listModel.addAll(Accessor.getAllPersonTyp());
		}
		return listModel;
	}

	String[] getHeaderColumn() {
		return cols;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#delete(java.lang.Object)
	 */
	@Override
	protected void delete(PersonTyp value) throws DeleteValueException {
		if (!Accessor.deletePersonTyp(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#insertOrUpdate(java.lang.Object)
	 */
	@Override
	protected void insertOrUpdate(PersonTyp value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		if (value.getPersonTyp_ID() > 0) {
			response = Accessor.updatePersonTyp(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addPersonTyp(value);
			if (!response.isReturnCode())
				throw new InsertValueException(response.getObject(), value
						.getBezeichnung());
		}

		SelectType tye = value.getPersonTyp_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (PersonTyp) response.getObject());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#performDelete(java.awt.event.ActionEvent)
	 */
	@Override
	protected void performDelete(ActionEvent e) {
		PersonTyp value = getSelection();
		if (value == null)
			return;

		if (!ClientMessageHandler.showConfirmDialog(
				ResourceUtil.getString(RESOURCE.getString("edit.delete.message"), value
						.getBezeichnung()),
				ResourceUtil.getString(
				RESOURCE.getString("typ.delete.confirm"), value
						.getBezeichnung()))) {
			return;
		}
		List<Person> personen = Accessor.findPerson(value);
		if (!personen.isEmpty()) {
			ClientMessageHandler.showInfoDialog(ResourceUtil.getString(RESOURCE
					.getString("typ.delete.error"), value
					.getBezeichnung()));
			return;
		}

		save__(value, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeModel#performEdit(java.awt.event.ActionEvent)
	 */
	@Override
	protected void performEdit(ActionEvent e) {
		PersonTyp value = (PersonTyp) getDeepCopy(getSelection());
		if ((value = (PersonTyp) createDialog(value, ResourceUtil.getString(
				RESOURCE.getString("typ.edit.update"), value.getBezeichnung()), e)) == null)
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
		PersonTyp value = new PersonTyp();
		if ((value = (PersonTyp) createDialog(value, RESOURCE
				.getString("typ.edit.new"), e)) == null)
			return;
		save__(value, false);

	}

	protected Object createDialog(PersonTyp value, String title, EventObject e) {
		GuiBuilder builder = new GuiBuilder(title, value);
		builder.open();
		return builder.hasBeenCanceled() ? null : value;
	}

}
