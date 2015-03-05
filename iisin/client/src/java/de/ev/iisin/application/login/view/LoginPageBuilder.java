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
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.login.LoginModel;

/**
 * @author Kemal Dönmez
 *
 */
public class LoginPageBuilder extends AbstractLoginPageBuilder {
	private static final ResourceBundle RESOURCE = ResourceUtils
	.getResourceBundle(LoginPageBuilder.class);

    // Constant Names for Welcome Images **************************************
    
    private static final String IMAGE_PATH = "resources/images/kaaba/";
    private static final String[] IMAGE_NAMES = {
    	"kaaba1.gif",
    	"kaaba2.gif",
    	"kaaba3.gif",
    	"kaaba4.gif",
    	"kaaba5.gif",
    	"kaaba6.gif",
    	"kaaba7.gif",
    	"kaaba8.gif",
    	"kaaba9.gif",
    	"kaaba10.gif"
            };
    /**
     * Describes the probability for an image to be choosen for display.
     * The indices used in this array are associated with the indices
     * used in array <code>IMAGE_NAMES</code>.
     * The sum of these probabilities must be 1.
     */
    private static final double[] IMAGE_PROBABILITIES = 
    { 0.3, 0.1, 0.1, 0.05, 0.05,0.7, 0.1, 0.1, 0.05, 0.05};
    // UI Components for the Welcome Panel 
    
    private JLabel logoLabel;
    
    // UI Components for the Login Panel 
    
    private JTextField      usernameField;
    private JPasswordField  passwordField;
    private JButton         loginButton;
    
    // UI Components for the Footer 
    private JButton exitButton;
    
    
    // Instance Creation ****************************************************

    /**
     * Constructs a <code>LoginPageBuilder</code> using the given LoginModel.
     * 
     * @param loginModule  provides the models and actions for login/logout
     */
    public LoginPageBuilder(LoginModel loginModule) {
        super(loginModule);
    }


    // Component Initialization *********************************************

    /**
     * Creates, configures and binds the components.
     */
    @Override
    protected void initComponents() {
        super.initComponents();
        logoLabel    = new JLabel(getCurrentIcon());
        logoLabel.setBorder(new LineBorder(Color.BLACK));
       
        BeanAdapter<LoginModel> beanAdapter = new BeanAdapter<LoginModel>(loginModule, true);
        
        usernameField = BasicComponentFactory.createTextField(
                beanAdapter.getValueModel(LoginModel.PROPERTYNAME_USERNAME), true);

        passwordField = BasicComponentFactory.createPasswordField(
                beanAdapter.getValueModel(LoginModel.PROPERTYNAME_PASSWORD),false);
        
        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        passwordField.getInputMap(JComponent.WHEN_FOCUSED).put(enterStroke, "login");
        passwordField.  getActionMap().put("login", loginModule.getLoginAction());

        loginButton = new JButton(loginModule.getLoginAction());
        loginButton.putClientProperty("jgoodies.isNarrow", Boolean.TRUE);
        loginButton.setDefaultCapable(true);
        
        exitButton = new JButton(loginModule.getExitAction());
   }
    
    private static Icon getCurrentIcon() {
        String path = IMAGE_PATH + IMAGE_NAMES[chooseImageIndex()];
        return ResourceUtils.readImageIcon(path);
    }
    
    private static int chooseImageIndex() {
        double randomDouble = new Random().nextDouble();
        double addedProbabilities = 0;
        for (int i = 0; i < IMAGE_PROBABILITIES.length; i++) {
            addedProbabilities += IMAGE_PROBABILITIES[i];
            if (randomDouble < addedProbabilities)
                return i;
        }
        return 0;
    }


    // Building *************************************************************

    /**
     * Builds the center of this login page, two subpanels on the
     * left hand side and right hand side, separated by a vertical 
     * divider.
     */
    protected JComponent buildCenter() {
        FormLayout layout = new FormLayout(
                "center:pref:grow",
                "0:grow, pref, 24dlu, pref, 0:grow");
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setBorder(Borders.DLU14_BORDER);
        builder.getPanel().setOpaque(false);
        
        CellConstraints cc = new CellConstraints();
        builder.add(logoLabel,         cc.xy(1, 2));
        builder.add(buildLoginPanel(), cc.xy(1, 4));
        return builder.getPanel();
    }
    
    
    
    /**
     * Builds and returns the login panel.
     */
    private JComponent buildLoginPanel() {
        FormLayout layout = new FormLayout(
                "left:pref, 6dlu, 100dlu, 4dlu, pref, 0:grow", 
                "0:grow, d, 2dlu, d, 0:grow");
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.getPanel().setOpaque(false);
        CellConstraints cc = new CellConstraints();

        builder.addLabel(RESOURCE.getString("login.name"),     cc.xy(1, 2)); // TODO: I18n
        builder.add(usernameField,   cc.xy(3, 2));
        builder.addLabel(RESOURCE.getString("login.pw"), cc.xy(1, 4)); // TODO: I18n
        builder.add(passwordField,   cc.xy(3, 4));
        builder.add(loginButton,     cc.xy(5, 4));
        return builder.getPanel();
    }    
    
    
    /**
     * Builds and returns the footer panel.
     */
    protected Component buildFooter() {
        FormLayout layout = new FormLayout(
                "left:p, 20dlu:grow, right:p", 
                "p:grow, d, p:grow");

        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.setBorder(DEFAULT_FOOTER_BORDER);
        
        CellConstraints cc = new CellConstraints();
        panel.add(exitButton,             cc.xy(1, 2));
        panel.add(createCopyrightLabel(), cc.xy(3, 2));

        return panel;
    }

}
