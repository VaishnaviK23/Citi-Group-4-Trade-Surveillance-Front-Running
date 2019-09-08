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

	private Connection openConnection() {
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

	@Override
	public int addTradeList(TradeList tradeList) {
		int rows_inserted = 0;
		String INSERT_TRADELIST = "insert into TradeList values(?, ?, ?, ?, ?, ?, ?, ?)";
		try {

			PreparedStatement ps = openConnection().prepareStatement(INSERT_TRADELIST);

			ps.setInt(1, tradeList.getTradeID());
			ps.setDouble(2, tradeList.getPrice());
			ps.setLong(3, tradeList.getQty());
			ps.setString(4, tradeList.getTypeOfSecurity());
			ps.setString(5, tradeList.getBuyOfSell());
			ps.setInt(6, tradeList.getTradeID());
			ps.setString(7, tradeList.getBrokerName());
			ps.setTimestamp(8, tradeList.getTimeStamp());

			rows_inserted = ps.executeUpdate();
			System.out.println("rows: " + rows_inserted);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rows_inserted;
	}

	@Override
	public TradeList findTradeListByID(int tradeID) {
		TradeList tradeList = new TradeList();
		tradeList = null;

		String FIND_TRADE = "Select * from TradeList where tradeID=?";

		try {
			PreparedStatement ps = openConnection().prepareStatement(FIND_TRADE);
			ps.setInt(1, tradeID);
			ResultSet set = ps.executeQuery();

			while (set.next()) {

				float price = set.getFloat("price");
				int quantity = set.getLong("quantity");
				String security = set.getString("Securitytype");
				String tradeType = set.getString("tradetype");
				int traderID = set.getInt("traderID");
				String company = set.getString("company");
				String brokerName = set.getString("brokerName");
				Timestamp timestamp = set.getTimestamp("timeStamp");

				TradeList tradeList1 = new TradeList(tradeID, timestamp, trader, price, quantity, security, tradeType, traderID,
						brokerName);

				return tradeList1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tradeList;
	}
	
	@Override
	public TradeList fetchBySr(int Sr, Connection conn) {
		// TODO Auto-generated method stub
		// return null;
		try {

			//Connection conn = getConnection();
			String str1 = "select * from TradeList where tradeID = ?";
			PreparedStatement ps1 = conn.prepareStatement(str1);
			ps1.setInt(1, Sr);
			ResultSet s1 = ps1.executeQuery();

			while (s1.next()) {

				int tradeID = s1.getInt("tradeID");
				Timestamp timeStamp = s1.getTimestamp("timeStamp");
				String buyOrSell = s1.getString("tradetype");
				String typeOfSecurity = s1.getString("securityType");
				int qty = s1.getInt("quantity");
				float price = s1.getFloat("price");
				String brokerName = s1.getString("brokerName");
				String company = s1.getString("company");
				int traderID = s1.getInt("traderID");
				
				String str2 = "select * from Trader where traderID = ?";
				PreparedStatement ps2 = conn.prepareStatement(str2);
				ps2.setInt(1, traderID);
				ResultSet s2 = ps2.executeQuery();
				
				String traderName = s2.getString("traderName");
				
				Trader trader = new Trader(traderID, traderName);
				
				
				TradeList tradeList = new TradeList(tradeID, timeStamp, trader, buyOrSell, typeOfSecurity, qty, price, brokerName, company);
				return tradeList;

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
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


	

}
