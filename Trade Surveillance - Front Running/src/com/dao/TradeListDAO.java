package com.dao;

import java.sql.Connection;

import java.util.List;

import com.pojo.TradeList;


public interface TradeListDAO {
	
<<<<<<< HEAD
	int addTradeList(TradeList tradeList);
	TradeList findTradeListByID(int tradeListID);
	int insertBetween(TradeList tradelist);

=======
	//public int addTradeList(TradeList tradeList);
	public TradeList findTradeListByID(int tradeListID);
	public TradeList fetchBySr(int Sr, Connection conn);
	public void genTrades(int genTradeQuantity);
	int insertBetween(TradeList tradelist);
	List<TradeList> display();
	
>>>>>>> c731b430bffcc7c4d9be713c4fc8079d28600039
}
