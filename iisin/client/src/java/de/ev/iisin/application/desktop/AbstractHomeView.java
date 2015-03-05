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

package de.ev.iisin.application.desktop;

import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.application.ResourceUtils;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import de.ev.iisin.application.common.ComponentFactory;

/**
 * @author Kemal Dönmez
 * 
 */
public abstract class AbstractHomeView extends AbstractView {

	private static final ResourceBundle RESOURCE = ResourceUtils
			.getResourceBundle(AbstractHomeView.class);

	private JTable table;
	private JScrollPane scroll;
	private SimpleInternalFrame frame;
	private AbstractHomeModel<?> model;
	private String frameName;
	
	public AbstractHomeView(AbstractHomeModel<?> model) {
		this(model, RESOURCE.getString("home.maintenance"));
	}

	public AbstractHomeView(AbstractHomeModel<?> model, String frameName){
		this.model = model;
		this.frameName = frameName;
	}


	protected AbstractHomeModel<?> getModel(){
		return model;
	}
	protected abstract AbstractTableAdapter<?> getTableAdapter();

	protected void initEvent() {

	}

	protected void initComponents() {
		table = ComponentFactory.createSortableTable(getTableAdapter(),
				model.getSelectionInList().getSelectionIndexHolder(), this);
		scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ev.iisin.application.desktop.AbstractView#buildPanel()
	 */
	@Override
	protected JComponent buildPanel() {
		initComponents();
		initEvent();
		frame = new SimpleInternalFrame(frameName);
		frame.setContent(buildView());
		return frame;
	}
	
	
	protected SimpleInternalFrame getFrame(){
		return frame;
	}
	protected JScrollPane getScrollPane(){
		return scroll;
	}
	protected JComponent buildView() {
		FormLayout layout = new FormLayout("fill:p:grow",
				"fill:p:grow, 6dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setBorder(Borders.DLU14_BORDER);
		CellConstraints cc = new CellConstraints();
		builder.add(scroll, cc.xy(1, 1));
		builder.add(getButtonBar(), cc.xy(1, 3));
		return builder.getPanel();
	}

	protected JComponent getButtonBar() {
		ButtonBarBuilder2 builder = ButtonBarBuilder2
				.createLeftToRightBuilder();
		builder.addButton(model.getActions());
		return builder.getPanel();
	}

}
