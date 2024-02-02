package com.fns.loader.ui.proxy;

import javax.swing.*;
import java.awt.*;

public class AddProxyFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	public AddProxyFrame(JPanel proxyPanel) {
		super((Frame) SwingUtilities.getWindowAncestor(proxyPanel), "Add Proxy", true);
		setPreferredSize(new Dimension(250, 250));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(proxyPanel);
		setResizable(false);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(7, 2, 5, 5));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		contentPane.add(new JLabel("Label:"));
		JTextField label = new JTextField();
		contentPane.add(label);

		contentPane.add(new JLabel("IP:"));
		JTextField ip = new JTextField();
		contentPane.add(ip);

		contentPane.add(new JLabel("Port:"));
		JTextField port = new JTextField();
		contentPane.add(port);

		contentPane.add(new JLabel("Username:"));
		JTextField user = new JTextField();
		contentPane.add(user);

		contentPane.add(new JLabel("Password:"));
		JTextField pass = new JTextField();
		contentPane.add(pass);

		JLabel emptyLabel = new JLabel();
		contentPane.add(emptyLabel);
		JLabel emptyLabel2 = new JLabel();
		contentPane.add(emptyLabel2);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(e -> {
			boolean success = processAddingProxy(label.getText(), ip.getText(), port.getText(), user.getText(), pass.getText());
			if (success) {
				ProxyGUI.updateProxyCombobox();
				dispose();
			}
		});
		contentPane.add(addButton);

		JButton cancelButton = new JButton("Cancel");
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
