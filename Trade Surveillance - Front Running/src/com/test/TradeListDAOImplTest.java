package com.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dao.TradeListDAOImpl;

class TradeListDAOImplTest {
	
	TradeListDAOImpl daoImpl = new TradeListDAOImpl();
	
	@Test
	void testGetRecordCount() {
		
		int noOfRecordsInTable = 10;
		int countFromFunction = daoImpl.getRecordCount();
		
		assertEquals(noOfRecordsInTable, countFromFunction);
	
	}

	@Test
	void testGenTrades() {
		int genTradeNo = 10;
		int insertedNoOfTrades=daoImpl.genTrades(genTradeNo);
		
		assertEquals(genTradeNo, insertedNoOfTrades);
		
	}
	
	

}
