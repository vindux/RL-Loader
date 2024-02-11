package com.fns.loader.ui.proxy;

import com.fns.loader.FnsProperties;
import com.fns.loader.proxy.Proxy;
import com.fns.loader.ui.Colors;
import com.fns.loader.ui.components.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProxyGUI extends JFrame {
	static FComboBox<String> comboBox;
	static DefaultComboBoxModel<String> comboBoxModel;
	FButton buttonStart;

	private ProxyGUI() {
		setTitle("Fns Proxy Selector Launcher");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 400);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

		FPanel contentPane = new FPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
		setContentPane(contentPane);

		FPanel proxyPanel = new FPanel();
		proxyPanel.setLayout(new BoxLayout(proxyPanel, BoxLayout.Y_AXIS));
		TitledBorder border = BorderFactory.createTitledBorder(new LineBorder(Colors.LIST_COLOR.brighter(), 1, true), "Proxies");
		border.setTitleColor(Colors.ACCENT_TEXT_COLOR);
		proxyPanel.setBorder(new CompoundBorder(border, BorderFactory.createEmptyBorder(0, 7, 0, 7)));
		ProxyTab.setupProxyTab(this, proxyPanel);
		contentPane.add(proxyPanel);

		FPanel startPanel = new FPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		startPanel.add(new FLabel("Proxy:"));
		startPanel.add(getProxiesComboBox());
		startPanel.add(startButton());

		contentPane.add(startPanel);

		revalidate();
		repaint();

		// Displaying the window
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		SwingUtilities.invokeLater(ProxyGUI::new);
	}

	public static void updateProxyCombobox() {
		var proxies = ProxyTab.getProxies();
		SwingUtilities.invokeLater(() -> {
			comboBoxModel.removeAllElements();
			comboBoxModel.addElement("~ None ~");
			comboBoxModel.addAll(proxies);
			comboBox.revalidate();
			comboBox.repaint();
		});
	}

	public static FComboBox<String> getProxiesComboBox() {
		var proxies = ProxyTab.getProxies();
		comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("~ None ~");
		comboBoxModel.addAll(proxies);
		comboBox = new FComboBox<>(comboBoxModel);
		comboBox.setSelectedIndex(0);
		return comboBox;
	}

	private JButton startButton() {
		buttonStart = new FButton("Start client");
		buttonStart.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				try {
					String proxyLabel = (String) comboBox.getSelectedItem();
					var proxy = ProxyTab.getProxy(proxyLabel);
					this.dispose();
					if (proxy == null) {
						launch(null, null, null, null);
					}
					else {
						launch(proxy[1], proxy[2], proxy[3], proxy[4]);
					}
				}
				catch (IOException | ExecutionException | InterruptedException executionException) {
					executionException.printStackTrace();
				}
			});
		});
		return buttonStart;
	}

	private void launch(String ip, String port, String user, String pass) throws IOException, ExecutionException, InterruptedException {
		if (ip != null && !ip.isEmpty() && port != null && !port.isEmpty()) {
			System.out.println("Setting up proxy with ip: " + ip + " port: " + port + " user: " + user + " pass: " + pass);
			new Thread(() -> {
				Proxy proxy = new Proxy(ip, port, user, pass);
				proxy.setup();
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException ignored) {
				}
				if (!proxy.checkIp()) {
					List<String> msg = new ArrayList<>();
					msg.add("Could not connect with this proxy: ");
					msg.add(ip + ":" + port);
					if (user != null && pass != null) {
						msg.set(1, msg.get(1) +":" + user + ":" + pass);
					}
					FDialog.createAndShowCustomDialog(this, "Proxy Error", msg.toArray(new String[0]));
					System.exit(0);
				}
				else {
					FnsProperties.setStart(true);
				}
			}).start();
		}
		else {
			System.out.println("No proxy selected.");
			FnsProperties.setStart(true);
		}
	}
}
