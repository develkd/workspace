package de.ev.iisin.pupil;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.ListModel;
import javax.swing.SingleSelectionModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.AbstractVetoableValueModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.uif_lite.AbstractDialog;
import com.jgoodies.uif_lite.application.ActionManager;
import com.jgoodies.uif_lite.application.Application;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.common.WaitCursor;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;

public class PupilEditModel extends PresentationModel<Mitglied> {

	/**
	 * Created at 15.10.2008
	 */
	private static final long serialVersionUID = -5113042943468694161L;

	public static final int TABINDEX_VORSCHAU = 0;

	public static final int TABINDEX_EDITOR = 1;

	private PupilMainModel mainModule;


//	private final SelectionInList<Mitglied> vetoablePupilSelection;
//
//	private final SelectionInList<Mitglied> pupilSelection;
//
//	private final SingleSelectionModel mainTabSelection;

	public PupilEditModel(PupilMainModel mainModule) {
		super(new ValueHolder(null, true));
		this.mainModule = mainModule;
//		pupilSelection = new SelectionInList<Mitglied>((ListModel) mainModule
//				.getListModel());
//		mainTabSelection = new DefaultSingleSelectionModel();
//		vetoablePupilSelection = new SelectionInList<Mitglied>(
//				(ListModel)mainModule.getListModel());
//		vetoablePupilSelection
//				.setSelectionIndexHolder(new CheckPendingEditValueHolder(
//						pupilSelection.getSelectionIndexHolder()));
	//	initEventHandling();

	}

//	protected void initEventHandling() {
//		pupilSelection.addPropertyChangeListener(
//				SelectionInList.PROPERTYNAME_SELECTION,
//				new PupilSelectionHandler());
//
//	}
	
	void updateList(Mitglied mitglied){
		setBean(mitglied);
		showPupilView();
	}

	// Accessors **************************************************************

	/**
	 * Returns Actions intended to be used for the Eintraege view.
	 * 
	 * @return Actions intended to be used for the Eintraege view
	 */
//	public Action[] getPupilActions() {
//		return new Action[] { getCreateAction(), getEditAction(),
//				getDeleteAction(), getPrintAction() };
//	}

	/**
	 * Returns Actions intended to be used for the Eintrageditor.
	 * 
	 * @return Actions intended to be used for the Eintraeditor
	 */
//	public Action[] getEditorActions() {
//		return new Action[] { getSaveAction(), getResetAction() };
//	}

//	private Action getEditAction() {
//		return editAction;
//	}
//
//	private Action getCreateAction() {
//		return createAction;
//	}
//
//	private Action getPrintAction() {
//		return printAction;
//	}
//
//	private Action getResetAction() {
//		return cancleAction;
//	}
//
//	private Action getSaveAction() {
//		return saveAction;
//	}
//
//	private Action getDeleteAction() {
//		return deleteAction;
//	}

//	public SingleSelectionModel getMainTabSelection() {
//		return mainTabSelection;
//	}
//
//	public SelectionInList<Mitglied> getPupilSelection() {
//		return pupilSelection;
//	}
//
//	public SelectionInList<Mitglied> getVetoablePupilSelection() {
//		return vetoablePupilSelection;
//	}

	public void showPupilView() {
		//setMainTabSelectionIndex(TABINDEX_VORSCHAU);
	}

//	private void setMainTabSelectionIndex(int newIndex) {
//		getMainTabSelection().setSelectedIndex(newIndex);
//	}

	public void openFatherDialog() {
		AbstractDialog dialog = new PupilFatherDialog(Application
				.getDefaultParentFrame(), this);
		dialog.open();

	}

	public void openMotherDialog() {
		AbstractDialog dialog = new PupilMotherDialog(Application
				.getDefaultParentFrame(), this);
		dialog.open();

	}

	// Helperclass -----------------------

	private abstract static class AbstractIconAction extends AbstractAction {

		AbstractIconAction(String name, String actionID) {
			super(name);
			if (actionID != null) {
				putValue(Action.SMALL_ICON, ActionManager.getIcon(actionID));
			}
		}

	}

	private class SaveAction extends AbstractIconAction {
		private SaveAction() {
			super("Speichern", Actions.SAVE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// saveLaEintrag();
		}
	}

	private class CancelAction extends AbstractIconAction {
		private CancelAction() {
			super("Verwerfen", Actions.CANCLE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// resetLaEintrag(false);
		}
	}

	private class CreateAction extends AbstractIconAction {
		private CreateAction() {
			super("Neuen Eintrag erstellen", Actions.CREATE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// createAndEditLaEintrag();
		}
	}

	private class EditAction extends AbstractIconAction {
		private EditAction() {
			super("Bearbeiten", Actions.EDIT_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// showEditor();
		}
	}

	private class DeleteAction extends AbstractIconAction {
		private DeleteAction() {
			super("Selekt. Eintrag löschen", Actions.DELETE_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// deleteLaEintrag();
		}
	}

	private class PrintAction extends AbstractIconAction {
		private PrintAction() {
			super("Alle Einträge drucken ", Actions.PRINT_ID);
		}

		public void actionPerformed(ActionEvent e) {
			// print();
		}
	}

	private class CheckPendingEditValueHolder extends
			AbstractVetoableValueModel {

		CheckPendingEditValueHolder(ValueModel subject) {
			super(subject);
		}

		public boolean proposedChange(Object oldValue, Object proposedNewValue) {
			if (equals(oldValue, proposedNewValue))
				return false;
			return /* hasPendingEdits() ? allowToSavePendingEdits() : */true;
		}
	}

//	private class PupilSelectionHandler implements PropertyChangeListener {
//
//		public void propertyChange(PropertyChangeEvent evt) {
//			WaitCursor.show();
//			try {
//
//				setBean(getPupilSelection().getSelection());
//				showPupilView();
//			} finally {
//				WaitCursor.hide();
//			}
//		}
//	}
	
	}
