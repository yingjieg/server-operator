package com.github.guoyj21.server_operator.commands;

import com.github.guoyj21.server_operator.shell.Prompt;

public class Blrpe implements ICommand {
	private String rp = "";

	public Blrpe(String[] pa) {
		if (pa.length == 1)
			this.rp = pa[0];
		else {
			for (int i = 0; i < pa.length - 1; i++) {
				this.rp += pa[i] + "&";
			}
			this.rp += pa[pa.length - 1];
		}
	}

	@Override
	public int result() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (Prompt.currentText.indexOf("ORDERED") != -1)
			return 1;
		if (Prompt.currentText.indexOf("FAULT CODE 7") != -1)
			return 3; // rp is not blocked;
		if (Prompt.currentText.indexOf("AB") != -1)
			return 4; // RP AB state
		if (Prompt.currentText.indexOf("FUNCTION BUSY") != -1)
			return 5; // function busy
		return 1;
	}

	@Override
	public Object getCommandLine() {
		String temp = "blrpe:rp=" + this.rp + ";";
		System.out.println(temp);
		return temp;
	}
}
