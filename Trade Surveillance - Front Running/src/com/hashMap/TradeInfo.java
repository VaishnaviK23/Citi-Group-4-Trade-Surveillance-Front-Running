package com.hashMap;

import java.util.ArrayList;

public class TradeInfo {
	
	
	int quantity;
	ArrayList<Integer> tradeNumberList;
	
	

	public TradeInfo() {
		this.quantity = 0;
		this.tradeNumberList = new ArrayList<Integer>();
	}
	
	
	public TradeInfo(int quantity, ArrayList<Integer> tradeNumberList) {
		this.quantity = quantity;
		this.tradeNumberList = tradeNumberList;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ArrayList<Integer> getTradeNumberList() {
		return tradeNumberList;
	}
	public void setTradeNumberList(ArrayList<Integer> tradeNumberList) {
		this.tradeNumberList = tradeNumberList;
	}
	@Override
	public String toString() {
		return "TradeInfo [quantity=" + quantity + ", tradeNumberList=" + tradeNumberList + "]";
	}
}
