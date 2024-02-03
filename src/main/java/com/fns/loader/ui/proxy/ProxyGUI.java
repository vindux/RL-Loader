package com.fns.loader.ui.proxy;

import com.fns.loader.FnsProperties;
import com.fns.loader.proxy.Proxy;
import com.fns.loader.ui.components.FDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProxyGUI extends JFrame {
	static JComboBox<String> comboBox;
	static DefaultComboBoxModel<String> comboBoxModel;
	JButton buttonStart;

	private ProxyGUI() {
		setTitle("Fns Proxy Selector Launcher");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(0, 0, 5, 0));

		JPanel proxyPanel = new JPanel();
		proxyPanel.setLayout(new BoxLayout(proxyPanel, BoxLayout.Y_AXIS));

		ProxyTab.setupProxyTab(proxyPanel);

		tabbedPane.add(proxyPanel, "Proxies");
		contentPane.add(tabbedPane);

		JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		startPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.GRAY));
		startPanel.add(new JLabel("Proxy:"));
		startPanel.add(proxies());
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

	private JComboBox<String> proxies() {
		var proxies = ProxyTab.getProxies();
		comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement("~ None ~");
		comboBoxModel.addAll(proxies);
		comboBox = new JComboBox<>(comboBoxModel);
		comboBox.setSelectedIndex(0);
		return comboBox;
	}

	private JButton startButton() {
		buttonStart = new JButton("Start client");
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
