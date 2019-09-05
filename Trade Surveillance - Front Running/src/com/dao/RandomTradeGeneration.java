package com.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.pojo.TradeList;
import com.pojo.Trader;

public class RandomTradeGeneration {
	
	static int count=0;
	//int recordCount = 0;
	
	static Timestamp timestamp = null;
	
	
	public TradeList generateRandomTrade() {

		
		Random random = new Random();
		RandomTradeGeneration rangen = new RandomTradeGeneration();
		
		
		
		/////////////////////////////////////////////////////////////////////////////////
		
		
		
		timestamp = new Timestamp(2019, 9, 13, 0, 0, count, 0);
		
		
		
//		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());		
//		System.out.println(timeStamp);
		
		
		////////////////////////////////////////////////////////////////////////////////
		
		
		int noOfTraders=10;
		int noOfBrokers = 10;
		
		String[] securityList = {"Call Option","Put Option","Futures","Shares"};
		String[] tradeTypeList = {"Buy", "Sell"};
		String[] companyList = {"Facebook", "Walmart", "Apple"};
		
		
		///////////////////////////////////////////////////////////////////////////////
		
		
		String[] traderNameList = new String[noOfTraders];
		int[] traderIdList = new int[noOfTraders];
		
		
		for(int i=0; i<noOfTraders/2; i++) {
			traderNameList[i] = "Firm"+(i+1);
			traderIdList[i] = i+1;
		}
		
		for(int i=0; i<noOfTraders/2; i++) {
			traderNameList[i+noOfTraders/2] = "Customer"+(i+1);
			traderIdList[i+noOfTraders/2] = i+1+noOfTraders/2;
		}
		
		String[] brokerNameList = new String[noOfBrokers];
		for(int i=0; i<noOfBrokers; i++) {
			brokerNameList[i]= "Broker"+(i+1);
		}
		
		
		
		Trader[] traderList = new Trader[noOfTraders];
		for(int i=0; i<noOfTraders; i++) {
			traderList[i] = new Trader(traderIdList[i], traderNameList[i]);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////
		
		

//		Trade trade = new Trade(tradeID, traderID, brokerName, quantity, price, tradeType, securityType, timeStamp);

		
		
		
		
		
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
			//new Timestamp(System.currentTimeMillis())
			
		
//			TradeList list = new TradeList(iD, time, trader, type, sec, qty, price, bro, company)
			
			int rec=new TradeListDAOImpl().getRecordCount();
		
			TradeList tradet = new TradeList(0, 
					timestamp,
					traderList[new Random().nextInt(traderList.length)], 
					tradeTypeList[new Random().nextInt(tradeTypeList.length)], 
					securityList[new Random().nextInt(securityList.length)], 
					rangen.generateQuantity(), 
					rangen.generatePrice(), 
					brokerNameList[new Random().nextInt(brokerNameList.length)],
					companyList[new Random().nextInt(companyList.length)]);
			
			count++;
			
			return tradet;
		
		
		
	}
	
	
	private float generatePrice() {
		DecimalFormat df = new DecimalFormat("#.####");
		
		Random random = new Random();
		
		double avg = 10d;
		double stdDev = 5d;
		
		String no = df.format(Math.abs(avg+random.nextGaussian())*stdDev);
		
		float num = Float.parseFloat(no);
//		num = Math.round(num);
		
		return num;
	}
	
	private int generateQuantity() {
		
		
		Random random = new Random();
		
		DecimalFormat df = new DecimalFormat("#.####");


		double avg = 100d;
		double stdDev = 100d;
		
		String no = df.format(Math.abs(avg+  (random.nextGaussian()*stdDev) ));
		
		
		double num = Double.parseDouble(no);
		
		int newNum = (int) Math.round(num);
		
		return 100*newNum;
	}
	
	
	private Connection openConnection() {

		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver loaded successfully");
			connection = 
					DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "abcd123");
			
			System.out.println("got connection");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}

}
