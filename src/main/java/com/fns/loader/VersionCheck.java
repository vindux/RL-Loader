package com.fns.loader;

import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@NoArgsConstructor
public class VersionCheck {

	public void check() {
		int liveVersion = getLiveVersion();
		if (liveVersion > FnsProperties.VERSION) {
			exitWithUpdateMessage();
		}
	}

	private void exitWithUpdateMessage() {
		String msg = "New version available.";
		String msg1 = "> Rerun the patcher to update.";
		String msg2 = "> Link can be found in the discord #info channel.";

		JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Loader Outdated");
		dialog.setIconImage(new ImageIcon(Objects.requireNonNull(Loader.class.getResource("/icon.png"))).getImage());
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.setSize(300, 150);
		dialog.setLayout(new FlowLayout());

		dialog.add(new JTextArea(msg + "\n" + msg1 + "\n" + msg2) {{
			setEditable(false);
			setBackground(dialog.getBackground());
			setBorder(new EmptyBorder(5, 5, 10, 5));
		}});
		dialog.add(new JLabel(" "));

		dialog.add(new JButton("OK") {{
			addActionListener(e -> dialog.dispose());
		}});

		dialog.setVisible(true);
		System.exit(0);
	}

	private int getLiveVersion() {
		StringBuilder response = new StringBuilder();
		try {
			URL url = new URL(FnsProperties.VERSION_LIVE);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();

			int version = Integer.parseInt(response.toString());
			System.out.println("Live version: " + version);
			return version;
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}
}
