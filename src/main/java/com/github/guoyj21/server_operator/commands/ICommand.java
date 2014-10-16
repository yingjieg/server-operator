package com.github.guoyj21.server_operator.commands;

public interface ICommand {
	public int result();

	public Object getCommandLine();
}
