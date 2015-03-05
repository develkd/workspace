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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.uif_lite.application.ResourceUtils;

import de.ev.iisin.application.Iisin;
import de.ev.iisin.application.desktop.AbstractView;
import de.ev.iisin.application.login.LoginModel;

/**
 * @author Kemal Dönmez
 *
 */
public abstract class AbstractLoginPageBuilder extends AbstractView {
	protected static final int HEADER_HEIGHT = 60;

	protected static final int SEPARATOR_HEIGHT = LookUtils.IS_LOW_RESOLUTION ? 2
			: 3;

	protected static final Border DEFAULT_FOOTER_BORDER = new EmptyBorder(12,
			20, 12, 20);

	private static final Dimension PREFERRED_SIZE = LookUtils.IS_LOW_RESOLUTION ? new Dimension(
			750, 550)
			: new Dimension(800, 600);


    /**
     * Provides the login and exit Actions as well as bound properties 
     * for the username, password, the login/logout state, and a message.
     */
    protected final LoginModel loginModule;
    
    protected JComponent logo;
    
    
    // Instance Creation ****************************************************

    /**
     * Constructs an <code>AbstractLoginPageBuilder</code> 
     * using the given LoginModel.
     * 
     * @param loginModule  provides the models and actions for login/logout
     */
    protected AbstractLoginPageBuilder(LoginModel loginModule) {
        this.loginModule = loginModule;
    }


    // Building *************************************************************

    /**
     * Initializes the components; does nothing.
     * Subclasses will likely override this method.
     */
    protected void initComponents() {
        logo = buildDefaultLogo();
    }
    
	protected JComponent buildPanel() {
		initComponents();

		FormLayout layout = new FormLayout("fill:pref:grow",
				"fill:80px, fill:1px, fill:pref:grow, fill:1px, fill:58px");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.getPanel().setBackground(new Color(245, 245, 245));
		builder.getPanel().setPreferredSize(PREFERRED_SIZE);

		CellConstraints cc = new CellConstraints();
		builder.add(buildHeader(), cc.xy(1, 1));
		builder.add(new JSeparator(), cc.xy(1, 2));
		builder.add(buildCenter(), cc.xy(1, 3));
		builder.add(new JSeparator(), cc.xy(1, 4));
		builder.add(buildFooter(), cc.xy(1, 5));
		return builder.getPanel();
	}
	
	
	protected JPanel buildHeader() {
		Icon isiIcon = ResourceUtils
				.readImageIcon("resources/images/BismillahArabic2.png");
		JLabel isiLabel = new JLabel(isiIcon);

		FormLayout layout = new FormLayout("fill:pref:grow, 17px", "fill:p");
		JPanel panel = new JPanel(layout);
		panel.setOpaque(false);
		panel.add(isiLabel, new CellConstraints(1, 1));
		return panel;
	}
	/**
	 * Builds and returns the center area.
	 * 
	 * @return the center area
	 */
	abstract protected JComponent buildCenter();

	/**
	 * Builds and returns the footer panel.
	 */
	protected Component buildFooter() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setBorder(DEFAULT_FOOTER_BORDER);

		panel.add(createCopyrightLabel(), BorderLayout.EAST);
		return panel;
	}

	// Convenience Ccomponents *************************************************

	/**
	 * Builds and returns a vertical separator.
	 */
	protected Component buildVerticalSeparator() {
		return new JSeparator(JSeparator.VERTICAL);
	}

	/**
	 * Creates and returns a bold label in dark gray for the copyright.
	 * 
	 * @return a bold label in dark gray for the copyright
	 */
	protected static JLabel createCopyrightLabel() {
		return new JLabel(Iisin.COPYRIGHT_TEXT);
	}


    private static JComponent buildDefaultLogo() {
        String text = "Bearbeitung";
        JLabel label = new JLabel(text); //, Font.BOLD, 16, true);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }



}
