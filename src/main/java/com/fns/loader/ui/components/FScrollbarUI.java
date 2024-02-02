package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class FScrollbarUI extends BasicScrollBarUI {

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(0, 0);
			}
		};
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(0, 0);
			}
		};
	}

	@Override
	protected void configureScrollBarColors() {
		thumbColor = Colors.BODY_COLOR.brighter();
		thumbDarkShadowColor = thumbColor.darker();
		thumbHighlightColor = thumbColor.brighter();
		thumbLightShadowColor = thumbColor.darker();
		trackColor = Colors.BODY_COLOR.darker();
		trackHighlightColor = trackColor.brighter();
	}
}