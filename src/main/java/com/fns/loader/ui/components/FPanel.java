package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import java.awt.*;

public class FPanel extends JPanel {

	public FPanel() {
		super();
		setBackground(Colors.BODY_COLOR);
	}

	public FPanel(LayoutManager layout) {
		super(layout);
		setBackground(Colors.BODY_COLOR);
	}
}
