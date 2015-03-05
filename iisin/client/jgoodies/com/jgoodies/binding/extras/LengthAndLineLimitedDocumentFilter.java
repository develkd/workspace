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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * Ensures a length and line limit before inserting text into a PlainDocument.<p>
 * 
 * I recommend to not use this class, because it lacks audio-visual feedback
 * when rejecting inserts.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.1 $
 * 
 * @see PlainDocument
 */
public final class LengthAndLineLimitedDocumentFilter extends DocumentFilter {
    
    private final int lengthLimit;
    
    private final int lineLimit;
    
    
    // Instance Creation *****************************************************
    
    public LengthAndLineLimitedDocumentFilter(
            int lengthLimit, 
            int lineLimit) {
        this.lengthLimit = lengthLimit;
        this.lineLimit = lineLimit;
    }
    
    
    // Overriding Superclass Behavior ****************************************

    /**
     * Invoked prior to insertion of text into the specified Document. 
     * Inserts the given string in upper case.
     *
     * @param fb        FilterBypass that can be used to mutate Document
     * @param offset    the offset into the document to insert the content >= 0.
     *    All positions that track change at or after the given location 
     *    will move.  
     * @param string    the string to insert
     * @param attr      the attributes to associate with the inserted content. 
     *     This may be null if there are no attributes.
     * @throws BadLocationException if the given insert position is not a
     *   valid position within the document
     */
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        PlainDocument document = (PlainDocument) fb.getDocument();
        // Reject if the document has more than 'lengthLimit' characters.
        if (document.getLength() >= lengthLimit)
            return;
        // Reject if the document has more than 'lineLimit' lines.
        if (document.getDefaultRootElement().getElementCount() >= lineLimit)
            return;
        super.insertString(fb, offset, string, attr);
    }

}
