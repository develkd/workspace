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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.extras.DelayedWriteValueModel;
import com.jgoodies.binding.list.SelectionInList;

import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.maintenance.member.MitgliedHomeModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilMainModel extends MitgliedHomeModel {
	/**
	 * Created at 01.10.2008
	 */
	private static final long serialVersionUID = 4934434449267922328L;

	// Names of the Bound Bean Properties *************************************

	public static final String CARDNAME_PUPIL = "pupil";

	// Lazily Created References to Submodules *******************************

	private PupilEditModel pupilEditModule;
	private PupilKursHomeModule pupilKursHomeModule;
	private MitgliedHomeModel mainModel;
	private SingleListSelectionAdapter singleListSelectionAdapter;
	private SelectionInList<Mitglied> vetoableSelectionInList;

	// Instance Creation ******************************************************

	/**
	 * Constructs a <code>MainModel</code> that has no project set, no
	 * selection and no tree model.
	 */
	public PupilMainModel(MitgliedHomeModel mainModel) {
		this.mainModel = mainModel;
		vetoableSelectionInList = mainModel.getSelectionInList();

		singleListSelectionAdapter = new SingleListSelectionAdapter(
				new DelayedWriteValueModel(vetoableSelectionInList
						.getSelectionIndexHolder(), 500));

		initEvent();
	}

	public MitgliedHomeModel getMitgliedMainModel() {
		return mainModel;
	}

	private void initEvent() {
		vetoableSelectionInList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION,
				new PupilSelectionHandler());
	}

	public SingleListSelectionAdapter getSingleListSelectionAdapter() {
		return singleListSelectionAdapter;
	}

	public SelectionInList<Mitglied> getVetoAbleSelectionInList() {
		return vetoableSelectionInList;
	}

	// Exposing Submodules ****************************************************

	public PupilEditModel getPupilEditModule() {
		if (pupilEditModule == null) {
			pupilEditModule = new PupilEditModel(this);
		}
		return pupilEditModule;
	}
	public PupilKursHomeModule getPupilKursHomeModule() {
		if (pupilKursHomeModule == null) {
			pupilKursHomeModule = new PupilKursHomeModule(this);
		}
		return pupilKursHomeModule;
	}


	private class PupilSelectionHandler implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			getPupilEditModule().updateList(
					vetoableSelectionInList.getSelection());
		}
	}

}
