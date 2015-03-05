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

package de.ev.iisin.view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import com.jgoodies.uif_lite.AbstractFrame;
import com.jgoodies.uif_lite.panel.CardPanel;

import de.ev.iisin.application.login.LoginModel;
import de.ev.iisin.application.login.LoginProcessor;
import de.ev.iisin.application.login.view.LoginPageBuilder;
import de.ev.iisin.application.login.view.LoginProcessPageBuilder;
import de.ev.iisin.application.model.MainModel;

/**
 * @author Kemal Dönmez
 *
 */
public class MainFrame extends AbstractFrame {
	
	
	/**
	 * Created at 01.10.2008
	 */
	private static final long serialVersionUID = -4509884700548097825L;

	private static final Dimension MINIMUM_SIZE = 
        new Dimension(1024, 768);
    
    private static final String CARDNAME_LOGIN             = "login";
    private static final String CARDNAME_LOGIN_PROCESS     = "loginProcess";
    private static final String CARDNAME_LOGOUT_PROCESS    = "logoutProcess";
//  private static final String CARDNAME_LOG_FAILED        = "logFailed";
    
    private static final String CARDNAME_PUPIL_MAIN        = "pupilMain";
    
 
    /**
     * Refers to the mainModel that provides all high-level models.
     * Used to build this frame's pages.
     * 
     * @see #buildContentPane()
     */
    private MainModel mainModel;
    
    /**
     * Holds the login and main pages in a panel that can switch cards.
     */
    private CardPanel pages;
    
    // Lazily Created Menu Bars ***********************************************
    
    /**
     * Holds the lazily create menu bar for the Erfassungsstelle.
     */
    private JMenuBar menuBar;
    
     
    
    // Built Markers for the Main Pages ***************************************
    
    /*
     * We do not hold the main pages, since they are contained in 
     * the CardPanel; we just store whether a panel has been built before.
     */
    private boolean mainPageBuild        = false;
    

    // Instance Creation ****************************************************

    /**
     * Constructs an instance of the ReLa's app's main frame.
     * 
     * @param mainModule   provides bound properties and high-level models
     */
    public MainFrame(MainModel mainModule) {
        super("I-ISIN e.V."); 
        this.mainModel = mainModule;
    }
    
    
    private void initEventHandling() {
        mainModel.getLoginModule().addPropertyChangeListener(
                LoginProcessor.PROPERTYNAME_STATE,
                new LoginStateChangeHandler());
   }

    private void release() {
   }
    
    
    /**
     * Locates the frame on the screen center; subclasses may override.
     */
    protected void locateOnScreen() {
        super.locateOnScreen();
       
        if (getToolkit().isFrameStateSupported(MAXIMIZED_BOTH))
            setExtendedState(MAXIMIZED_BOTH);
    }

    
    // Building *************************************************************

    public void build() {
        super.build();
        initEventHandling();
    }
    
    
    /**
     * Builds and returns this frame's content pane.
     */
    protected JComponent buildContentPane() {
        pages = new CardPanel();
        pages.add(CARDNAME_LOGIN, 
                new LoginPageBuilder(mainModel.getLoginModule()).getPanel());
        pages.add(CARDNAME_LOGIN_PROCESS,  
                new LoginProcessPageBuilder(mainModel.getLoginModule()).getPanel());
      
        return pages;
    }
    
    
    // Implementing Abstract Behavior ****************************************

    /**
     * Returns the frame's id; used to store and restore frame specific
     * state in the preferences.
     * 
     * @return the frame's window ID
     * @see com.jgoodies.uif.AbstractFrame#getWindowID()
     */
    public String getWindowID() {
        return "main";
    }

    
    /**
     * Returns the frame's minimum size. It is used by the WindowUtils
     * to resize the window if the user has shrinked the window below
     * this given size.
     * 
     * @return the frame's minimum size
     * @see com.jgoodies.uif.AbstractFrame#getWindowMinimumSize()
     */
  
    public Dimension getWindowMinimumSize() {
        return MINIMUM_SIZE;
    }


    protected void configureCloseOperation() {
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainModel.aboutToExitApplication();
            }
        });
    }


    // Lazily Building Menu Bars and Main Pages *******************************
    

//    private JMenuBar getMenuBar() {
//      if (menuBar == null) {
//    	  menuBar = new JMenuBar();
//    	  menuBar.add(new MainMenuBuilder(mainModel).build());
//		}
//		return menuBar;
//    }
   
    private void ensureMainPageBuild() {
        if (!mainPageBuild) {
            pages.add(CARDNAME_PUPIL_MAIN,  
                    new MainPageBuilder(mainModel).build());
            mainPageBuild = true;
        }
    }


    
    // Event Handling *********************************************************

    // Switches to the card associated with the login state and user role 
    private class LoginStateChangeHandler implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            int state = ((Integer) evt.getNewValue()).intValue();
            
            if (state == LoginModel.LOGGED_IN) {
                showMainPage();
                return;
            }
            setJMenuBar(null);
            if (state == LoginModel.LOGGED_OUT) {
                pages.showCard(CARDNAME_LOGIN);
            } else if (state == LoginModel.LOGGING_IN)
                pages.showCard(CARDNAME_LOGIN_PROCESS);
            else if (state == LoginModel.LOGGING_OUT){
                pages.showCard(CARDNAME_LOGOUT_PROCESS);
                release();
                setTitle("I-ISIN e.V. ");
            }
        }
        
        private void showMainPage() {
                 ensureMainPageBuild();
                pages.showCard(CARDNAME_PUPIL_MAIN);
              // setJMenuBar(getMenuBar());
//                setTitle(MainManager.getCurrentOrgeinheitAsString() 
//                        + MainManager.getCurrentGueltigkeitAsString()
//                        + "        "
//                        + MainManager.getCurrentLaHeftWoche());
     
    }
 
    
     
    // Benutzerart ************************************************************
    
    }   


}
