package com.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pojo.TradeList;
import com.pojo.Trader;

@Path("/check")
class Test {

	@SuppressWarnings("deprecation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)

	public TradeList incoming(JsonObject incomingData) {

		TradeListDAO dao = new TradeListDAOImpl();

		String tradeID_ui = incomingData.getString("tradeID");
		String traderID_ui = incomingData.getString("traderID");
		String brokerName_ui = incomingData.getString("brokerName");
		String quantity_ui = incomingData.getString("quantity");
		String price_ui = incomingData.getString("price");
		String tradeType_ui = incomingData.getString("tradeType");
		String securityType_ui = incomingData.getString("securityType");
		String company_ui = incomingData.getString("company");

		int traderID = Integer.parseInt(traderID_ui);

		Trader trader = new Trader();
		String traderName = trader.getTraderName();
		Trader trader1 = new Trader(traderID, traderName);

		int tradeID = Integer.parseInt(tradeID_ui);

		String brokerName = brokerName_ui;
		int quantity = Integer.parseInt(quantity_ui);
		float price = Float.parseFloat(price_ui);
		String tradeType = tradeType_ui;
		String company = company_ui;
		String securityType = securityType_ui;
		
		TradeList t1=dao.findTradeListByID(tradeID-1);
		Timestamp ts=t1.getTimeStamp();
		ts.setSeconds(ts.getSeconds()+1);
		

//		Date date = new Date();
//
//		long time = date.getTime();
//
//		Timestamp ts = new Timestamp(time);
//		System.out.println(ts);
		
		
		
		

		TradeList tradelist = new TradeList(tradeID, ts, trader1, tradeType, securityType, quantity, price, brokerName,
				company);

		int inserted = dao.insertBetween(tradelist);
		System.out.println(inserted);

		List<TradeList> list = dao.display();
		// list.forEach(System.out::println);
		JSONArray list1 = new JSONArray();

		for (int i = 0; i < list.size(); i++) {
			JSONObject eachData = new JSONObject();
			eachData.put("tradeID", list.get(i).getTradeID());
			eachData.put("price", list.get(i).getPrice());
			list1.add(i, eachData);

		}

		JSONObject root = new JSONObject();
		root.put("data", list1);
		System.out.println(list1);

		return tradelist;

	}
}

//	public class TestInsert {
//
//		public void main(String[] args) {
//
//			TradeListDAO dao = new TradeListDAOImpl();
//
////		
//			Test o = new Test();
//
//			TradeList tradelist = o.incoming(incomingData);
//			int inserted = dao.insertBetween(tradelist);
//			System.out.println(inserted);
//
//			List<TradeList> list = dao.display();
//			// list.forEach(System.out::println);
//			JSONArray list1 = new JSONArray();
//
//			for (int i = 0; i < list.size(); i++) {
//				JSONObject eachData = new JSONObject();
//				eachData.put("tradeID", list.get(i).getTradeID());
//				eachData.put("price", list.get(i).getPrice());
//				list1.add(i, eachData);
//
//			}
//
//			JSONObject root = new JSONObject();
//			root.put("data", list1);
//			System.out.println(list1);
//
//		}
//
//	}
//}
