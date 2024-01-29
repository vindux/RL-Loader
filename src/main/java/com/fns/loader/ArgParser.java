package com.fns.loader;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class ArgParser {

	public Set<String> parseAndStore(String[] args) {
		ArgsWriter argsWriter = new ArgsWriter();
		Set<String> argSet = new HashSet<>(Arrays.asList(args));

		String accountArg;
		if ((accountArg = argSet.stream().filter(s -> s.startsWith("--account")).findFirst().orElse(null)) != null) {
			argsWriter.writeAccount(accountArg.replaceAll("--account=", ""));
			argSet.remove(accountArg);
		}
		String scriptArg;
		if ((scriptArg = argSet.stream().filter(s -> s.startsWith("--script")).findFirst().orElse(null)) != null) {
			argsWriter.writeScript(scriptArg.replaceAll("--script=", ""));
			argSet.remove(scriptArg);
		}
		String worldArg;
		if ((worldArg = argSet.stream().filter(s -> s.startsWith("--world")).findFirst().orElse(null)) != null) {
			argsWriter.writeWorld(worldArg.replaceAll("--world=", ""));
			argSet.remove(worldArg);
		}
		String fpsArg;
		if ((fpsArg = argSet.stream().filter(s -> s.startsWith("--fps")).findFirst().orElse(null)) != null) {
			argsWriter.writeFps(fpsArg.replaceAll("--fps=", ""));
			argSet.remove(fpsArg);
		}


		return argSet;
	}
}
