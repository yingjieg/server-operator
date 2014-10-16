package com.github.guoyj21.server_operator.io;

import com.trilead.ssh2.Connection;

public interface ILogin {
	public void closeConnection();

	public void closeSession();

	public Connection getConnection();
}
