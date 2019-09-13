
package com.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dao.TradeListDAO;
import com.dao.TradeListDAOImpl;
import com.pojo.Student;
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

	void testAddTradeList() {

		TradeListDAOImpl daoImpl = new TradeListDAOImpl();
		Trader trader = new Trader(11, "adc");
		Date date = new Date();

		long time = date.getTime();

		Timestamp ts = new Timestamp(time);
		
		TradeList tradelist = new TradeList(10000, ts, trader, "buy", "option", 5463, 300, "abc", "FB");
		int actual = daoImpl.addTradeList(tradelist);

		assertEquals(1, actual);
		TradeList tradelists=daoImpl.deleteTradeList(10000);
	}

	@Test
	void testFindTradeListByID() {
		TradeListDAO tradelistDAO = new TradeListDAOImpl();
		TradeList tradelist = tradelistDAO.findTradeListByID(4);
		assertEquals(4, tradelist.getTradeID());
		assertEquals("abc", tradelist.getBrokerName());
		assertEquals(46, tradelist.getQty());

	}

	@Test
	void testFindTradeListByID_neg() {
		TradeListDAO tradelistDAO = new TradeListDAOImpl();
		TradeList tradelist = tradelistDAO.findTradeListByID(1000000);
		assertEquals(null, tradelist);

	}

//	@Test
//	void testFetchBySr() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetRecordCount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGenTrades() {
//		fail("Not yet implemented");
//	}

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

	@Test
	void testDeleteTradeList() {
		// fail("Not yet implemented");

		TradeListDAO dao = new TradeListDAOImpl();
		TradeList tradelist = dao.deleteTradeList(100000);
		assertEquals(null, tradelist);

	}

}
