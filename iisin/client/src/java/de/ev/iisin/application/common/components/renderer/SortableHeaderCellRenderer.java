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
import java.awt.Dimension;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.common.interfaces.ComparatorIdentifier;
import de.ev.iisin.common.util.ResourceUtil;

/**
 * @author doenmez
 * @version $Revision: 1.7 $
 */
public class SortableHeaderCellRenderer extends DefaultTableCellRenderer
		implements ComparatorIdentifier {

	private static final long serialVersionUID = -4922138287500450763L;
	static final ResourceBundle RESOURCE_MAP = ResourceUtil
			.getBundlePath(SortableHeaderCellRenderer.class);

	private ImageIcon downImage;
	private ImageIcon upImage;
	private JLabel imageLabel;
	private JLabel textLabel;
	private boolean isASC;
	private int cellPos;

	public SortableHeaderCellRenderer(int cellPos, int defaultSortColumn,
			boolean isASC) {
		this.cellPos = cellPos;

		this.isASC = isASC;
		this.cellPos = cellPos;
		setOpaque(true);
		loadImages();
		imageLabel = new JLabel(isASC ? upImage : downImage,
				SwingConstants.RIGHT);
		imageLabel.setVisible(cellPos == defaultSortColumn);
		textLabel = new JLabel();

	}

	
	public void toggleSortdirection() {
		isASC = !isASC;
		setNewImage(isASC ? upImage : downImage);
	}

	public void setASC(boolean isASC) {
		this.isASC = isASC;
	}

	public boolean isASC() {
		return isASC;
	}

	public int getCellPos() {
		return cellPos;
	}

	public void enableSorting(boolean enablement) {

		imageLabel.setVisible(enablement);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Font font = table.getFont();
		setFont(font);

		Dimension dim = getPreferredSize();
		Dimension newDim = new Dimension(dim.width, (getFont().getSize() + 10));
		JTableHeader header = table.getTableHeader();
		header.setPreferredSize(newDim);
		textLabel.setText((value == null) ? "" : value.toString());
		return buildPanel(header);

	}
	

	private JPanel buildPanel(JTableHeader header) {
		FormLayout layout = new FormLayout("p,3dlu, p", "p");
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		builder.add(textLabel, cc.xy(1, 1));
		builder.add(imageLabel, cc.xy(3, 1));
		JPanel panel = builder.getPanel();
		panel.setOpaque(false);
		panel.setForeground(UIManager.getColor("TableHeader.foreground"));
		panel.setBackground(UIManager.getColor("TableHeader.background"));
		panel.setBorder( UIManager.getBorder("TableHeader.cellBorder"));
		return panel;

	}

	private void loadImages() {
		downImage = ResourceUtils.getImageIcon("down.image",
				SortableHeaderCellRenderer.class);
		// new ImageIcon(ResourceUtil.getImage("down.image",
		// SortableHeaderCellRenderer.class));
		upImage = ResourceUtils.getImageIcon("up.image",
				SortableHeaderCellRenderer.class);
		// new ImageIcon(ResourceUtil.getImage("up.image",
		// SortableHeaderCellRenderer.class));
//		toottipText = ResourceUtil.getString("column.tooltip.text",
//				SortableHeaderCellRenderer.class);
	}

	private void setNewImage(ImageIcon image) {
		imageLabel.setIcon(image);
	}

}
