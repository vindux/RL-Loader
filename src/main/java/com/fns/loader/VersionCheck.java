package com.fns.loader;

import com.fns.loader.ui.components.FDialog;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
		String msg1 = "Rerun the patcher to update.";
		String msg2 = "Link can be found in the discord #info channel.";

		FDialog.createAndShowCustomDialog(null, JLabel.LEFT, "Loader Outdated", msg, msg1, msg2);
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
			// System.out.println("Live version: " + version);
			return version;
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}
}
