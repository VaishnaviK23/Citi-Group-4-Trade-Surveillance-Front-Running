package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pojo.Trader;

public class TraderDAOImpl implements TraderDAO {
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
	public int addTrader(Trader trader) {
		int rows_inserted = 0;
		String INSERT_TRADER = "insert into Trader values(?, ?)";
		try {

			PreparedStatement ps = openConnection().prepareStatement(INSERT_TRADER);

			ps.setInt(1, trader.getTraderID());
			ps.setString(2, trader.getTraderName());

			rows_inserted = ps.executeUpdate();
			System.out.println("rows: " + rows_inserted);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rows_inserted;
	}

	@Override
	public Trader findTraderByID(int traderID) {
		Trader trader = new Trader();
		trader = null;

		String FIND_TRADER = "Select * from Trader where traderID=?";

		try {
			PreparedStatement ps = openConnection().prepareStatement(FIND_TRADER);
			ps.setInt(1, traderID);
			ResultSet set = ps.executeQuery();

			while (set.next()) {

				String traderName = set.getString("traderName");

				Trader trader1 = new Trader(traderID, traderName);

				return trader1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return trader;
	}

}
