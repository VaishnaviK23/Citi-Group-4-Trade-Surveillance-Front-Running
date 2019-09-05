package com.businesslogic;

import java.util.LinkedList;
import java.util.ListIterator;

import com.pojo.TradeList;

public class FrontRunningDetectionList {
	
	public boolean findFRScenario2 (LinkedList<TradeList> tradeList) {
		// TODO Auto-generated method stub
		// tradeDAOImpl obj = new tradeDAOImpl();
		// LinkedList<TradeList> tradeList = new LinkedList<TradeList>();
		ListIterator<TradeList> curr_iterator = tradeList.listIterator();
		ListIterator<TradeList> bck_iterator = tradeList.listIterator();
		ListIterator<TradeList> fwd_iterator = tradeList.listIterator();
		boolean flag = false;
		int quantity = 0;
		String comp = null;
		int trader;
		
		label: while (curr_iterator.hasNext()) {
			TradeList trade = (TradeList) curr_iterator.next();
			if (trade.getBuyOrSell().equals("Sell") && trade.getQty() > 20000) {
				
				comp = trade.getCompany();
				bck_iterator = tradeList.listIterator(curr_iterator.previousIndex());
				while (bck_iterator.hasPrevious()) {
					
					int count1 = 0;
					TradeList tradeFirmSell = (TradeList) bck_iterator.previous();
					if (tradeFirmSell.getTrader().getTraderID() != trade.getTrader().getTraderID()) {
						if ((trade.getTypeOfSecurity().equals("Shares") || trade.getTypeOfSecurity().equals("Futures")) && tradeFirmSell.getCompany().equals(comp) && tradeFirmSell.getBuyOrSell().equals("Buy") && tradeFirmSell.getTypeOfSecurity().equals("Put Option")) {
							System.out.println("Trade 1: " + trade.toString());
							System.out.println("Front running detected @ tradeID:  " + tradeFirmSell.toString());
							System.out.println();
							flag = true;
							continue label;
						}
						if (tradeFirmSell.getCompany().equals(comp) && (tradeFirmSell.getBuyOrSell()).equals("Sell")) {
							
							if((trade.getTypeOfSecurity().equals("Shares") || trade.getTypeOfSecurity().equals("Futures")) 
									&& tradeFirmSell.getTypeOfSecurity().equals("Call Option")) {
								System.out.println("Trade 1: " + trade.toString());
								System.out.println("Front running detected @ tradeID:  " + tradeFirmSell.toString());
								System.out.println();
								flag =  true;
								continue label;
							}
							
							
							
							trader = tradeFirmSell.getTrader().getTraderID();
							
							quantity = tradeFirmSell.getQty();
							fwd_iterator = tradeList.listIterator(curr_iterator.nextIndex());
							while (fwd_iterator.hasNext()) {
								int count2 = 0;
								TradeList tradeFirmBuy = (TradeList) fwd_iterator.next();
								if (tradeFirmBuy.getTypeOfSecurity().equals(tradeFirmSell.getTypeOfSecurity())) {
									if ((trade.getTypeOfSecurity().equals("Shares") || trade.getTypeOfSecurity().equals("Futures")) && 
											(tradeFirmSell.getTypeOfSecurity().equals("Shares") || tradeFirmSell.getTypeOfSecurity().equals("Futures"))) {
										if (tradeFirmBuy.getCompany().equals(comp) && tradeFirmBuy.getBuyOrSell().equals("Buy") && 
												tradeFirmBuy.getQty() >= quantity && tradeFirmBuy.getTrader().getTraderID() == trader) {
											System.out.println("Trade 1: " + trade.toString());
											System.out.println("Second suspicious trade: " + tradeFirmSell.toString());
											System.out.println("Front running detected @ tradeID:  " + tradeFirmBuy.toString());
											System.out.println();
											flag = true;
											continue label;
										}
									}
									
								}
								
								count2++;
								if (count2 == 60)
									break;
							}
							
						}
						count1++;
						if (count1 == 60) 
							break;
					}
				}

			}
		}
		
		return flag;

		
	}
	
	public boolean findFRScenario1 (LinkedList<TradeList> tradeList) {
		// TODO Auto-generated method stub
		// tradeDAOImpl obj = new tradeDAOImpl();
		// LinkedList<TradeList> tradeList = new LinkedList<TradeList>();
		ListIterator curr_iterator = tradeList.listIterator();
		ListIterator bck_iterator = tradeList.listIterator();
		ListIterator fwd_iterator = tradeList.listIterator();
		
		boolean flag = false;
		int quantity = 0;
		String comp = null;
		int trader;
		
		label: while (curr_iterator.hasNext()) {
			TradeList trade = (TradeList) curr_iterator.next();
			if (trade.getBuyOrSell().equals("Buy") && trade.getQty() > 20000) {
				
				comp = trade.getCompany();
				bck_iterator = tradeList.listIterator(curr_iterator.previousIndex());
				while (bck_iterator.hasPrevious()) {
					
					int count1 = 0;
					TradeList tradeFirmBuy = (TradeList) bck_iterator.previous();
					if (trade.getTrader().getTraderID() != tradeFirmBuy.getTrader().getTraderID()) {
						if (trade.getTypeOfSecurity().equals("Shares") && tradeFirmBuy.getCompany().equals(comp) 
								&& tradeFirmBuy.getBuyOrSell().equals("Sell") && tradeFirmBuy.getTypeOfSecurity().equals("Put Option")) {
							System.out.println("Trade 1:  " + trade.toString());
							System.out.println("Front running detected @ tradeID:  " + tradeFirmBuy.toString());
							System.out.println();
							flag = true;
							continue label;
						}
						if (tradeFirmBuy.getCompany().equals(comp) && (tradeFirmBuy.getBuyOrSell()).equals("Buy")) {
							
							if((trade.getTypeOfSecurity().equals("Shares") ||
									trade.getTypeOfSecurity().equals("Futures")) && tradeFirmBuy.getTypeOfSecurity().equals("Call Option")) {
								System.out.println("Trade 1:  " + trade.toString());
								System.out.println("Front running detected @ tradeID:  " + tradeFirmBuy.toString());
								System.out.println();
								flag = true;
								continue label;
							}
							
							
							
							trader = tradeFirmBuy.getTrader().getTraderID();
							
							quantity = tradeFirmBuy.getQty();
							fwd_iterator = tradeList.listIterator(curr_iterator.nextIndex());
							while (fwd_iterator.hasNext()) {
								int count2 = 0;
								TradeList tradeFirmSell = (TradeList) fwd_iterator.next();
								if (tradeFirmBuy.getTypeOfSecurity().equals(tradeFirmSell.getTypeOfSecurity())) {
									if ((trade.getTypeOfSecurity().equals("Shares") || trade.getTypeOfSecurity().equals("Futures")) && 
											(tradeFirmSell.getTypeOfSecurity().equals("Shares") || tradeFirmSell.getTypeOfSecurity().equals("Futures"))) {
										if (tradeFirmSell.getCompany().equals(comp) && tradeFirmSell.getBuyOrSell().equals("Sell") && 
												(tradeFirmSell.getQty() >= (0.9 * quantity) || tradeFirmSell.getQty() <= (1.1 * quantity)) &&
												tradeFirmSell.getTrader().getTraderID() == trader) {
											System.out.println("Trade 1:  " + trade.toString());
											System.out.println("Second suspicious trade: " + tradeFirmBuy.toString());
											System.out.println("Front running detected @ tradeID:  " + tradeFirmSell.toString());
											System.out.println();
											flag = true;
											continue label;
										}
									}
									
								}
								
								count2++;
								if (count2 == 60)
									break;
							}
							
						}
						count1++;
						if (count1 == 60) 
							break;
					}
					}

			}
		}
		if (flag)
			return true;
		return false;

		
	}


}
