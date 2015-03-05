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

package de.ev.iisin.application.common.components.renderer;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.common.utils.LineWrapper;

/**
 * @author doenmez
 * @version $Revision: 1.2 $
 */
public class MultiHeaderLineCellRenderer extends DefaultTableCellRenderer
		implements TableCellRenderer {
	
	private static final long serialVersionUID = -675495161616560400L;
	private int zeilenLaenge;
	private FontMetrics fontMetrics;

	public MultiHeaderLineCellRenderer(int zeilenLaenge) {
		this.zeilenLaenge = zeilenLaenge;
		setOpaque(true);
		setForeground(UIManager.getColor("TableHeader.foreground"));
		setBackground(UIManager.getColor("TableHeader.background"));
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Font font = table.getFont();
		setFont(font);		
		setFontMetrics(table.getFontMetrics(font));
		JTableHeader header = table.getTableHeader();
		TableColumn col = header.getColumnModel().getColumn(column);
		String str = (value == null) ? "" : value.toString();
		
		String[] lableText = LineWrapper.getZeilen(str, zeilenLaenge);
		JPanel pan = buildPanel(lableText, isSelected, col, getInsets());
		return pan;
	}

	protected JLabel getCreatedLabel(String value, boolean isSelected) {
		JLabel view =  new JLabel(value);
		view.setHorizontalAlignment(getHorizontalHeaderTextAlignment());
		view.setVerticalTextPosition(JLabel.TOP);
		view.setOpaque(false);
		return view;
	}

	protected FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	protected void setFontMetrics(FontMetrics fontMetrics) {
		this.fontMetrics = fontMetrics;
	}

	protected int stringWidth(String string) {
		return getFontMetrics().stringWidth(string) + 8;
	}
	
	protected int getHorizontalHeaderTextAlignment(){
		return JLabel.LEFT;
	}

	protected JPanel buildPanel(String[] lableText, boolean isSelected,
			TableColumn col, Insets inset) {
		int length = lableText.length;
		String p = "p";
		for (int i = 0; i < length - 1; i++) {
			p += ",1dlu,p";
		}
		FormLayout layout = new FormLayout("p", p);
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();

		int i = 0;
		for (String text : lableText) {
			int width = col.getWidth() - inset.right - inset.left;
			String value = fitString(width, text);
			builder.add(getCreatedLabel(value, isSelected), cc.xy(1, i*2+1));
			i++;
		}

		JPanel panel = builder.getPanel();
		panel.setOpaque(false);
		return panel;
	}

	
	protected String fitString(int width, String text) {
		return clipStringIfNecessary(text, width);
	}

	private String clipStringIfNecessary(String string, int availTextWidth) {
		if ((string == null) || (string.equals(""))) {
			return "";
		}
		int textWidth = stringWidth(string);
		if (textWidth > availTextWidth) {
			return clipString(string, availTextWidth);
		}
		return string;
	}

	private String clipString(String string, int availTextWidth) {
		// c may be null here.
		String clipString = "...";
		int width = stringWidth(clipString);
		// NOTE: This does NOT work for surrogate pairs and other fun
		// stuff
		int nChars = 0;
		for (int max = string.length(); nChars < max; nChars++) {
			width += getFontMetrics().charWidth(string.charAt(nChars));
			if (width > availTextWidth) {
				break;
			}
		}
		string = string.substring(0, nChars) + clipString;
		return string;
	}
}
