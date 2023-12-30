package com.fns.loader;

import lombok.Getter;
import lombok.Setter;


public class FnsProperties {
	@Getter @Setter private static String ip = null;
	@Getter @Setter private static String port = null;
	@Getter @Setter private static String user = null;
	@Getter @Setter private static String pass = null;
	@Getter @Setter private static boolean start = false;
}
