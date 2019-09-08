package com.dao;

import com.pojo.TradeList;


public interface TradeListDAO {
	
	public int addTradeList(TradeList tradeList);
	public TradeList findTradeListByID(int tradeListID);
	public TradeList fetchBySr(int Sr, Connection conn);
	public void genTrades(int genTradeQuantity)

}
