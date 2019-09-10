package com.hashMap;
import java.util.ArrayList;
import java.util.HashMap;
import com.dao.TradeListDAOImpl;
import com.hashMap.ExtractTradeData;
import com.pojo.TradeList;
//import com.dao.*;

public class GenerateHashMap {

	HashMap<String, HashMap<Integer, Integer>> past = new HashMap<String, HashMap<Integer, Integer>>(); 
	HashMap<String, HashMap<Integer, Integer>> future = new HashMap<String, HashMap<Integer, Integer>>(); 
	ArrayList<TradeList> trades = null;
	static int interval = 4;
	int databaseSize;
	//HashMap(TraderID , TradeIDList[])
	HashMap<Integer,ArrayList<Integer>> TrackFraudTrades = new HashMap<Integer,ArrayList<Integer>>();
	
	public GenerateHashMap() {
			this.trades = new ExtractTradeData().getDataFromDatabase();
			//this.databaseSize = new TradeListDAOImpl().getRecordCount();
			this.databaseSize = 8;
			System.out.println("Database Size: "+this.databaseSize);
			//updating the future HashMap

			for(int i=1;i<=interval;i++) {
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
			System.out.println(this.future);
	}
	
	public void parseDatabase(ArrayList<TradeList> allTrades, HashMap<String, HashMap<Integer,Integer>> past, HashMap<String, HashMap<Integer, Integer>> future) {
		
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
			System.out.println("Trade Num: "+ i);
			TradeList trade = allTrades.get(i);
			String key = generateKey(trade);
			
			if(isLargeTrade(trade)) {
				findFRScenario(trade); // Omkar's stuff
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
				HashMap<Integer, Integer> innerMap = this.past.get(outerKey);
				int	traderId = tradeToRemove.getTrader().getTraderID();
				
				if(innerMap.containsKey(traderId)) {
					innerMap.put(traderId, innerMap.get(traderId) - tradeToRemove.getQty());
					if(innerMap.get(traderId)<=0) {
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
						
						HashMap<Integer, Integer> inner = this.past.get(outerKey);
						int innerValue = inner.get(innerKey);
						inner.put(innerKey, innerValue+tradeToAdd.getQty());
					}
					else {						
						HashMap<Integer, Integer> temp = this.past.get(outerKey);
						temp.put(tradeToAdd.getTrader().getTraderID(), tradeToAdd.getQty());
					}
								
				}
				else {
					HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
					temp.put(tradeToAdd.getTrader().getTraderID(), tradeToAdd.getQty());
					this.past.put(outerKey, temp);
				}
				
			}
			
			System.out.println("---------------Past Data-----------------");
			System.out.println(this.past);
			
			// Add into futures HashTable
			

			//remove futureStart from the table and add futureEnd+1 to the table
			if(futureStart < databaseSize) {
				TradeList futureTradeToRemove = allTrades.get(futureStart);
				String futureOuterKey = generateKey(futureTradeToRemove);
				HashMap<Integer, Integer> FutureInnerMap = this.future.get(futureOuterKey);
				int	traderId = futureTradeToRemove.getTrader().getTraderID();
				System.out.println("Removing: trader "+traderId+" from future");
				if(FutureInnerMap.containsKey(traderId)) {
					FutureInnerMap.put(traderId, FutureInnerMap.get(traderId) - futureTradeToRemove.getQty());
					if(FutureInnerMap.get(traderId)<=0) {
						System.out.println("Remove Trade traderID: "+traderId);
						FutureInnerMap.remove(traderId);
						if(FutureInnerMap.keySet().size()==0) {
							this.future.remove(futureOuterKey);
						}
					}
				}
				futureStart++;
			
			
			
			if(futureEnd < databaseSize-1) {
				TradeList tradeToAdd = allTrades.get(futureEnd+1);
				
				futureOuterKey = generateKey(tradeToAdd);
				
				if(this.future.containsKey(futureOuterKey)) {
					int innerKey = tradeToAdd.getTrader().getTraderID();
					if(this.future.get(futureOuterKey).containsKey(innerKey)) {
						
						HashMap<Integer, Integer> inner = this.future.get(futureOuterKey);
						int innerValue = inner.get(innerKey);
						inner.put(innerKey, innerValue+tradeToAdd.getQty());
					}
					else {						
						HashMap<Integer, Integer> temp = this.future.get(futureOuterKey);
						temp.put(tradeToAdd.getTrader().getTraderID(), tradeToAdd.getQty());
					}
								
				}
				else {
					HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
					temp.put(tradeToAdd.getTrader().getTraderID(), tradeToAdd.getQty());
					this.future.put(futureOuterKey, temp);
				}
				
			}
			else {
				System.out.println("future End greater than size of database");
			}
			futureEnd++;
			System.out.println("---------Future--------------");
			System.out.println(this.future);
		}
		}

	}
	private void addIntoHashTable(HashMap<String, HashMap<Integer, Integer>> hashTable, TradeList tradeList) {
		String key = generateKey(tradeList);
		System.out.println(key);
		if(hashTable.containsKey(key))
		{
			HashMap<Integer, Integer> temp = hashTable.get(key); //get inner Hash Table
			int innerKey = tradeList.getTrader().getTraderID();
			System.out.println(temp.size());
			if(temp.containsKey(tradeList.getTrader().getTraderID())) { //if trader exists
				temp.put(innerKey, temp.get(innerKey)+tradeList.getQty());
			}
			else { // if not found
				temp.put(innerKey, tradeList.getQty());
			}			
		}
		else {
			HashMap<Integer, Integer> innerMap = new HashMap<Integer, Integer>();
			innerMap.put(tradeList.getTrader().getTraderID(), tradeList.getQty());
			hashTable.put(key, innerMap);
		}


	}

	
	private String generateKey(TradeList tradeList) {
		// TODO Auto-generated method stub
		return (tradeList.getCompany() + ";" + tradeList.getBuyOrSell() ).toLowerCase();
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


