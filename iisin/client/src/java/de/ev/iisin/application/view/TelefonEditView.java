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

package de.ev.iisin.application.view;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.ComponentFactory;
import de.ev.iisin.application.common.components.renderer.TelefonTypListCellRenderer;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;

/**
 * @author Kemal Dönmez
 * 
 */
public class TelefonEditView extends AbstractView {
	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(TelefonEditView.class);

	private final String[] cols = new String[] {
			RESOURCE.getString("member.phone.type"),
			RESOURCE.getString("member.phone.number") };

	private JScrollPane telePane;
	private JComponent telefonTable;

	private TelefonEditModel model;
	private JComboBox typComponent;
	private JComponent numberComponent;
	private ValueModel numberModel;

	public TelefonEditView(TelefonEditModel model) {
		this.model = model;
	}


	private void initComponents() {
		
		typComponent = ComponentFactory.createComboBox(model
				.getTypSelectionInList(), (AbstractValueModel) model
				.getTypSelectionInList().getSelectionHolder(),
				new TelefonTypListCellRenderer());

		numberModel = model.getModel(Telefon.PROPERTY_NUMMER);
		//numberComponent = ComponentFactory.createIntegerField(numberModel);
		numberComponent = ComponentFactory.createTextField(numberModel);
		telefonTable = ComponentFactory.createTable(getTableAdapter(), model
				.getSelectionInList().getSelectionIndexHolder());

		telePane = new JScrollPane(telefonTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 */
	@Override
	protected JComponent buildPanel() {
		initComponents();
		FormLayout layout = new FormLayout("fill:80dlu:grow, 3dlu, p",
				"p, 4dlu, fill:60dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setOpaque(false);
		CellConstraints cc = new CellConstraints();
		builder.add(createEditCoponent(), cc.xy(1, 1));
		builder.add(telePane, cc.xy(1, 3));
		builder.add(buildButtonComponent(), cc.xywh(3, 1, 1, 3));
		builder.setOpaque(false);
		return builder.getPanel();
	}

	private JComponent createEditCoponent() {
		FormLayout layout = new FormLayout(
				"pref, 3dlu, 80dlu, 6dlu, pref, 3dlu, fill:p:grow",
				"fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JLabel(RESOURCE.getString("member.phone.type")), cc.xy(
				1, 1));
		builder.add(typComponent, cc.xy(3, 1));
		builder.add(new JLabel(RESOURCE.getString("member.phone.number")), cc
				.xy(5, 1));
		builder.add(numberComponent, cc.xy(7, 1));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	private JComponent buildButtonComponent() {
		FormLayout layout = new FormLayout("pref", "p, 3dlu, p, 3dlu, p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JButton(model.getNewAction()), cc.xy(1, 1));
		builder.add(new JButton(model.getEditAction()), cc.xy(1, 3));
		builder.add(new JButton(model.getDeleteAction()), cc.xy(1, 5));
		builder.setOpaque(false);
		return builder.getPanel();

	}

	protected AbstractTableAdapter<Telefon> getTableAdapter() {
		return new TelefonTableAdptar();
	}

	@SuppressWarnings("serial")
	private class TelefonTableAdptar extends AbstractTableAdapter<Telefon> {

		public TelefonTableAdptar() {
			super(model.getListModel(), cols);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Telefon t = (Telefon) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				TelefonTyp typ = t.getTyp();
				return typ == null ?"??":t.getTyp().getBezeichnung();
			case 1:
				return t.getNummer();
			default:
				throw new IllegalStateException("Unknown column index: "
						+ columnIndex);
			}

		}

	}

}
