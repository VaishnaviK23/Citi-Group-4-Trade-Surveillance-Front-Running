<<<<<<< HEAD
package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

<<<<<<< HEAD
import com.pojo.Student;
=======
>>>>>>> 2e2285f0fb399cbaf32d09baa41481920d61efdf
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
<<<<<<< HEAD
		String INSERT_TRADELIST = "insert into TradeList values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
=======
		String INSERT_TRADELIST = "insert into TradeList values(?, ?, ?, ?, ?, ?, ?, ?)";
>>>>>>> 2e2285f0fb399cbaf32d09baa41481920d61efdf
		try {

			PreparedStatement ps = openConnection().prepareStatement(INSERT_TRADELIST);

<<<<<<< HEAD
			ps.setInt(1, (tradeList.getTradeID()));
			ps.setInt(2, tradeList.getTrader().getTraderID());
			ps.setString(3, tradeList.getBrokerName());
			ps.setInt(4, tradeList.getQty());
			ps.setFloat(5, tradeList.getPrice());
			ps.setString(6, tradeList.getBuyOrSell());
			ps.setString(7, tradeList.getTypeOfSecurity());
			ps.setTimestamp(8, tradeList.getTimeStamp());
			ps.setString(9, tradeList.getCompany());
			
=======
			ps.setInt(1, tradeList.getTradeID());
			ps.setDouble(2, tradeList.getPrice());
			ps.setLong(3, tradeList.getQuantity());
			ps.setString(4, tradeList.getSecurity());
			ps.setString(5, tradeList.getTradeType());
			ps.setInt(6, tradeList.getTradeID());
			ps.setString(7, tradeList.getBrokerName());
			ps.setTimestamp(8, tradeList.getTimestamp());
>>>>>>> 2e2285f0fb399cbaf32d09baa41481920d61efdf

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

			String UPDATE = "Update TradeList set tradeID=tradeID+1, timestamp=timestamp+0.00001 where tradeID >= ?";

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

		
		if (tradelist.getTrader().getTraderID()==0) {
			rows_inserted=0;
		}
		return rows_inserted;

	}

<<<<<<< HEAD
	@Override
	public List<TradeList> display() {
		// TODO Auto-generated method stub
		List<TradeList> tradelists = new ArrayList<TradeList>();

		String FIND_ALL = "select * from TradeList";// result set

		try {
			

			Statement st = openConnection().createStatement();
			ResultSet set = st.executeQuery(FIND_ALL);

			while (set.next()) {
				int tradeID=set.getInt("tradeID");
				int traderID=set.getInt("traderID");
				String brokerName=set.getString("brokername");
				int quantity=set.getInt("quantity");
				float price=set.getFloat("price");
				String tradeType=set.getString("tradeType");
				String security=set.getString("securityType");
				Timestamp timestamp=set.getTimestamp("timestamp");
				String company=set.getString("company");
				
				TraderDAO dao= new TraderDAOImpl();
				Trader trader=new Trader();
				trader=dao.findTraderByID(traderID);
				

				TradeList tradelist = new TradeList(tradeID, timestamp, trader, tradeType, security, quantity, price, brokerName, company);
				tradelists.add(tradelist);

			}

			System.out.println("List size: " + tradelists.size());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tradelists;
	}
	
	@Override
	public TradeList deleteTradeList(int tradeID) {
		// TODO Auto-generated method stub
		TradeList tradelist = new TradeList();
		
		tradelist=null;
		String TRADELIST_DELETE = "Delete from TradeList where tradeID=?";
		String FIND_TRADELIST = "select * from Tradelist where tradeID=?";
		try {
			PreparedStatement ps = openConnection().prepareStatement(TRADELIST_DELETE);
			PreparedStatement ps1 = openConnection().prepareStatement(FIND_TRADELIST);
			ps.setInt(1, tradeID);
			ps1.setInt(1, tradeID);
			ResultSet set = ps1.executeQuery();

			while (set.next()) {
				
				int traderID=set.getInt("traderID");
				String brokerName=set.getString("brokername");
				int quantity=set.getInt("quantity");
				float price=set.getFloat("price");
				String tradeType=set.getString("tradeType");
				String security=set.getString("securityType");
				Timestamp timestamp=set.getTimestamp("timestamp");
				String company=set.getString("company");
				
				TraderDAO dao= new TraderDAOImpl();
				Trader trader=new Trader();
				trader=dao.findTraderByID(traderID);
				

				tradelist = new TradeList(tradeID, timestamp, trader, tradeType, security, quantity, price, brokerName, company);
				 

			}

			ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tradelist;
	}


	

=======
>>>>>>> 2e2285f0fb399cbaf32d09baa41481920d61efdf
}
=======
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

//	@Override
//	public int addTradeList(TradeList tradeList) {
//		int rows_inserted = 0;
//		String INSERT_TRADELIST = "insert into TradeList values(?, ?, ?, ?, ?, ?, ?, ?)";
//		try {
//
//			PreparedStatement ps = openConnection().prepareStatement(INSERT_TRADELIST);
//
//			ps.setInt(1, (tradeList.getTradeID()));
//			ps.setInt(2, tradeList.getTrader().getTraderID());
//			ps.setString(3, tradeList.getBrokerName());
//			ps.setInt(4, tradeList.getQty());
//			ps.setFloat(5, (float) tradeList.getPrice());
//			ps.setString(6, tradeList.getBuyOrSell());
//			ps.setString(7, tradeList.getTypeOfSecurity());
//			ps.setTimestamp(8, tradeList.getTimeStamp());
//			ps.setString(9, tradeList.getCompany());
//			
//
//			rows_inserted = ps.executeUpdate();
//			System.out.println("rows: " + rows_inserted);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return rows_inserted;
//	}

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
				int quantity = set.getInt("quantity");
				String security = set.getString("Securitytype");
				String tradeType = set.getString("tradetype");
				int traderID = set.getInt("traderID");
				String company = set.getString("company");
				String brokerName = set.getString("brokerName");
				Timestamp timestamp = set.getTimestamp("timeStamp");
				
				Trader trader= new Trader();
				TraderDAO dao= new TraderDAOImpl();
				trader=dao.findTraderByID(traderID);

				TradeList tradeList1 = new TradeList(tradeID, timestamp, trader, tradeType, security, quantity, price, brokerName, company);

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
			
			connection.close();
			
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


	public int insertBetween(TradeList tradelist) {
		int rows_inserted = 0;
		try {

			String CREATE_TEMP = "create table temp(tradeID number NOT NULL, traderID number, brokerName varchar(20), quantity number(38, 0),  price number(7, 2), tradeType varchar(20), securityType varchar(20), timestamp timestamp(6), company varchar(20))";

			String COPY_PREVIOUS = "INSERT INTO temp\r\n" + "SELECT \r\n" + "     *\r\n" + "FROM \r\n"
					+ "     TradeList\r\n" + "WHERE \r\n" + "     tradeID between 1 and ? ";

			String UPDATE = "Update TradeList set tradeID=tradeID+1, timestamp=timestamp+0.00001 where tradeID >= ?";

			String INSERT_TRADER_NEW = "insert into temp values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

			ps2.setInt(1, (tradelist.getTradeID()));
			ps2.setInt(2, tradelist.getTrader().getTraderID());
			ps2.setString(3, tradelist.getBrokerName());
			ps2.setInt(4, tradelist.getQty());
			ps2.setFloat(5, (float) tradelist.getPrice());
			ps2.setString(6, tradelist.getBuyOrSell());
			ps2.setString(7, tradelist.getTypeOfSecurity());
			ps2.setTimestamp(8, tradelist.getTimeStamp());
			ps2.setString(9, tradelist.getCompany());

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

	@Override
	public List<TradeList> display() {
		// TODO Auto-generated method stub
		List<TradeList> tradelists = new ArrayList<TradeList>();

		String FIND_ALL = "select * from TradeList";// result set

		try {
			

			Statement st = openConnection().createStatement();
			ResultSet set = st.executeQuery(FIND_ALL);

			while (set.next()) {
				int tradeID=set.getInt("tradeID");
				int traderID=set.getInt("traderID");
				String brokerName=set.getString("brokername");
				int quantity=set.getInt("quantity");
				float price=set.getFloat("price");
				String tradeType=set.getString("tradeType");
				String security=set.getString("securityType");
				Timestamp timestamp=set.getTimestamp("timestamp");
				String company=set.getString("company");
				
				TraderDAO dao= new TraderDAOImpl();
				Trader trader=new Trader();
				trader=dao.findTraderByID(traderID);
				

				TradeList tradelist = new TradeList(tradeID, timestamp, trader, tradeType, security, quantity, price, brokerName, company);
				tradelists.add(tradelist);

			}

			System.out.println("List size: " + tradelists.size());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tradelists;
	}


	

}
>>>>>>> c731b430bffcc7c4d9be713c4fc8079d28600039
