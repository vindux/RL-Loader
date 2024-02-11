package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FDialog {
	public static int YES_OPTION = 1;
	public static int NO_OPTION = 0;

	public static void createAndShowCustomDialog(JFrame parentComponent, String title, String... messages) {
		JDialog customDialog = new JDialog(parentComponent, true);
		customDialog.setTitle(title);
		int lineHeight = new JLabel().getFontMetrics(new JLabel().getFont()).getHeight();
		customDialog.setSize(300, 130 + messages.length * lineHeight);
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FPanel panel = new FPanel(new GridLayout(0, 1));
		panel.setBackground(Colors.BODY_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));

		for (String message : messages) {
			FLabel label = new FLabel(message);
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}

		FPanel buttonPanel = new FPanel(new FlowLayout(FlowLayout.CENTER));
		FButton closeButton = new FButton("Ok.");
		closeButton.setPreferredSize(new Dimension(100, 30));
		closeButton.addActionListener(e -> customDialog.dispose());
		buttonPanel.add(closeButton);
		panel.add(buttonPanel);

		customDialog.add(panel);
		customDialog.setLocationRelativeTo(parentComponent);
		customDialog.setVisible(true);
	}

	public static int createAndShowCustomConfirmDialogue(JFrame parentComponent, String title, String... messages) {
		AtomicInteger choice = new AtomicInteger(NO_OPTION);

		JDialog customDialog = new JDialog(parentComponent, true);
		customDialog.setTitle(title);
		int lineHeight = new JLabel().getFontMetrics(new JLabel().getFont()).getHeight();
		customDialog.setSize(300, 130 + messages.length * (lineHeight + 5));
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FPanel panel = new FPanel(new GridLayout(0, 1));
		panel.setBackground(Colors.BODY_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (String message : messages) {
			FLabel label = new FLabel(message);
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
		}

		FPanel buttonPanel = new FPanel(new FlowLayout(FlowLayout.CENTER));
		FButton confirmButton = new FButton("Yes");
		confirmButton.setPreferredSize(new Dimension(100, 30));
		confirmButton.addActionListener(e -> {
			customDialog.dispose();
			choice.set(YES_OPTION);
		});
		buttonPanel.add(confirmButton);
		FButton cancelButton = new FButton("No");
		cancelButton.setPreferredSize(new Dimension(100, 30));
		cancelButton.addActionListener(e -> customDialog.dispose());
		buttonPanel.add(cancelButton);
		panel.add(buttonPanel);

		customDialog.add(panel);
		customDialog.setLocationRelativeTo(parentComponent);
		customDialog.setVisible(true);

		return choice.get();
	}

}
