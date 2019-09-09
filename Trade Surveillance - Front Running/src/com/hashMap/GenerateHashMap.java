package com.hashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hashMap.ExtractTradeData;
import com.pojo.TradeList;
//import com.dao.*;

public class GenerateHashMap {

	// HashMap(TraderID, totalQuant)
	HashMap<String, HashMap<Integer, Integer>> past = new HashMap<String, HashMap<Integer, Integer>>();
	HashMap<String, HashMap<Integer, Integer>> future = new HashMap<String, HashMap<Integer, Integer>>();
	ArrayList<TradeList> trades = null;

	public GenerateHashMap() {
		this.trades = new ExtractTradeData().getDataFromDatabase();

		// updating the future HashMap
		for (int i = 0; i <= 60; i++) {
			// String key = trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell();
			// Add the incoming trade to HashMap

			// Lower Case Key
			String key = (trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell()).toLowerCase();
			int traderid = trades.get(i).getTrader().getTraderID();
			int traderQuant = trades.get(i).getQty();

			if (future.containsKey(key)) {

				if (future.get(key).containsKey(traderid)) {
					// update the inside hashmap
					future.get(key).put(traderid, future.get(key).get(traderid) + traderQuant);

				} else {
					future.get(key).put(traderid, traderQuant);
				}

			} else {

				// traderValue.put(traderid, traderQuant)
				HashMap<Integer, Integer> traderValue = new HashMap<Integer, Integer>();
				traderValue.put(traderid, traderQuant);
				future.put(key, traderValue);

			}

		}
		System.out.println(future);
	}

	public void findFRScenario(TradeList victim) {

		String key1 = victim.getCompany() + ";" + victim.getTypeOfSecurity();

		if (victim.getTypeOfSecurity() == "Buy") {
			String key2 = victim.getCompany() + ";Sell";

			HashMap<Integer, Integer> pastTraderMap = past.get(key1);
			HashMap<Integer, Integer> futureTraderMap = future.get(key2);
			ArrayList<Integer> frTransactions = new ArrayList<Integer>();
			Integer findInFuture, pastSecurities;

			Set<Entry<Integer, Integer>> pastMapIterSet = pastTraderMap.entrySet();

			for (Entry pastTraderEntry : pastMapIterSet) {
				findInFuture = (int) pastTraderEntry.getKey();

				if (futureTraderMap.containsKey(findInFuture)) {
					pastSecurities = (Integer) pastTraderEntry.getValue();
					if ((Integer) futureTraderMap.get(findInFuture) - pastSecurities < (0.1 * pastSecurities)) {
						frTransactions.add(findInFuture);
					}

				}
			}
		}

	}

	public static void main(String[] args) {
		GenerateHashMap obj = new GenerateHashMap();

	}

}