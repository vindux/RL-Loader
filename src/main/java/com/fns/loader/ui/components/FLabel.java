package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;

public class FLabel extends JLabel {

	public FLabel() {
		this("");
	}

	public FLabel(String text) {
		super(text);
		setOpaque(false);
		setForeground(Colors.TEXT_COLOR);
	}
}
