package com.dao;

import java.sql.Connection;

import java.util.List;

import com.pojo.TradeList;


public interface TradeListDAO {
	
	//public int addTradeList(TradeList tradeList);
	public TradeList findTradeListByID(int tradeListID);
	public TradeList fetchBySr(int Sr, Connection conn);
	public void genTrades(int genTradeQuantity);
	int insertBetween(TradeList tradelist);
	List<TradeList> display();
	
}
