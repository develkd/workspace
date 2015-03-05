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

package de.ev.iisin.application.common;

import javax.swing.JComponent;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.AbstractDialog;
import com.jgoodies.uif_lite.application.Application;
import com.jgoodies.validation.ValidationResult;

import de.ev.iisin.binding.Validate;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractEditorView<M> extends AbstractDialog {
	private M model;

	public AbstractEditorView(String title, M model) {
		super(Application.getDefaultParentFrame(), title);
		this.model = model;
	}

	protected M getModel() {
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jgoodies.uif_lite.AbstractDialog#buildContent()
	 */
	@Override
	protected JComponent buildContent() {
		FormLayout layout = new FormLayout("fill:p:grow", "fill:p:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.setBorder(Borders.createEmptyBorder("6dlu, 4dlu, 6dlu, 4dlu"));
		builder.add(buildView(), cc.xy(1, 1));
		builder.add(getButtonPanle(), cc.xy(1, 3));
		return builder.getPanel();
	}

	protected abstract JComponent buildView();

	protected JComponent getButtonPanle() {
		return buildButtonBarWithOKCancel();
	}

	@Override
	public void doAccept() {
		ValidationResult result = ((Validate)getModel()).validate(new ValidationResult());
		if (result.isEmpty()){
			super.doAccept();
			return;
		}
		new ValidationDialog(result).open();
	}

}
