package com.github.guoyj21.server_operator.entry;

import java.util.ArrayList;

import com.github.guoyj21.server_operator.io.SessionOperation;

public class STP {

	public ArrayList<RP> rps;
	private SessionOperation session = null;

	public STP(SessionOperation session) {
		this.session = session;
	}

	public SessionOperation getSession() {
		return this.session;
	}

	public void init(String text) {
		String lines[] = text.split("\n");

		if (rps != null)
			this.rps.clear(); // clear all data before input new data.

		boolean flag = false;

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].indexOf("END") != -1)
				break;

			if (lines[i].indexOf("RP    STATE  TYPE") != -1) {
				flag = true;
				continue;
			}

			if (flag) {
				String temps[] = lines[i].trim().split("\\s+");
				RP rp = null;
				if (temps.length == 4)
					rp = new RP(Integer.parseInt(temps[0]), temps[1].trim(), temps[2].trim(), temps[3].trim(), "no");
				else
					rp = new RP(Integer.parseInt(temps[0]), temps[1].trim(), temps[2].trim(), temps[3].trim(),
							temps[4].trim());

				if (rps == null)
					rps = new ArrayList<RP>();

				rps.add(rp);
			}
		}
	}

}
