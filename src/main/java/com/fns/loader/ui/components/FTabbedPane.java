package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.lang.reflect.Field;

public class FTabbedPane extends JTabbedPane {

	public FTabbedPane() {
		super();
		setBackground(Colors.BODY_COLOR);
		setForeground(Colors.ACCENT_TEXT_COLOR);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setFocusable(false);
		setUI(new BasicTabbedPaneUI() {
			@Override
			protected void installDefaults() {
				super.installDefaults();
				highlight = Color.magenta;
				lightHighlight = Colors.TEXT_COLOR;
				shadow = Colors.BODY_COLOR;
				darkShadow = Colors.TEXT_COLOR;
				focus = Color.green;
				Field field = FieldUtils.getDeclaredField(BasicTabbedPaneUI.class, "selectedColor", true);
//				System.out.println(FieldUtils.getAllFieldsList(BasicTabbedPaneUI.class));
				field.setAccessible(true);
				try {
					field.set(this, Colors.BODY_COLOR.brighter());
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				field.setAccessible(false);
			}
		});
	}
}
