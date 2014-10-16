package com.github.guoyj21.server_operator.io;

import java.io.IOException;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;

public class Upload extends SCPClient {

	public Upload(Connection conn) {
		super(conn);
	}

	public void putFile(String file, String destination) {
		try {
			this.put(file, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
