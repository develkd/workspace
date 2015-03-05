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

package de.ev.iisin.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ActionManager;
import com.jgoodies.uif_lite.application.Application;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.action.Actions;
import de.ev.iisin.application.common.components.ToolBarButton;
import de.ev.iisin.application.desktop.component.HeaderBorder;
import de.ev.iisin.application.desktop.component.ImageBackgroundPanel;
import de.ev.iisin.application.model.MainModel;

/**
 * @author Kemal Dönmez
 * 
 */
public class MainMenuBuilder {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(MainMenuBuilder.class);

	private Image backgroundImage;

	// Instance Creation ******************************************************

	public MainMenuBuilder() {

	}

	// Building ***************************************************************

	public JComponent build() {
		FormLayout layout = new FormLayout(
				"p, 30dlu, p, fill:0:grow, p,3dlu,p,3dlu,p", "p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(createMenueButton(ActionManager.get(Actions.EXIT_ID)), cc
				.xy(1, 1));
		builder.add(createMenueButton(ActionManager
				.get(Actions.UPDATE_PASSWORD)), cc.xy(3, 1));
		builder.add(createMenueButton(ActionManager
				.get(Actions.OPEN_PUPIL_VIEW)), cc.xy(5, 1));
		builder.add(createMenueButton(ActionManager
				.get(Actions.OPEN_MAINTENANCE)), cc.xy(7, 1));
		builder.add(createMenueButton(ActionManager.get(Actions.ABOUT_ID)), cc
				.xy(9, 1));
		 builder.setOpaque(false);
		return builder.getPanel();
	}

	private JButton createMenueButton(Action action) {
		ToolBarButton tb = new ToolBarButton(action);
//		if (backgroundImage == null) {
//			backgroundImage = tb.getBackgroundImage();
//		}
		return tb;

	}

}
