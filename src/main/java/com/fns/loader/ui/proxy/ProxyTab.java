package com.fns.loader.ui.proxy;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProxyTab {
	private static final String USER_HOME = System.getProperty("user.home");
	private static final File DATA_FILE = new File(USER_HOME, ".runelite/launcher/table_data.csv");
	private static JButton buttonAdd, buttonRemove;
	private static DefaultTableModel proxyTableModel;
	private static JTable proxyTable;
	private static JPanel parentPanel;

	public static List<String> getProxies() {
		var proxies = proxyTableModel.getDataVector();
		List<String> proxyList = new ArrayList<>();
		for (var proxy : proxies) {
			var proxyArr = (String) proxy.toArray(new String[proxy.size()])[0];
			if (!proxyArr.isEmpty()) {
				proxyList.add(proxyArr);
			}
		}
		return proxyList;
	}

	public static String[] getProxy(String label) {
		var dataVector = proxyTableModel.getDataVector();
		for (var proxy : dataVector) {
			if (proxy.get(0).equals(label)) {
				return (String[]) proxy.toArray(new String[proxy.size()]);
			}
		}
		return null;
	}

	public static void setupProxyTab(JPanel proxyPanel) {
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setFocusable(false);
		addTableToPane(tablePanel);
		proxyPanel.add(tablePanel);
		parentPanel = proxyPanel;

		JPanel inputPanel = new JPanel(new BorderLayout(5, 3));
		setupLabelsAndFields();
		setupButtons();

		JPanel buttonPanel = new JPanel(new FlowLayout());
		addButtonsTo(buttonPanel);

		inputPanel.add(buttonPanel, BorderLayout.SOUTH);

		proxyPanel.add(inputPanel);
	}

	private static void addTableToPane(JPanel panel) {
		// Table with 5 columns
		proxyTableModel = new NonEditableTableModel(new Object[]{"Label", "IP Address", "Port", "Username", "Password"}, 0);
		proxyTableModel.addTableModelListener(e -> writeTableToFile(DATA_FILE));
		// Initialize the table with data from the file
		List<String[]> data = readCsvFile(DATA_FILE);
		initializeTableWithData(data);
		proxyTable = new JTable(proxyTableModel);
		proxyTable.getTableHeader().setReorderingAllowed(false); // make columns non-moveable
		proxyTable.setFocusable(false);
		TableCellRenderer headerRenderer = proxyTable.getTableHeader().getDefaultRenderer();
		if (headerRenderer instanceof DefaultTableCellRenderer) {
			((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(SwingConstants.LEFT);
		}
		JScrollPane tableScrollPane = new JScrollPane(proxyTable);
		panel.add(tableScrollPane);
	}

	private static String label(String s) {
		return "<html><font color='#ffe100'>" + s + "</font></html>";
	}

	private static void writeTableToFile(File outputFile) {
		try {
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
				int rowCount = proxyTableModel.getRowCount();
				int columnCount = proxyTableModel.getColumnCount();

				for (int row = 0; row < rowCount; row++) {
					for (int col = 0; col < columnCount; col++) {
						Object val = proxyTableModel.getValueAt(row, col);
						String value = val == null ? "" : val.toString();
						bw.write(value);
						if (col != columnCount - 1) {
							bw.write(",");
						}
					}
					bw.newLine();
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<String[]> readCsvFile(File file) {
		try {
			if (file.exists()) {
				List<String> lines = Files.readAllLines(file.toPath());
				return lines.stream()
						.map(line -> line.split(","))
						.collect(Collectors.toList());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private static void initializeTableWithData(List<String[]> data) {
		for (String[] row : data) {
			if (row.length == 2) {
				row = new String[]{"", row[0], row[1], "", ""};
			}
			else if (row.length == 4) {
				row = new String[]{"", row[0], row[1], row[2], row[3]};
			}
			proxyTableModel.addRow(row);
		}
	}

	private static void setupLabelsAndFields() {
		// Text input fields and buttons
//		JLabel labelIP = new JLabel("IP:");
//		JLabel labelPort = new JLabel("Port:");
//		JLabel labelUser = new JLabel("User:");
//		JLabel labelPass = new JLabel("Pass:");
		JTextField inputFieldIP = new JTextField();
		inputFieldIP.setPreferredSize(new Dimension(125, 20));
		JTextField inputFieldPort = new JTextField();
		inputFieldPort.setPreferredSize(new Dimension(125, 20));
		JTextField inputFieldUser = new JTextField();
		inputFieldUser.setPreferredSize(new Dimension(125, 20));
		JTextField inputFieldPass = new JTextField();
		inputFieldPass.setPreferredSize(new Dimension(125, 20));
	}

	private static void setupButtons() {
		buttonAdd = new JButton("Add Proxy");
		buttonAdd.setPreferredSize(new Dimension(120, 25));
		buttonAdd.addActionListener(e -> {
			new AddProxyFrame(parentPanel);
		});

		buttonRemove = new JButton("Remove Proxy");
		buttonRemove.setPreferredSize(new Dimension(120, 25));
		buttonRemove.addActionListener(e -> {
			int selectedRow = proxyTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(proxyTable, "Select a row to remove.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else { // -1 means no row is selected
				proxyTableModel.removeRow(selectedRow);
				ProxyGUI.updateProxyCombobox();
			}
		});
	}

	private static void addButtonsTo(JPanel buttonPanel) {
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonRemove);
	}

	public static void addRow(String labelText, String ipText, String portText, String userText, String passText) {
		proxyTableModel.addRow(new Object[]{labelText, ipText, portText, userText, passText});
	}

	public static class NonEditableTableModel extends DefaultTableModel {
		public NonEditableTableModel(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			// Set specific columns as editable, if needed
			// For now, make all cells non-editable
			return false;
		}
	}
}
