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

import de.ev.iisin.application.model.MainModel;

/**
 * @author Kemal Dönmez
 *
 */
public class MainController {

    /**
     * Refers to the module that provides all high-level models.
     * Used to modify the project and access the domain object tree.
     * 
     * @see #getMainModule()
     */
    private final MainModel mainModule;

    
    // Instance Creation ******************************************************
    
    /**
     * Constructs the <code>MainController</code> for the given
     * main module. Many methods require that the default parent frame
     * is set once it is available. 
     * 
     * @param mainModule   provides bound properties and high-level models
     * 
     * @see #setDefaultParentFrame(Frame) 
     */
    public MainController(MainModel mainModule) {
        this.mainModule = mainModule;
    }
    
    
    

    // Action Behavior ******************************************************

    /**
     * Start the migration.
     */
//    void startMigration() {
//        new ReLaMigration();       
//    }
//    
//    void startHeaderFilterer(){
//        new LaStreckenHeaderFilterer(false);
//    }
//
//    /**
//     * Start the import.
//     */
//  void startImport(){
//        new ImportInfrastrukturBuilder();
//    }
//    

    // Accessing Collaborators **********************************************

    private MainModel getMainModule() {
        return mainModule;
    }



}
