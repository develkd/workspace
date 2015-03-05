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

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import de.ev.iisin.application.common.interfaces.Tableconfigurator;

/**
 * @author Kemal Dönmez
 *
 */
public class SortableTable extends StripedTable {
	/**
	 * Erzeugt am 04.08.2009
	 */
	private static final long serialVersionUID = -8380693301126885962L;
	private SortableHeader    header;
    private boolean           isSortableHeaderShown;
    private int               defaultSortColumn;

    public SortableTable(TableModel model) {
        super(model);
    }
    
    protected JTableHeader createDefaultTableHeader() {
        header = new SortableHeader(getColumnModel());
        return header;
    }
    
    @Tableconfigurator
    public void configureTable(boolean hasSortableHeader,
            int defaultSortColumn, boolean isASC) {
        if (hasSortableHeader) {
            this.defaultSortColumn = defaultSortColumn;
            sort(isASC);
        }
    }
    
    private void sort(boolean isASC) {
        showHeaderAsSortable(defaultSortColumn, true, isASC);
    }
    
    public void showHeaderAsSortable(int defaultSortColumn, boolean sort,
            boolean isASC) {
        if (!isSortableHeaderShown) {
            header.configureAsSortableHeader(defaultSortColumn, sort, isASC);
            isSortableHeaderShown = true;
        }
    }
    
    public void showHeaderAsNotSortable() {
        header.configureAsUnsortableHeader();
        isSortableHeaderShown = false;
    }

}
