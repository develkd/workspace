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

package de.ev.iisin.wizard.gui;

import java.util.List;

import javax.swing.JComponent;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.common.AbstractEditorView;
import de.ev.iisin.binding.ValidableModel;
import de.ev.iisin.wizard.provider.ComponentPair;
import de.ev.iisin.wizard.provider.ComponentProvider;

/**
 * @author Kemal Dönmez
 * 
 */
public class GuiBuilder extends AbstractEditorView<ValidableModel> {

	/**
	 * Erzeugt am 28.01.2009
	 */
	private static final long serialVersionUID = -7433206338856909064L;

	public GuiBuilder(String title, ValidableModel model) {
		super(title,model);
	}

	public JComponent getComponent(){
		return buildView();
	}
	@Override
	protected JComponent buildView() {
		List<ComponentPair> des = ComponentProvider.getInstance()
				.provide(getModel());
		FormLayout layout = new FormLayout("p,10dlu,fill:[150dlu,p]:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		for (ComponentPair pair : des) {
			builder.append(pair.getLabelText(), pair.getComponent());
		}
		return builder.getPanel();

	}
}
