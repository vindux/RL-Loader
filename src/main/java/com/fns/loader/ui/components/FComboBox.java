package com.fns.loader.ui.components;

import com.fns.loader.ui.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class FComboBox<E> extends JComboBox<E> {

	public FComboBox(ComboBoxModel<E> model) {
		super(model);
		setOpaque(true);
		setBackground(Colors.BODY_COLOR.brighter());
		setForeground(Colors.TEXT_COLOR);

		// Set custom renderer for the combo box items
		setRenderer(new CustomComboBoxRenderer<>());

		// Customize the scrollbar
		customizeScrollBar(new JScrollBar(JScrollBar.VERTICAL));

		// Set the custom combo box UI
		setUI(new CustomComboBoxUI());
	}

	private void customizeScrollBar(JScrollBar scrollBar) {
		scrollBar.setUI(new FScrollbarUI());
		scrollBar.setBackground(Colors.BODY_COLOR.brighter());
		scrollBar.setForeground(Colors.TEXT_COLOR);
	}

	private static class CustomComboBoxRenderer<E> extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (isSelected || cellHasFocus) {
				component.setBackground(Colors.BODY_COLOR.darker());
				component.setForeground(Colors.TEXT_COLOR);
			}
			else {
				component.setBackground(Colors.BODY_COLOR.brighter());
				component.setForeground(Colors.TEXT_COLOR);
			}

			return component;
		}
	}

	private static class CustomComboBoxUI extends BasicComboBoxUI {

		@Override
		protected JButton createArrowButton() {
			JButton jButton = new JButton() {
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					int width = getWidth();
					int height = getHeight();

					g2.setColor(Colors.BODY_COLOR.brighter());
					g2.fillRect(0, 0, width, height);

					g2.setColor(Colors.TEXT_COLOR.darker());
					g2.setStroke(new BasicStroke(2f));

					int arrowSize = 8;
					int x = (width - arrowSize) / 2;
					int y = (height - arrowSize) / 2;

					Polygon arrow = new Polygon();
					arrow.addPoint(x, y);
					arrow.addPoint(x + arrowSize, y);
					arrow.addPoint(x + arrowSize / 2, y + arrowSize);

					g2.fill(arrow);
				}
			};
			jButton.setBorder(BorderFactory.createEmptyBorder());
			return jButton;
		}

		@Override
		public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(Colors.BODY_COLOR.brighter());
			g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

			g2.setColor(Colors.BODY_COLOR.brighter().brighter());
			g2.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
			super.paintCurrentValue(g, bounds, false);
		}

		@Override
		protected ComboPopup createPopup() {
			BasicComboPopup popup = new BasicComboPopup(comboBox) {
				@Override
				protected JScrollPane createScroller() {
					// Use your custom scrollbar here
					JScrollBar customScrollBar = new JScrollBar(JScrollBar.VERTICAL);
					customScrollBar.setBackground(Colors.BODY_COLOR.brighter());
					customScrollBar.setForeground(Colors.TEXT_COLOR);
					customScrollBar.setUI(new FScrollbarUI());
					customScrollBar.setPreferredSize(new Dimension(10, 0));
					customScrollBar.setUnitIncrement(16);

					JScrollPane scroller = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scroller.setBorder(BorderFactory.createEmptyBorder());
					scroller.setVerticalScrollBar(customScrollBar);
					return scroller;
				}
			};
			popup.getAccessibleContext().setAccessibleParent(comboBox);
			return popup;
		}

	}
}
