package com.fns.loader.ui.test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T extends JFrame {

	private final JPanel left;
	private final JPanel middle;
	private final JPanel right;
	private final JPanel bottom;

	static List<JTextField> textfields = new ArrayList<>();
	static {
		for (int i = 0; i < 11; i++) {
			textfields.add(new JTextField());
		}
	}
	Map<String, List<String>> data = Map.of(
			"1", List.of("label1", "login1", "password1", "world1", "maxfps1", "scriptname1", "scriptconfig1", "ip1", "port1", "username1", "password1"),
			"2", List.of("label2", "login2", "password2", "world2", "maxfps2", "scriptname2", "scriptconfig2", "ip2", "port2", "username2", "password2")
	);

	public T() {
		setTitle("T");
		setSize(420, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		left = new JPanel();
		left.setBackground(Color.gray);
		left.setPreferredSize(new Dimension(112, 409));

		middle = new JPanel();
		middle.setBackground(Color.lightGray);
		middle.setPreferredSize(new Dimension(90, 409));

		right = new JPanel();
		right.setBackground(Color.gray);
		right.setPreferredSize(new Dimension(190, 409));

		bottom = new JPanel();
		bottom.setBackground(Color.darkGray);
		bottom.setPreferredSize(new Dimension(392, 40));

		gbc.gridwidth = 3;
		add(left, gbc);
		add(middle, gbc);
		add(right, gbc);

		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.CENTER;
		add(bottom, gbc);

		setupPanels();

		setVisible(true);
	}

	public static void main(String[] args) {
		new T();
	}

	private void setupPanels() {
		setupLeft();
		setupMiddle();
		setupRight();
		setupBottom();
	}

	private void setupLeft() {
		JList<String> list = new JList<>(new String[]{"1", "2"});
		list.addListSelectionListener(e -> {
			var sel = list.getSelectedValue();
			System.out.println("selected = " + sel);
			updateTextFields(sel);
		});
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.createHorizontalScrollBar();
		Dimension preferred = left.getPreferredSize();
		jScrollPane.setPreferredSize(new Dimension(preferred.width - 10, preferred.height - 10));
		left.add(jScrollPane);
	}

	private void setupMiddle() {
		JPanel inner = new JPanel();
		var p = middle.getPreferredSize();
		inner.setPreferredSize(new Dimension(p.width - 5, p.height - 50));
		int rows = textfields.size() + 1;
		var layout = new GridLayout(rows, 1, 0, 10);
		inner.setLayout(layout);
		inner.add(new JLabel("Label"));
		inner.add(new JLabel("Login"));
		inner.add(new JLabel("Password"));
		inner.add(new JLabel("World"));
		inner.add(new JLabel("Max Fps"));
		inner.add(new JLabel("Script Name"));
		inner.add(new JLabel("Script Config"));
		inner.add(new JLabel("Proxy"));
		inner.add(new JLabel("IP"));
		inner.add(new JLabel("Port"));
		inner.add(new JLabel("Username"));
		inner.add(new JLabel("Password"));
		middle.add(inner);
	}

	private void setupRight() {
		JPanel inner = new JPanel();
		var p = right.getPreferredSize();
		inner.setPreferredSize(new Dimension(p.width - 5, p.height - 50));
		int rows = textfields.size() + 1;
		var layout = new GridLayout(rows, 1, 0, 10);
		inner.setLayout(layout);

		// setup text fields
		for (int i = 0; i < 11; i++) {
			textfields.set(i, new JTextField());
		}

		inner.add(textfields.get(0)); // Label
		inner.add(textfields.get(1)); // Login
		inner.add(textfields.get(2)); // Password
		inner.add(textfields.get(3)); // World
		inner.add(textfields.get(4)); // Max Fps
		inner.add(textfields.get(5)); // Script Name
		inner.add(textfields.get(6)); // Script Config
		inner.add(new JLabel()); // Proxy
		inner.add(textfields.get(7)); // IP
		inner.add(textfields.get(8)); // Port
		inner.add(textfields.get(9)); // Username
		inner.add(textfields.get(10)); // Password
		right.add(inner);
	}

	private void setupBottom() {

	}

	private void updateTextFields(String sel) {
		List<String> list = data.get(sel);
		for (int i = 0; i < list.size(); i++) {
			textfields.get(i).setText(list.get(i));
		}
	}
}
