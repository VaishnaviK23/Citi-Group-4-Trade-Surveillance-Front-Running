package com.login;

import com.login.Trader;

public interface TraderDAO {
	
	int addTrader(Trader trader);
	Trader findTraderByID(int traderID);
	

}
