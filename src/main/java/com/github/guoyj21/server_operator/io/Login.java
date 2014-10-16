package com.github.guoyj21.server_operator.io;

import java.io.IOException;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

public class Login {
	private String userName;
	private String password;
	private String hostName;
	public static Login loginInstance = null;
	private Connection con = null;
	private Session session = null;

	public Login(String host, String user, String pwd) {
		this.userName = user;
		this.password = pwd;
		this.hostName = host;
	}

	public static Login getInstance(String host, String user, String pwd) {
		if (loginInstance == null) {
			loginInstance = new Login(host, user, pwd);
		}
		return loginInstance;
	}

	public Session createSession() {
		this.con = new Connection(this.hostName);
		try {
			con.connect();
			boolean isAuthenticated = con.authenticateWithPassword(this.userName, this.password);
			if (isAuthenticated == false)
				throw new IOException("Authentication failed !!!");
			this.session = con.openSession();
		} catch (IOException e) {
			e.printStackTrace();
			this.con.close();
			this.session.close();
		}

		return this.session;
	}

	public Connection getConnection() {
		return this.con;
	}

	public void closeAll() {
		if (this.con != null)
			this.con.close();
		if (this.session != null)
			this.session.close();
	}
}
