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

package de.ev.iisin.application.common.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.jgoodies.forms.layout.Sizes;

import de.ev.iisin.application.common.components.renderer.MultiHeaderLineCellRenderer;
import de.ev.iisin.application.common.utils.LineWrapper;

public class MultiHeaderLine extends JTableHeader {

	private static final long serialVersionUID = -5152260061864345417L;
	public static final String PROPERTYNAME_FONTMETRICS = "fontMetrics";

	private int rows = -1;
	private int zeilenLaenge;
	private FontMetrics fontMetrics;
	private Dimension dimHeaderHeight;

	public MultiHeaderLine(TableColumnModel columnModel) {
		super(columnModel);
		setReorderingAllowed(false);
		fontMetrics = getFontMetrics(getFont());
		setRenderer();
	}

	// ---------------------- API ---------------------------------

	public void setZeilenLaenge(int zeilenLaenge) {
		this.zeilenLaenge = zeilenLaenge;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		fitColumnWidths();
		calcHeaderHeight();
	}

	public void setFontMetrics(FontMetrics fontMetrics) {
		FontMetrics oldFontMetrics = this.fontMetrics;
		this.fontMetrics = fontMetrics;
		firePropertyChange(PROPERTYNAME_FONTMETRICS, oldFontMetrics,
				fontMetrics);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	// ------------ HelperMethods -------------------------

	private void setRenderer() {
		MultiHeaderLineCellRenderer renderer = new MultiHeaderLineCellRenderer(
				zeilenLaenge);
		Enumeration<TableColumn> enume = columnModel.getColumns();
		while (enume.hasMoreElements()) {
			TableColumn col = (TableColumn) enume.nextElement();
			col.setHeaderRenderer(renderer);
		}
	}

	private void calcHeaderHeight() {

		if (dimHeaderHeight == null) {
			dimHeaderHeight = getPreferredSize();
			if (rows < 0)
				rows = dimHeaderHeight.height;

			int delta = rows > 1 ? 8-rows : 8;
			int y = (fontMetrics.getHeight() + delta)*rows;
			
			setPreferredSize(new Dimension(dimHeaderHeight.width, y));
		}
	}

	private void fitColumnWidths() {
		TableColumnModel model = getColumnModel();

		int count = model.getColumnCount();

		for (int index = 0; index < count; index++) {
			if (count != -1) {
				TableColumn column = model.getColumn(index);
				int columnLength = getHeaderColumnLength(column);
				int pixel = Sizes.dialogUnitXAsPixel(columnLength, this);
				column.setPreferredWidth(pixel);
				column.setMaxWidth(pixel + 100);
			}
		}
	}

	private int getHeaderColumnLength(TableColumn column) {
		Object ob = column.getHeaderValue();
		if (ob instanceof String) {
			String new_name = (String) ob;
			String[] lableText = LineWrapper.getZeilen(new_name, zeilenLaenge);

			int x = 0;
			int zeile = 0;
			for (int i = 0; i < lableText.length; i++) {
				String text = (String) lableText[i];
				if (text.equals(""))
					continue;
				int currentLength = fontMetrics.stringWidth(text) + 8;
				x = currentLength > x ? currentLength : x;
				zeile++;
			}
			calcMaxHeight(zeile);
			return x;
		}
		return column.getPreferredWidth();
	}

	private void calcMaxHeight(int length) {
		rows = rows < length ? length : rows;
	}

}
