package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FScrollPane extends JScrollPane {

	public FScrollPane(Component view) {
		super(view);
		setBackground(Colors.BODY_COLOR);
		setForeground(Colors.TEXT_COLOR);
		setBorder(new LineBorder(Colors.BODY_COLOR, 0));

		// Set the background color for the viewport
		getViewport().setBackground(Colors.BODY_COLOR);

		// Customize the vertical scrollbar
		JScrollBar verticalScrollBar = new JScrollBar(JScrollBar.VERTICAL);
		verticalScrollBar.setBackground(Colors.BODY_COLOR);
		verticalScrollBar.setForeground(Colors.TEXT_COLOR);
		verticalScrollBar.setPreferredSize(new Dimension(10, 0));
		verticalScrollBar.setOpaque(true);
		verticalScrollBar.setBorder(new LineBorder(Colors.BODY_COLOR, 0));
		verticalScrollBar.setUI(new FScrollbarUI());
		verticalScrollBar.setUnitIncrement(16);
		setVerticalScrollBar(verticalScrollBar);
	}
}
