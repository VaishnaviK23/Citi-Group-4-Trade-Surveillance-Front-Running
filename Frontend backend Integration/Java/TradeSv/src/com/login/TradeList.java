package com.login;

import java.sql.Timestamp;

public class TradeList {

	private int tradeID;
	private double price;
	private long quantity;
	private String security;
	private String tradeType;
	private int traderID;
	private String brokerName;
	private Timestamp timestamp;

	public int getTradeID() {
		return tradeID;
	}

	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public int getTraderID() {
		return traderID;
	}

	public void setTraderID(int traderID) {
		this.traderID = traderID;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "TradeList [tradeID=" + tradeID + ", price=" + price + ", quantity=" + quantity + ", security="
				+ security + ", tradeType=" + tradeType + ", traderID=" + traderID + ", brokerName=" + brokerName
				+ ", timestamp=" + timestamp + "]";
	}

	public TradeList(int tradeID, double price, long quantity, String security, String tradeType, int traderID,
			String brokerName, Timestamp timestamp) {
		super();
		this.tradeID = tradeID;
		this.price = price;
		this.quantity = quantity;
		this.security = security;
		this.tradeType = tradeType;
		this.traderID = traderID;
		this.brokerName = brokerName;
		this.timestamp = timestamp;
	}

	public TradeList() {
		// TODO Auto-generated constructor stub
	}

}
