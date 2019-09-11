package com.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.dao.*;
import com.pojo.*;



public class ExtractTradeData {

	public ArrayList<TradeList> getDataFromDatabase(){
		TradeListDAOImpl obj = new TradeListDAOImpl();
		Connection connection = obj.openConnection();
		
		String query = "select * from tradelist";
		
		
		int recordCount = 0;
		ArrayList<TradeList> tradeData = new ArrayList<TradeList>();
		PreparedStatement ps = null;
		ResultSet set = null;
		try {
			ps = connection.prepareStatement(query);
			set = ps.executeQuery();
			ResultSetMetaData rsmd = set.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			
			while (set.next()) {
				int tradeID = set.getInt("tradeID");
				float price = set.getFloat("price");
				int quantity = set.getInt("quantity");
				String security = set.getString("SecurityType");
				String tradeType = set.getString("tradeType");
				int traderID = set.getInt("traderID");
				Trader trader = new Trader(traderID, "randomName");
				String brokerName = set.getString("brokerName");
				Timestamp timestamp = set.getTimestamp("timeStamp");
				String company = set.getString("Company");
				TradeList tradeList1 = new TradeList(tradeID,timestamp,trader,tradeType,security,quantity, price,brokerName, company );
				
				tradeData.add(tradeList1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tradeData;
	}
	 
}
