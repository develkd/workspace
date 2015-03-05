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

package de.ev.iisin.application.controller;

/**
 * @author Kemal Dönmez
 *
 */
public class MainManager {
	
	
	
    public static boolean openPasswordDialog() {
        String fehlertext = "Passwort konnte nicht geändert werden\n";
//
//        LaPasswordModel model = new LaPasswordModel(MainManager
//                .getCurrentUser(), true);
//        AbstractDialog dialog = new LaPasswordDialog(Application
//                .getDefaultParentFrame(), model);
//        dialog.open();
//
//        if (!dialog.hasBeenCanceled()) {
//            try {
//                logonFacade.updatePasswort(model.getPasswordForVerifying(),
//                        model.getCreatedPassword());
//            } catch (SystemException e) {
//                ReLaClientExceptionHandler.handle(e.getError(), fehlertext
//                        + e.getMessage(), e, ClientErrorConstants.WARN);
//            } catch (ServerException e) {
//                if (e.getError() == Error.AUTHENTICATION_FAILED)
//                    fehlertext += "Die Authentifizierung für das Ändern konnte nicht bestätigen werden!";
//
//                if (e.getError() == Error.PASSWORD_EXISTS)
//                    fehlertext += "Passwort ist bereits in der Historie enthalten!";
//
//                ReLaClientExceptionHandler.handle(e.getError(), fehlertext, e,
//                        ClientErrorConstants.WARN);
//            } catch (RemoteException e) {
//                ReLaClientExceptionHandler.handle(Error.REMOTE_ERROR,
//                        fehlertext + e.getMessage(), e,
//                        ClientErrorConstants.WARN);
//            } finally {
//                model.clear();
//            }
//            return false;
//        }

        return true;

    }

}
