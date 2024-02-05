package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class FTabbedPane extends JTabbedPane {

	public FTabbedPane() {
		super();
		System.out.println("TabbedPane.highlight = " + UIManager.getColor("TabbedPane.highlight"));
		setBackground(Colors.BODY_COLOR);
		setForeground(Colors.ACCENT_TEXT_COLOR);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setFocusable(false);
		setUI(new BasicTabbedPaneUI() {
//			@Override
//			protected void installDefaults() {
//				super.installDefaults();
//				highlight = Colors.BODY_COLOR.brighter();
////				lightHighlight = Colors.BODY_COLOR.brighter();
//				shadow = Colors.BODY_COLOR;
//				darkShadow = Colors.BODY_COLOR.brighter().brighter();
//				focus = Colors.BODY_COLOR.brighter();
//				Field field = FieldUtils.getDeclaredField(BasicTabbedPaneUI.class, "selectedColor", true);
//				field.setAccessible(true);
//				try {
//					field.set(this, Colors.BODY_COLOR.brighter());
////					field.set(this, Color.green);
//				}
//				catch (IllegalAccessException e) {
//					throw new RuntimeException(e);
//				}
//				field.setAccessible(false);
//			}

//			@Override
//			protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
////				super.paintContentBorder(g, tabPlacement, selectedIndex);
//				System.out.println(lightHighlight);
//				System.out.println("TabbedPane.highlight = " + UIManager.getColor("TabbedPane.highlight"));
//			}
		});
	}
}
