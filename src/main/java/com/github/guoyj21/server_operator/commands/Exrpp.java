package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Exrpp implements ICommand {
	private String commandLine;

	public Object getCommandLine() {
		return this.commandLine;
	}

	public Exrpp() {
		this.commandLine = "exrpp:rp=all;\r\n";
	}

	public Exrpp(String pa) {
		this.commandLine = "exrpp:rp=" + pa + ";\r\n";
	}

	@Override
	public int result() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Prompt.getCurrentText().indexOf("END") != -1)
				return 1;
		}
	}

}
