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

package de.ev.iisin.application.desktop;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.uif_lite.application.ActionManager;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.handler.ClientMessageHandler;
import de.ev.iisin.application.handler.ClientMessageType;
import de.ev.iisin.common.exceptions.DeepCopyClassException;
import de.ev.iisin.common.exceptions.DeepCopyFileException;
import de.ev.iisin.common.exceptions.DeleteValueException;
import de.ev.iisin.common.exceptions.ErrorTemplate;
import de.ev.iisin.common.exceptions.InsertValueException;
import de.ev.iisin.common.exceptions.UpdateValueException;
import de.ev.iisin.common.util.copy.DeepCopy;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractHomeModel<M> extends Model {
	protected enum SelectType {
		INSERT, UPDATE, DELETE
	}
	
	private static final ResourceBundle RESOURCE = ResourceUtils
	.getResourceBundle(AbstractHomeModel.class);


	public final static String PROPERTY_CARDNAME = "cardName";
	private String cardName;

	private Action createAction;
	private Action editAction;

	private Action deleteAction;
	private int selectedIndex = -1;

	private SelectionInList<M> selectionInList;

	public AbstractHomeModel() {
		editAction = new EditAction();
		createAction = new CreateAction();
		deleteAction = new DeleteAction();
	}

	protected abstract void performNew(ActionEvent e);

	protected abstract void performEdit(ActionEvent e);

	protected abstract void performDelete(ActionEvent e);

	public abstract ArrayListModel<M> getListModel();

	protected abstract Object createDialog(M value, String title, EventObject e);

	/**
	 * The Object to be insert or update.
	 * 
	 * @param value
	 * @throws InsertValueException
	 *             if in exception occurs during inserting.
	 * @throws UpdateValueException
	 *             if in exception occurs during updating.
	 */
	abstract protected void insertOrUpdate(M value)
			throws InsertValueException, UpdateValueException;

	/**
	 * The object to be delete;
	 * 
	 * @param value
	 * @throws DeleteValueException
	 *             if in exception occurs during deleting.
	 */
	abstract protected void delete(M value) throws DeleteValueException;

	public SelectionInList<M> getSelectionInList() {
		if (selectionInList == null) {
			selectionInList = new SelectionInList<M>((ListModel) getListModel());
		}
		return selectionInList;
	}

	public M getSelection() {
		selectedIndex = getSelectionInList().getSelectionIndex();
		return getSelectionInList().getSelection();
	}

	protected final int getSelectedIndex() {
		return selectedIndex;
	}


	/**
	 * Returns the name of the current card in the main page. If this card name
	 * changes the main page will switch cards to display the appropriate card.
	 * Other views may observe this property to perform other view related
	 * actions.
	 * 
	 * @return the name of the current card in the main page.
	 * 
	 * @see #setCardName(String)
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * Sets a new card name for the main page.
	 * 
	 * @param newMainCard
	 *            the name of the card to set
	 * 
	 * @see #getMainCard()
	 */
	public void setMainCard(String cardName) {
		String oldCardName = getCardName();
		this.cardName = cardName;
		firePropertyChange(PROPERTY_CARDNAME, oldCardName, cardName);
	}

	public Action[] getActions() {
		return new Action[] { getCreateAction(), getEditAction(),
				getDeleteAction() };
	}

	private Action getEditAction() {
		return editAction;
	}

	private Action getCreateAction() {
		return createAction;
	}

	private Action getDeleteAction() {
		return deleteAction;
	}

	/**
	 * The object to be save
	 * 
	 * @param newParam
	 * @param isDeleting
	 * @return don't mind it. It be all ways be null.
	 */
	protected Object save__(M value, boolean isDeleting) {
		if(value == null)
			return null;
		
		if (!isDeleting) {
			try {
				insertOrUpdate(value);
			} catch (InsertValueException e) {
				ClientMessageHandler.handle(e.getError(), e
						.getResponseMessage(), ClientMessageType.ERROR);

			} catch (UpdateValueException e) {
				ClientMessageHandler.handle(e.getError(),
						ClientMessageType.ERROR);
			}
		} else {
			try {
				delete(value);
			} catch (DeleteValueException e) {
				ClientMessageHandler.handle(e.getError(),
						ClientMessageType.ERROR);
			}
		}
		return null;
	}

	protected void sort() {

	}

	protected void reselect(SelectType type, M value) {
		switch (type) {
		case INSERT:
			getListModel().add(value);
			getSelectionInList().setSelection(value);
			break;

		case UPDATE:
			getListModel().remove(selectedIndex);
			getSelectionInList().clearSelection();
			selectedIndex = getListModel().size() < selectedIndex ? 0
					: selectedIndex;
			getListModel().add(selectedIndex, value);
			getSelectionInList().setSelectionIndex(selectedIndex);
			break;
		case DELETE:
			if (getSelectedIndex() < 0)
				return;
			getListModel().remove(getSelectedIndex());
			if (!getSelectionInList().getList().isEmpty()) {
				getSelectionInList().setSelectionIndex(0);
			}
			break;
		}

		selectedIndex = -1;
		sort();
	}

	protected Object getDeepCopy(Object orig) {
		try {
			return orig == null ? null : DeepCopy.copy(orig);
		} catch (DeepCopyFileException e) {
			ClientMessageHandler.handle(ErrorTemplate.DEEPCOPY_FILE,
					ClientMessageType.ERROR);
		} catch (DeepCopyClassException e) {
			ClientMessageHandler.handle(ErrorTemplate.DEEPCOPY_CLASS,
					ClientMessageType.ERROR);
		}

		return orig;
	}

	// Helper Methods ----------------------------

	@SuppressWarnings("serial")
	private class CreateAction extends AbstractIconAction {
		private CreateAction() {
			super(RESOURCE.getString("home.action.new"), Actions.CREATE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			performNew(e);
		}
	}

	@SuppressWarnings("serial")
	private class EditAction extends AbstractIconAction {
		private EditAction() {
			super(RESOURCE.getString("home.action.edit"), Actions.EDIT_ID);
		}

		public void actionPerformed(ActionEvent e) {
			performEdit(e);
		}
	}

	@SuppressWarnings("serial")
	private class DeleteAction extends AbstractIconAction {
		private DeleteAction() {
			super(RESOURCE.getString("home.action.delete"), Actions.DELETE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			performDelete(e);
		}
	}

	protected abstract static class AbstractIconAction extends AbstractAction {

		protected AbstractIconAction(String name, String actionID) {
			super(name);
			if (actionID != null) {
				putValue(Action.SMALL_ICON, ActionManager.getIcon(actionID));
			}
		}

	}
}
