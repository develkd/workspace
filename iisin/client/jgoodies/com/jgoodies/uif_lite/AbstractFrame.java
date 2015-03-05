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

package com.jgoodies.uif_lite;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.jgoodies.uif_lite.application.ResourceUtils;

/**
 * A light version of the JGoodies <code>AbstractFrame</code> class, which:
 * <ul>
 * <li>doesn't store and restore its state, for example, the window bounds
 * <li>has no minimum width
 * <li>has no support for aesthetic default sizes
 * <li>has no support for different logo icon sizes
 * </ul>
 * 
 * @author Karsten Lentzsch
 */
public abstract class AbstractFrame extends JFrame {

	private static final String APPLICATION_ICON_ID = "application.icon";

	// Instance Creation ******************************************************

	protected AbstractFrame(String label) {
		super(label);
	}

	// Building ***************************************************************

	/**
	 * Builds the frame's content pane, packs it, locates it on the screen, sets
	 * the icon, restores the saved state, and registers listeners. It then is
	 * ready to be opened.
	 * <p>
	 * 
	 * Subclasses should rarely override this method.
	 */
	public void build() {
		JComponent content = buildContentPane();
		setContentPane(content);
		pack();
		locateOnScreen();
		configureWindowIcon();
		configureCloseOperation();
	}

	// Abstract Behavior ****************************************************

	/**
	 * Builds and returns this frame's content pane.
	 * 
	 * @return the built content pane
	 */
	abstract protected JComponent buildContentPane();

	/**
	 * Configures the behavior that will happen when the user initiates a
	 * "close" on this frame.
	 */
	abstract protected void configureCloseOperation();

	// Default Behavior *****************************************************

	/**
	 * Locates the frame on the screen center; subclasses may override.
	 */
	protected void locateOnScreen() {
		Dimension paneSize = getSize();
		Dimension screenSize = getToolkit().getScreenSize();
		setLocation((screenSize.width - paneSize.width) / 2,
				(screenSize.height - paneSize.height) / 2);
	}

	/**
	 * Sets this frame's icon, which is fetched via the
	 * <code>ResourceUtils</code>.
	 */
	protected void configureWindowIcon() {
		ImageIcon logo = ResourceUtils.getImageIcon(APPLICATION_ICON_ID,
				AbstractFrame.class);
		if (logo != null)
			setIconImage(logo.getImage());
	}

}