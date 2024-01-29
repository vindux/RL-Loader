package com.fns.loader;


import com.fns.loader.gui.GUI;

import javax.swing.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author Arnah, EthanVann
 * @since Nov 07, 2020
 */
@SuppressWarnings({"BusyWait", "CallToPrintStackTrace", "InstantiationOfUtilityClass"})
public class Loader {

	public Loader() {
		new Thread(() -> {
			// First we need to grab the ClassLoader the launcher uses to launch the client.
			ClassLoader objClassLoader;
			loop:
			while (true) {
				objClassLoader = (ClassLoader) UIManager.get("ClassLoader");
				if (objClassLoader != null) {
					for (Package pack : objClassLoader.getDefinedPackages()) {
						if (pack.getName().equals("net.runelite.client.rs")) {
							break loop;
						}
					}
				}
				try {
					Thread.sleep(100);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Classloader found");
			try {
				URLClassLoader classLoader = (URLClassLoader) objClassLoader;
				// Add our hijack client to the classloader
				Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				addUrl.setAccessible(true);

				URI uri = Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI();
				if (uri.getPath().endsWith("classes/")) {// Intellij
					uri = uri.resolve("..");
				}
				if (!uri.getPath().endsWith(".jar")) {
					uri = uri.resolve("rl-loader.jar");
				}
				addUrl.invoke(classLoader, uri.toURL());
				System.out.println(uri.getPath());

				// Execute our code inside the runelite client classloader
				Class<?> clazz = classLoader.loadClass(ClientHijack.class.getName());
				clazz.getConstructor().newInstance();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args) {
		// Force disable the "JVMLauncher", was just easiest way to do what I wanted at the time.
		System.setProperty("runelite.launcher.reflect", "true");
		System.out.println(Arrays.toString(args));

//		String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

		ArgParser argParser = new ArgParser();
		Set<String> argSet = argParser.parseAndStore(args);

		if (argSet.contains("--useproxies")) {
			argSet.remove("--useproxies");
			SwingUtilities.invokeLater(GUI::run);
			do {
				sleep(10);
			} while (!FnsProperties.isStart());
		}
		argSet.add("--disable-telemetry");

		args = argSet.toArray(new String[0]);
		System.out.println(Arrays.toString(args));

		new Loader();

		try {
			Class<?> clazz = Class.forName("net.runelite.launcher.Launcher");
			clazz.getMethod("main", String[].class).invoke(null, (Object) args);
		}
		catch (Exception ignored) {
		}
	}

	private static void sleep(int duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}