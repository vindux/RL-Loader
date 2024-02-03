package com.fns.loader.proxy;

import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

@NoArgsConstructor
public class Proxy {
	private String host;
	private String port;
	private String username;
	private String password;

	public Proxy(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public Proxy(String host, String port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public void setup() {
		if (host != null) System.setProperty("socksProxyHost", host);
		if (port != null) System.setProperty("socksProxyPort", port);
		if (username != null) System.setProperty("java.net.socks.username", username);
		if (password != null) System.setProperty("java.net.socks.password", password);

		if (username != null && password != null) {

			final String user = username;
			final char[] passC = password.toCharArray();

			java.net.Authenticator.setDefault(new java.net.Authenticator() {
				private final PasswordAuthentication auth = new PasswordAuthentication(user, passC);

				protected PasswordAuthentication getPasswordAuthentication() {
					return auth;
				}
			});
			System.out.println("\nSetup proxy.");
		}
	}

	public boolean checkIp() {
		StringBuilder response = new StringBuilder();
		try {
			URL url = new URL("https://checkip.amazonaws.com/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String currentIP = response.toString();

		System.out.println("Current IP: " + currentIP);
		System.out.println("IPs match: " + currentIP.equals(host));
		return currentIP.equals(host);
	}
}
