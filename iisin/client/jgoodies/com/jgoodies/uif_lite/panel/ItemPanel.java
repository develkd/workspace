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

package com.jgoodies.uif_lite.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * A light version of the JGoodies UIF class <code>ItemPanel</code>.
 * A panel that stacks action components generated from
 * <code>Action</code> instances as decorated components.
 *
 * @author Karsten Lentzsch
 */
public final class ItemPanel extends JPanel {
    
    // Color Costants *********************************************************
    
    /**
	 * Erzeugt am 03.01.2009
	 */
	private static final long serialVersionUID = -4430300636603011963L;
	private static final Color    DARK_BLUE   = new Color(  0,  51, 153);
    private static final Color    PALE_BLUE   = new Color(122, 187, 255);
//    private static final Color    MEDIUM_BLUE = new Color( 46,  82, 175);
//    private static final Color    BLUE        = new Color( 99, 117, 214);
//    private static final Color    LIGHT_BLUE  = new Color(214, 232, 242);
    
    // Instance Fields ********************************************************
    
	private final Icon	defaultIcon;
	private final boolean forceDefaultIcon;
	
	
	/**
	 * Constructs an <code>ItemPanel</code>.
	 */
	public ItemPanel() {
	    this(null, false, Color.black);
	}
	

    /**
	 * Constructs an <code>ActionPanel</code> using the specified default icon
	 * and hint, whether we will always force to use the default icon instead
	 * of the individual icons as provided by the actions.
	 */
	public ItemPanel(Icon defaultIcon, boolean forceDefaultIcon, 
                     Color textForeground) {
		super(new GridBagLayout());
		this.forceDefaultIcon = forceDefaultIcon;
        //this.textForeground = textForeground;
		setOpaque(false);
        this.defaultIcon = defaultIcon;
	}
    
    
	/**
	 * Creates an action component and adds it to the panel.
     * 
     * @param action  the action that will be used to create the component
     * @return the action component
	 */
	private JComponent add(Action action) {
		JComponent actionComponent = createActionComponent(action);
		addAction(actionComponent);
		return actionComponent;
	}
    
    
    /**
     * Adds the actions from the given list to the panel.
     * 
     * @param actions	a list of actions to add to the panel
     */
	@SuppressWarnings("all")
    public void addAll(List actions) {
        for (Iterator i = actions.iterator(); i.hasNext();) {
            Action action = (Action) i.next();
            add(action);
        }
    }
	
	
    /**
     * Adds the actions from the given list to the panel.
     * 
     * @param actions   an array of actions to add to the panel
     */
    public void addAll(Action[] actions) {
        addAll(Arrays.asList(actions));
    }
    
    
	/**
	 * Adds an action component to the panel.
	 */
	private void addAction(JComponent actionComponent) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill		= GridBagConstraints.BOTH;
		gbc.anchor		= GridBagConstraints.NORTHWEST;
		gbc.gridwidth	= GridBagConstraints.REMAINDER;
		gbc.gridheight 	= 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(actionComponent, gbc);
	}
	
	
	/**
	 * Adds a separator component to the panel.
	 */
	public void addStrut(int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(height, 0, 0, 0);
		add(Box.createVerticalStrut(height), gbc);
	}
	
	
	/**
	 * Adds a separator component to the panel.
	 */
	public void addSeparator() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(7, 10, 7, 10);
		JComponent c = new JSeparator(); // UIF Extras uses GradientSeparator
		add(c, gbc);
	}
	
	
    /**
     * Creates and returns an action component.<p>
     * 
     * Code has been copied from the outdated JGoodies Kiosk demo.
     */
    private JComponent createActionComponent(Action action) {

        final JButton button = new JButton(action);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(getIconFor(action));
        button.setToolTipText(null);
        if (forceDefaultIcon) {
            button.setRolloverIcon(null);
        }
        button.setHorizontalAlignment(SwingConstants.LEFT);
        final Border myBorder =
            BorderFactory.createCompoundBorder(
                new LineBorder(DARK_BLUE),
                new EmptyBorder(2, 4, 2, 4));
        button.setBorder(myBorder);
        //button.setText(name);

        button.setBackground(PALE_BLUE);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event) {
                button.setContentAreaFilled(true);
                button.setBorderPainted(true);
            }
            public void mouseExited(MouseEvent event) {
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
            }
        });
        return button;
    }

	/**
	 * Looks up the icon from the given action and returns it, 
     * or the default icon, if the action has no individual icon set.
	 */
	private Icon getIconFor(Action action) {
		Icon icon = (Icon) action.getValue(Action.SMALL_ICON);
		return forceDefaultIcon || null == icon ? defaultIcon : icon;
	}
	
}