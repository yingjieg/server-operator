package com.github.guoyj21.server_operator.entry;

import java.util.ArrayList;

public class RP {
	private int rpNumber;
	private String state;
	private String type;
	private String maint;
	private String piu;

	private ArrayList<Block> blocks;

	public RP(int rpNumber, String state, String type, String maint, String piu) {
		this.rpNumber = rpNumber;
		this.state = state;
		this.type = type;
		this.maint = maint;
		this.piu = piu;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	public int getRpNumber() {
		return rpNumber;
	}

	public void setRpNumber(int rpNumber) {
		this.rpNumber = rpNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaint() {
		return maint;
	}

	public void setMaint(String maint) {
		this.maint = maint;
	}

	public String getPiu() {
		return piu;
	}

	public void setPiu(String piu) {
		this.piu = piu;
	}

}
