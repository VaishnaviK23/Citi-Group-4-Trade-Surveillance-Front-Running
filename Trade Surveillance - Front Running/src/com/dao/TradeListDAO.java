package com.dao;

import com.pojo.TradeList;


public interface TradeListDAO {
	
	int addTradeList(TradeList tradeList);
	TradeList findTradeListByID(int tradeListID);
	int insertBetween(TradeList tradelist);

}
