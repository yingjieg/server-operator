package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Exrup implements ICommand {

	private String commandLine;

	public Exrup(String rpNumber) {
		this.commandLine = "exrup:rp=" + rpNumber + ";\r\n";
	}

	public Exrup() {
		this.commandLine = "exrup:rp=all;";
	}

	@Override
	public int result() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (Prompt.getCurrentText().indexOf("END") != -1)
				return 1;
		}
	}

	@Override
	public Object getCommandLine() {
		return this.commandLine;
	}
}
