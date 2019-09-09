package com.login;

public class Trader {

	private int traderID;
	private String traderName;

	public int getTraderID() {
		return traderID;
	}

	public void setTraderID(int traderID) {
		this.traderID = traderID;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public Trader() {
		super();
		this.traderID = 0;
		this.traderName = null;
	}

	public Trader(int traderID, String traderName) {
		super();
		this.traderID = traderID;
		this.traderName = traderName;
	}

	@Override
	public String toString() {
		return "Trader [traderID=" + traderID + ", traderName=" + traderName + "]";
	}

}
