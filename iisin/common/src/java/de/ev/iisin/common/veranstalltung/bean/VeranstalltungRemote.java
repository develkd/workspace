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

package de.ev.iisin.common.veranstalltung.bean;

import java.util.List;

import javax.ejb.Remote;

import de.ev.iisin.common.rmi.RmiResponse;
import de.ev.iisin.common.veranstalltung.domain.Spalte;
import de.ev.iisin.common.veranstalltung.domain.Veranstalltung;
import de.ev.iisin.common.veranstalltung.domain.VeranstalltungsTyp;
import de.ev.iisin.common.veranstalltung.domain.Zeile;

/**
 * @author Kemal D�nmez
 *
 */
@Remote
public interface VeranstalltungRemote {
	   /**
     * REMOTE JNDI NAME
     */
    static final String JNDI_REMOTE_NAME = "VeranstalltungBean/remote";

    List<VeranstalltungsTyp> getAllVeranstalltungsTypen();
    List<Veranstalltung> getAllVeranstalltungen();
    List<Zeile> getAllZeilenFor(Veranstalltung veranstalltung);
    List<Spalte> getAllSpaltenFor(Zeile zeile);
    
    RmiResponse addVeranstalltungsTyp(VeranstalltungsTyp veranstalltungsTyp);
    RmiResponse updateVeranstalltungsTyp(VeranstalltungsTyp veranstalltungsTyp);
    void deleteVeranstalltungsTyp(VeranstalltungsTyp veranstalltungsTyp);

    RmiResponse addVeranstalltung(Veranstalltung veranstalltung);
    RmiResponse updateVeranstalltung(Veranstalltung veranstalltung);
    void deleteVeranstalltung(Veranstalltung veranstalltung);
    
    RmiResponse addZeile(Veranstalltung veranstalltung);
    RmiResponse updateZeile(Veranstalltung veranstalltung, Zeile zeile);
    void deleteZeile(Veranstalltung veranstalltung, Zeile zeile);
   
    RmiResponse addSpalte(Zeile zeile);
    RmiResponse updateSpalte(Zeile zeile);
    void deleteSpalte(Zeile zeile);
 
}
