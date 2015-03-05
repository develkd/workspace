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

package de.ev.iisin.application.login.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.ev.iisin.application.login.LoginModel;

/**
 * @author Kemal Dönmez
 *
 */
public class LoginProcessPageBuilder extends AbstractLoginPageBuilder {

    private JLabel descriptionLabel;
    private JLabel messageLabel;
    
    
    // Instance Creation ****************************************************

    /**
     * Constructs a <code>LoginProcessPageBuilder</code> for the given LoginModel.
     * 
     * @param loginModule  provides the models and actions for login/logout
     */
    public LoginProcessPageBuilder(LoginModel loginModule) {
        super(loginModule);
    }


    // Component Initialization *********************************************

    /**
     * Creates, configures and binds the components.
     */
    protected void initComponents() {
        super.initComponents();
        descriptionLabel = new JLabel(
                "Melde " + loginModule.getUsername() + " an.");
        descriptionLabel.setForeground(Color.DARK_GRAY);
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(Font.BOLD, 12));
            
        messageLabel  = new JLabel("", JLabel.LEFT);
        messageLabel.setForeground(Color.DARK_GRAY);
        messageLabel.setFont(descriptionLabel.getFont());
        
        initEventHandling();
    }
    

    private void initEventHandling() {
        loginModule.addPropertyChangeListener(
                LoginModel.PROPERTYNAME_USERNAME,
                new UsernameChangeHandler());
        PropertyConnector.connect(
                messageLabel, "text", 
                loginModule, LoginModel.PROPERTYNAME_LOGIN_MESSAGE);
    }


    // Building *************************************************************

    /**
     * Builds and returns the center of the login process page: a welcome label
     * on the left hand side and message labels on the right hand side, 
     * both separated by a vertical divider.
     * 
     * @return the center of the login process page
     */
    protected JComponent buildCenter() {
        FormLayout layout = new FormLayout(
                "r:d:g, 10dlu, 1, 10dlu, l:d:grow",
                "default:grow");
        layout.setColumnGroups(new int[][]{{1, 5}});
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.getPanel().setOpaque(false);
        
        CellConstraints cc = new CellConstraints();
        builder.add(logo,                     cc.xy(1, 1));
        builder.add(buildVerticalSeparator(), cc.xy(3, 1, "c, f"));
        builder.add(buildRightPanel(),        cc.xy(5, 1));
        return builder.getPanel();
    }
    
    /**
     * Builds and returns the right-hand side of the login process page.
     * 
     * @return the right-hand side of the login process page
     */
    private Component buildRightPanel() {
        FormLayout layout = new FormLayout(
                "left:pref, p:grow",
                "bottom:pref:grow, 4dlu, top:max(12dlu;pref):grow");
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.getPanel().setOpaque(false);

        CellConstraints cc = new CellConstraints();
        builder.add(descriptionLabel, cc.xy(1, 1));
        builder.add(messageLabel,     cc.xy(1, 3));
        
        return builder.getPanel();
    }
    
    
    // Event Handling *********************************************************
    
    private class UsernameChangeHandler implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            String username = (String) evt.getNewValue();
            descriptionLabel.setText("Melde " + username + " an.");
        }
        
    }


}
