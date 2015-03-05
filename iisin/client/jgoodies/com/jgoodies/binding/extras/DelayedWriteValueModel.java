/*
 * Copyright (c) 2002-2005 JGoodies Karsten Lentzsch. All Rights Reserved.
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
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
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

package com.jgoodies.binding.extras;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Timer;

import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.ValueModel;


/**
 * A ValueModel that deferres value changes for a specified delay.
 * Useful to coalesce frequent changes. For example if a heavy computation
 * shall be performed only for a "stable" selection after a series of
 * quick selection changes.<p>
 * 
 * Wraps a given subject ValueModel and returns the subject value or 
 * the value to be set as this model's value. Observes subject value changes 
 * and forwards them to listeners of this model. If a value is set to this 
 * model, a Swing Timer is used to delay the value commit to the subject. 
 * A previously started timer - if any - will be stopped before.<p>
 * 
 * TODO: Consider adding a feature to choose the direction of the 
 * value change to defer. Currently this class defers the write access,
 * i. e. a call to <code>#setValue(Object)</code> will commit the value
 * from this model to the subject after a delay. The other direction, 
 * updates from the subject to this model, could be deferred too.
 * If the subject reports a change, this change could be re-reported
 * after a given delay. I'm not sure if the other direction should be
 * provided by this class, or a separate class. In the latter case, 
 * the class name could indicate the purpose which may reduce potential
 * confusions when using a model that can defer the one or the other direction.<p>
 * 
 * TODO: Describe how and when listeners get notified about the delayed change.<p>
 * 
 * TODO: Write about the recommended delay time - above the double-click time
 * and somewhere below a second, e.g. 100ms to 200ms.<p>
 * 
 * TODO: Write about a slightly different commit handling. The current 
 * implementation defers the commit until the value is stable for the 
 * specified delay; it's a DelayUntilStableForXXXmsValueModel. Another
 * feature is to delay for a specified time but ensure that some commits
 * and change notifications happen. The latter is a CoalescingWriteValueModel.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.1 $
 */
public final class DelayedWriteValueModel extends AbstractValueModel {
    
    /**
	 * Erzeugt am 22.02.2009
	 */
	private static final long serialVersionUID = 5477868535342748954L;

	/**
     * Refers to the underlying subject ValueModel.
     */
    private final ValueModel subject;
    
    /**
     * The Timer used to perform the delayed commit.
     */
    private final Timer timer;
    
    /**
     * Holds the most recent pending value. It is updated 
     * evertime <code>#setValue</code> is invoked.
     */
    private Object pendingValue = new Integer(1967);
    
    
    // Instance Creation ******************************************************
    
    /**
     * Constructs a DelayedWriteValueModel for the given subject ValueModel
     * and the specified Timer delay in milliseconds.
     * 
     * @param subject   the underlying (or wrapped) ValueModel 
     * @parem delay     the milliseconds to wait before a change
     *     shall be committed
     */
    public DelayedWriteValueModel(ValueModel subject, int delay) {
        this.subject = subject;
        this.timer = new Timer(delay, new ValueCommitListener());
        timer.setRepeats(false);
        subject.addValueChangeListener(new SubjectValueChangeHandler());
    }
    
    
    // ValueModel Implementation ******************************************
    
    /**
     * Returns the subject's value or in case of a pending commit,
     * the pending new value.
     * 
     * @return the subject's current or future value.
     */
    public Object getValue() {
        return hasPendingChange()
            ? pendingValue
            : subject.getValue();
    }
    
    
    /**
     * Sets the given new value after this model's delay.
     * Does nothing if the new value and the latest pending value are the same.
     * 
     * @param newValue   the value to set
     */
    public void setValue(Object newValue) {
        if (newValue == pendingValue)
            return;
        if (hasPendingChange()) {
            timer.stop();
        }
        pendingValue = newValue;
        timer.start();
    }
    
    
    // Misc *******************************************************************
    
    private boolean hasPendingChange() {
        return timer.isRunning();
    }
    
    
    // Event Handling *****************************************************
    
    
    private class ValueCommitListener implements ActionListener {
        
        /**  
         * An ActionEvent has been fired by the Timer after its delay.
         * Commits the pending value and stops the timer.
         */
        public void actionPerformed(ActionEvent e) {
            subject.setValue(pendingValue);
            timer.stop();
        }
    }
    
    
    // Forwards value changes in the subject.
    private class SubjectValueChangeHandler implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            fireValueChange(evt.getOldValue(), evt.getNewValue(), true);
        }
    }
}