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

import java.awt.Frame;

import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.AbstractDialog;


/**
 * @author Kemal Dönmez
 * 
 */
public class PupilFatherDialog extends AbstractDialog {
	/**
	 * Created at 27.11.2008
	 */
	private static final long serialVersionUID = 1042299985267773071L;
	private PupilEditModel model;

	public PupilFatherDialog(Frame owner, PupilEditModel model) {
		super(owner, "Vater");
		this.model = model;
	}

	@Override
	public JPanel buildContent() {
		FormLayout layout = new FormLayout("p,3dlu, fill:p:grow",
				"p, 3dlu,p, 1dlu, p, 1dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.addTitle("Vater ", cc.xyw(1, 1, 3));
		builder.addLabel("Name :", cc.xy(1, 3));
		builder.addLabel("Osman", cc.xy(3, 3));
		builder.addLabel("Nachname :", cc.xy(1, 5));
		builder.addLabel("Gazi", cc.xy(3, 5));
		builder.addLabel("MobilTel :", cc.xy(1, 7));
		builder.addLabel("0176 123 456 32", cc.xy(3, 7));
		return builder.getPanel();
	}

}
