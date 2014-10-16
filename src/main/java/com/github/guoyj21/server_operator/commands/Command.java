package com.github.guoyj21.server_operator.commands;

public class Command implements ICommand {
	private String commandLine;

	public Object getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	public int result() {
		return 1;
	}
}
