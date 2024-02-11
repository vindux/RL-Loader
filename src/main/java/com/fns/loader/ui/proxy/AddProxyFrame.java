package com.fns.loader.ui.proxy;

import com.fns.loader.ui.clientlauncher.ClientLauncherGUI;
import com.fns.loader.ui.clientlauncher.ConfigurationsTab;
import com.fns.loader.ui.components.FButton;
import com.fns.loader.ui.components.FLabel;
import com.fns.loader.ui.components.FPanel;
import com.fns.loader.ui.components.FTextField;

import javax.swing.*;
import java.awt.*;

public class AddProxyFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	public AddProxyFrame(FPanel proxyPanel) {
		super((Frame) SwingUtilities.getWindowAncestor(proxyPanel), "Add Proxy", true);
		setPreferredSize(new Dimension(250, 250));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(proxyPanel);
		setResizable(false);

		FPanel contentPane = new FPanel();
		contentPane.setLayout(new GridLayout(7, 2, 5, 5));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		contentPane.add(new FLabel("Label:"));
		FTextField label = new FTextField();
		contentPane.add(label);

		contentPane.add(new FLabel("IP:"));
		FTextField ip = new FTextField();
		contentPane.add(ip);

		contentPane.add(new FLabel("Port:"));
		FTextField port = new FTextField();
		contentPane.add(port);

		contentPane.add(new FLabel("Username:"));
		FTextField user = new FTextField();
		contentPane.add(user);

		contentPane.add(new FLabel("Password:"));
		FTextField pass = new FTextField();
		contentPane.add(pass);

		FLabel emptyLabel = new FLabel();
		contentPane.add(emptyLabel);
		FLabel emptyLabel2 = new FLabel();
		contentPane.add(emptyLabel2);

		FButton addButton = new FButton("Add");
		addButton.addActionListener(e -> {
			boolean success = processAddingProxy(label.getText(), ip.getText(), port.getText(), user.getText(), pass.getText());
			if (success) {
				ProxyGUI.updateProxiesCombobox();
				if (ClientLauncherGUI.isRunning()) {
					ConfigurationsTab.updateProxiesComboBox();
				}
				else {
					System.out.println("ClientLauncherGUI is not running.");
				}
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

	private boolean processAddingProxy(String labelText, String ipText, String portText, String userText, String passText) {
		if (labelText.isEmpty() || ipText.isEmpty() || portText.isEmpty()) {
			errorDialogue("Label, IP, and Port fields cannot be empty.");
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
		if (!ipText.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
			errorDialogue("IP must be in the form of xxx.xxx.xxx.xxx.");
			return false;
		}
		if (!portText.matches("\\d+")) {
			errorDialogue("Port must be a number.");
			return false;
		}
		if (Integer.parseInt(portText) < 1 || Integer.parseInt(portText) > 65535) {
			errorDialogue("Port must be between 1 and 65535.");
			return false;
		}
		if (!userText.isEmpty() && passText.isEmpty()) {
			errorDialogue("If username is specified, password must be specified as well.");
			return false;
		}
		if (userText.isEmpty() && !passText.isEmpty()) {
			errorDialogue("If password is specified, username must be specified as well.");
			return false;
		}
		ProxyTab.addRow(labelText, ipText, portText, userText, passText);
		return true;
	}

	private void errorDialogue(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
