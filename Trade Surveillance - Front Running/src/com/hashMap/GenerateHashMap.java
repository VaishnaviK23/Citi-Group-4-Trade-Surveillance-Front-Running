package com.hashMap;
import com.hashMap.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.dao.TradeListDAOImpl;
import com.hashMap.ExtractTradeData;
import com.pojo.TradeList;
//import com.dao.*;

public class GenerateHashMap {

	HashMap<String, HashMap<Integer, TradeInfo>> past = new HashMap<String, HashMap<Integer, TradeInfo>>(); 
	HashMap<String, HashMap<Integer, TradeInfo>> future = new HashMap<String, HashMap<Integer, TradeInfo>>(); 
	ArrayList<TradeList> trades = null;
	static int interval = 4;
	int databaseSize;
	//HashMap(TraderID , TradeIDList[])
	HashMap<Integer,ArrayList<Integer>> trackFraudTrades = new HashMap<Integer,ArrayList<Integer>>();
	ArrayList<Integer> fraudulentTransactions;
	
	
	public GenerateHashMap() {
			this.fraudulentTransactions = new ArrayList<Integer>();
			this.trades = new ExtractTradeData().getDataFromDatabase();
			//this.databaseSize = new TradeListDAOImpl().getRecordCount();
			this.databaseSize = 8;	
			System.out.println("Database Size: "+this.databaseSize);
			//updating the future HashMap
			
			ArrayList<Integer> initialArray = new ArrayList<Integer>();
			int firstTradeId = trades.get(0).getTradeID();
			initialArray.add(firstTradeId);
			trackFraudTrades.put(trades.get(0).getTrader().getTraderID(), initialArray);
			for(int i=1;i<=interval;i++) {
				//String key = trades.get(i).getCompany() + ";" + trades.get(i).getBuyOrSell();
				//Add the incoming trade to HashMap
				
				//Lower Case Key
				String key = generateKey(trades.get(i));
				int traderid =trades.get(i).getTrader().getTraderID();
				int traderQuant = trades.get(i).getQty();
				int tradeid = trades.get(i).getTradeID();
				
				if(trackFraudTrades.containsKey(traderid)) {
					
					ArrayList<Integer> temp;
					temp = trackFraudTrades.get(traderid);
					temp.add(tradeid);
					trackFraudTrades.put(traderid, temp);
					
				}else {
					
					ArrayList<Integer> temp = new ArrayList<>();
					temp.add(tradeid);
					trackFraudTrades.put(traderid, temp);
				}
				
				if(future.containsKey(key)) {
						if(future.get(key).containsKey(traderid)) {
							//update the inside hashmap
							System.out.println("Aggrgating: "+ traderQuant);
							TradeInfo tradeInfo = future.get(key).get(traderid);
							tradeInfo.quantity += traderQuant;
							tradeInfo.tradeNumberList.add(tradeid);
							future.get(key).put(traderid, tradeInfo);
							
						}else {
							ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
							listOfTrades.add(tradeid);
							TradeInfo tradeInfo = new TradeInfo(traderQuant,listOfTrades);
							future.get(key).put(traderid,tradeInfo);
						
						}
						
					}else {
						
						//traderValue.put(traderid, traderQuant)
						HashMap<Integer, TradeInfo> traderValue = new HashMap<Integer, TradeInfo>();
						ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
						listOfTrades.add(tradeid);
						TradeInfo tradeInfo = new TradeInfo(traderQuant,listOfTrades);
						traderValue.put(traderid, tradeInfo);
						future.put(key , traderValue);
					}
					
			}
			System.out.println(this.future);
	}
	
	public void parseDatabase(ArrayList<TradeList> allTrades, HashMap<String, HashMap<Integer,TradeInfo>> past, HashMap<String, HashMap<Integer, TradeInfo>> future) {
		
		int current = 0;
		int pastStart = 0;
		int pastEnd = 0;
		int futureStart = 1;
		int futureEnd = interval; // if database size less than 60 then futureEnd will point to the last element in the database
		int pastDataSize = 1;
		int futureDataSize = interval;
		// start from 1 as the trade 0 is added in the "past" hash table 
		for(int i=0; i<this.databaseSize; i++)
		{
						
			TradeList trade = allTrades.get(i);
			System.out.println("Current Trade: "+ trade);
			String key = generateKey(trade);
			System.out.println("---------------Past Data-----------------");
			System.out.println(this.past);
			
			System.out.println("---------------Future Data-----------------");
			System.out.println(this.future);
			
			
			if(i>=1 && isLargeTrade(trade)) {
				//findFRScenario(trade); // Omkar's stuff
			}
			
			//update HashTable
			current++;
			if(pastDataSize<=interval) {
				pastEnd++;
				addIntoHashTable(this.past,allTrades.get(pastEnd-1));
				pastDataSize++;
			}
			else {
				//remove pastStart from the table and add current to the table
				
				TradeList tradeToRemove = allTrades.get(pastStart);
				String outerKey = generateKey(tradeToRemove);
				HashMap<Integer, TradeInfo> innerMap = this.past.get(outerKey);
				int	traderId = tradeToRemove.getTrader().getTraderID();
				
				if(innerMap.containsKey(traderId)) {
					
					TradeInfo tradeInfo = innerMap.get(traderId);
					tradeInfo.quantity -= tradeToRemove.getQty();
					tradeInfo.tradeNumberList.remove(tradeInfo.tradeNumberList.indexOf(tradeToRemove.getTradeID()));
					innerMap.put(traderId, tradeInfo);
					if(innerMap.get(traderId).getQuantity()<=0) {
//						System.out.println("Remove Trade traderID: "+traderId);
						innerMap.remove(traderId);
						if(innerMap.keySet().size()==0) {
							this.past.remove(outerKey);
						}
					}
				}
				pastStart++;			
				TradeList tradeToAdd = allTrades.get(current-1);
				outerKey = generateKey(tradeToAdd);
				
				if(this.past.containsKey(outerKey)) {
					int innerKey = tradeToAdd.getTrader().getTraderID();
					if(this.past.get(outerKey).containsKey(innerKey)) {
						
						HashMap<Integer, TradeInfo> inner = this.past.get(outerKey);
						TradeInfo tradeInfo = inner.get(innerKey);
						tradeInfo.quantity+=tradeToAdd.getQty();
						tradeInfo.tradeNumberList.add(tradeToAdd.getTradeID());
						inner.put(innerKey, tradeInfo);
						
					}
					else {						
						HashMap<Integer, TradeInfo> temp = this.past.get(outerKey);
						
						ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
						listOfTrades.add(tradeToAdd.getTradeID());
						TradeInfo tradeInfo = new TradeInfo(tradeToAdd.getQty(), listOfTrades);
						
						temp.put(tradeToAdd.getTrader().getTraderID(), tradeInfo);
					}
								
				}
				else {
					HashMap<Integer, TradeInfo> temp = new HashMap<Integer, TradeInfo>();
					ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
					listOfTrades.add(tradeToAdd.getTradeID());
					TradeInfo tradeInfo = new TradeInfo(tradeToAdd.getQty(), listOfTrades);
					temp.put(tradeToAdd.getTrader().getTraderID(), tradeInfo);
					this.past.put(outerKey, temp);
				}
				
			}
			
			

			//remove pastStart from the table and add current to the table
			if(futureStart < databaseSize)
			{
				TradeList futureTradeToRemove = allTrades.get(futureStart);
				String outerKey = generateKey(futureTradeToRemove );
				HashMap<Integer, TradeInfo> futureInnerMap = this.future.get(outerKey);
				int	traderId = futureTradeToRemove.getTrader().getTraderID();
				
				if(futureInnerMap.containsKey(traderId)) {
					
					TradeInfo tradeInfo = futureInnerMap.get(traderId);
					tradeInfo.quantity -= futureTradeToRemove.getQty();
					tradeInfo.tradeNumberList.remove(tradeInfo.tradeNumberList.indexOf(futureTradeToRemove.getTradeID()));
					futureInnerMap.put(traderId, tradeInfo);
					if(futureInnerMap.get(traderId).getQuantity()<=0) {
//						System.out.println("Remove Trade traderID: "+traderId);
						futureInnerMap.remove(traderId);
						if(futureInnerMap.keySet().size()==0) {
							this.future.remove(outerKey);
						}
					}
				}
			
			futureStart++;	
			
			if(futureEnd < databaseSize - 1) {
				TradeList tradeToAdd = allTrades.get(futureEnd+1);
			
				String futureOuterKey = generateKey(tradeToAdd);
				
				if(this.future.containsKey(futureOuterKey)) {
					int innerKey = tradeToAdd.getTrader().getTraderID();
					if(this.future.get(futureOuterKey).containsKey(innerKey)) {
						
						HashMap<Integer, TradeInfo> inner = this.future.get(futureOuterKey);
						TradeInfo tradeInfo = inner.get(innerKey);
						tradeInfo.quantity+=tradeToAdd.getQty();
						tradeInfo.tradeNumberList.add(tradeToAdd.getTradeID());
						inner.put(innerKey, tradeInfo);
						
					}
					else {						
						HashMap<Integer, TradeInfo> temp = this.future.get(futureOuterKey);
						
						ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
						listOfTrades.add(tradeToAdd.getTradeID());
						TradeInfo tradeInfo = new TradeInfo(tradeToAdd.getQty(), listOfTrades);
						
						temp.put(tradeToAdd.getTrader().getTraderID(), tradeInfo);
					}
								
				}
				else {
					HashMap<Integer, TradeInfo> temp = new HashMap<Integer, TradeInfo>();
					ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
					listOfTrades.add(tradeToAdd.getTradeID());
					TradeInfo tradeInfo = new TradeInfo(tradeToAdd.getQty(), listOfTrades);
					temp.put(tradeToAdd.getTrader().getTraderID(), tradeInfo);
					this.future.put(futureOuterKey, temp);
				}

			}
						
			else {
				System.out.println("future End greater than size of database");
			}
			
			futureEnd++;
			
			}
			
			
			
//			System.out.println("Past Data--------------------------------");
//			System.out.println(this.past);
//			

			// Add into futures HashTable
			

			//remove futureStart from the table and add futureEnd+1 to the table

		}

	}
	private void addIntoHashTable(HashMap<String, HashMap<Integer, TradeInfo>> hashTable, TradeList tradeList) {
		String key = generateKey(tradeList);
		System.out.println(key);
		if(hashTable.containsKey(key))
		{
			HashMap<Integer, TradeInfo> temp = hashTable.get(key); //get inner Hash Table
			int innerKey = tradeList.getTrader().getTraderID();
			if(temp.containsKey(tradeList.getTrader().getTraderID())) { //if trader exists
				
				TradeInfo tradeInfo = temp.get(innerKey);
				ArrayList<Integer> listOfTrades = tradeInfo.getTradeNumberList();
				tradeInfo.quantity += tradeList.getQty();
				listOfTrades.add(tradeList.getTradeID());
				temp.put(innerKey, tradeInfo);
			}
			else { // if not found
				
				ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
				listOfTrades.add(tradeList.getTradeID());
				
				TradeInfo tradeInfo = new TradeInfo(tradeList.getQty(), listOfTrades);

				temp.put(innerKey, tradeInfo);
			}			
		}
		else {
			HashMap<Integer, TradeInfo> innerMap = new HashMap<Integer, TradeInfo>();
			
			ArrayList<Integer> listOfTrades = new ArrayList<Integer>();
			listOfTrades.add(tradeList.getTradeID());
			
			TradeInfo tradeInfo = new TradeInfo(tradeList.getQty(), listOfTrades);
			
			
			innerMap.put(tradeList.getTrader().getTraderID(), tradeInfo);
			hashTable.put(key, innerMap);
		}


	}

//	
	private String generateKey(TradeList tradeList) {
		// TODO Auto-generated method stub
		return (tradeList.getCompany() + ";" + tradeList.getBuyOrSell()+";"+tradeList.getTypeOfSecurity() ).toLowerCase();
	}

	static boolean isLargeTrade(TradeList trade) {
		if(trade.getQty() >= 20000)
			return true;
		return false;
	}
	
//
//	void findFRScenario(TradeList victim) {
//
//		String key1 = generateKey(victim);
//
////		detect bbs sss
//		if (victim.getBuyOrSell().equals("Buy") && (victim.getTypeOfSecurity().equals("Shares"))) {
//			String key2 = (victim.getCompany() + ";Sell" + ";Shares").toLowerCase();
//
//			HashMap<Integer, Integer> pastTraderMap = past.get(key1);
//			HashMap<Integer, Integer> futureTraderMap = future.get(key2);
//
//			Integer findInFuture, pastSecurities;
//
//			Set<Entry<Integer, Integer>> pastMapIterSet = pastTraderMap.entrySet();
//
//			for (Entry pastTraderEntry : pastMapIterSet) {
//				findInFuture = (int) pastTraderEntry.getKey();
//
//				if (futureTraderMap.containsKey(findInFuture)) {
//					pastSecurities = (Integer) pastTraderEntry.getValue();
//					if (((Integer) futureTraderMap.get(findInFuture) < (1.1 * pastSecurities)) || ((Integer) futureTraderMap.get(findInFuture) > (0.9 * pastSecurities))) {
//						this.fraudulentTransactions.add(findInFuture);
//					}
//				}
//			}
//		}
//		//detect bbs fff
//		if (victim.getTypeOfSecurity().equals("Buy") && (victim.getTypeOfSecurity().equals("Futures"))) {
//			String key2 = (victim.getCompany() + ";Sell" +";Futures").toLowerCase();
//
//			HashMap<Integer, Integer> pastTraderMap = past.get(key2);
//
//			Integer findInPast, pastSecurities;
//
//			Set<Entry<Integer, Integer>> pastMapIterSet = pastTraderMap.entrySet();
//
//			for (Entry pastTraderEntry : pastMapIterSet) {
//				findInPast = (int) pastTraderEntry.getKey();
//
//				if (pastTraderMap.containsKey(findInPast)) {
//					pastSecurities = (Integer) pastTraderEntry.getValue();
//					if ((Integer) pastTraderMap.get(findInPast) - pastSecurities < (0.1 * pastSecurities)) {
//						this.fraudulentTransactions.add(findInPast);
//					}
//
//				}
//			}
//		}
//		//detect bbs sfs
//		if (victim.getTypeOfSecurity().equals("Buy") && (victim.getTypeOfSecurity().equals("Futures"))) {
//			String key2 = (victim.getCompany() + ";Sell" +";Shares").toLowerCase();
//
//			HashMap<Integer, Integer> pastTraderMap = past.get(key2);
//
//			Integer findInPast, pastSecurities;
//
//			Set<Entry<Integer, Integer>> pastMapIterSet = pastTraderMap.entrySet();
//
//			for (Entry pastTraderEntry : pastMapIterSet) {
//				findInPast = (int) pastTraderEntry.getKey();
//
//				if (pastTraderMap.containsKey(findInPast)) {
//					pastSecurities = (Integer) pastTraderEntry.getValue();
//					if ((Integer) pastTraderMap.get(findInPast) - pastSecurities < (0.1 * pastSecurities)) {
//						this.fraudulentTransactions.add(findInPast);
//					}
//
//				}
//			}
//		}
//		//detect bbs fsf
//		if (victim.getTypeOfSecurity().equals("Buy") && (victim.getTypeOfSecurity().equals("Shares"))) {
//			String key2 = (victim.getCompany() + ";Sell" +";Futures").toLowerCase();
//
//			HashMap<Integer, Integer> pastTraderMap = past.get(key2);
//
//			Integer findInPast, pastSecurities;
//
//			Set<Entry<Integer, Integer>> pastMapIterSet = pastTraderMap.entrySet();
//
//			for (Entry pastTraderEntry : pastMapIterSet) {
//				findInPast = (int) pastTraderEntry.getKey();
//
//				if (pastTraderMap.containsKey(findInPast)) {
//					pastSecurities = (Integer) pastTraderEntry.getValue();
//					if ((Integer) pastTraderMap.get(findInPast) - pastSecurities < (0.1 * pastSecurities)) {
//						this.fraudulentTransactions.add(findInPast);
//					}
//
//				}
//			}
//		}
//	}
//	
//	
	
	public static void main(String[] args) {
		GenerateHashMap obj = new GenerateHashMap();
		obj.parseDatabase(obj.trades, obj.past, obj.future);
	}
	
}


