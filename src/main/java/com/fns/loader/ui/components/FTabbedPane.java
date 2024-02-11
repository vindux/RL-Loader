package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class FTabbedPane extends JTabbedPane {

	public FTabbedPane() {
		super();
		setBackground(Colors.BODY_COLOR);
		setForeground(Colors.ACCENT_TEXT_COLOR);
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		setFocusable(false);
		setUI(new BasicTabbedPaneUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				highlight = Colors.TEXT_COLOR;
				lightHighlight = Colors.BODY_COLOR.brighter().brighter().brighter();
				shadow = Colors.BODY_COLOR;
				darkShadow = Colors.BODY_COLOR.brighter().brighter().brighter();
				focus = Colors.BODY_COLOR.brighter();
			}
		});
	}
}
