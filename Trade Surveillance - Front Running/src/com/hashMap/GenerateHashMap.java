package com.hashMap;
import java.util.ArrayList;
import java.util.HashMap;

import com.dao.TradeListDAOImpl;
import com.hashMap.ExtractTradeData;
import com.pojo.TradeList;
//import com.dao.*;

public class GenerateHashMap {
	
	//HashMap(TraderID, totalQuant)
	HashMap<String, HashMap<Integer, Integer>> past = new HashMap<String, HashMap<Integer, Integer>>(); 
	HashMap<String, HashMap<Integer, Integer>> future = new HashMap<String, HashMap<Integer, Integer>>(); 
	ArrayList<TradeList> trades = null;
	static int interval = 60;
	int databaseSize;
	//HashMap(TraderID , TradeIDList[])
	HashMap<Integer,ArrayList<Integer>> TrackFraudTrades = new HashMap<Integer,ArrayList<Integer>>();
	
	public GenerateHashMap() {
			this.trades = new ExtractTradeData().getDataFromDatabase();
			this.databaseSize = new TradeListDAOImpl().getRecordCount();
			System.out.println("Database Size: "+this.databaseSize);
			//updating the future HashMap
			HashMap<Integer, Integer> firstTrader = new HashMap<Integer, Integer>();
			firstTrader.put(trades.get(0).getTradeID(), trades.get(0).getQty());
			past.put((trades.get(0).getCompany() + ";" + trades.get(0).getBuyOrSell()).toLowerCase(),
					firstTrader);
			
			for(int i=0;i<interval;i++) {
				//String key = trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell();
				//Add the incoming trade to HashMap
				
				//Lower Case Key
				String key = (trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell()).toLowerCase();
				int traderid =trades.get(i).getTrader().getTraderID();
				int traderQuant = trades.get(i).getQty();
				int tradeid = trades.get(i).getTradeID();
				
				if(TrackFraudTrades.containsKey(traderid)) {
					
					ArrayList<Integer> temp;
					temp = TrackFraudTrades.get(traderid);
					temp.add(tradeid);
					TrackFraudTrades.put(traderid, temp);
					
				}else {
					
					ArrayList<Integer> temp = new ArrayList<>();
					temp.add(tradeid);
					TrackFraudTrades.put(traderid, temp);
				}
				
				if(future.containsKey(key)) {
						
						if(future.get(key).containsKey(traderid)) {
							//update the inside hashmap
							future.get(key).put(traderid, future.get(key).get(traderid) + traderQuant );
							
						}else {
						
							future.get(key).put(traderid,traderQuant);
						
						}
						
					}else {
						
						//traderValue.put(traderid, traderQuant)
						HashMap<Integer, Integer> traderValue = new HashMap<Integer, Integer>();
						traderValue.put(traderid, traderQuant);
						future.put(key , traderValue);
						
					}
					
			}
	}
	
	public void parseDatabase(ArrayList<TradeList> allTrades, HashMap<String, HashMap<Integer,Integer>> past, HashMap<String, HashMap<Integer, Integer>> future) {
		
		int current = 1;
		int pastStart = 0;
		int pastEnd = 0;
		int futureStart = 1;
		int futureEnd = this.databaseSize>60? 60 : this.databaseSize;
		
		for(int i=1; i<this.databaseSize; i++)
		{
			TradeList trade = allTrades.get(i);
			String companyName = trade.getCompany().toLowerCase();
			String tradeType = trade.getBuyOrSell().toLowerCase();
			String key = companyName+";"+tradeType;
			
			if(isLargeTrade(trade)) {
				findFRScenario(trade);
			}
			
			//update HashTable
			
			
		}
	}
	
	static boolean isLargeTrade(TradeList trade) {
		if(trade.getQty() >= 20000)
			return true;
		return false;
	}
	
	static void findFRScenario(TradeList trade) {
		
	}
	
	
	public static void main(String[] args) {
		GenerateHashMap obj = new GenerateHashMap();
		obj.parseDatabase(obj.trades, obj.past, obj.future);
		
	}
	
}


