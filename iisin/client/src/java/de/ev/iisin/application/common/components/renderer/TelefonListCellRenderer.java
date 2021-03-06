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

package de.ev.iisin.application.common.components.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.ev.iisin.common.stammdaten.adresse.domain.Telefon;
import de.ev.iisin.common.stammdaten.adresse.domain.TelefonTyp;

/**
 * @author Kemal D�nmez
 * 
 */
public class TelefonListCellRenderer extends DefaultListCellRenderer {

	/**
	 * Erzeugt am 31.05.2009
	 */
	private static final long serialVersionUID = -8386654699306307571L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		Telefon telefon = (Telefon) value;
		if (telefon == null) {
			setText("");
			return component;
		}
		TelefonTyp ttyp = telefon.getTyp();
		// TelefonTypen ttyp = telefon.getTyp();
		String typ = ttyp == null ? " " : ttyp.getBezeichnung();
		StringBuilder builder = new StringBuilder(typ == null ? "" : typ);
		builder.append(" : ");
		builder.append(telefon.getNummer());
		setText(builder.toString());
		return component;
	}

}
