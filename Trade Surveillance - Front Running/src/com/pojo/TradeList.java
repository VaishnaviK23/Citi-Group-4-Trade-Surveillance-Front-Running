package com.pojo;

import java.sql.Timestamp;

import com.pojo.Trader;

public class TradeList {
	int tradeID;
	Timestamp timeStamp;
	String buyOrSell;
	String typeOfSecurity;
	int qty;
	float price;
	String brokerName;
	Trader trader;
	String company;
	
	
	
	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public TradeList() {
		this.tradeID = 0;
		this.trader = null;
		this.brokerName = null;
		this.buyOrSell = null;
		this.company = null;
		this.price = 0;
		this.qty = 0;
		this.timeStamp = null;
		this.typeOfSecurity = null;
	}
	
	
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

	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public TradeList(int iD, Timestamp time, Trader trader, String type, String sec, int qty, float price, String bro, String company) {
		tradeID = iD;
		this.timeStamp = time;
		this.buyOrSell = type;
		this.typeOfSecurity = sec;
		this.qty = qty;
		this.price = price;
		this.brokerName = bro;
		this.company = company;
		this.trader = trader;
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
	
	@Override
	public String toString() {
		return "TradeList [ID=" + tradeID + ", time=" + timeStamp + ", type=" + buyOrSell + ", sec=" + typeOfSecurity + ", qty=" + qty
				+ ", price=" + price + ", broker=" + brokerName + "]";
	}

}
