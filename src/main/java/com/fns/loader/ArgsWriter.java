package com.fns.loader;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ArgsWriter {
	private String pid;
	private String account;
	private String script;
	private String world;

	private ArgsWriter() {
		// Private constructor to enforce the use of the builder
	}

	public static void setWorld(String world) {
		if (!isNumber(world) && !world.equalsIgnoreCase("f2p") && !world.equalsIgnoreCase("p2p")) {
			exitWithError("Invalid world: " + world + "\nValid worlds are numbers, 'f2p' and 'p2p'");
		}
		if (Integer.parseInt(world) <= 300) {
			exitWithError("Invalid world: " + world + "\nWorlds can't be less than 301.");
		}
		System.setProperty("ap.fns.world", world);
	}

	public static void setAccount(String account) {
		if (!account.contains(":")) {
			exitWithError("Invalid account: " + account + "\nAccounts must be in the format 'login:password'");
		}
		String[] accountArr = account.split(":");
		String login = accountArr[0];
		String password = accountArr[1];
		System.out.println("login: " + login + ", password: " + password);
		System.setProperty("ap.fns.login", login);
		System.setProperty("ap.fns.password", password);
	}

	public static void setScript(String script) {
		System.setProperty("ap.fns.script", script);
	}

	public void writeArgs() {
		if (pid == null || (account == null && script == null)) return;

		try (OutputStream output = new FileOutputStream(new File(System.getProperty("java.io.tmpdir"), pid))) {
			Properties props = new Properties();
			if (account != null) {
				if (!account.contains(":")) {
					exitWithError("Invalid account: " + account + "\nAccounts must be in the format 'login:password'");
				}
				String[] accountArr = account.split(":");
				String login = accountArr[0];
				String password = accountArr[1];
				System.out.println("login: " + login + ", password: " + password);
				props.setProperty("login", login);
				props.setProperty("password", password);
			}
			if (script != null) {
				System.out.println("script: " + script);
				props.setProperty("script", script);
			}
			if (world != null) {
				if (!isNumber(world) && !world.equalsIgnoreCase("f2p") && !world.equalsIgnoreCase("p2p")) {
					exitWithError("Invalid world: " + world + "\nValid worlds are numbers, 'f2p' and 'p2p'");
				}
				if (Integer.parseInt(world) <= 300) {
					exitWithError("Invalid world: " + world + "\nWorlds can't be less than 301.");
				}

				System.out.println("world: " + world);
				props.setProperty("world", world);
			}
			props.store(output, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void exitWithError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	private static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	public static class Builder {

		private final ArgsWriter argsWriter;

		public Builder(String pid) {
			argsWriter = new ArgsWriter();
			argsWriter.pid = pid;
		}

		public void withAccount(String account) {
			argsWriter.account = account;
		}

		public void withScript(String script) {
			argsWriter.script = script;
		}

		public void withWorld(String world) {
			argsWriter.world = world;
		}

		public ArgsWriter build() {
			return argsWriter;
		}

	}
}
