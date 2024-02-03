package com.fns.loader.ui.clientlauncher;

import com.fns.loader.ui.Colors;
import com.fns.loader.ui.components.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
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

public class ConfigurationsTab {
	private static JFrame frame;
	private static File DATA_FILE = null;
	private static FButton buttonAdd, buttonRemove;
	private static DefaultTableModel configurationsTableModel;
	private static FTable configurationsTable;
	private static FPanel parentPanel;

	public static void setupConfigurationsTab(JFrame jFrame, FPanel configurationPanel, String rlExeDir) {
		frame = jFrame;
		DATA_FILE = new File(rlExeDir + "/configurations.csv");

		FPanel tablePanel = new FPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setFocusable(false);
		addTableToPane(tablePanel);
		tablePanel.setBorder(new MatteBorder(0, 0, 1, 0, Colors.BODY_COLOR.brighter().brighter()));
		configurationPanel.add(tablePanel);
		parentPanel = configurationPanel;

		FPanel inputPanel = new FPanel(new BorderLayout(5, 3));
		setupButtons();

		FPanel buttonPanel = new FPanel(new FlowLayout());
		addButtonsTo(buttonPanel);

		inputPanel.add(buttonPanel, BorderLayout.SOUTH);

		configurationPanel.add(inputPanel);
	}

	public static List<String> getConfigurations() {
		var configurations = configurationsTableModel.getDataVector();
		List<String> configurationsList = new ArrayList<>();
		for (var configuration : configurations) {
			var configurationsArr = (String) configuration.toArray(new String[configuration.size()])[0];
			if (!configurationsArr.isEmpty()) {
				configurationsList.add(configurationsArr);
			}
		}
		return configurationsList;
	}

	public static String[] getConfiguration(String label) {
		var dataVector = configurationsTableModel.getDataVector();
		for (var configuration : dataVector) {
			if (configuration.get(0).equals(label)) {
				return (String[]) configuration.toArray(new String[configuration.size()]);
			}
		}
		return null;
	}

	private static void addTableToPane(FPanel panel) {
		// Table with 7 columns
		configurationsTableModel = new NonEditableTableModel(new Object[]{"Label", "Login", "Password", "World", "Script Name", "Script Config", "FPS"}, 0, false);
		configurationsTableModel.addTableModelListener(e -> writeTableToFile(DATA_FILE));
		// Initialize the table with data from the file
		List<String[]> data = readCsvFile(DATA_FILE);
		initializeTableWithData(data);
		configurationsTable = new FTable(configurationsTableModel);
		configurationsTable.getTableHeader().setReorderingAllowed(false); // make columns non-moveable
		configurationsTable.setFocusable(false);
		configurationsTable.getTableHeader().setResizingAllowed(false);
		TableCellRenderer headerRenderer = configurationsTable.getTableHeader().getDefaultRenderer();
		if (headerRenderer instanceof DefaultTableCellRenderer) {
			((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(SwingConstants.LEFT);
		}
		FScrollPane tableScrollPane = new FScrollPane(configurationsTable);
		panel.add(tableScrollPane);
	}

	private static void writeTableToFile(File outputFile) {
		try {
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
				int rowCount = configurationsTableModel.getRowCount();
				int columnCount = configurationsTableModel.getColumnCount();

				for (int row = 0; row < rowCount; row++) {
					for (int col = 0; col < columnCount; col++) {
						Object val = configurationsTableModel.getValueAt(row, col);
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
			if (row.length != 7) {
				System.err.println("Invalid row in configurations.csv: " + String.join(",", row));
//				row = new String[]{"", row[0], row[1], "", ""};
			}
			configurationsTableModel.addRow(row);
		}
	}

	private static void setupButtons() {
		buttonAdd = new FButton("Add Configuration");
		buttonAdd.setPreferredSize(new Dimension(165, 25));
		buttonAdd.addActionListener(e -> {
			new AddConfigurationFrame(parentPanel);
		});

		buttonRemove = new FButton("Remove Configuration");
		buttonRemove.setPreferredSize(new Dimension(165, 25));
		buttonRemove.addActionListener(e -> {
			int selectedRow = configurationsTable.getSelectedRow();
			if (selectedRow == -1) {
				FDialog.createAndShowCustomDialog(frame, "Error", "Select a row to remove.");
			}
			else { // -1 means no row is selected
				configurationsTableModel.removeRow(selectedRow);
				ClientLauncherGUI.updateConfigurationsCombobox();
			}
		});
	}

	private static void addButtonsTo(JPanel buttonPanel) {
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonRemove);
	}

	public static void addRow(String labelText, String loginText, String passwordText, String worldText, String scriptNameText, String scriptConfigText, String fpsText) {
		configurationsTableModel.addRow(new Object[]{labelText, loginText, passwordText, worldText, scriptNameText, scriptConfigText, fpsText});
	}
}
