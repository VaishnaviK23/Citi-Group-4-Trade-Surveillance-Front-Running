package com.dao;



import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.pojo.TradeList;
import com.pojo.Trader;

public class TradeListDAOImpl implements TradeListDAO {

	public Connection openConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver loaded successfully");

			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "abcd123");
			System.out.println("got connection");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}


	
	public int getRecordCount() {
		
		TradeListDAOImpl daoImpl = new TradeListDAOImpl();
		Connection connection = daoImpl.openConnection();
		
		String FIND_RECORD_COUNT = "select * from tradelist2";
		
		int recordCount = 0;
		
		PreparedStatement ps = null;
		ResultSet set = null;
		try {
			ps = connection.prepareStatement(FIND_RECORD_COUNT);
			set = ps.executeQuery();
			while(set.next()) {
				recordCount += 1;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		System.out.println("record count: "+recordCount);

		
		return recordCount;
	}
	
	
	
	
	
	
	@Override
	public void genTrades(int genTradeQuantity) {
		// TODO Auto-generated method stub
		
		int rows_inserted = 0;
		
		Connection con = openConnection();
		
		
		TradeListDAOImpl daoImpl = new TradeListDAOImpl();
		
		int recordCount = daoImpl.getRecordCount();
		
		ResultSet set = null;
		
		//System.out.println(recordCount);
		
		RandomTradeGeneration genTrade = new RandomTradeGeneration();
		
		Timestamp timestamp = null;
		
		
		for(int i=0;i<genTradeQuantity;i++) {
		
			try {
				//Thread.sleep(20);
				String INSERT_TRADE = "INSERT /*+ APPEND */ into tradelist2 values(?,?,?,?,?,?,?,?,?)";
				
				
				TradeList trade =  genTrade.generateRandomTrade();
			
				
				PreparedStatement ps = con.prepareStatement(INSERT_TRADE);
				
				Trader trader = trade.getTrader();
				
				timestamp = trade.getTimeStamp();
				
				timestamp.setSeconds(timestamp.getSeconds()+recordCount);
				
				ps.setInt(1, (1+i+recordCount + trade.getTradeID()));
				ps.setInt(2, trader.getTraderID());
				ps.setString(3, trade.getBrokerName());
				ps.setInt(4, trade.getQty());
				ps.setFloat(5, (float) trade.getPrice());
				ps.setString(6, trade.getBuyOrSell());
				ps.setString(7, trade.getTypeOfSecurity());
				ps.setTimestamp(8, trade.getTimeStamp());
				ps.setString(9, trade.getCompany());
				
				//System.out.println(trade);
				ps.executeUpdate();
				//set.afterLast();
				
				
				//System.out.println("rows: "+rows_inserted);
				
				
			} catch (SQLException e	) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}

	@Override
	public int addTradeList(TradeList tradeList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TradeList findTradeListByID(int tradeListID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradeList fetchBySr(int Sr, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
