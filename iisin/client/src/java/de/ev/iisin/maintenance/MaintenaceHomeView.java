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

package de.ev.iisin.maintenance;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JComponent;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;
import com.jgoodies.uif_lite.panel.CardPanel;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.application.common.components.ActionButton;
import de.ev.iisin.application.desktop.AbstractMainPageBuilder;
import de.ev.iisin.application.desktop.AbstractMainPageModel;
import de.ev.iisin.application.desktop.SidebarBuilder;
import de.ev.iisin.maintenance.admin.AdminHomeView;
import de.ev.iisin.maintenance.admin.AdminMainModel;
import de.ev.iisin.maintenance.bank.BankHomeModel;
import de.ev.iisin.maintenance.bank.BankHomeView;
import de.ev.iisin.maintenance.human.HumanHomeModel;
import de.ev.iisin.maintenance.human.HumanHomeView;
import de.ev.iisin.maintenance.member.MitgliedHomeModel;
import de.ev.iisin.maintenance.member.MitgliedHomeView;
import de.ev.iisin.maintenance.typs.BerufsTypHomeModel;
import de.ev.iisin.maintenance.typs.BerufsTypHomeView;
import de.ev.iisin.maintenance.typs.PersonTypHomeModel;
import de.ev.iisin.maintenance.typs.PersonTypHomeView;
import de.ev.iisin.maintenance.typs.TelefonTypHomeModel;
import de.ev.iisin.maintenance.typs.TelefonTypHomeView;

/**
 * @author Kemal Dönmez
 * 
 */
public class MaintenaceHomeView extends AbstractMainPageBuilder {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(MaintenaceHomeView.class);

	private MaintenaceHomeModel mainModel;

	public MaintenaceHomeView(MaintenaceHomeModel mainModel) {
		super(mainModel);
		this.mainModel = mainModel;
	}

	protected CardPanel buildPages() {
		CardPanel panel = super.buildPages();
		panel.add(AdminMainModel.CARDNAME_ADMIN, new AdminHomeView(mainModel
				.getAdminMainModel()).getPanel());
		panel.add(MitgliedHomeModel.CARDNAME_MEMBER, new MitgliedHomeView(
				mainModel.getMitgliedMainModel()).getPanel());
		panel.add(HumanHomeModel.CARDNAME_HUMAN, new HumanHomeView(mainModel
				.getHumanHomeModel()).getPanel());
		panel.add(BankHomeModel.CARDNAME_BANK, new BankHomeView(mainModel
				.getBankHomeModel()).getPanel());
		panel.add(PersonTypHomeModel.CARDNAME_PERSON_TYP,
				new PersonTypHomeView(mainModel.getPersonTypMainModel())
						.getPanel());
		panel.add(TelefonTypHomeModel.CARDNAME_TELEFON_TYP,
				new TelefonTypHomeView(mainModel.getTelefonTypMainModel())
						.getPanel());
		panel.add(BerufsTypHomeModel.CARDNAME_BERUFS_TYP,
				new BerufsTypHomeView(mainModel.getBerufsTypHomeModel())
						.getPanel());

		return panel;
	}

	@Override
	protected SimpleInternalFrame buildSideBar() {
		return new MaintenanceSidebarBuilder(mainModel).buildFrame();
	}

	private class MaintenanceSidebarBuilder extends SidebarBuilder {
		private StringBuilder typBuilder = new StringBuilder();

		public MaintenanceSidebarBuilder(AbstractMainPageModel model) {
			super(model);
		}

		@Override
		protected JComponent getPanel() {

			FormLayout layout = new FormLayout("fill:p:grow",
					"p, 10dlu, p, 10dlu, p");
			PanelBuilder builder = new PanelBuilder(layout);
			builder.setBorder(Borders.createEmptyBorder("10,3,10,3"));
			CellConstraints cc = new CellConstraints();
			builder.add(buildMainAction(), cc.xy(1, 1));
			builder.addSeparator(RESOURCE.getString("sep.group"), cc.xy(1, 3));
			builder.add(buildTypAction(), cc.xy(1, 5));
			return builder.getPanel();
		}

		private JComponent buildMainAction() {
			List<ActionButton> buttons = buildActionButtons();
			FormLayout layout = new FormLayout("fill:p:grow", pbuilder
					.toString());
			PanelBuilder builder = new PanelBuilder(layout);
			CellConstraints cc = new CellConstraints();
			int i = 1;
			for (ActionButton button : buttons) {
				builder.add(button, cc.xy(1, i++));
			}
			return builder.getPanel();
		}

		private JComponent buildTypAction() {
			List<ActionButton> buttons1 = buildActionTypButtons();
			FormLayout layout = new FormLayout("fill:p:grow", pbuilder
					.toString());
			PanelBuilder builder = new PanelBuilder(layout);
			CellConstraints cc = new CellConstraints();
			int i = 1;
			for (ActionButton button : buttons1) {
				builder.add(button, cc.xy(1, i++));
			}

			return builder.getPanel();

		}

		protected List<ActionButton> buildActionTypButtons() {
			typBuilder.append("p,");

			ArrayList<ActionButton> buttons = new ArrayList<ActionButton>();
			for (Action action : mainModel.getSidbarTypActions()) {
				buttons.add(new ActionButton(action));
				typBuilder.append("p,");
			}

			return buttons;

		}

	}

}
