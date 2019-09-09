package com.login;

import com.login.TradeList;


public interface TradeListDAO {
	
	int addTradeList(TradeList tradeList);
	TradeList findTradeListByID(int tradeListID);

}
