package com.dao;

import java.sql.Timestamp;
import java.util.List;

import com.pojo.TradeList;
import com.pojo.Trader;

public class InsertTest {

	public static void main(String[] args) {
		
		TradeListDAO dao = new TradeListDAOImpl();
		
		TradeList t1=dao.findTradeListByID(10);
		Timestamp ts=t1.getTimeStamp();
		ts.setSeconds(ts.getSeconds()+1);
		
		Trader trader=new Trader(12, "abc");

		
		TradeList trade=new TradeList(11, ts, trader, "sell", "futures", 46, 74, "abc", "fb");
		
		int i=dao.insertBetween(trade);
		System.out.println(i);
		
		List<TradeList> tr2=dao.display();
		tr2.forEach(System.out::println);
		
		

	}

}
