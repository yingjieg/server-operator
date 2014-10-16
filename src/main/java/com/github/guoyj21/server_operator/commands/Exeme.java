package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Exeme implements ICommand {
	private String commandLine;

	public Exeme(int em, String rpNumber) {
		if (em == -1)
			this.commandLine = "\r\n";
		else
			this.commandLine = "exeme:rp=" + rpNumber + ",em=" + em + ";\r\n";
	}

	@Override
	public int result() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// UNREASONABLE OR TOO HIGH EM-ADDRESS
		if (Prompt.currentText.indexOf("FAULT CODE 10") != -1)
			return 10;

		// INDICATED EM DOES NOT EXIST
		if (Prompt.currentText.indexOf("FAULT CODE 11") != -1)
			return 11;

		// RP NOT BLOCKED
		if (Prompt.currentText.indexOf("FAULT CODE 35") != -1)
			return 35;

		if (Prompt.currentText.indexOf("EXECUTED") != -1)
			return 1;

		// other condition.
		System.out.println(Prompt.currentText);
		return 0;
	}

	@Override
	public Object getCommandLine() {
		return this.commandLine;
	}
}
