package com.fns.loader.ui.components;


import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
	private final boolean allCellsNotEditable;

	public NonEditableTableModel(Object[] columnNames, int rowCount, boolean allCellsNotEditable) {
		super(columnNames, rowCount);
		this.allCellsNotEditable = allCellsNotEditable;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// if allCellsNotEditable is true, then no cells are editable
		// else only the first column is not editable
		if (allCellsNotEditable) {
			return false;
		}
		return column != 0;
	}
}