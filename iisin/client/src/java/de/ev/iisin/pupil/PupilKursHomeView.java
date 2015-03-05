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

import javax.swing.JComponent;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.common.interfaces.Sortable;
import de.ev.iisin.application.desktop.AbstractHomeView;
import de.ev.iisin.common.stammdaten.kurs.domain.Kursnote;

/**
 * @author Kemal Dönmez
 * 
 */
public class PupilKursHomeView extends AbstractHomeView {
	private PupilKursHomeModule model;
	public PupilKursHomeView(PupilKursHomeModule model) {
		super(model,"Kurse und Kursnoten");
		this.model = model;
	}

	
	protected JComponent buildView() {
		FormLayout layout = new FormLayout("fill:p:grow",
				"fill:0:grow, 6dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(getScrollPane(), cc.xy(1, 1));
		builder.add(getButtonBar(), cc.xy(1, 3));
		getFrame().setBorder(Borders.EMPTY_BORDER);

		return builder.getPanel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractHomeView#getTableAdapter()
	 */
	@Override
	@Sortable
	protected AbstractTableAdapter<?> getTableAdapter() {
		return new KursnotenTableAdptar(model);
	}

	@SuppressWarnings("serial")
	private class KursnotenTableAdptar extends AbstractTableAdapter<Kursnote> {

		public KursnotenTableAdptar(PupilKursHomeModule model) {
			super(model.getListModel(), model.getHeaderColumn());
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Kursnote k = (Kursnote) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return k.getBezeichnung();
			case 1:
				return k.getNote();
			case 2:
				return k.getBemerkung();
			default:
				throw new IllegalStateException("Unknown column index: " + columnIndex);

			}
		}
	}
}
