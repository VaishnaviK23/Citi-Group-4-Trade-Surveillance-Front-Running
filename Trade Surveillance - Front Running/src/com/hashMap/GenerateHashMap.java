package com.hashMap;
import java.util.ArrayList;
import java.util.HashMap;
import com.hashMap.ExtractTradeData;
import com.pojo.TradeList;
//import com.dao.*;

public class GenerateHashMap {
	
	//HashMap(TraderID, totalQuant)
		HashMap<String, HashMap<Integer, Integer>> past = new HashMap<String, HashMap<Integer, Integer>>(); 
	HashMap<String, HashMap<Integer, Integer>> future = new HashMap<String, HashMap<Integer, Integer>>(); 
	ArrayList<TradeList> trades = null;
	
	
	public GenerateHashMap() {
		this.trades = new ExtractTradeData().getDataFromDatabase();
			
			//updating the future HashMap
			for(int i=0;i<5;i++) {
				//String key = trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell();
				//Add the incoming trade to HashMap
				
				//Lower Case Key
				String key = (trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell()).toLowerCase();
				int traderid =trades.get(i).getTrader().getTraderID();
				int traderQuant = trades.get(i).getQty();
				
					if(future.containsKey(key)) {
						
						if(future.get(key).containsKey(traderid)) {
							//update the inside hasmap
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
			System.out.println(future);
	}
	
	public void updateHashMap() {
		
	}
	
	public static void main(String[] args) {
		GenerateHashMap obj = new GenerateHashMap();
	}
	
}


