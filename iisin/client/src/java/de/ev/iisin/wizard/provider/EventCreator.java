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

package de.ev.iisin.wizard.provider;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.AbstractValueModel;

import de.ev.iisin.common.util.crypt.Password;

/**
 * @author Kemal Dönmez
 * 
 */
public class EventCreator {

	/**
	 * Erzeugt am 30.01.2009
	 */
	private static final long serialVersionUID = 6669674293264621287L;

	private PasswortChangedHandler handler;
	private PresentationModel<Object> model;
	private String property;

	private EventCreator() {

	}

	protected static AbstractValueModel createPasswordHandler(String property,
			PresentationModel<Object> model) {

		return new EventCreator().createPassword(property, model);
	}

	private AbstractValueModel createPassword(String property,
			PresentationModel<Object> model) {
		AbstractValueModel valueModel = model.getModel(property);
		handler = new PasswortChangedHandler(valueModel);
		this.model = model;
		this.property = property;
		model.addBeanPropertyChangeListener(property, handler);
		return valueModel;
	}

	private class PasswortChangedHandler implements PropertyChangeListener {
		private AbstractValueModel valueModel;

		protected PasswortChangedHandler(AbstractValueModel valueModel) {
			this.valueModel = valueModel;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String value = (String) evt.getNewValue();
			if (value != null) {
				model.removeBeanPropertyChangeListener(property, handler);
				valueModel.setValue(Password.encryptMD5(value));
				model.addBeanPropertyChangeListener(property, handler);
			}
		}

	}
}
