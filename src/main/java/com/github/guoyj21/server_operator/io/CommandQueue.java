package com.github.guoyj21.server_operator.io;

import java.util.LinkedList;
import java.util.Queue;

import com.github.guoyj21.server_operator.commands.ICommand;
import com.github.guoyj21.server_operator.shell.Prompt;

public class CommandQueue implements Runnable {

	private SessionOperation session = null;

	private Queue<ICommand> queue = null;

	public CommandQueue(SessionOperation session) {
		this.session = session;

		queue = new LinkedList<ICommand>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (0 == queue.size()) {
					Thread.sleep(500);
				} else {
					this.runCommand(queue); // here need to handle command
											// return string.
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void runCommand(Queue<ICommand> queue) {
		if (0 == queue.size())
			System.out.println("error in command queue");

		if (this.session == null)
			System.out.println("error, null pointer in command queue");

		ICommand icom = queue.poll();

		System.out.println(icom.getCommandLine());

		Object ob = icom.getCommandLine();

		if (Prompt.getLastOutput().indexOf("TIMEOUT") != -1)
			this.session.runCmd("\r\n");

		if (ob instanceof String)
			this.session.runCmd(ob.toString());
		else
			this.session.runCmd((Integer) ob);
	}

	public void transferCommand(ICommand com) {
		if (com != null)
			this.queue.offer(com);
		else
			System.out.println("offer queue error");
	}
}
