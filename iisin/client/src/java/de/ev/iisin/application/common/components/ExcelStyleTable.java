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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.Method;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import de.ev.iisin.application.common.components.renderer.StripedTableCellRenderer;
import de.ev.iisin.application.common.utils.SystemUtils;



/**
 * @author doenmez
 * @version $Revision: 1.2 $
 */
public class ExcelStyleTable extends JTable {

	private static final long serialVersionUID = 2564995921103864232L;
	   private Color evenColor;
	    private Color oddColor;

	    public ExcelStyleTable(Object[][] values, Object[] columnNames) {
	        super(values, columnNames);
	        init();
	    }

	    public ExcelStyleTable(TableModel tableModel) {
	        super(tableModel);
	        init();
	    }

	    private void init() {
	      
	        setOpaque(false);
	        evenColor = Color.WHITE;
	        oddColor = new Color(237, 243, 254);
	        setRowHeight(getFont().getSize() + 10);
	        if (SystemUtils.IS_JAVA_6_OR_LATER) {
	            setFillsViewportHeight();
	        }
	        setShowGrid(false);
	        setIntercellSpacing(new Dimension(0, 0));
	        setDefaultRenderer(Object.class, new StripedTableCellRenderer());
	        getTableHeader().setReorderingAllowed(false);
	    }

	    @Override
	    public void paint(Graphics g) {
	  //      int stripeHeight = getRowHeight();
	        Rectangle clip = g.getClipBounds();

	        int x = clip.x;
	        int y = clip.y;
	        int w = clip.width;
	        int h = clip.height;
	        int row = 0;

	        int y2 = y+h;


	        while (y < y2) {
	        	 int wstripeHeight = getRowHeight(row);
	            Color color = row % 2 == 0 ? evenColor : oddColor;
	            g.setColor(color);
	            g.fillRect(x, y, w, wstripeHeight);
	            y += wstripeHeight;
	            row++;
	        }
	        super.paint(g);
	    }

	    
	    private void setFillsViewportHeight() {
	        try {
	            Method method = getClass().getMethod("setFillsViewportHeight", Boolean.TYPE);
	            method.invoke(this, Boolean.TRUE);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
