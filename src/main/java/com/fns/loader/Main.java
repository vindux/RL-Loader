package com.fns.loader;

import javax.management.MXBean;
import javax.swing.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

public class Main extends JFrame {

	public static void main(String[] args) throws IOException, URISyntaxException {
		System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

		ProcessBuilder processBuilder = new ProcessBuilder();
		var path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replace("rl-loader.jar", "");
		System.out.println(path);
		processBuilder.command(path + "RuneLite.exe", "--account=test:tomtato", "--script=Agility:Varrock", "--world=301", "--fps=50");
		processBuilder.start();
	}

}
