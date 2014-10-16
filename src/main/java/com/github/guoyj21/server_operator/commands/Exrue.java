package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Exrue implements ICommand {

	private String cmdline;

	public Exrue(String rpNumber, String suid) {
		this.cmdline = "exrue:rp=" + rpNumber + ",suid=\"" + suid + "\";\r\n";
	}

	@Override
	public int result() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// INDICATED RP IS NOT MANUALLY BLOCKED
		if (Prompt.currentText.indexOf("FAULT CODE 6") != -1)
			return 6;

		// INDICATED SU (SOFTWARE UNIT) DOES NOT EXIST
		if (Prompt.currentText.indexOf("FAULT CODE 45") != -1)
			return 45;

		// EQUIPMENT IS DEFINED FOR A SOFTWARE UNIT
		if (Prompt.currentText.indexOf("FAULT CODE 67") != -1)
			return 67;

		if (Prompt.currentText.indexOf("EXECUTED") != -1)
			return 1;

		return 0;
	}

	@Override
	public Object getCommandLine() {
		return this.cmdline;
	}

}
