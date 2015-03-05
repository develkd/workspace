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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.ActionButton;

/**
 * @author Kemal Dönmez
 *
 */
public class SidebarBuilder{
	
	private AbstractMainPageModel model;
	protected StringBuilder  pbuilder;
	
	
	public SidebarBuilder(AbstractMainPageModel model){
		this.model = model;
		pbuilder = new StringBuilder();
	}
	
	protected JComponent getVisualCalendar(){
		return   ComponentFactory.createJXMonthView(Calendar.MONDAY, model.getDateModel());
	}
	
	/* (non-Javadoc)
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 */
	public SimpleInternalFrame buildFrame (){
		SimpleInternalFrame frame = new SimpleInternalFrame("Admin");
		frame.setContent(getPanel());
		return frame;
	}
	
	
	
	protected JComponent getPanel(){
		List<ActionButton> buttons = buildActionButtons();

		FormLayout layout = new FormLayout("fill:p:grow", "p,10dlu,"+pbuilder.toString());
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setBorder(Borders.createEmptyBorder("10,3,10,3"));

		CellConstraints cc = new CellConstraints();
		builder.add(getVisualCalendar(),cc.xy(1,1));
		int i = 3;
		for (ActionButton button : buttons) {
			builder.add(button,cc.xy(1, i++));
			
		}
		return builder.getPanel();
	}

	
	protected List<ActionButton> buildActionButtons(){
		ArrayList<ActionButton> buttons = new ArrayList<ActionButton>();
		for (Action action : model.getSidbarActions()) {
			buttons.add(new ActionButton(action));
			pbuilder.append("p,");
		}
		
		return buttons;
		
	}
	
}
