package com.dao;

import com.pojo.Trader;

public interface TraderDAO {
	
	int addTrader(Trader trader);
	Trader findTraderByID(int traderID);
	

}
