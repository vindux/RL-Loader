package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import java.awt.*;

public class FToolTip extends JToolTip {
		public FToolTip(JComponent component) {
			super();
			setComponent(component);
			setBackground(Colors.BODY_COLOR.brighter().brighter());
			setForeground(Colors.TEXT_COLOR.brighter().brighter());
			setBorder(BorderFactory.createLineBorder(Colors.LIST_COLOR.brighter().brighter(), 1, true));
		}
}
