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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;


/**
 * @author Kemal Dönmez
 *
 */
public class EditableComboBoxAdapter<E>  extends AbstractListModel implements ComboBoxModel {

    /**
	 * Erzeugt am 11.08.2009
	 */
	private static final long serialVersionUID = -7840880932166850244L;

	/**
     * Holds the list of choices.
     */
    private final ListModel listModel;

    /**
     * Refers to the ValueModel that holds the current selection.
     * In case this adapter is constructed for a SelectionInList
     * or SelectionInListModel, the selection holder will be updated
     * if the SIL or SILModel changes its selection holder.
     */
    private ValueModel selectionHolder;

    /**
     * Holds the listener that handles selection changes.
     */
    private final PropertyChangeListener selectionChangeHandler;


    // Instance creation ******************************************************

    /**
     * Constructs a ComboBoxAdapter for the specified List of items
     * and the given selection holder. Structural changes in the list
     * will be ignored.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * List countryList = Arrays.asList(countries);
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countryList, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param items            the list of items
     * @param selectionHolder  holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public EditableComboBoxAdapter(List<E> items, ValueModel selectionHolder) {
        this(new ListModelAdapter<E>(items), selectionHolder);
        if (items == null) {
            throw new NullPointerException("The list must not be null.");
        }
    }


    /**
     * Constructs a ComboBoxAdapter for the given ListModel and selection
     * holder. Structural changes in the ListModel will be reflected by
     * this adapter, but won't affect the selection.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * ListModel countryList = new ArrayListModel(Arrays.asList(countries));
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countryList, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param listModel         the initial list model
     * @param selectionHolder   holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public EditableComboBoxAdapter(ListModel listModel, ValueModel selectionHolder) {
        if (listModel == null) {
            throw new NullPointerException("The ListModel must not be null.");
        }
        if (selectionHolder == null) {
            throw new NullPointerException("The selection holder must not be null.");
        }
        this.listModel = listModel;
        this.selectionHolder = selectionHolder;
        listModel.addListDataListener(new ListDataChangeHandler());
        selectionChangeHandler = new SelectionChangeHandler();
        setSelectionHolder(selectionHolder);
    }


    /**
     * Constructs a ComboBoxAdapter for the specified List of items and the
     * given selection holder. Structural changes in the list will be ignored.
     * <p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(countries, contryModel);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param items             the list of items
     * @param selectionHolder   holds the selection of the combo
     * @throws NullPointerException if the list of items or the selection holder
     *         is {@code null}
     */
    public EditableComboBoxAdapter(E[] items, ValueModel selectionHolder) {
        this(new ListModelAdapter<E>(items), selectionHolder);
    }


    /**
     * Constructs a ComboBoxAdapter for the given SelectionInList. Note that
     * selections which are not elements of the list will be rejected.<p>
     *
     * <strong>Example:</strong><pre>
     * String[] countries = new String[] { &quot;USA&quot;, &quot;Germany&quot;, &quot;France&quot;, ... };
     * List countryList = Arrays.asList(countries);
     * ValueModel countryModel = new PropertyAdapter(customer, &quot;country&quot;, true);
     * SelectionInList sil     = new SelectionInList(countryList, countryModel);
     * ComboBoxAdapter adapter = new ComboBoxAdapter(sil);
     * JComboBox countryBox    = new JComboBox(adapter);
     * </pre>
     *
     * @param selectionInList        provides the list and selection
     * @throws NullPointerException if the <code>selectionInList</code> is
     *         {@code null}
     */
    public EditableComboBoxAdapter(SelectionInList<E> selectionInList) {
        this(selectionInList, selectionInList);
        selectionInList.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_HOLDER,
                new SelectionHolderChangeHandler());
    }


    // ComboBoxModel API ****************************************************

    /**
     * Returns the selected item by requesting the current value from the
     * either the selection holder or the SelectionInList's selection.
     *
     * @return The selected item or {@code null} if there is no selection
     */
    @SuppressWarnings("unchecked")
	public E getSelectedItem() {
        return (E) selectionHolder.getValue();
    }


    /**
     * Sets the selected item. The implementation of this method should notify
     * all registered <code>ListDataListener</code>s that the contents has
     * changed.
     *
     * @param object the list object to select or {@code null} to clear
     *        the selection
     */
    public void setSelectedItem(Object object) {
    	if(!(object instanceof String))
    		object = object.toString();
    	
        selectionHolder.setValue(object);
    }


    /**
     * Returns the length of the item list.
     *
     * @return the length of the list
     */
    public int getSize() {
        return listModel.getSize();
    }


    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @SuppressWarnings("unchecked")
	public E getElementAt(int index) {
        return (E) listModel.getElementAt(index);
    }


    // Helper Code ************************************************************

    private void setSelectionHolder(ValueModel newSelectionHolder) {
        ValueModel oldSelectionHolder = selectionHolder;
        if (oldSelectionHolder != null) {
            oldSelectionHolder.removeValueChangeListener(selectionChangeHandler);
        }
        selectionHolder = newSelectionHolder;
        if (newSelectionHolder == null) {
            throw new NullPointerException("The selection holder must not be null.");
        }
        newSelectionHolder.addValueChangeListener(selectionChangeHandler);
    }


    // Event Handling *********************************************************

    private void fireContentsChanged() {
        fireContentsChanged(this, -1, -1);
    }


    /**
     * Listens to selection changes and fires a contents change event.
     */
    private final class SelectionChangeHandler implements PropertyChangeListener {

        /**
         * The selection has changed. Notifies all
         * registered listeners about the change.
         *
         * @param evt the property change event to be handled
         */
        public void propertyChange(PropertyChangeEvent evt) {
            fireContentsChanged();
        }

    }


    /**
     * Handles ListDataEvents in the list model.
     */
    private final class ListDataChangeHandler implements ListDataListener {

        /**
         * Sent after the indices in the index0, index1 interval have been
         * inserted in the data model. The new interval includes both index0 and
         * index1.
         *
         * @param evt a <code>ListDataEvent</code> encapsulating the event
         *        information
         */
        public void intervalAdded(ListDataEvent evt) {
            fireIntervalAdded(EditableComboBoxAdapter.this,
                              evt.getIndex0(),
                              evt.getIndex1());
        }


        /**
         * Sent after the indices in the index0, index1 interval have been
         * removed from the data model. The interval includes both index0 and
         * index1.
         *
         * @param evt a <code>ListDataEvent</code> encapsulating the event
         *        information
         */
        public void intervalRemoved(ListDataEvent evt) {
            fireIntervalRemoved(EditableComboBoxAdapter.this,
                                evt.getIndex0(),
                                evt.getIndex1());
        }


        /**
         * Sent when the contents of the list has changed in a way that's too
         * complex to characterize with the previous methods. For example, this
         * is sent when an item has been replaced. Index0 and index1 bracket the
         * change.
         *
         * @param evt a <code>ListDataEvent</code> encapsulating the event
         *        information
         */
        public void contentsChanged(ListDataEvent evt) {
            fireContentsChanged(EditableComboBoxAdapter.this,
                                evt.getIndex0(),
                                evt.getIndex1());
        }
    }


    /**
     * Listens to changes of the selection holder and updates our internal
     * reference to it.
     */
    private final class SelectionHolderChangeHandler implements PropertyChangeListener {

        /**
         * The SelectionInList has changed the selection holder.
         * Update our internal reference to the new holder and
         * notify registered listeners about a selection change.
         *
         * @param evt the property change event to be handled
         */
        public void propertyChange(PropertyChangeEvent evt) {
            setSelectionHolder((ValueModel) evt.getNewValue());
            fireContentsChanged();
        }
    }


    // Helper Classes *********************************************************

    /**
     * Converts a List to ListModel by wrapping the underlying list.
     */
    private static final class ListModelAdapter<E> extends AbstractListModel {

        /**
		 * Erzeugt am 11.08.2009
		 */
		private static final long serialVersionUID = 5465467815111901389L;
		private final List<E> aList;

        ListModelAdapter(List<E> list) {
            this.aList = list;
        }

        ListModelAdapter(E[] elements) {
            this(Arrays.asList(elements));
        }

       /**
        * Returns the length of the list.
        * @return the length of the list
        */
        public int getSize() { return aList.size(); }

        /**
         * Returns the value at the specified index.
         * @param index the requested index
         * @return the value at <code>index</code>
         */
        public E getElementAt(int index) { return aList.get(index); }

    }


}
