package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import java.awt.*;

public class FDialog {
	public static void createAndShowCustomDialog(JFrame parentComponent, String message, String title) {
		JDialog customDialog = new JDialog(parentComponent, title, true);
		customDialog.setTitle(title);
		customDialog.setSize(300, 150);
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FPanel panel = new FPanel(new BorderLayout());
		panel.setBackground(Colors.BODY_COLOR); // Set your desired background color
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
}
