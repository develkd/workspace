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

package de.ev.iisin.application.common;

import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.extras.ShorthandDateFormatter;
import com.jgoodies.binding.extras.ShorthandTimeFormatter;
import com.jgoodies.binding.formatter.EmptyDateFormatter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.layout.Sizes;

import de.ev.iisin.application.common.components.BorderlessTextField;
import de.ev.iisin.application.common.components.EditableComboBoxAdapter;
import de.ev.iisin.application.common.components.ExcelStyleTable;
import de.ev.iisin.application.common.components.ImageButton;
import de.ev.iisin.application.common.components.MultiHeaderLineTable;
import de.ev.iisin.application.common.components.SortableTable;
import de.ev.iisin.application.common.components.WarningLabelHolder;
import de.ev.iisin.application.common.components.calendar.JXMonthView;
import de.ev.iisin.application.common.formatter.DateFormatter;
import de.ev.iisin.application.common.formatter.PositivLongFormat;
import de.ev.iisin.application.common.formatter.PositivNumberFormat;
import de.ev.iisin.application.common.formatter.PositiveNumberFormatter;
import de.ev.iisin.application.common.formatter.TimeFormatter;
import de.ev.iisin.application.common.interfaces.ColumnWidthProvider;
import de.ev.iisin.application.common.utils.TableProvider;

/**
 * @author Kemal Dönmez
 * 
 */
public class ComponentFactory extends BasicComponentFactory {
	private ComponentFactory() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	/**
	 * Erzeugt und gibt ein StripedTable-instace zurück.Der Header kann nicht
	 * verschoben werden.
	 * 
	 * @param model
	 *            das TableModel, falls das Model <code>null</code> ist, wird
	 *            das DefaultTableModel genommen
	 * @param singleSelectionIndexHolder
	 *            das ValueModel, falls der singleSelectionIndexHolder
	 *            <code>null</code> ist, wird das DefaultListSelectionModel
	 *            genommen
	 * @return die erzeugete JTable
	 */
	public static JTable createTable(TableModel tableModel,
			ValueModel singleSelectionIndexHolder) {

		return createSortableTable(tableModel, singleSelectionIndexHolder, null);
	}

	/**
	 * Erzeugt und gibt ein SortableTableHeader-instace zurück. Der Header kann
	 * nicht verschoben werden.
	 * 
	 * @param tableModel
	 *            das zugrundeliegende <code>Tablemodel</code>
	 * @param singleSelectionIndexHolder
	 *            der <code>ValueHolder</code> für die Selektion
	 * @param hasSortableHeader
	 *            gibt an, ob die Tabelle mittels <code>Header</code>-klick
	 *            sortierbar ist
	 * @param tableHolder
	 *            das Object, welches die Table einbettet.
	 * 
	 * @return gibt einen sortierbare Tabelle zurück, wenn hasSortableHeader =
	 *         true, sonst eine unsortiebare Tablle
	 */
	public static JTable createSortableTable(TableModel tableModel,
			ValueModel singleSelectionIndexHolder, Object tableHolder) {
		SortableTable table = new SortableTable(tableModel);
		configureTable(singleSelectionIndexHolder, tableModel, table);
		if (tableHolder != null)
			TableProvider.getInstance().provide(tableHolder, table);
		return table;
	}

	/**
	 * Erzeugt eine Tabelle, die in der Vertikalen auch ein Kopfelement
	 * beinhaltet
	 * 
	 * @param tableModel
	 *            das zugrundeliegende <code>Tabelmodel</code>
	 * @param singleSelectionIndexHolder
	 *            der <code>ValueHolder</code> für die Selektion
	 * @return eine neue JTable-instanz
	 */
	public static JTable createExcelStyleTable(TableModel tableModel,
			ValueModel singleSelectionIndexHolder) {
		JTable table = new ExcelStyleTable(tableModel);
		configureTable(singleSelectionIndexHolder, tableModel, table);
		return table;
	}

	/**
	 * Erzeugt und gibt ein JTable-instace zurück. Der Header kann nicht
	 * verschoben werden und die Höhe passt sich dem Zeilenanzahl an.
	 * 
	 * @param model
	 *            das TableModel, falls das Model <code>null</code> ist, wird
	 *            das DefaultTableModel genommen
	 * @param singleSelectionIndexHolder
	 *            das ValueModel, falls der singleSelectionIndexHolder
	 *            <code>null</code> ist, wird das DefaultListSelectionModel
	 *            genommen
	 * @param zeilenLaenge
	 *            die Zeilenlänge, falls die zeilenLaenge kleiner <code>1</code>
	 *            ist, wird der Header einzeiglig.
	 * @return die erzeugete JTable
	 */
	public static JTable createMultiHeaderLineTable(TableModel tableModel,
			ValueModel singleSelectionIndexHolder, int zeilenLaenge) {
		zeilenLaenge = zeilenLaenge < 1 ? 1 : zeilenLaenge;

		MultiHeaderLineTable table = new MultiHeaderLineTable(tableModel,
				zeilenLaenge);
		configureTable(singleSelectionIndexHolder, tableModel, table);
		return table;
	}

	/**
	 * Erzeugt ein <code>JXMonthView</code>-Objekt, dessen Anfangstag durch
	 * den <code>Calendar</code>wert bestimmt wird.
	 * 
	 * @return das erzeugte Objekt
	 */
	public static JXMonthView createJXMonthView(int firstDayOfWeek,
			ValueModel model) {
		JXMonthView monthView = new JXMonthView();
		monthView.setFirstDayOfWeek(firstDayOfWeek);
		monthView.setOpaque(false);
		monthView.setAntialiased(true);
		monthView.setTraversable(true);
		DateToMonthViewConnector connector = DateToMonthViewConnector.connect(
				model, monthView);
		connector.updateMonthView();
		model.setValue(new Date());

		return monthView;
	}

	/**
	 * Erzeugt eine Checkbox, dessen Hintergrund farbdurchlässig ist
	 * 
	 * @param valueModel
	 *            das zugrundeliegende <code>ValueModel</code>
	 * @param text
	 *            die Beschriftung der Checkbox
	 * @return eine JCheckBox-instanz
	 */
	public static JCheckBox createCheckBox(ValueModel valueModel, String text) {
		JCheckBox checkBox = BasicComponentFactory.createCheckBox(valueModel,
				text);
		checkBox.setOpaque(false);
		return checkBox;
	}

	/**
	 * Erzeugt eine nicht editierbares Combobox
	 * 
	 * @param <E>
	 *            der Typ der Comboboxelemente und der Selektion
	 * @param selectionInList
	 *            stellt die Liste und die Seletion bereit
	 * @param valueModel
	 *            das Model, welches den zubearbeitenden Wert hält
	 * @param renderer
	 *            die Darstellungseigenschaft der Objekte
	 * @return eine nicht editierbares Combobox
	 */
	public static <E> JComboBox createComboBox(
			SelectionInList<E> selectionInList, AbstractValueModel valueModel,
			ListCellRenderer renderer) {

		JComboBox box = new JComboBox(new ComboBoxAdapter<E>(selectionInList,
				valueModel));
		if (renderer != null)
			box.setRenderer(renderer);
		return box;
	}

	public static <E> JComboBox createEditableComboBox(
			SelectionInList<E> selectionInList, AbstractValueModel valueModel,
			ListCellRenderer renderer) {

		JComboBox box = new JComboBox(new EditableComboBoxAdapter<E>(selectionInList,
				valueModel));
		if (renderer != null)
			box.setRenderer(renderer);
		box.setEditable(true);

		return box;
	}


	/**
	 * Erzeugt eine nicht editierbares Combobox
	 * 
	 * @param <E>
	 *            der Typ der Comboboxelemente und der Selektion
	 * @param selectionInList
	 *            stellt die Liste und die Seletion bereit
	 * @param renderer
	 *            die Darstellungseigenschaft der Objekte
	 * @return eine nicht editierbares Combobox
	 */
	public static <E> JComboBox createComboBox(
			SelectionInList<E> selectionInList, ListCellRenderer renderer) {
		return BasicComponentFactory.createComboBox(selectionInList, renderer);
	}

	/**
	 * Erzeugt eine nicht editierbares Combobox
	 * 
	 * @param <E>
	 *            der Typ der Comboboxelemente und der Selektion
	 * @param selectionInList
	 *            stellt die Liste und die Seletion bereit
	 * @param valueModel
	 *            das Model, welches den zubearbeitenden Wert hält
	 * @return eine nicht editierbares Combobox
	 */
	public static <E> JComboBox createComboBox(
			SelectionInList<E> selectionInList, AbstractValueModel valueModel) {

		JComboBox box = new JComboBox(new ComboBoxAdapter<E>(selectionInList,
				valueModel));
		return box;
	}

	public static JLabel createImageLabel(ValueModel valueModel) {
		JLabel label = new JLabel();
		Bindings.bind(label, "icon", valueModel);
		return label;
	}

	public static ImageButton createImageButton(ValueModel valueModel) {
		ImageButton button = new ImageButton(valueModel);
		// Bindings.bind(button, "icon", valueModel);
		return button;
	}

	/**
	 * Creates and returns a formatted text field that is bound to the Date
	 * value of the given ValueModel. In addition to
	 * BasicComponentFactory#createDateField, the formatter used by this method
	 * accepts the shorthands DDMM, DDMMYY, and DDMMYYYY.
	 * 
	 * @param valueModel
	 *            the model that holds the value to be edited
	 * @return a formatted text field for Date instances that is bound
	 * 
	 * @throws NullPointerException
	 *             if the valueModel is <code>null</code>
	 */
	public static JFormattedTextField createDateField(ValueModel valueModel) {
		DateFormat shortFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		shortFormat.setLenient(false);
		JFormattedTextField.AbstractFormatter defaultFormatter = new ShorthandDateFormatter(
				shortFormat);
		JFormattedTextField.AbstractFormatter displayFormatter = new EmptyDateFormatter();
		DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(
				defaultFormatter, displayFormatter);

		return createFormattedTextField(valueModel, formatterFactory);
	}

	/**
	 * Creates and returns a formatted text field that is bound to the Date
	 * value of the given ValueModel and contains a wanring label. In addition
	 * to BasicComponentFactory#createDateField, the formatter used by this
	 * method accepts the shorthands DDMM, DDMMYY, and DDMMYYYY.
	 * 
	 * @param valueModel
	 *            the model that holds the value to be edited
	 * @return a formatted text field for Date instances that is bound
	 * 
	 * @throws NullPointerException
	 *             if the valueModel is <code>null</code>
	 */
	public static JFormattedTextField createDateField(ValueModel valueModel,
			WarningLabelHolder warnigLabel) {
		DateFormat shortFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		shortFormat.setLenient(false);
		JFormattedTextField.AbstractFormatter defaultFormatter = new DateFormatter(
				shortFormat, warnigLabel);
		JFormattedTextField.AbstractFormatter displayFormatter = new EmptyDateFormatter();
		DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(
				defaultFormatter, displayFormatter);

		return createFormattedTextField(valueModel, formatterFactory);
	}

	/**
	 * Creates and returns a formatted text field for times that is bound to the
	 * Date value of the given ValueModel. In addition to
	 * BasicComponentFactory#createDateField, the formatter used by this method
	 * accepts the DDMM shorthands.
	 * 
	 * @param valueModel
	 *            the model that holds the value to be edited
	 * @return a formatted text field for Date instances that is bound
	 * 
	 * @throws NullPointerException
	 *             if the valueModel is <code>null</code>
	 */
	public static JFormattedTextField createTimeField(ValueModel valueModel) {
		DateFormat shortFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		shortFormat.setLenient(false);
		return createFormattedTextField(valueModel, new ShorthandTimeFormatter(
				shortFormat));
	}

	/**
	 * Creates and returns a formatted text field for times that is bound to the
	 * Date value of the given ValueModel and contains a wanring label. In
	 * addition to BasicComponentFactory#createDateField, the formatter used by
	 * this method accepts the DDMM shorthands.
	 * 
	 * @param valueModel
	 *            the model that holds the value to be edited
	 * @return a formatted text field for Date instances that is bound
	 * 
	 * @throws NullPointerException
	 *             if the valueModel is <code>null</code>
	 */
	public static JFormattedTextField createTimeField(ValueModel valueModel,
			WarningLabelHolder warningHolder) {
		DateFormat shortFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		shortFormat.setLenient(false);
		return createFormattedTextField(valueModel, new TimeFormatter(
				shortFormat, warningHolder));
	}

	public static JTextArea createTextArea(ValueModel valueModel) {
		return createTextArea(valueModel, true);
	}

	/**
	 * Creates and returns a formatted text field for Ueberlaenge that is bound
	 * to the Integer value of the given <code>ValueModel</code>. These
	 * Integers are interpreted as Ueberlaenge meter and will be converted to
	 * Strings and vice versa by means of a NumberFormatter.
	 * <p>
	 * 
	 * TODO: Consider using a custom Formatter for the Ueberlaenge conversion,
	 * just as it has been done with KmLageFormat/KmLageFormatter.
	 * 
	 * @param valueModel
	 *            the model that holds the Ueberlaenge to be edited
	 * @return a formatted text field for Ueberlange values that is bound to the
	 *         given ValueModel
	 * @throws NullPointerException
	 *             if the model is <code>null</code>
	 */
	public static JFormattedTextField createIntagerField(ValueModel valueModel,
			PositivNumberFormat format) {
		JFormattedTextField textField = createFormattedTextField(valueModel,
				getNuberFormatter(format, Integer.class));
		textField.setColumns(2);
		return textField;
	}

	public static JFormattedTextField createLongField(ValueModel valueModel,
			PositivLongFormat format) {
		JFormattedTextField textField = createFormattedTextField(valueModel,
				getNuberFormatter(format, Long.class));
		textField.setColumns(2);
		return textField;
	}

	private static DefaultFormatter getNuberFormatter(Format format,
			Class<?> valueClass) {
		return new PositiveNumberFormatter(format, valueClass);
	}

	public static JTextArea createTextArea(ValueModel valueModel,
			boolean commitOnFocusLost) {
		JTextArea textArea = new JTextArea();
		changeTabFunction(textArea);
		Bindings.bind(textArea, valueModel, commitOnFocusLost);
		return textArea;
	}

	public static JTextField createBordlessTextField(ValueModel valueModel,
			boolean commitOnFocusLost) {
		JTextField textField = new BorderlessTextField();
		textField.setBorder(null);
		Bindings.bind(textField, valueModel, commitOnFocusLost);
		return textField;
	}

	public static JFormattedTextField createBordlessNumberField(
			ValueModel valueModel) {
		JFormattedTextField textField = new BorderlessTextField(
				PositivNumberFormat.LONG);
		textField.setBorder(null);
		Bindings.bind(textField, valueModel);
		return textField;
	}

	/**
	 * Creates and returns a text field with the content bound to the given
	 * ValueModel. Text changes are committed to the model on focus lost.
	 * 
	 * @param valueModel
	 *            the model that provides the value
	 * @return a text field that is bound to the given value model
	 * 
	 * @throws NullPointerException
	 *             if the valueModel is {@code null}
	 * 
	 * @see #createTextField(ValueModel, boolean)
	 */
	public static JTextField createTextField(ValueModel valueModel) {
		return createTextField(valueModel, false);
	}

	public static JLabel createLabelField(ValueModel valueModel) {
		JLabel label = new JLabel();
		Bindings.bind(label, valueModel);
		return label;
	}

	// --------------- Helpermethods -----------------------------

	private static void configureTable(ValueModel singleSelectionIndexHolder,
			TableModel tableModel, JTable table) {
		table.getTableHeader().setReorderingAllowed(false);
		if (singleSelectionIndexHolder != null)
			table.setSelectionModel(new SingleListSelectionAdapter(
					singleSelectionIndexHolder));
		lookForColumnWidthProvider(tableModel, table);
	}

	private static void lookForColumnWidthProvider(TableModel tableModel,
			JTable aTable) {
		if (tableModel instanceof ColumnWidthProvider) {
			ColumnWidthProvider provider = (ColumnWidthProvider) tableModel;
			setColumnWidths(aTable, provider.getColumnWidthHints());
		}

	}

	private static void setColumnWidths(JTable table, int[] columnWidthHints) {
		if (columnWidthHints == null) {
			return;
		}
		for (int index = 0; index < columnWidthHints.length; index++) {
			int hint = columnWidthHints[index];
			if (hint != -1) {
				TableColumn column = table.getColumnModel().getColumn(index);
				int pixel = Sizes.dialogUnitXAsPixel(hint, table);
				column.setMinWidth(pixel);
				column.setMaxWidth(pixel + 200);
			}
		}
	}

	@SuppressWarnings("all")
	private static void changeTabFunction(JTextArea textArea) {
		// Mit Tab wird vorwärts gesprungen
		Set set = new HashSet(
				textArea
						.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		set.clear();
		set.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
		textArea.setFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);

		// Mit Shift-Tab wird rückwärts gesprungen
		set = new HashSet(
				textArea
						.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		set.clear();
		set.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK));
		textArea.setFocusTraversalKeys(
				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
	}

}
