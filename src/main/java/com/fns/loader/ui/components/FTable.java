package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.*;
import java.awt.*;

public class FTable extends JTable {

	public FTable(DefaultTableModel model) {
		super(model);
		setForeground(Colors.TEXT_COLOR);
		setBackground(Colors.BODY_COLOR);
		setGridColor(Colors.BODY_COLOR.brighter().brighter());
		setRowHeight(26);
		setBorder(null);

		// Override the renderer for cells
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			protected void setValue(Object value) {
				setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Set left padding
				super.setValue(value);
			}

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				Border outsideBorder;
				if (column == 0) {
					outsideBorder = BorderFactory.createMatteBorder(0, 1, 0, 0, Colors.BODY_COLOR.brighter().brighter());
					Border insideBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
					label.setBorder(new CompoundBorder(outsideBorder, insideBorder));
				}
				else {
					label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				}

				setBackground(Colors.BODY_COLOR);
				setForeground(Colors.TEXT_COLOR);
				return this;
			}
		};
		setDefaultRenderer(Object.class, cellRenderer);

		// Override the renderer for header
		JTableHeader header = getTableHeader();
		header.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Dimension getPreferredSize() {
				Dimension dimension = super.getPreferredSize();
				dimension.height = 26; // Set the preferred height of the header
				return dimension;
			}

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				Border outsideBorder;
				if (column == 0) {
					outsideBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.BODY_COLOR.brighter().brighter());
				}
				else {
					outsideBorder = BorderFactory.createMatteBorder(1, 0, 1, 1, Colors.BODY_COLOR.brighter().brighter());
				}
				Border insideBorder = BorderFactory.createEmptyBorder(0, 5, 0, 0);
				label.setBorder(new CompoundBorder(outsideBorder, insideBorder));

				label.setForeground(Colors.ACCENT_TEXT_COLOR);
				label.setBackground(Colors.BODY_COLOR.darker());
				return label;
			}
		});
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component component = super.prepareRenderer(renderer, row, column);

		// Explicitly handle row selection
		if (isRowSelected(row)) {
			component.setBackground(Colors.BODY_COLOR.darker());
			component.setForeground(Colors.TEXT_COLOR);
		}

		return component;
	}

	@Override
	public Component prepareEditor(TableCellEditor editor, int row, int column) {
		JTextField component = (JTextField) super.prepareEditor(editor, row, column);

		// Set background color for cells being edited
		component.setBackground(Colors.BODY_COLOR.brighter().brighter());
		component.setForeground(Colors.TEXT_COLOR);
		component.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		component.setCaretColor(Colors.TEXT_COLOR);
		component.setSelectedTextColor(Color.BLACK);
		component.setSelectionColor(Colors.TEXT_COLOR.darker().darker());
		return component;
	}
}
