package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import java.awt.*;

public class FDialog {
	public static void createAndShowCustomDialog(JFrame parentComponent, String title, String message) {
		JDialog customDialog = new JDialog(parentComponent, true);
		customDialog.setTitle(title);
		customDialog.setSize(300, 150);
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FPanel panel = new FPanel(new BorderLayout());
		panel.setBackground(Colors.BODY_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));

		FLabel label = new FLabel(message);
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, BorderLayout.CENTER);

		FPanel buttonPanel = new FPanel();
		FButton closeButton = new FButton("Ok.");
		closeButton.setPreferredSize(new Dimension(100, 30));
		closeButton.addActionListener(e -> customDialog.dispose());
		buttonPanel.add(closeButton);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		customDialog.add(panel);
		customDialog.setLocationRelativeTo(parentComponent);
		customDialog.setVisible(true);
	}

	public static void createAndShowCustomDialog(JFrame parentComponent, String title, String... messages) {
		JDialog customDialog = new JDialog(parentComponent, true);
		customDialog.setTitle(title);
		int lineHeight = new JLabel().getFontMetrics(new JLabel().getFont()).getHeight();
		customDialog.setSize(300, 130 + messages.length * lineHeight);
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FPanel panel = new FPanel(new GridLayout(messages.length + 1, 1));
		panel.setBackground(Colors.BODY_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));

		for (String message : messages) {
			FLabel label = new FLabel(message);
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}

		FButton closeButton = new FButton("Ok.");
		closeButton.setPreferredSize(new Dimension(100, 30));
		closeButton.addActionListener(e -> customDialog.dispose());
		panel.add(closeButton);

		customDialog.add(panel);
		customDialog.setLocationRelativeTo(parentComponent);
		customDialog.setVisible(true);
	}
}
