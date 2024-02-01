package com.fns.loader;

import lombok.NoArgsConstructor;

import javax.swing.JOptionPane;

@NoArgsConstructor
public class ArgsWriter {

	public void writeWorld(String world) {
		if (!isNumber(world) && !world.equalsIgnoreCase("f2p") && !world.equalsIgnoreCase("p2p")) {
			exitWithError("Invalid world: " + world + "\nValid worlds are numbers, 'f2p' and 'p2p'");
		}
		if (Integer.parseInt(world) <= 300) {
			exitWithError("Invalid world: " + world + "\nWorlds can't be less than 301.");
		}
		System.setProperty("ap.fns.world", world);
		System.out.println("world: " + world);
	}

	public void writeAccount(String account) {
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

	public void writeScript(String script) {
		var s = script.split(":");
		var scriptName = s[0];
		var scriptConfig = s.length > 1 ? s[1] : "";

		System.setProperty("ap.fns.script", scriptName);
		if (script.contains(":")) {
			System.setProperty("ap.fns.scriptConfig", scriptConfig);
		}

		System.out.println("script: " + scriptName + ", config: " + scriptConfig);
	}

	public void writeFps(String fps) {
		if (!isNumber(fps)) {
			exitWithError("Invalid fps: " + fps + "\nFps must be a number between 5 and 50.");
		}
		int fpsInt = Integer.parseInt(fps);
		if (fpsInt < 5 || fpsInt > 50) {
			exitWithError("Invalid fps: " + fps + "\nFps must be a number between 5 and 50.");
		}
		System.out.println("fps: " + fps);
		System.setProperty("ap.fns.fps", fps);
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
}
