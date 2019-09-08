package com.dao;

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
			ps.setLong(3, tradeList.getQuantity());
			ps.setString(4, tradeList.getSecurity());
			ps.setString(5, tradeList.getTradeType());
			ps.setInt(6, tradeList.getTradeID());
			ps.setString(7, tradeList.getBrokerName());
			ps.setTimestamp(8, tradeList.getTimestamp());

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

				double price = set.getDouble("price");
				long quantity = set.getLong("quantity");
				String security = set.getString("security");
				String tradeType = set.getString("tradeType");
				int traderID = set.getInt("traderID");

				String brokerName = set.getString("brokerName");
				Timestamp timestamp = set.getTimestamp("timestamp");

				TradeList tradeList1 = new TradeList(tradeID, price, quantity, security, tradeType, traderID,
						brokerName, timestamp);

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
			String str = "select * from Excel where ID = ?";
			PreparedStatement ps = conn.prepareStatement(str);
			ps.setInt(1, Sr);
			ResultSet s = ps.executeQuery();

			while (s.next()) {

				int tradeID = s.getInt("ID");
				Timestamp timeStamp = s.getTimestamp("Time");
				String buyOrSell = s.getString("Type");
				String typeOfSecurity = s.getString("Security");
				int qty = s.getInt("Qty");
				int price = s.getInt("Price");
				String brokerName = s.getString("Broker");
				String company = s.getString("company");

				TradeList TradeList = new TradeList(tradeID, timeStamp, buyOrSell, typeOfSecurity, qty, price, brokerName, company);
				return TradeList;

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
