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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import de.ev.iisin.application.common.components.renderer.StripedTableCellRenderer;
import de.ev.iisin.application.common.utils.SystemUtils;

/**
 * @author Kemal Dönmez
 *
 */
public class StripedTable extends JTable {

    /**
	 * Created at 01.11.2008
	 */
	private static final long serialVersionUID = 5228293970471993951L;
	private Color evenColor;
    private Color oddColor;

 
    public StripedTable(TableModel tableModel) {
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
    }

    @Override
    public void paint(Graphics g) {
        int stripeHeight = getRowHeight();
        Rectangle clip = g.getClipBounds();

        int x = clip.x;
        int y = clip.y;
        int w = clip.width;
        int h = clip.height;
        int row = 0;

        int y2 = y+h;

        if (y != 0) {
            int diff = y % stripeHeight;
            row = y / stripeHeight;
            y -= diff;
        }

        while (y < y2) {
            Color color = row % 2 == 0 ? evenColor : oddColor;
//                if (((JTable)c).isRowSelected(row)) {
//                    color = c.hasFocus()
//                        ? selectedFocusedColor
//                        : selectedNotFocusedColor;
//                }
            g.setColor(color);
            g.fillRect(x, y, w, stripeHeight);
            y += stripeHeight;
            row++;
        }
        super.paint(g);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (c instanceof JComponent) {
            
            JComponent jc = (JComponent) c;
            jc.setOpaque(isCellSelected(row, column));
            
        }
        return c;
    }

    // Helper Code ************************************************************

    private void setFillsViewportHeight() {
        try {
            Method method = getClass().getMethod("setFillsViewportHeight", Boolean.TYPE);
            method.invoke(this, Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
