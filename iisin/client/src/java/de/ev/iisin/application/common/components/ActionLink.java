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

package de.ev.iisin.application.common.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ActionManager;

/**
 * @author Kemal Dönmez
 * 
 */
public class ActionLink extends JPanel implements ActionListener {

	/**
	 * Created at 25.11.2008
	 */
	private static final long serialVersionUID = 6843417369975158246L;

	private ActionLabel actionLabel;

	private ActionPanel actionPanel;

	private String actionCommand;

	private Action action;

	public ActionLink(String name, String actionCommand) {
		this(name, actionCommand, null);
	}

	public ActionLink(String name, String actionCommand, Icon icon) {
		if (icon == null) {
			actionLabel = new ActionLabel(name);
		} else {
			actionLabel = new ActionLabel(name, icon);
		}
		actionPanel = new ActionPanel();
		this.actionCommand = actionCommand;
		addComponents();
		initEvents();
	}

	private void initEvents() {
		addMouseListener(new MouseMotionHandler());
	}

	public void actionPerformed(ActionEvent e) {
		ActionManager.get(e.getActionCommand()).actionPerformed(e);
	}

	private void addComponents() {

		FormLayout layout = new FormLayout("p", "p,1");
		setLayout(layout);
		CellConstraints cc = new CellConstraints();
		add(actionLabel, cc.xy(1, 1));
		add(actionPanel, cc.xy(1, 2));
	}

	private void showMouseDown() {
		actionLabel.showAsSelected(false);
		actionPanel.setVisible(false);
	}

	private void showMouseOver() {
		actionLabel.showAsSelected(true);
		actionPanel.setVisible(true);
	}

	private class MouseMotionHandler extends MouseAdapter {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			showMouseOver();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			showMouseDown();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (actionPanel.isVisible()) {
				if (action != null) {
					action.actionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, action.toString()));
				} else {

					actionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, actionCommand));
				}
			}
		}

	}

	private class ActionLabel extends JLabel {
		/**
		 * Created at 25.11.2008
		 */
		private static final long serialVersionUID = 3769794997907918697L;

		private Color orgForeground;
		private Color selectedForeground;

		ActionLabel(String actionName) {
			super(actionName);
		}

		ActionLabel(String actionName, Icon icon) {
			super(actionName, icon, JLabel.LEFT);
		}

		public void updateUI() {
			super.updateUI();
			orgForeground = getTitleColor();
			if (orgForeground != null) {
				setForeground(orgForeground);
			}
			// setFont(getTitleFont());
		}

		public void showAsSelected(boolean isSelected) {
			setForeground(isSelected ? selectedForeground : orgForeground);
		}

		private Color getTitleColor() {
			selectedForeground = UIManager
					.getColor("InternalFrame.activeTitleBackground");
			return UIManager.getColor("Label.foreground");
		}

		/**
		 * Looks up and returns the font used for title labels. Since Mac Aqua
		 * uses an inappropriate titled border font, we use a bold label font
		 * instead. Actually if the title is used in a titled separator, the
		 * bold weight is questionable. It seems that most native Aqua tools use
		 * a plain label in titled separators.
		 * 
		 * @return the font used for title labels
		 */
		// private Font getTitleFont() {
		// return FormUtils.isLafAqua() ? UIManager.getFont("Label.font")
		// .deriveFont(Font.BOLD) : UIManager
		// .getFont("TitledBorder.font");
		// }
		public boolean imageUpdate(Image img, int infoflags, int x, int y,
				int w, int h) {
			return super.imageUpdate(img, infoflags, x, y, w, h);
		}

	}

	private class ActionPanel extends JPanel {

		/**
		 * Created at 25.11.2008
		 */
		private static final long serialVersionUID = -1589866006288184277L;

		ActionPanel() {
			setVisible(false);
		}

		public void paint(Graphics g) {
			super.paint(g);
			g.drawLine(0, 0, getWidth(), 0);
		}

		public void updateUI() {
			super.updateUI();
			Color foreground = getTitleColor();
			if (foreground != null) {
				setForeground(foreground);
			}
		}

		private Color getTitleColor() {
			return UIManager.getColor("InternalFrame.activeTitleBackground");
		}
	}
}
