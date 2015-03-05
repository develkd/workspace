/*
 * Copyright (c) 2008 Kemal D�nmez. All Rights Reserved.
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

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.common.collect.ArrayListModel;

import de.ev.iisin.application.common.components.renderer.SortableHeaderCellRenderer;
import de.ev.iisin.application.common.interfaces.ComparatorIdentifier;

/**
 * The sortable tableheader. It fires a selection-changed-event to sort the
 * table-colum.data.
 * 
 * @author doenmez
 * @version $Revision: 1.7 $
 */
public class SortableHeader extends JTableHeader {
    
    public static final String                          PROPERTYNAME_HEADER_COLUMN_SELECTION = "headerColoumnSelection";
    
    private static final long                           serialVersionUID                     = 6022945176575843404L;
    private SelectionInList<SortableHeaderCellRenderer> selectionCols;
    private ArrayListModel<SortableHeaderCellRenderer>  sortableRendererList;
    private ValueModel                                  indexHolder;
    private MousePressedHandler                         mouseListener;
    private ArrayListModel<TableCellRenderer>           defaultRendererList;
    private int                                         defaultSortColumn;

    public SortableHeader(TableColumnModel columnModel) {
        super(columnModel);
        setReorderingAllowed(false);
        init();
    }
    
    private void init() {
        defaultRendererList = new ArrayListModel<TableCellRenderer>();
        
        selectionCols = new SelectionInList<SortableHeaderCellRenderer>(
                (ListModel) sortableRendererList);
        indexHolder = selectionCols.getSelectionHolder();
        indexHolder.addValueChangeListener(new SelectionIndexChangeHandler());
        mouseListener = new MousePressedHandler(indexHolder);
        getDefaultTableColumnCellRenderer();
    }
    
    private void getDefaultTableColumnCellRenderer() {
        Enumeration<TableColumn> enume = columnModel.getColumns();
        while (enume.hasMoreElements()) {
            TableColumn col = (TableColumn) enume.nextElement();
            defaultRendererList.add(col.getHeaderRenderer());
        }
        
    }
    protected void configureAsUnsortableHeader() {
        removeMouseListener(mouseListener);
        setDefaultRenderer();
        indexHolder.setValue(0);
        sortableRendererList.get(0).setASC(false);
    }

    protected void configureAsSortableHeader(int defaultSortColumn,
            boolean sort, boolean isASC) {
        addMouseListener(mouseListener);
        setSortableRenderer(defaultSortColumn, sort, isASC);
    }
   

//    protected void configureAsSortableHeader(int defaultSortColumn, boolean sort) {
//        addMouseListener(mouseListener);
//        setSortableRenderer(defaultSortColumn, sort);
//    }
    
    
//    protected void configureAsUnsortableHeader() {
//        removeMouseListener(mouseListener);
//        setDefaultRenderer();
//    }
    
    private void setSortableRenderer(int defaultSortColumn, boolean sort,
            boolean isASC) {
        sortableRendererList = new ArrayListModel<SortableHeaderCellRenderer>();
        
        Enumeration<TableColumn> enume = columnModel.getColumns();
        int cellPos = 0;
        while (enume.hasMoreElements()) {
            TableColumn col = (TableColumn) enume.nextElement();
            SortableHeaderCellRenderer renderer = new SortableHeaderCellRenderer(
                    cellPos++, defaultSortColumn, isASC);
            col.setHeaderRenderer(renderer);
            sortableRendererList.add(renderer);
        }
        if(sort)
        sort(defaultSortColumn);
    }
    
   
    private void setDefaultRenderer() {
        Enumeration<TableColumn> enume = columnModel.getColumns();
        int cellPos = 0;
        while (enume.hasMoreElements()) {
            TableColumn col = (TableColumn) enume.nextElement();
            col.setHeaderRenderer(defaultRendererList.get(cellPos++));
        }
    }
    
  

//    
//    private void sort(int pos) {
//        indexHolder.setValue(pos);
//        sortableRendererList.get(pos).toggleSortdirection();
//    }
//    
    private void sort(int pos) {
        defaultSortColumn = pos;
        indexHolder.setValue(pos);
    }
 
    private void fireHeaderSelectionEvent(int pos, boolean toggle) {
        if (sortableRendererList == null || sortableRendererList.isEmpty()) return;
        
        ComparatorIdentifier comparator = sortableRendererList.get(pos);
        comparator.enableSorting(true);
        if (toggle) comparator.toggleSortdirection();
        firePropertyChange(PROPERTYNAME_HEADER_COLUMN_SELECTION, null,
                comparator);
    }
 
    private static class MousePressedHandler extends MouseAdapter {
        
        private final ValueModel selectionIndexHolder;
        
        MousePressedHandler(ValueModel selectionIndexHolder) {
            this.selectionIndexHolder = selectionIndexHolder;
        }
        
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) /* && e.getClickCount() == 2 */) {
                
                SortableHeader sheader = (SortableHeader) e.getComponent();
                Point p = sheader.getMousePosition();
                selectionIndexHolder.setValue(null);
                selectionIndexHolder.setValue(sheader.columnAtPoint(p));
                sheader.repaint();
            }
        }
    }
    
   private class SelectionIndexChangeHandler implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            Integer newValue = (Integer) evt.getNewValue();
            Integer oldValue = (Integer) evt.getOldValue();
            
            if (oldValue != null) {
                ComparatorIdentifier comparator = sortableRendererList
                        .get(oldValue.intValue());
                comparator.enableSorting(false);
                firePropertyChange(PROPERTYNAME_HEADER_COLUMN_SELECTION, null,
                        comparator);
            }
            
            if (newValue != null) {
                fireHeaderSelectionEvent(newValue.intValue(), true);
            }
        }
    }

    
//    private class SelectionIndexChangeHandler implements PropertyChangeListener {
//        public void propertyChange(PropertyChangeEvent evt) {
//            Integer newValue = (Integer) evt.getNewValue();
//            Integer oldValue = (Integer) evt.getOldValue();
//            
//            if (oldValue != null) {
//                ComparatorIdentifier comparator = sortableRendererList
//                        .get(oldValue.intValue());
//                comparator.enableSorting(false);
//                firePropertyChange(PROPERTYNAME_HEADER_COLUMN_SELECTION, null,
//                        comparator);
//            }
//            
//            if (newValue != null) {
//                ComparatorIdentifier comparator = sortableRendererList
//                        .get(newValue.intValue());
//                comparator.enableSorting(true);
//                comparator.toggleSortdirection();
//                firePropertyChange(PROPERTYNAME_HEADER_COLUMN_SELECTION, null,
//                        comparator);
//            }
//        }
//    }
}
