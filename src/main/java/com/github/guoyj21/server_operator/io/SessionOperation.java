package com.github.guoyj21.server_operator.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.guoyj21.server_operator.shell.Prompt;
import com.github.guoyj21.server_operator.view.Console;
import com.trilead.ssh2.ChannelCondition;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

public class SessionOperation implements Runnable {
	private Session session = null;
	private Login login = null;
	private Console console = null;
	private InputStream stdout = null;
	private BufferedReader br = null;

	public SessionOperation(String ip, String name, String pwd) {
		this.login = Login.getInstance(ip, name, pwd);
		this.session = login.createSession();
		this.startSehll();
	}

	public SessionOperation(Console c, String ip, String name, String pwd) {
		this.console = c;
		this.login = Login.getInstance(ip, name, pwd);
		this.session = login.createSession();
		this.startSehll();
	}

	private void startSehll() {
		if (this.session == null) {
			System.out.println("Session is null");
			System.exit(1);
		}
		try {
			this.session.requestPTY("xterm");
			this.session.startShell();
			this.runCmd("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * send command text to session.
	 */
	public void runCmd(String cmd) {
		try {
			this.session.getStdin().write(cmd.getBytes());
			// this.session.waitForCondition(ChannelCondition.CLOSED |
			// ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 2000);
			this.session.waitForCondition(ChannelCondition.STDOUT_DATA | ChannelCondition.EXIT_STATUS
					| ChannelCondition.CLOSED, 500);
			this.session.getStdin().write("\r\n".getBytes());

			Prompt.currentText.delete(0, Prompt.currentText.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runCmd(int cmd) {
		try {
			this.session.getStdin().write(cmd);
			this.session.waitForCondition(ChannelCondition.STDOUT_DATA | ChannelCondition.EXIT_STATUS
					| ChannelCondition.CLOSED, 500);
			this.session.getStdin().write("\r\n".getBytes());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void getOutput() {
		if (console == null) {
			System.out.println("console is null");
			System.exit(1);
		}
		if (this.stdout == null)
			stdout = new StreamGobbler(this.session.getStdout());
		if (this.br == null)
			br = new BufferedReader(new InputStreamReader(stdout));

		try {
			String line = null;
			while (true)// will block, if no data
			{
				line = br.readLine();
				console.setText(line + '\n');
				Prompt.setCurrentText(line + "\r\n");
				Prompt.setLastOutput(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.login.getConnection();
	}

	public void close() {
		this.login.closeAll();
	}

	public void run() {
		this.getOutput();
	}
}
