package com.fns.loader;

import net.runelite.client.RuneLite;

/**
 * @author Arnah
 * @since Nov 07, 2020
 */
@SuppressWarnings({"BusyWait", "CallToPrintStackTrace"})
public class ClientHijack {

	public ClientHijack() {
		System.out.println("Client hijacked");
		new Thread(() -> {
			while (RuneLite.getInjector() == null) {
				try {
					Thread.sleep(100);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Injector found");
			RuneLite.getInjector().getInstance(HijackedClientBackup.class).start();
		}).start();
	}
}