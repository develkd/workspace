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

package de.ev.iisin.application.model;

import com.jgoodies.binding.beans.Model;

/**
 * @author Kemal Dönmez
 *
 */
public abstract class AbstractMainModel extends Model {
	
	public final static String PROPERTY_CARDNAME = "cardName";
	public static final String CARDNAME_EMPTY = "empty";

	private String cardName;
	
	
	/**
	 * Returns the name of the current card in the main page. If this card name
	 * changes the main page will switch cards to display the appropriate card.
	 * Other views may observe this property to perform other view related
	 * actions.
	 * 
	 * @return the name of the current card in the main page.
	 * 
	 * @see #setCardName(String)
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * Sets a new card name for the main page.
	 * 
	 * @param newMainCard
	 *            the name of the card to set
	 * 
	 * @see #getMainCard()
	 */
	public void setMainCard(String cardName) {
		String oldCardName = getCardName();
		this.cardName= cardName;
		firePropertyChange(PROPERTY_CARDNAME, oldCardName,cardName);
	}
}
