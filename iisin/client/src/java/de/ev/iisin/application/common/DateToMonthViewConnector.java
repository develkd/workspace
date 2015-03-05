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

package de.ev.iisin.application.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import com.jgoodies.binding.beans.PropertyAdapter;
import com.jgoodies.binding.value.ValueModel;

import de.ev.iisin.application.common.components.calendar.DateSpan;
import de.ev.iisin.application.common.components.calendar.JXMonthView;

/**
 * @author Kemal Dönmez
 *
 */
public class DateToMonthViewConnector {
	
	
    private final ValueModel valueModel;
    private final JXMonthView monthView;


    // Instance Creation ******************************************************

    private DateToMonthViewConnector(ValueModel valueModel, JXMonthView monthView) {
        this.valueModel = valueModel;
        this.monthView = monthView;
        initEventHandling();
    }


    @SuppressWarnings("unchecked")
    public static final DateToMonthViewConnector connect(
            Object bean, String datePropertyName,
            JXMonthView monthView) {
        return connect(new PropertyAdapter(bean, datePropertyName), monthView);
    }


    public static final DateToMonthViewConnector connect(
            ValueModel valueModel,
            JXMonthView monthView) {
        return new DateToMonthViewConnector(valueModel, monthView);
    }


    // API ********************************************************************

    public void updateModel() {
        DateSpan span = monthView.getSelectedDateSpan();
        Date newDate = span != null
            ? span.getStartAsDate()
            : new Date(System.currentTimeMillis());
        Date oldDate = (Date) valueModel.getValue();
        if (!newDate.equals(oldDate)) {
            valueModel.setValue(newDate);
        }
    }


    public void updateMonthView() {
        Date date = (Date) valueModel.getValue();
        monthView.setSelectedDateSpan(new DateSpan(date, date));
    }


    // Event Handling *********************************************************

    private void initEventHandling() {
        valueModel.addValueChangeListener(
                new DatePropertyChangeHandler());
        monthView.addPropertyChangeListener(
        		JXMonthView.PROPERTYNAME_SELECTED_DATE_SPAN,
                new SelectedDateChangeHandler());
    }


    /**
     * Listens to changes of the date bean property and sets the new date
     * as date span in the month view.
     */
    final class DatePropertyChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateMonthView();
        }

    }


    /**
     * Listens to changes of the month view's selected date span property
     * and sets its start date in the date ValueModel.
     */
    final class SelectedDateChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateModel();
        }

    }


}
