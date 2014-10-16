package com.github.guoyj21.server_operator.view;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public Class getColumnClass(int column) {
		return (getValueAt(0, column).getClass());
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
