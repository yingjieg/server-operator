package com.github.guoyj21.server_operator.entry;

public class Block {
	private String rpNumber;
	private String suName;
	private String suid;
	private String version;
	private String suptr;
	private int cm;
	private int em;

	public Block(String suName, String suid, String suptr, int cm, int em) {
		this.suName = suName;
		this.suid = suid;
		this.suptr = suptr;
		this.cm = cm;
		this.em = em;
	}

	public String getSuName() {
		return suName;
	}

	public void setSuName(String suName) {
		this.suName = suName;
	}

	public String getSuid() {
		return suid;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSuptr() {
		return suptr;
	}

	public void setSuptr(String suptr) {
		this.suptr = suptr;
	}

	public int getCm() {
		return cm;
	}

	public void setCm(int cm) {
		this.cm = cm;
	}

	public int getEm() {
		return em;
	}

	public void setEm(int em) {
		this.em = em;
	}

	public String getRpNumber() {
		return rpNumber;
	}

	public void setRpNumber(String rpNumber) {
		this.rpNumber = rpNumber;
	}
}
