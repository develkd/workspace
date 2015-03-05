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

import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JList;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.uif_lite.application.ResourceUtils;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.renderer.PupilCellRenderer;
import de.ev.iisin.application.desktop.AbstractMainPageView;
import de.ev.iisin.common.stammdaten.member.domain.Mitglied;
import de.ev.iisin.maintenance.member.MitgliedHomeModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilMainPageBuilder extends AbstractMainPageView<MitgliedHomeModel> {

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(PupilMainPageBuilder.class);

	private final PupilMainModel pupilmainModel;
	private JList pupilList;
	private PupilCellRenderer renderer;
	

	// Instance Creation ****************************************************

	public PupilMainPageBuilder(PupilMainModel pupilmainModel) {
		super(pupilmainModel.getMitgliedMainModel());
		this.pupilmainModel = pupilmainModel;
		renderer = new PupilCellRenderer();
	}

	// Building *************************************************************



	@Override
	protected SimpleInternalFrame buildSideBar() {
		SimpleInternalFrame sideBar = new SimpleInternalFrame(RESOURCE.getString("pupil.title"));
		sideBar.setEnabled(true);
		SelectionInList<Mitglied> vetoablePupil = pupilmainModel.getVetoAbleSelectionInList();
		pupilList = ComponentFactory.createList(vetoablePupil,renderer);
		pupilList.setSelectionModel(pupilmainModel.getSingleListSelectionAdapter());
		sideBar.setContent(buildPupilPanel(pupilList));

		return sideBar;
	}
	


	protected JComponent buildMainPanel() {
		return new PupilHomeBuilder(pupilmainModel).getPanel();
	}
	

}
