package com.fns.loader.ui.proxy;

import com.fns.loader.ui.Colors;
import com.fns.loader.ui.clientlauncher.ClientLauncherGUI;
import com.fns.loader.ui.clientlauncher.ConfigurationsTab;
import com.fns.loader.ui.components.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
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
	private static File DATA_FILE;
	private static JFrame frame;
	private static FButton buttonAdd, buttonRemove;
	private static DefaultTableModel proxyTableModel;
	private static FTable proxyTable;
	private static FPanel parentPanel;

	public static void setupProxyTab(JFrame jFrame, FPanel proxyPanel, String rlExeDir) {
		frame = jFrame;
		DATA_FILE = new File(rlExeDir + "/proxies.csv");

		FPanel tablePanel = new FPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setFocusable(false);
		addTableToPane(tablePanel);
		tablePanel.setBorder(new MatteBorder(0, 0, 1, 0, Colors.BODY_COLOR.brighter().brighter()));
		proxyPanel.add(tablePanel);
		parentPanel = proxyPanel;

		FPanel inputPanel = new FPanel(new BorderLayout(5, 3));
		setupButtons();
		FPanel buttonPanel = new FPanel(new FlowLayout());
		addButtonsTo(buttonPanel);

		inputPanel.add(buttonPanel, BorderLayout.SOUTH);

		proxyPanel.add(inputPanel);
	}

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

	private static void addTableToPane(FPanel panel) {
		// Table with 5 columns
		proxyTableModel = new NonEditableTableModel(new Object[]{"Label", "IP Address", "Port", "Username", "Password"}, 0, true);
		proxyTableModel.addTableModelListener(e -> writeTableToFile(DATA_FILE));
		// Initialize the table with data from the file
		List<String[]> data = readCsvFile(DATA_FILE);
		initializeTableWithData(data);
		proxyTable = new FTable(proxyTableModel);
		proxyTable.getTableHeader().setReorderingAllowed(false); // make columns non-moveable
		proxyTable.setFocusable(false);
		TableCellRenderer headerRenderer = proxyTable.getTableHeader().getDefaultRenderer();
		if (headerRenderer instanceof DefaultTableCellRenderer) {
			((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(SwingConstants.LEFT);
		}
		FScrollPane tableScrollPane = new FScrollPane(proxyTable);
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

	private static void setupButtons() {
		buttonAdd = new FButton("Add Proxy");
		buttonAdd.setPreferredSize(new Dimension(120, 25));
		buttonAdd.addActionListener(e -> {
			new AddProxyFrame(parentPanel);
		});

		buttonRemove = new FButton("Remove Proxy");
		buttonRemove.setPreferredSize(new Dimension(120, 25));
		buttonRemove.addActionListener(ProxyTab::removeButtonPressed);
	}

	private static void addButtonsTo(JPanel buttonPanel) {
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonRemove);
	}

	public static void addRow(String labelText, String ipText, String portText, String userText, String passText) {
		proxyTableModel.addRow(new Object[]{labelText, ipText, portText, userText, passText});
	}

	private static void removeButtonPressed(ActionEvent e) {
		int selectedRow = proxyTable.getSelectedRow();
		if (selectedRow == -1) { // -1 means no row is selected
			FDialog.createAndShowCustomDialog(frame, "Error", "Select a row to remove.");
		}
		else {
			Object proxyLabel = proxyTable.getModel().getValueAt(selectedRow, 0);
			if (!ClientLauncherGUI.isRunning()) {
				int choice = FDialog.createAndShowCustomConfirmDialogue(frame, "Warning", "Really remove the proxy: " + proxyLabel + "?");
				if (choice == FDialog.NO_OPTION) return;
				proxyTableModel.removeRow(selectedRow);
				ProxyGUI.updateProxiesCombobox();
			}
			else {
				// If client launcher gui is running,
				// check if the proxy is being used by any configuration
				// if so, display a warning how many will be effected
				// if user confirms, remove the proxy
				FTable table = ConfigurationsTab.getConfigurationsTable();
				int rows = table.getModel().getRowCount();
				int columns = table.getModel().getColumnCount();
				List<Integer> affectedRows = new ArrayList<>();
				for (int i = 0; i < rows; i++) {
					Object item = table.getModel().getValueAt(i, columns - 1);
					if (item.equals(proxyLabel)) {
						affectedRows.add(i);
					}
				}

				int choice;
				if (affectedRows.isEmpty()) {
					choice = FDialog.createAndShowCustomConfirmDialogue(frame, "Warning", "Really remove the proxy: " + proxyLabel + "?");
				}
				else {
					String config = affectedRows.size() == 1 ? "configuration" : "configurations";
					choice = FDialog.createAndShowCustomConfirmDialogue(frame, "Warning", "Removing the proxy: " + proxyLabel, "will affect " + affectedRows.size() + " " + config + ".", "Continue?");
				}
				if (choice == FDialog.NO_OPTION) return;

				proxyTableModel.removeRow(selectedRow);
				ProxyGUI.updateProxiesCombobox();
				ConfigurationsTab.updateProxiesComboBox();
				ConfigurationsTab.updateAffectedRows(affectedRows);
			}

		}
	}
}
