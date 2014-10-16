package com.github.guoyj21.server_operator.shell;

import com.github.guoyj21.server_operator.io.SessionOperation;

public class Prompt {

	private static String lastOutput;

	public static StringBuffer currentText = new StringBuffer();

	public static PromptType getPrompt(SessionOperation s) {

		if (lastOutput.indexOf('>') != -1 && lastOutput.indexOf("OSmon") == -1)
			return PromptType.AP;
		if (lastOutput.indexOf("OSmon") != -1)
			return PromptType.OSmon;
		if (lastOutput.indexOf('<') != -1)
			return PromptType.MML;

		s.runCmd("\n");

		return Prompt.getPrompt(s);

	}

	public static void setMML(SessionOperation s) {
		PromptType pt = Prompt.getPrompt(s);
		if (pt == PromptType.AP)
			s.runCmd("mml -ac\r\n");
		if (pt == PromptType.OSmon)
			s.runCmd("end\r\n");
	}

	public static void setAP(SessionOperation s) {
		PromptType pt = Prompt.getPrompt(s);
		if (pt == PromptType.MML)
			s.runCmd("exit;\r\n");
		if (pt == PromptType.OSmon) {
			s.runCmd("end\r\n");
			s.runCmd("exit;\r\n");
		}
	}

	public static void setOSmon(SessionOperation s, int rpNumber) {
		PromptType pt = Prompt.getPrompt(s);
		if (pt == PromptType.AP) {
			s.runCmd("mml -c\r\n");
			s.runCmd("terdi:rp=" + rpNumber + ";\r\n");
		}
		if (pt == PromptType.MML)
			s.runCmd("terdi:rp=" + rpNumber + ";\r\n");
	}

	public static String getLastOutput() {
		return lastOutput;
	}

	public static void setLastOutput(String lastOutput) {
		Prompt.lastOutput = lastOutput;
	}

	public synchronized static StringBuffer getCurrentText() {
		return currentText;
	}

	public synchronized static void setCurrentText(String line) {
		Prompt.currentText.append(line);
	}
}
