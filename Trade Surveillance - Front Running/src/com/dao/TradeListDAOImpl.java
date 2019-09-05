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
	public int insertBetween(TradeList tradelist) {
		// TODO Auto-generated method stub

		int rows_inserted = 0;
		try {

			String CREATE_TEMP = "create table temp(tradeID number PRIMARY KEY, price number(7, 2), quantity number(10), security varchar(20), tradeType varchar(10), traderID number, brokerName varchar(15), timestamp timestamp)";

			String COPY_PREVIOUS = "INSERT INTO temp\r\n" + "SELECT \r\n" + "     *\r\n" + "FROM \r\n"
					+ "     TradeList\r\n" + "WHERE \r\n" + "     tradeID between 1 and ? ";

			String UPDATE = "Update TradeList set tradeID=tradeID+1 where tradeID >= ?";

			String INSERT_TRADER_NEW = "insert into temp values(?, ?, ?, ?, ?, ?, ?, ?)";

			String COPY_LATER = "INSERT INTO temp\r\n" + "SELECT \r\n" + "     *\r\n" + "FROM \r\n"
					+ "     TradeList\r\n" + "WHERE \r\n" + "     tradeID > ? ";

			String DROP = "Drop table TradeList";
			String RENAME_TABLE = "Alter table temp RENAME to TradeList";
			String COMMIT="commit";

			PreparedStatement ps1 = openConnection().prepareStatement(CREATE_TEMP);
			PreparedStatement ps7 = openConnection().prepareStatement(UPDATE);
			PreparedStatement ps3 = openConnection().prepareStatement(COPY_PREVIOUS);
			PreparedStatement ps2 = openConnection().prepareStatement(INSERT_TRADER_NEW);
			PreparedStatement ps4 = openConnection().prepareStatement(COPY_LATER);
			PreparedStatement ps8 = openConnection().prepareStatement(COMMIT);
			

			PreparedStatement ps5 = openConnection().prepareStatement(DROP);
			PreparedStatement ps6 = openConnection().prepareStatement(RENAME_TABLE);

			ps1.executeQuery();
			ps3.setInt(1, tradelist.getTradeID() - 1);
			ps3.executeQuery();
			ps7.setInt(1, tradelist.getTradeID());
			ps7.executeQuery();

			ps2.setInt(1, tradelist.getTradeID());
			ps2.setDouble(2, tradelist.getPrice());
			ps2.setLong(3, tradelist.getQuantity());
			ps2.setString(4, tradelist.getSecurity());
			ps2.setString(5, tradelist.getTradeType());
			ps2.setInt(6, tradelist.getTraderID());
			ps2.setString(7, tradelist.getBrokerName());
			ps2.setTimestamp(8, tradelist.getTimestamp());

			rows_inserted = ps2.executeUpdate();
			ps8.executeQuery();
			ps4.setInt(1, tradelist.getTradeID());
			ps4.executeQuery();
			ps5.executeQuery();
			ps6.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rows_inserted;

	}

}
