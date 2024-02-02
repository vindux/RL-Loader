package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FButton extends JButton {

	public FButton() {
		this("");
	}

	public FButton(String text) {
		super(text);
		setFocusPainted(false);
		setFocusable(false);
		setBackground(Colors.BUTTON_COLOR);
		setForeground(Colors.BUTTON_TEXT_COLOR);
		setOpaque(true);
		setBorder(new CompoundBorder(new LineBorder(Colors.BUTTON_COLOR.brighter()),
				new EmptyBorder(5, 5, 5, 5)));
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(Colors.BUTTON_COLOR.darker());
		}
		else if (getModel().isRollover()) {
			g.setColor(Colors.BUTTON_COLOR.brighter());
		}
		else {
			g.setColor(Colors.BUTTON_COLOR);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
