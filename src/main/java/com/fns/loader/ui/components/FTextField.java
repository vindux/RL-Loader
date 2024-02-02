package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FTextField extends JTextField {

	public FTextField() {
		super();
		setOpaque(true);
		setBackground(Colors.TEXTFIELD_COLOR);
		setCaretColor(Colors.TEXT_COLOR);
		setForeground(Colors.TEXT_COLOR);
		setSelectionColor(Colors.TEXT_COLOR.darker().darker());
		setBorder(new CompoundBorder(new LineBorder(Colors.LIST_COLOR.brighter()), new EmptyBorder(2,2,2,2)));
	}
}
