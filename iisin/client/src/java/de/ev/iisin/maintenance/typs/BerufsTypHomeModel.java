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
import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.stammdaten.beruf.domain.Beruf;
import de.ev.iisin.common.stammdaten.beruf.domain.BerufsTyp;
import de.ev.iisin.common.util.ResourceUtil;
import de.ev.iisin.wizard.gui.GuiBuilder;

/**
 * @author Kemal Dönmez
 * 
 */
public class BerufsTypHomeModel extends AbstractHomeModel<BerufsTyp> {
	public static final String CARDNAME_BERUFS_TYP = "berufsTyp";

	/**
	 * Erzeugt am 26.05.2009
	 */
	private static final long serialVersionUID = 8347502537228741966L;

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(BerufsTypHomeModel.class);

	private ArrayListModel<BerufsTyp> listModel;
	private final String[] cols = new String[] {
			RESOURCE.getString("typ.table.name"),
			RESOURCE.getString("typ.table.pupil") };

	String[] getHeaderColumn() {
		return cols;
	}

	@Override
	public ArrayListModel<BerufsTyp> getListModel() {
		if (listModel == null) {
			listModel = new ArrayListModel<BerufsTyp>();
			listModel.addAll(Accessor.getAllBerufsTyp());
		}
		return listModel;
	}

	@Override
	protected void performNew(ActionEvent e) {
		BerufsTyp value = new BerufsTyp();
		if ((value = (BerufsTyp) createDialog(value, RESOURCE
				.getString("typ.edit.new"), e)) == null)
			return;
		save__(value, false);

	}

	@Override
	protected void performEdit(ActionEvent e) {
		BerufsTyp value = (BerufsTyp) getDeepCopy(getSelection());
		if ((value = (BerufsTyp) createDialog(value, ResourceUtil.getString(
				RESOURCE.getString("typ.edit.update"), value.getBezeichnung()),
				e)) == null)
			return;
		save__(value, false);

	}

	@Override
	protected void performDelete(ActionEvent e) {
		BerufsTyp value = getSelection();
		if (value == null)
			return;

		if (!ClientMessageHandler.showConfirmDialog(ResourceUtil.getString(
				RESOURCE.getString("typ.delete.message"), value
						.getBezeichnung()), ResourceUtil.getString(RESOURCE
				.getString("typ.delete.confirm"), value.getBezeichnung()))) {
			return;
		}
		List<Beruf> berufe = Accessor.findBeruf(value);
		if (!berufe.isEmpty()) {
			ClientMessageHandler.showInfoDialog(ResourceUtil.getString(RESOURCE
					.getString("typ.delete.error"), value.getBezeichnung()));
			return;
		}

		save__(value, true);

	}

	@Override
	protected void delete(BerufsTyp value) throws DeleteValueException {
		if (!Accessor.deleteBerufsTyp(value))
			throw new DeleteValueException();
		reselect(SelectType.DELETE, value);
	}

	@Override
	protected void insertOrUpdate(BerufsTyp value) throws InsertValueException,
			UpdateValueException {
		RmiResponse response = null;
		if (value.getBerufsTyp_ID() > 0) {
			response = Accessor.updateBerufsTyp(value);
			if (response.getObject() == null)
				throw new UpdateValueException();
		} else {
			response = Accessor.addBerufsTyp(value);
			if (!response.isReturnCode())
				throw new InsertValueException(response.getObject(), value
						.getBezeichnung());
		}

		SelectType tye = value.getBerufsTyp_ID() > 0 ? SelectType.UPDATE
				: SelectType.INSERT;
		reselect(tye, (BerufsTyp) response.getObject());

	}

	public ArrayListModel<BerufsTyp> getBerufsTypListModel() {
		 return new ArrayListModel<BerufsTyp>(Accessor.getAllBerufsTyp());
	}

	@Override
	protected Object createDialog(BerufsTyp value, String title, EventObject e) {
		GuiBuilder builder = new GuiBuilder(title, value);
		builder.open();
		return builder.hasBeenCanceled() ? null : value;
	}

}
