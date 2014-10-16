package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Blrpi implements ICommand {
	private String rpNumber;

	public Blrpi(String num) {
		this.rpNumber = num;
	}

	@Override
	public int result() {
		// rp already blocked
		if (Prompt.currentText.indexOf("FAULT CODE 103") != -1)
			return 103;
		return 1;
	}

	@Override
	public Object getCommandLine() {
		String temp = "blrpi:rp=" + this.rpNumber + ";";
		return temp;
	}
}
