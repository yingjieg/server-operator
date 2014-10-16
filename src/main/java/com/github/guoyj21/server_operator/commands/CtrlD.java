package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class CtrlD implements ICommand {

	@Override
	public int result() {
		if (Prompt.currentText.indexOf("DEBLOCKING PERFORMED") != -1)
			return 2;
		if (Prompt.currentText.indexOf("DEBLOCKING FAILED") != -1)
			return 3;

		return 1;
	}

	@Override
	public Object getCommandLine() {
		return 0x04;
	}

}
