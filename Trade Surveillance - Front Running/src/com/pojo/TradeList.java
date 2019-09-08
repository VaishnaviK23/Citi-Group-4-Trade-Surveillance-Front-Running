package com.pojo;

import java.sql.Timestamp;

public class TradeList {
	int tradeID;
	Timestamp timeStamp;
	String buyOrSell;
	String typeOfSecurity;
	int qty;
	float price;
	String brokerName;
	int traderID;
	public int getTradeID() {
		return tradeID;
	}

	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(String buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public String getTypeOfSecurity() {
		return typeOfSecurity;
	}

	public void setTypeOfSecurity(String typeOfSecurity) {
		this.typeOfSecurity = typeOfSecurity;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public int getTraderID() {
		return traderID;
	}

	public void setTraderID(int traderID) {
		this.traderID = traderID;
	}

	String company;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public TradeList(int iD, Timestamp time, String type, String sec, int qty, int price, String bro, String company) {
		tradeID = iD;
		this.timeStamp = time;
		this.buyOrSell = type;
		this.typeOfSecurity = sec;
		this.qty = qty;
		this.price = price;
		this.brokerName = bro;
		this.company = company;
	}


	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "TradeList [ID=" + tradeID + ", time=" + timeStamp + ", type=" + buyOrSell + ", sec=" + typeOfSecurity + ", quantity=" + qty
				+ ", price=" + price + ", broker=" + brokerName + "]";
	}

}
