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
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 * @author Kemal Dönmez
 * 
 */
public class ValidationDialog extends AbstractDialog {

	/**
	 * Erzeugt am 31.01.2009
	 */
	private static final long serialVersionUID = 2030674430952461888L;
	private ValidationResult result;

	public ValidationDialog(ValidationResult result) {
		super(Application.getDefaultParentFrame(), "FEHLER");
		this.result = result;
	}

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

	private JComponent getButtonPanle() {
		return buildButtonBarWithClose();
	}

	private JComponent buildView() {
		DefaultValidationResultModel defMod = new DefaultValidationResultModel();
		defMod.setResult(result);
		return ValidationResultViewFactory.createReportList(defMod);
	}

}
