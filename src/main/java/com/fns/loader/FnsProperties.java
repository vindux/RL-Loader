package com.fns.loader;

import lombok.Getter;
import lombok.Setter;


public class FnsProperties {
	public static final int VERSION = 1;
	public static final String VERSION_LIVE = "https://raw.githubusercontent.com/vindux/RL-Loader/main/src/main/java/com/fns/loader/VERSION.txt";
	@Getter @Setter private static boolean start = false;
}
