package com.fns.loader.ui.clientlauncher;

import com.fns.loader.ui.Colors;
import com.fns.loader.ui.components.*;
import com.fns.loader.ui.proxy.ProxyTab;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ClientLauncherGUI extends JFrame {
	static FComboBox<String> comboBox;
	static DefaultComboBoxModel<String> comboBoxModel;
	FButton buttonStart;

	private ClientLauncherGUI() {
//		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String path = System.getenv("LOCALAPPDATA") + "/Runelite/";

		setTitle("Client Launcher");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(650, 450);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

		FPanel contentPane = new FPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
		setContentPane(contentPane);

		FTabbedPane tabbedPane = new FTabbedPane();

		FPanel configurationsPanel = new FPanel();

		FPanel proxyPanel = new FPanel();
		proxyPanel.setLayout(new BoxLayout(proxyPanel, BoxLayout.Y_AXIS));
		ProxyTab.setupProxyTab(this, proxyPanel);

		configurationsPanel.setLayout(new BoxLayout(configurationsPanel, BoxLayout.Y_AXIS));
//		TitledBorder border = BorderFactory.createTitledBorder(new LineBorder(Colors.LIST_COLOR.brighter(), 1, true), "Configurations");
//		border.setTitleColor(Colors.ACCENT_TEXT_COLOR);
//		configurationsPanel.setBorder(new CompoundBorder(border, BorderFactory.createEmptyBorder(0, 7, 0, 7)));
		ConfigurationsTab.setupConfigurationsTab(this, configurationsPanel, path);
//		contentPane.add(configurationsPanel);


		tabbedPane.addTab("Configurations", configurationsPanel);
		tabbedPane.add("Proxies", proxyPanel);
		contentPane.add(tabbedPane);

		FPanel startPanel = new FPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		startPanel.add(new FLabel("Configuration:"));
		startPanel.add(configurations());
		startPanel.add(startButton());

		contentPane.add(startPanel);

		revalidate();
		repaint();

		// Displaying the window
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void run() {
		UIManager.put("TabbedPane.contentAreaColor", Colors.BODY_COLOR.darker());

		SwingUtilities.invokeLater(ClientLauncherGUI::new);
	}

	public static void main(String[] args) {
		run();
	}

	public static void updateConfigurationsCombobox() {
		var configurations = ConfigurationsTab.getConfigurations();
		SwingUtilities.invokeLater(() -> {
			comboBoxModel.removeAllElements();
			// comboBoxModel.addElement("~ None ~");
			comboBoxModel.addAll(configurations);
			comboBox.revalidate();
			comboBox.repaint();
		});
	}

	private FComboBox<String> configurations() {
		var configurations = ConfigurationsTab.getConfigurations();
		comboBoxModel = new DefaultComboBoxModel<>();
		// comboBoxModel.addElement("~ None ~");
		comboBoxModel.addAll(configurations);
		comboBox = new FComboBox<>(comboBoxModel);
		if (comboBoxModel.getSize() > 0) comboBox.setSelectedIndex(0);
		return comboBox;
	}

	private FButton startButton() {
		buttonStart = new FButton("Start client");
		buttonStart.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				String configurationLabel = (String) comboBox.getSelectedItem();
				var configuration = ConfigurationsTab.getConfiguration(configurationLabel);
				// this.dispose();
				if (configuration == null) {
					FDialog.createAndShowCustomDialog(this, "Error", "Select a configuration to launch.");
				}
				else {
					System.out.println("Starting client with configuration: " + configuration[0] + " " + configuration[1] + " " + configuration[2] + " " + configuration[3] + " " + configuration[4] + " " + configuration[5] + " " + configuration[6]);
					try {
						launch(configuration[1], configuration[2], configuration[3], configuration[4], configuration[5], configuration[6]);
					}
					catch (IOException | URISyntaxException ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		});
		return buttonStart;
	}

	private void launch(String loginText, String passwordText, String worldText, String scriptNameText, String scriptConfigText, String fpsText) throws IOException, URISyntaxException {
		ProcessBuilder processBuilder = new ProcessBuilder();
//		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replace("rl-loader.jar", "");
		String path = System.getenv("LOCALAPPDATA") + "/Runelite/";
		System.out.println(path);
		List<String> args = new ArrayList<>();
		if (loginText != null && !loginText.isEmpty() && passwordText != null && !passwordText.isEmpty()) {
			args.add("--account=" + loginText + ":" + passwordText);
		}
		if (worldText != null && !worldText.isEmpty()) {
			args.add("--world=" + worldText);
		}
		if (scriptNameText != null && !scriptNameText.isEmpty()) {
			String arg = "--script=" + scriptNameText;
			if (scriptConfigText != null && !scriptConfigText.isEmpty()) {
				arg += ":" + scriptConfigText;
			}
			args.add(arg);
		}
		if (fpsText != null && !fpsText.isEmpty()) {
			args.add("--fps=" + fpsText);
		}

		processBuilder.command(path + "RuneLite.exe");
		processBuilder.command().addAll(args);
		System.out.println(processBuilder.command());
		processBuilder.start();
	}

}
