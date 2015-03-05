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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;

/**
 * A light version of the JGoodies UIF AbstractDialog.<p>
 * 
 * An abstract superclass that minimizes the effort required to build
 * consistent Swing dialogs quickly. It forms a template process 
 * to build a standardized dialog and provides convenience behavior 
 * to create frequently used dialog buttons, button bars and actions.
 * Also, this class can assist you in buffering dialog input values;
 * therefore it use the the {@link BufferedValueModel} from the 
 * JGoodies Binding library. See below for more information on buffering.<p>
 * 
 * The dialog build process begins with the <code>#build</code> method that
 * builds the content pane, applies a resize hook, sets the content pane,
 * packs the dialog, sets the resizable state and locates it on the screen.
 * Subclasses will rarely override the #build method. Instead, subclasses
 * must implement <code>#buildContent</code> to build the dialog's 
 * main content. This content shall include the button bar
 * or button stack but not the header and not the dialog border.
 * The optional dialog header is built in <code>#buildHeader</code>
 * which answers <code>null</code> by default to indicate that the dialog
 * has no header. The border returned by <code>#getDialogBorder</code> 
 * is wrapped around the content, not the header. Most dialogs will use a 
 * default border for a plain dialog and a different border dialog with tabs.
 * The <code>#resizeHook</code> allows to resize the dialog to achieve
 * an aestetic aspect ratio. See {@link com.jgoodies.uif.util.Resizer} for
 * an example how to resize a dialog consistently. 
 * <code>#locateOnScreen</code> enables you to change the dialog's location
 * on the screen. By default a dialog is located relative to its parent
 * frame or dialog. Other useful locations are the screen center or
 * a screen corner; class {@link com.jgoodies.uif.util.ScreenUtils} can assist you 
 * in setting such a location.<p>
 * 
 * To open a dialog invoke <code>#open</code>, to close it you can call 
 * <code>#close</code>. Once it has been closed you can call 
 * <code>#hasBeenCanceled</code> to understand whether this
 * dialog has been canceled or not.<p>
 * 
 * AbstractDialog provides methods to create the following frequently used
 * buttons: <i>OK, Cancel, Accept, Apply, Reset, Close</i>. The button texts
 * are internationalized and can be localized under the keys mentioned in the
 * associated <code>#createXXXButton</code> method. If you click on such 
 * a button, the associated <code>#doXXX</code> method will be invoked.
 * For example, if you click on the <i>Apply</i> button, <code>#doApply</code>
 * is invoked. If you override a <code>#doXXX</code> method, be sure to 
 * call the super behavior. There are three methods that build button bars 
 * from the standardized buttons mentioned before, where 
 * <code>#createButtonBarWithOKCancel</code> is the most frequently used.<p>
 * 
 * Find below a sample subclass that has a header, a button bar with <i>OK</i>
 * and <i>Cancel</i> and is resized to an aestetic aspect ratio:
 * <pre>
 * public final class MyDialog extends AbstractDialog {
 * 
 *     protected JComponent buildHeader() {
 *         return new HeaderPanel(
 *             "My Dialog",
 *             "You can do click on the tabs below to ...",
 *             ResourceUtils.getIcon(MyResourceIDs.MY_DIALOG_ICON)
 *         );
 *     }
 * 
 *     public JComponent buildContent() {
 *         JPanel content = new JPanel(new BorderLayout());
 *         content.add(buildMainPanel(),             BorderLayout.CENTER);
 *         content.add(buildButtonBarWithOKCancel(), BorderLayout.SOUTH);
 *         return content;
 *     }
 * 
 *     private JComponent buildMainPanel() {
 *         // Your custom code to build the main panel...
 *     }
 * 
 *     protected void resizeHook(JComponent component) {
 *         Resizer.DEFAULT.resize(component);
 *     }
 * 
 * }
 * </pre><p>
 * 
 * <strong>Buffering Input Values</strong><br>
 * This class provides optional behavior to delay the commit of dialog 
 * input values. You can create buffered models that hold back value changes 
 * until you either commit or flush the buffered values. You can find more
 * information about binding and buffering values in the documentation
 * for the JGoodies Binding library. This library is used to implement
 * buffering in this class.<p> 
 * 
 * AbstractDialog holds a <code>triggerChannel</code> that can be shared 
 * by instances of BufferedValueModel to commit and flush buffered values. 
 * This way changes of dialog values can be delayed until the trigger channel
 * triggers a commit or flush of the buffered values.<p>
 * 
 * By default the trigger channel changes to true in <code>#doApply</code> 
 * and <code>#doAccept</code> and changes to false in <code>#doReset</code> 
 * and <code>#doCancel</code>. And so, clicking on <i>Apply</i> or <i>OK</i> 
 * will commit buffered dialog values; while clicking on <i>Reset</i> or 
 * <i>Cancel</i> will reset the buffered values to the original values. 
 * See the documentation f the JGoodies Data Binding framework for details.<p>
 * 
 * The trigger channel is provided as the bound Java bean property 
 * <i>triggerChannel</i> that must be a non-<code>null</code> 
 * <code>ValueModel</code> with values of type <code>Boolean</code>. 
 * Attempts to read or write other value types may be rejected 
 * with runtime exceptions. The trigger channel is initialized as an 
 * instance of {@link Trigger}.<p>
 * 
 * Find below a sample subclass, a customer editor, that buffers 
 * the values of two input fields for the customer's last name and first name:
 * <pre>
 * public final class CustomerDialog extends AbstractDialog {
 * 
 *     private Component lastNameField;
 *     private Component firstNameField;
 *     private Component titleField;
 *     ...
 * 
 *     public CustomerDialog(Frame owner, Customer customer) {
 *         super(owner, "Edit Customer");
 *         initComponents(customer);
 *     }
 * 
 *     private void initComponents(Customer customer) {
 * 
 *         // Bound field using a DocumentAdapter
 *         lastNameField = new JTextField(40);
 *         lastNameField.setDocument(
 *             new DocumentAdapter(
 *                 buffer(new PropertyAdapter(customer, "lastName"))
 *             )
 *         );
 * 
 *         // Bound field using a DocumentToValueConnector
 *         firstNameField = new JTextField(40);
 *         new DocumentToValueConnector(
 *             firstNameField, 
 *             buffer(new PropertyAdapter(customer, "firstName"))
 *         );
 * 
 *         // Bound field created by a factory 
 *         titleField = BasicComponentFactory.createTextField(
 *             buffer(new PropertyAdapter(customer, "title"))
 *         );
 *         titleField.setColumns(20);
 *     }
 * 
 *     public JComponent buildContent() {
 *         JPanel content = new JPanel(new BorderLayout());
 *         content.add(buildMainPanel(),             BorderLayout.CENTER);
 *         content.add(buildButtonBarWithOKCancel(), BorderLayout.SOUTH);
 *         return content;
 *     }
 * 
 *     private JComponent buildMainPanel() {
 *         FormLayout layout = new FormLayout(
 *             "pref, 4dlu, default",  // columns
 *             ""                      // add rows dynamically
 *         );
 *         DefaultFormBuilder builder = new DefaultFormBuilder(layout);
 *         builder.setDefaultDialogBorder();
 *         builder.append("Last name",  lastNameField);
 *         builder.append("First name", firstNameField);
 *         builder.append("Title",      titleField);
 *         ...
 *         return builder.getPanel();
 *     }
 * 
 * }
 * </pre>
 * 
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 * 
 * @see     ButtonBarFactory
 * @see     com.jgoodies.uif.util.Resizer
 * @see     com.jgoodies.binding.value.ValueModel
 * @see     com.jgoodies.binding.value.BufferedValueModel
 * @see     com.jgoodies.binding.value.Trigger
 */

public abstract class AbstractDialog extends JDialog {

    
    // Default labels and IDs for the frequently used dialog Actions **********
    
    private static final String CANCEL_LABEL = "Abbrechen";
    private static final String CLOSE_LABEL  = "Schlieﬂen";
    private static final String OK_LABEL     = "OK";
    
    private static final String ACCEPT_ID = "accept";
    private static final String CANCEL_ID = "cancel";
    private static final String CLOSE_ID  = "close";
    
    /**
     * Indicates whether the dialog has been canceled or not.
     * The value is reset to <code>false</code> in the #open method.
     */
    private boolean canceled = false;
    
    /**
     * Holds a three-state trigger channel that can be used to trigger
     * commit and reset events in instances of {@link BufferedValueModel}.
     * The trigger value is changed to true in <code>#doApply</code> and 
     * <code>#doOK</code> and changed to false in <code>#doReset</code> and 
     * <code>#doCancel</code>.<p>
     * 
     * The trigger channel is initialized as a <code>Trigger</code>
     * but may be replaced with any other <code>ValueModel</code>
     * that accepts booleans.
     */
    private ValueModel triggerChannel = new Trigger();


    // Fields for the lazily created frequently used dialog Actions ***********
    
    private Action applyAction;
    private Action cancelAction;
    private Action closeAction;
    private Action okAction;
    
    
    // Instance Creation ******************************************************
    
    public AbstractDialog(Frame owner) {
        this(owner, "I-ISIN");
    }
    /**
     * Constructs a modal <code>AbstractDialog</code> with the given 
     * <code>Frame</code> as its owner using the given window title.
     * 
     * @param owner   the dialog's parent frame
     * @param title   the window title
     */
    public AbstractDialog(Frame owner, String title) {
        this(owner, title, true);
    }
    

    /**
     * Constructs an <code>AbstractDialog</code> with the given 
     * <code>Frame</code> as its owner using the given window title 
     * and the specified modal state.
     * 
     * @param owner   the dialog's parent frame
     * @param title   the window title
     * @param modal   true for a model dialog, false for non-modal
     */
    public AbstractDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        configureWindowClosing();
    }
    

    /**
     * Builds the dialog's content pane, packs it, sets the resizable property,
     * and locates it on the screen. The dialog is then ready to be opened.<p>
     * 
     * Subclasses should rarely override this method.
     */
    protected void build() {
        JComponent content = buildContentPane();
        setContentPane(content);
        pack(); 
        setResizable();
        locateOnScreen();
    }

    
    /**
     * Builds and returns the content pane, sets the border and 
     * puts an optional header component in the dialog's north.<p>
     * 
     * Subclasses will rarely override this method.
     * 
     * @return the dialog's content pane with a border set and 
     *     optional header in the north
     */
    private JComponent buildContentPane() {
        JComponent center = buildContent();
        center.setBorder(getDialogBorder());
        return center;
    }
    

    // Abstract Building Behavior *********************************************

    /**
     * Builds and answers the dialog's main content without header and border.
     * Subclass must override this method. See the class comment for a detailed
     * description how this method is part of the build process.
     * 
     * @return the dialog's main content without header and border
     */
    abstract protected JComponent buildContent();
    

    // Dialog Life Cycle ******************************************************

    /**
     * Builds the dialog content, marks it as not canceled and makes it visible.
     */
    public void open() {
        build();
        canceled = false;
        setVisible(true);
    }

    
    /**
     * Closes the dialog: makes it invisible and disposes it, 
     * which in turn releases all required OS resources.
     */
    public void close() {
        dispose();
    }
    

    /**
     * Checks and answers whether the dialog has been canceled.
     * This indicator is set in #doAccept and #doCancel.
     * 
     * @return true indicates that the dialog has been canceled
     */
    public boolean hasBeenCanceled() {
        return canceled;
    }

    
    // Configuration **********************************************************

    /**
     * Configures the closing behavior: invokes #doCloseWindow
     * instead of just closing the dialog. This allows subclasses
     * to hook into the close process and perform a custom code sequence.
     */
    private void configureWindowClosing() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowClosingHandler());
    }

    /**
     * TODO: Move this constant to the Forms Borders class
     */
    protected static final Border DIALOG_BORDER =
        new EmptyBorder(12, 10, 10, 10);
    
    /**
     * Returns the border that will be put around the content, which
     * has been created by #buildContent.<p>
     * 
     * Subclasses will typically use <code>DIALOG_BORDER</code> or 
     * <code>CARD_DIALOG_BORDER</code>, which is more narrow than the default.
     * 
     * @return the dialog's border
     */
    protected Border getDialogBorder() {
        return DIALOG_BORDER;
    }
    

    /**
     * Sets the dialog's resizable state. By default dialogs are non-resizable;
     * subclasses may override.
     */
    protected void setResizable() {
        setResizable(false);
    }
    

    /**
     * Locates the dialog on the screen. The default implementation 
     * sets the location relative to the parent.<p>
     * 
     * Subclasses may choose to center the dialog or put it in a screen corner.
     */
    private void locateOnScreen() {
        setLocationRelativeTo(getParent());
    }
    

    // Buffering Support ******************************************************

    /**
     * Creates and returns a {@link BufferedValueModel} on the given ValueModel
     * using the dialog's trigger channel to trigger commit and flush events.
     * This is just a convenience method to minimize the code necessary
     * to buffer underlying models.
     * 
     * @param valueModel  the value model to be buffered
     * @return a BufferedValueModel triggered by the dialog's trigger channel 
     * @see BufferedValueModel
     * @see ValueModel
     * @see Trigger
     */
    public final BufferedValueModel buffer(ValueModel valueModel) {
        return new BufferedValueModel(valueModel, getTriggerChannel());
    }

   
    /**
     * Returns a ValueModel that can be used to trigger commit and flush events 
     * in BufferedValueModels. The trigger channel's value changes to true 
     * in <code>#doApply</code> which is invoked by <code>#doAccept</code>, 
     * and it changes to false in <code>#doReset</code> and 
     * <code>#doCancel</code>.
     *
     * @return the dialog's trigger channel
     * @see BufferedValueModel
     * @see ValueModel
     * @see #setTriggerChannel(ValueModel)
     */
    public final ValueModel getTriggerChannel() {
        return triggerChannel;
    }
    
    /**
     * Ensures that the trigger channel changes to true which in turn triggers 
     * commit events in all {@link BufferedValueModel} instances that share 
     * this trigger. By default this method is invoked in {@link #doApply()}.
     * 
     * @see #triggerFlush()
     * @see #doApply()
     */
    protected final void triggerCommit() {
        if (Boolean.TRUE.equals(getTriggerChannel().getValue()))
            getTriggerChannel().setValue(null);
        getTriggerChannel().setValue(Boolean.TRUE);
    }
    
    /**
     * Ensures that the trigger channel changes to false which in turn triggers 
     * flush events in all {@link BufferedValueModel} instances that share 
     * this trigger. By default this method is invoked in {@link #doReset()}.
     * 
     * @see #triggerCommit()
     * @see #doReset()
     */
    protected final void triggerFlush() {
        if (Boolean.FALSE.equals(getTriggerChannel().getValue()))
            getTriggerChannel().setValue(null);
        getTriggerChannel().setValue(Boolean.FALSE);
    }
    

    // Common Buttons *******************************************************

    /**
     * Creates and returns an Accept button with the given label text 
     * and default state. Clicking this button invokes #doAccept,
     * which by default closes this dialog.<p>
     * 
     * While this method allows to use a custom label, method
     * <code>#createOKButton</code> uses a standardized internationalized 
     * <i>OK</i> text.<p>
     * 
     * Subclasses that use this method to create the dialog OK or Accept button
     * shall ensure that <code>#doAccept</code> performs the appropriate actions. 
     * 
     * @param text       the button's label text
     * @param isDefault  true to make the button the default button
     * @return an accept button with the given label text and default state
     * 
     * @see #doAccept()
     * @see #createOKButton(boolean)
     */
    private JButton createAcceptButton(String text, boolean isDefault) {
        JButton button = new JButton(new DispatchingAction(ACCEPT_ID, text));
        if (isDefault)
            setDefaultButton(button);
        return button;
    }
    

    /**
     * Creates and returns a Cancel button. Clicking this button invokes
     * #doCancel which by default closes the dialog. 
     * The button's label text can be localized under key 
     * <code>ResourceID.BUTTON_CANCEL_TEXT</code>. 
     * 
     * @return a Cancel button
     * 
     * @see #doCancel()
     * @see #getString(String, String)
     */
    protected JButton createCancelButton() {
        return new JButton(getCancelAction());
    }
    

    /**
     * Creates and returns a Close button. Clicking this button invokes
     * #doClose which by default just closes the dialog.
     * The button's label text can be localized under key
     * <code>ResourceID.BUTTON_CLOSE_TEXT</code>. 
     * 
     * @param isDefault  true to make this the default button
     * @return a Close button
     * 
     * @see #doClose()
     * @see #getString(String, String)
     */
    protected JButton createCloseButton(boolean isDefault) {
        JButton closeButton = new JButton(getCloseAction());
        if (isDefault)
            setDefaultButton(closeButton);
        return closeButton;
    }
    

    /**
     * Creates and returns an OK button. Clicking this button invokes
     * #doAccept which by default performs a #doAccept, sets the 
     * canceled marker to false and finally closes the dialog.<p>
     * 
     * This method uses #createAcceptButton to create the button,
     * and uses a standardized internationalized <i>OK</i> label text
     * that you can be localize under key
     * <code>ResourceID.BUTTON_OK_TEXT</code>. 
     * 
     * @param isDefault   true to make this the default button
     * @return an OK button
     * 
     * @see #doAccept()
     * @see #doApply()
     * @see #getString(String, String)
     */
    protected JButton createOKButton(boolean isDefault) {
        JButton okButton = new JButton(getOKAction());
        if (isDefault)
            setDefaultButton(okButton);
        return okButton;
    }
    

    /**
     * Sets a button as default button in the dialog's root pane.
     * 
     * @param button   the button to be set as default
     */
    private void setDefaultButton(JButton button) {
        getRootPane().setDefaultButton(button);
    }


    // Common Button Bars ***************************************************

    /**
     * Builds and returns a button bar with a Close button.
     * Uses #createCloseButton to create a standardized button.
     * 
     * @return a button bar that contains a Close button
     * @see #createCloseButton(boolean)
     */
    protected JComponent buildButtonBarWithClose() {
        JPanel bar = ButtonBarFactory.buildCloseBar(createCloseButton(true));
        bar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        return bar;
    }
    

    /**
     * Builds and returns a button bar with an OK and a Cancel button.
     * Uses #createOKButton and #createCancelButton to create 
     * standardized buttons.
     * 
     * @return a button bar that contains an OK and a Cancel button
     * @see #createOKButton(boolean)
     * @see #createCancelButton()
     */
    protected JComponent buildButtonBarWithOKCancel() {
        JPanel bar = ButtonBarFactory.buildOKCancelBar(
            createOKButton(true),
            createCancelButton());
        bar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        return bar;
    }
    

    /**
     * Invokes <code>#doApply</code>, marks the dialog as uncanceled and
     * finally closes it. This method is intended to apply value changes
     * and then quit the dialog. Typically the core of this action is
     * performed in <code>#doApply</code>.
     *  
     * This method is invoked if an <i>Accept</i> or <i>OK</i> button created 
     * by <code>#createAcceptButton</code> or <code>createOKButton</code> 
     * has been clicked. Such buttons use a DispatchingActionListener 
     * that in turn delegates the action perform to this method.
     */
    public void doAccept() {
        doApply();
        canceled = false;
        close();
    }

    /**
     * Applies changes in the dialog and leaves the dialog open. 
     * Changes the trigger channel to true, which will commit values 
     * buffered by BufferedValueModels that share this dialog's 
     * trigger channel.<p>
     * 
     * This method is invoked if an <i>Apply</i> button created by 
     * <code>#createApplyButton</code> has been clicked. 
     * Such buttons use a DispatchingActionListener that in turn 
     * delegates the action perform to this method.
     * By default this method is called by <code>#doAccept</code>
     * which is performed when an <i>Accept</i> or <i>OK</i> button
     * has been clicked.<p>
     * 
     * Subclasses that override this method shall typically invoke
     * this super method or shall ensure that the trigger is committed.
     * 
     * @see #doAccept()
     * @see #doReset()
     * @see #triggerCommit()
     */
    private void doApply() {
        triggerCommit();
    }
    

    /**
     * Resets changed dialog values, marks the dialog as canceled and closes it.
     * 
     * This method is invoked if a <i>Cancel</i> button created by 
     * <code>#createCancelButton</code> has been clicked. 
     * Such buttons use a DispatchingActionListener that in turn 
     * delegates the action perform to this method.
     */
    public void doCancel() {
        triggerFlush();
        canceled = true;
        close();
    }

    
    /**
     * Marks the dialog as uncanceled and closes it. 
     * 
     * This method is invoked if a <i>Close</i> button created by 
     * <code>#createCloseButton</code> has been clicked. 
     * Such buttons use a DispatchingActionListener that in turn 
     * delegates the action perform to this method.
     */
    public void doClose() {
        canceled = false;
        close();
    }

    /**
     * Performs the close window action that is invoked if the dialog
     * is closing, for example by clicking the dialog's close button 
     * (often in the upper right corner).
     * By default this methods performs a cancel via <code>#doCancel</code>.
     */
    protected void doCloseWindow() {
        doCancel();
    }
    
    
    /**
     * Lazily creates and returns the Cancel Action that invokes #doCancel.
     * 
     * @return the lazily created Cancel Action
     * 
     * @see #createCancelButton
     * @see #buildButtonBarWithOKCancel
     * @see #buildButtonBarWithOKCancelApply()
     */
    public Action getCancelAction() {
        if (cancelAction == null) {
            cancelAction = new DispatchingAction(
                    CANCEL_ID, 
                    CANCEL_LABEL);
        }
        return cancelAction;
    }
    

    /**
     * Lazily creates and returns the Close Action that invokes #doClose.
     * 
     * @return the lazily created Close Action
     * 
     * @see #createCloseButton
     * @see #buildButtonBarWithOKCancelApply()
     */
    public Action getCloseAction() {
        if (closeAction == null) {
            closeAction = new DispatchingAction(
                    CLOSE_ID, 
                    CLOSE_LABEL);
        }
        return closeAction;
    }
    

    /**
     * Lazily creates and returns the OK Action that invokes #doAccept.
     * 
     * @return the lazily created OK Action
     * 
     * @see #createOKButton
     * @see #buildButtonBarWithOKCancel
     * @see #buildButtonBarWithOKCancelApply()
     */
    public Action getOKAction() {
        if (okAction == null) {
            okAction = new DispatchingAction(
                    ACCEPT_ID, 
                    OK_LABEL);
        }
        return okAction;
    }
    

    // Invokes #doCloseWindow when the window is about to be closed.
    private class WindowClosingHandler extends WindowAdapter {
        
        public void windowClosing(WindowEvent e) {
            doCloseWindow();
        }

    }

    
    /*
     * An implementation of the Action interface that dispatches using an ID.
     * Provides the behavior required for the default buttons created above.
     */
    private class DispatchingAction extends AbstractAction {

        private final String id;

        private DispatchingAction(String id, String name) {
            this.id = id;
            putValue(Action.NAME, name);
        }

        /**
         * Dispatches using this Action's stored id, then delegates to the 
         * appropriate dialog method that does the real work.
         */
        public void actionPerformed(ActionEvent evt) {
            if (id.equals(ACCEPT_ID)) {
                doAccept();
            } else if (id.equals(CANCEL_ID)) {
                doCancel();
            } else if (id.equals(CLOSE_ID)) {
                doClose();
            } else
                throw new IllegalStateException("Unknown action name" + id);
        }

        public String toString() {
            return super.toString() + ": " + id;
        }
    }

    
}