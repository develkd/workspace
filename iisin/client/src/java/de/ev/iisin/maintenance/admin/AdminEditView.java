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

package de.ev.iisin.maintenance.admin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.common.stammdaten.admin.domain.Admin;
import de.ev.iisin.maintenance.common.PersonEditorView;

/**
 * @author Kemal Dönmez
 * 
 */
public class AdminEditView extends PersonEditorView<Admin> {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(AdminEditView.class);

	/**
	 * Erzeugt am 03.08.2009
	 */
	private static final long serialVersionUID = 9147447666368884436L;
	private JComponent passwortComponent;
	private JComponent passwortRefComponent;
	private JComponent adminComponent;
	private JComponent teacherComponent;
	private JComponent mentorComponent;
	private AdminEditModel model;
	private ValueModel adminModel;
	private JComponent passwortTabComponent;
	private PresentationModel<AdminEditModel> passModel;
	
	
	public AdminEditView(String title, AdminEditModel model) {
		super(title, model.getPersonEditorModel());
		this.model = model;
		passModel = new PresentationModel<AdminEditModel>(model);
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		adminModel = model.getModel(Admin.PROPERTY_ADMIN);
		passwortComponent = ComponentFactory.createPasswordField(passModel
				.getModel(AdminEditModel.PROPERTY_PASSWORD));
		passwortRefComponent = ComponentFactory.createPasswordField(passModel
				.getModel(AdminEditModel.PROPERTY_PASSWORD_REF));
		adminComponent = ComponentFactory.createCheckBox(adminModel, RESOURCE
				.getString("edit.admin"));
		teacherComponent = ComponentFactory.createCheckBox(model
				.getModel(Admin.PROPERTY_TEACHER), RESOURCE
				.getString("edit.teacher"));
		mentorComponent = ComponentFactory.createCheckBox(model
				.getModel(Admin.PROPERTY_MENTOR), RESOURCE
				.getString("edit.mentor"));

	}

	private void initEvents() {
		adminModel.addValueChangeListener(new ValueChangeListener());
	}

	@Override
	protected String getPersonTabName(){
		return RESOURCE.getString("tab.admin");
	}

	@Override
	protected JComponent buildView() {
		initComponents();
		initEvents();
		super.buildView();
		passwortTabComponent = buildPasswortComponent();
		checkAdminStatus(model.getBean().isAdmin());
		return getTappedPane();
	}

	private JComponent buildPasswortComponent() {
		FormLayout layout = new FormLayout("p, 3dlu, fill:p:grow, ",
				"fill:0:grow,p, 3dlu, p,fill:0:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder
				.setBorder(Borders
						.createEmptyBorder("7dlu, 14dlu, 7dlu, 14dlu"));

		CellConstraints cc = new CellConstraints();
		builder.addLabel(RESOURCE
				.getString("edit.passwd"), cc.xy(1, 2));
		builder.add(passwortComponent, cc.xy(3, 2));
		builder.addLabel(RESOURCE
				.getString("edit.refpasswd"), cc.xy(1, 4));
		builder.add(passwortRefComponent, cc.xy(3, 4));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	@Override
	protected JComponent buildPersonPanel() {
		FormLayout layout = new FormLayout("p", "p,10dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(buildAdminPanel(), cc.xy(1, 1));
		builder.add(super.buildPersonPanel(), cc.xy(1, 3));

		builder.setOpaque(false);
		return builder.getPanel();
	}

	private JComponent buildAdminPanel() {
		FormLayout layout = new FormLayout(
				"fill:0:grow,p, 10dlu, p, 10dlu, p,fill:0:grow", "p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(adminComponent, cc.xy(2, 1));
		builder.add(teacherComponent, cc.xy(4, 1));
		builder.add(mentorComponent, cc.xy(6, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	private void removePasswortTab() {
		getTappedPane().remove(passwortTabComponent);
	}

	private void addPasswortTab() {
		getTappedPane().addTab(RESOURCE.getString("tab.passwd"), 
		passwortTabComponent);

	}

	private void checkAdminStatus(boolean isAdmin) {
		if (isAdmin) {
			addPasswortTab();
		} else {
			removePasswortTab();
		}

	}

	private class ValueChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			checkAdminStatus(((Boolean) evt.getNewValue()).booleanValue());
		}

	}
}
