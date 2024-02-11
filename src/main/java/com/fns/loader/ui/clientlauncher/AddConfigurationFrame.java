package com.fns.loader.ui.clientlauncher;

import com.fns.loader.ui.components.*;
import com.fns.loader.ui.proxy.ProxyGUI;

import javax.swing.*;
import java.awt.*;

public class AddConfigurationFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	public AddConfigurationFrame(FPanel configurationPanel) {
		super((Frame) SwingUtilities.getWindowAncestor(configurationPanel), "Add Configuration", true);
		setPreferredSize(new Dimension(250, 290));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(configurationPanel);
		setResizable(false);

		FPanel contentPane = new FPanel();
		contentPane.setLayout(new GridLayout(0, 2, 5, 5));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		FLabel configLabel = new FLabel("Label:");
		configLabel.setToolTipText("The name of the configuration");
		contentPane.add(configLabel);
		FTextField labelField = new FTextField();
		labelField.setFont(new Font("Arial", Font.PLAIN, 11));
		labelField.setToolTipText("The name of the configuration");
		contentPane.add(labelField);

		FLabel loginLabel = new FLabel("Login:");
		loginLabel.setToolTipText("The login of the RuneScape Account");
		contentPane.add(loginLabel);
		FTextField loginField = new FTextField();
		loginField.setFont(new Font("Arial", Font.PLAIN, 11));
		loginField.setToolTipText("The login of the RuneScape Account");
		contentPane.add(loginField);

		FLabel passwordLabel = new FLabel("Password:");
		passwordLabel.setToolTipText("The password of the RuneScape Account");
		contentPane.add(passwordLabel);
		FTextField passwordField = new FTextField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordField.setToolTipText("The password of the RuneScape Account");
		contentPane.add(passwordField);

		FLabel worldLAbel = new FLabel("World:");
		worldLAbel.setToolTipText("The world to connect to");
		contentPane.add(worldLAbel);
		FTextField worldField = new FTextField();
		worldField.setFont(new Font("Arial", Font.PLAIN, 11));
		worldField.setToolTipText("The world to connect to");
		contentPane.add(worldField);

		FLabel scriptNameLabel = new FLabel("Script Name:");
		scriptNameLabel.setToolTipText("The name of the script to run");
		contentPane.add(scriptNameLabel);
		FTextField scriptNameField = new FTextField();
		scriptNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		scriptNameField.setToolTipText("The name of the script to run");
		contentPane.add(scriptNameField);

		FLabel scriptConfigLabel = new FLabel("Script Config:");
		scriptConfigLabel.setToolTipText("The configuration of the script to run");
		contentPane.add(scriptConfigLabel);
		FTextField scriptConfigField = new FTextField();
		scriptConfigField.setFont(new Font("Arial", Font.PLAIN, 11));
		scriptConfigField.setToolTipText("The configuration of the script to run");
		contentPane.add(scriptConfigField);

		FLabel fpsLabel = new FLabel("FPS:");
		fpsLabel.setToolTipText("The max FPS to run the client at");
		contentPane.add(fpsLabel);
		FTextField fpsField = new FTextField();
		fpsField.setFont(new Font("Arial", Font.PLAIN, 11));
		fpsField.setToolTipText("The max FPS to run the client at");
		contentPane.add(fpsField);

		FLabel proxyLabel = new FLabel("Proxy:");
		proxyLabel.setToolTipText("The proxy to use");
		contentPane.add(proxyLabel);
		JComboBox<String> proxyComboBox = ProxyGUI.getProxiesComboBox();
		proxyComboBox.setFont(new Font("Arial", Font.PLAIN, 11));
		proxyComboBox.setToolTipText("The proxy to use");
		contentPane.add(proxyComboBox);

		FLabel emptyLabel = new FLabel();
		contentPane.add(emptyLabel);
		FLabel emptyLabel2 = new FLabel();
		contentPane.add(emptyLabel2);

		FButton addButton = new FButton("Add");
		addButton.addActionListener(e -> {
			Object proxy = proxyComboBox.getSelectedItem();
			boolean success = processAddingCongifuration(
					labelField.getText(),
					loginField.getText(),
					passwordField.getText(),
					worldField.getText(),
					scriptNameField.getText(),
					scriptConfigField.getText(),
					fpsField.getText(),
					proxy == null ? "~ None ~" : proxy.toString());
			if (success) {
				ClientLauncherGUI.updateConfigurationsCombobox();
				dispose();
			}
		});
		contentPane.add(addButton);

		FButton cancelButton = new FButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		contentPane.add(cancelButton);

		add(contentPane);

		pack();
		setVisible(true);
	}

	private boolean processAddingCongifuration(String labelText, String loginText, String passwordText, String worldText, String scriptNameText, String scriptConfigText, String fpsText, String proxy) {
		if (labelText.isEmpty()) {
			errorDialogue("Label cannot be empty.");
			return false;
		}
		if (labelText.length() > 10) {
			errorDialogue("Label cannot be longer than 10 characters.");
			return false;
		}
		if (!labelText.matches("[a-zA-Z0-9]+")) {
			errorDialogue("Label can only contain alphanumerical characters.");
			return false;
		}
		if (labelText.equals("~ None ~")) {
			errorDialogue("Label cannot be \"~ None ~\".");
			return false;
		}
		if (ConfigurationsTab.getConfiguration(labelText) != null) {
			errorDialogue("Label already exists.");
			return false;
		}
		loginText = loginText.isEmpty() ? null : loginText;
		passwordText = passwordText.isEmpty() ? null : passwordText;
		worldText = worldText.isEmpty() ? null : worldText;
		scriptNameText = scriptNameText.isEmpty() ? null : scriptNameText;
		scriptConfigText = scriptConfigText.isEmpty() ? null : scriptConfigText;
		fpsText = fpsText.isEmpty() ? null : fpsText;

		ConfigurationsTab.addRow(labelText, loginText, passwordText, worldText, scriptNameText, scriptConfigText, fpsText, proxy);
		return true;
	}

	private void errorDialogue(String message) {
		FDialog.createAndShowCustomDialog((JFrame) this.getParent().getParent(), "Error", message);
	}
}
