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

package de.ev.iisin.application.view;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.uif_lite.application.ResourceUtils;
import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.application.Accessor;
import de.ev.iisin.binding.ValidablePresentationModel;
import de.ev.iisin.common.person.domain.Person;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class TelefonEditModel extends ValidablePresentationModel<Telefon> {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(TelefonEditModel.class);

	/**
	 * Erzeugt am 31.05.2009
	 */
	private static final long serialVersionUID = -7206394103800975322L;
	private Action newAction;
	private Action deleteAction;
	private Action editAction;
	private Person person;
	private ArrayListModel<Telefon> listModel;
	private SelectionInList<Telefon> selectionListModel;

	private SelectionInList<TelefonTyp> typSelectionInList;
	private ArrayListModel<TelefonTyp> typListModel;

	public TelefonEditModel(Telefon bean) {
		super(bean);
		person = bean.getPerson();
		getBean().setPerson(person);

		newAction = new NewAction();
		deleteAction = new DeleteAction();
		editAction = new EditAction();
		listModel = new ArrayListModel<Telefon>();
		selectionListModel = new SelectionInList<Telefon>((ListModel) listModel);
		listModel.addAll(Accessor.getAllTelefonFor(person));
		typSelectionInList = new SelectionInList<TelefonTyp>(
				(ListModel) getTypListModel());
		if (!listModel.isEmpty())
			typSelectionInList.setSelectionIndex(0);
		initEvent();
	}

	private void initEvent() {
		selectionListModel.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION,
				new TelefonSelectionChanged());
		getBean().addPropertyChangeListener(Telefon.PROPERTY_PERSON,
				new PersonChangedHandler());
	}

	public ArrayListModel<Telefon> getListModel() {
		return listModel;
	}

	public SelectionInList<Telefon> getSelectionInList() {
		return selectionListModel;
	}

	public ArrayListModel<TelefonTyp> getTypListModel() {
		if (typListModel == null) {
			typListModel = new ArrayListModel<TelefonTyp>();
			typListModel.addAll(Accessor.getAllTelefonTyp());
		}
		return typListModel;
	}

	public SelectionInList<TelefonTyp> getTypSelectionInList() {
		return typSelectionInList;
	}

	TelefonTyp getSelectedTyp() {
		return typSelectionInList.getSelection();
	}

	void setSelectedTyp(TelefonTyp typ) {
		typSelectionInList.setSelection(typ);
	}

	public Action getNewAction() {
		return newAction;
	}

	public Action getEditAction() {
		return editAction;
	}

	public Action getDeleteAction() {
		return deleteAction;
	}

	private void createNewBean() {
		Telefon newBean = new Telefon();
		newBean.setPerson(person);
		setBean(newBean);
		getBean().addPropertyChangeListener(Telefon.PROPERTY_PERSON,
				new PersonChangedHandler());

	}


	private class NewAction extends AbstractAction {
		/**
		 * Erzeugt am 31.05.2009
		 */
		private static final long serialVersionUID = -8079177558695522155L;

		public NewAction() {
			super(RESOURCE.getString("member.action.add"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Telefon telefon = getBean(); // getEditableCopy();
			TelefonTyp typ = getTypSelectionInList().getSelection();// telefon.getTyp();
			telefon.setTyp(typ);
			getListModel().add(telefon);
			person.setTelefon(getListModel());
			createNewBean();
		}

	}

	private class EditAction extends AbstractAction {
		/**
		 * Erzeugt am 31.05.2009
		 */
		private static final long serialVersionUID = 1502680602389043458L;

		public EditAction() {
			super(RESOURCE.getString("member.action.replace"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Telefon orgTele = getSelectionInList().getSelection();
			if (orgTele == null)
				return;
			Telefon telefon = getEditableCopy(orgTele);
			telefon.setTyp(getTypSelectionInList().getSelection());
			getListModel().add(telefon);
			getListModel().remove(orgTele);
			person.setTelefon(getListModel());
			createNewBean();

		}

	}

	private class DeleteAction extends AbstractAction {
		/**
		 * Erzeugt am 31.05.2009
		 */
		private static final long serialVersionUID = -7077265180292452103L;

		public DeleteAction() {
			super(RESOURCE.getString("member.action.delete"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Telefon telefon = getSelectionInList().getSelection();
			if (telefon == null)
				return;

			getListModel().remove(telefon);
			person.setTelefon(getListModel());
			createNewBean();
		}

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.binding.Validate#validate(com.jgoodies.validation.ValidationResult)
	 */
	public ValidationResult validate(ValidationResult result) {
		// TODO Auto-generated method stub
		return null;
	}


	private class TelefonSelectionChanged implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Telefon telefon = (Telefon) evt.getNewValue();
			TelefonTyp typ = null;
			if (telefon != null) {
				typ = telefon.getTyp();
				getTypSelectionInList().setSelection(typ);
				setBean(telefon);
			}else{
				createNewBean();
			}

			
		}

	}

	private class PersonChangedHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Person p = (Person) evt.getNewValue();
			Person oldP = (Person) evt.getOldValue();
			if (p == null)
				return;
			if (oldP != null) {
				List<Telefon> te = new ArrayList<Telefon>(listModel);
				oldP.setTelefon(te);
			}
			person = p;
			getBean().setPerson(p);
			listModel.clear();
			try {
				listModel.addAll(p.getTelefon());

			} catch (Exception e) {
				List<Telefon> list = Accessor.getAllTelefonFor(p);
				listModel.addAll(list);
				p.setTelefon(list);

				// TODO: handle exception
			}

		}

	}

}
