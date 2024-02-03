package com.fns.loader.ui.clientlauncher;

import com.fns.loader.ui.components.*;

import javax.swing.*;
import java.awt.*;

public class AddConfigurationFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	public AddConfigurationFrame(FPanel configurationPanel) {
		super((Frame) SwingUtilities.getWindowAncestor(configurationPanel), "Add Configuration", true);
		setPreferredSize(new Dimension(250, 250));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(configurationPanel);
		setResizable(false);

		FPanel contentPane = new FPanel();
		contentPane.setLayout(new GridLayout(9, 2, 5, 5));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		FLabel label = new FLabel("Label:");
		label.setToolTipText("The name of the configuration");
		contentPane.add(label);
		FTextField labelField = new FTextField();
		labelField.setFont(new Font("Arial", Font.PLAIN, 11));
		labelField.setToolTipText("The name of the configuration");
		contentPane.add(labelField);

		FLabel login = new FLabel("Login:");
		login.setToolTipText("The login of the RuneScape Account");
		contentPane.add(login);
		FTextField loginField = new FTextField();
		loginField.setFont(new Font("Arial", Font.PLAIN, 11));
		loginField.setToolTipText("The login of the RuneScape Account");
		contentPane.add(loginField);

		FLabel password = new FLabel("Password:");
		password.setToolTipText("The password of the RuneScape Account");
		contentPane.add(password);
		FTextField passwordField = new FTextField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordField.setToolTipText("The password of the RuneScape Account");
		contentPane.add(passwordField);

		FLabel world = new FLabel("World:");
		world.setToolTipText("The world to connect to");
		contentPane.add(world);
		FTextField worldField = new FTextField();
		worldField.setFont(new Font("Arial", Font.PLAIN, 11));
		worldField.setToolTipText("The world to connect to");
		contentPane.add(worldField);

		FLabel scriptName = new FLabel("Script Name:");
		scriptName.setToolTipText("The name of the script to run");
		contentPane.add(scriptName);
		FTextField scriptNameField = new FTextField();
		scriptNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		scriptNameField.setToolTipText("The name of the script to run");
		contentPane.add(scriptNameField);

		FLabel scriptConfig = new FLabel("Script Config:");
		scriptConfig.setToolTipText("The configuration of the script to run");
		contentPane.add(scriptConfig);
		FTextField scriptConfigField = new FTextField();
		scriptConfigField.setFont(new Font("Arial", Font.PLAIN, 11));
		scriptConfigField.setToolTipText("The configuration of the script to run");
		contentPane.add(scriptConfigField);

		FLabel fps = new FLabel("FPS:");
		fps.setToolTipText("The max FPS to run the client at");
		contentPane.add(fps);
		FTextField fpsField = new FTextField();
		fpsField.setFont(new Font("Arial", Font.PLAIN, 11));
		fpsField.setToolTipText("The max FPS to run the client at");
		contentPane.add(fpsField);

		FLabel emptyLabel = new FLabel();
		contentPane.add(emptyLabel);
		FLabel emptyLabel2 = new FLabel();
		contentPane.add(emptyLabel2);

		FButton addButton = new FButton("Add");
		addButton.addActionListener(e -> {
			boolean success = processAddingCongifuration(
					labelField.getText(),
					loginField.getText(),
					passwordField.getText(),
					worldField.getText(),
					scriptNameField.getText(),
					scriptConfigField.getText(),
					fpsField.getText());
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

	private boolean processAddingCongifuration(String labelText, String loginText, String passwordText, String worldText, String scriptNameText, String scriptConfigText, String fpsText) {
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

		ConfigurationsTab.addRow(labelText, loginText, passwordText, worldText, scriptNameText, scriptConfigText, fpsText);
		return true;
	}

	private void errorDialogue(String message) {
		FDialog.createAndShowCustomDialog((JFrame) this.getParent().getParent(), "Error", message);
	}
}
