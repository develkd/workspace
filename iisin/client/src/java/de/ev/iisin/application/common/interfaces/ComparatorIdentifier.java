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

package de.ev.iisin.application.common.interfaces;

/**
 * F�r JTableHeader, die eine Sortierung durch einen Headerklicke erm�glichen wollen.
 * 
 * @author doenmez
 * @version $Revision: 1.2 $
 */
public interface ComparatorIdentifier {
    
    /**
     * Gibt an, ob in steigender oder fallender Richtung sortiert wird.
     * 
     * @return true, falls steigend, sonst fallend
     */
    
    public boolean isASC();
    
    /**
     * Gibt den Header an, nach dem sortiert werden soll
     * 
     * @return die Headerposition
     */
    public int getCellPos();
    
    /**
     * �ndert die Sortierrichtung
     */
    public void toggleSortdirection();
    
    /**
     * Stellt die Eingenschaft der Sortierung der Spalte ein.
     * 
     * @param enabled. true, wenn Sortierung erm�glicht werden soll, ansonsten false
     */
    public void enableSorting(boolean enabled);
    
}
