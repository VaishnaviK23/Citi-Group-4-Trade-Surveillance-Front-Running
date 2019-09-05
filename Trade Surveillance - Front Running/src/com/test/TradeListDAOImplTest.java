package com.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dao.TradeListDAOImpl;

import com.pojo.TradeList;
import com.pojo.Trader;

class TradeListDAOImplTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindTradeListByID() {
		fail("Not yet implemented");
	}

	@Test
	void testFetchBySr() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRecordCount() {
		fail("Not yet implemented");
	}

	@Test
	void testGenTrades() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertBetween() {
		TradeListDAOImpl daoImpl = new TradeListDAOImpl();

		TradeList t1 = daoImpl.findTradeListByID(10);
		Timestamp ts = t1.getTimeStamp();
		ts.setSeconds(ts.getSeconds() + 1);

		Trader trader = new Trader(12, "abc");

		TradeList trade = new TradeList(11, ts, trader, "sell", "futures", 46, 74, "abc", "fb");

		int actual = daoImpl.insertBetween(trade);

		assertEquals(1, actual);

	}

//	void testInsertBetween_neg() {
//		TradeListDAOImpl daoImpl = new TradeListDAOImpl();
//		
//		TradeList t1=daoImpl.findTradeListByID(10);
//		Timestamp ts=t1.getTimeStamp();
//		ts.setSeconds(ts.getSeconds()+1);
//		
//		Trader trader=new Trader(16, "abc");
//
//		
//		TradeList trade=new TradeList(11, ts, trader, "sell", "futures", 46, 74, "abc", "fb");
//		
//		
//		int actual = daoImpl.insertBetween(trade);
//
//		assertEquals(0, actual);
//		
//		
//	
//		
//		
//	}

	@Test
	void testDisplay() {
		TradeListDAOImpl daoImpl = new TradeListDAOImpl();
		List<TradeList> tradelists = daoImpl.display();

		TradeList tradelist = tradelists.get(0);

		assertEquals(1, tradelist.getTradeID());

	}

}
