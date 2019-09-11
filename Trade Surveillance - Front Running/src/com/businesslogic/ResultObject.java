package com.businesslogic;

import java.util.ArrayList;

import com.dao.TradeListDAOImpl;
import com.pojo.TradeList;

public class ResultObject {
	
	private TradeList currentTrade;
	private TradeInfo suspiciousTrades;
	private TradeInfo frontRunningTrades;
	private ArrayList<TradeList> listOfSuspiciousTrades = new ArrayList<TradeList>();
	private ArrayList<TradeList> listOfFrontRunningTrades = new ArrayList<TradeList>();
	private int scenarioNumber;
	
	
	
	public ArrayList<TradeList> getListOfSuspiciousTrades() {
		return listOfSuspiciousTrades;
	}

	public void setListOfSuspiciousTrades(ArrayList<TradeList> listOfSuspiciousTrades) {
		ArrayList<Integer> tempList = this.suspiciousTrades.getTradeNumberList();
		for (Integer tradeId : tempList) {
			this.listOfSuspiciousTrades.add(new TradeListDAOImpl().findTradeListByID(tradeId));
		}
	}

	public ArrayList<TradeList> getListOfFrontRunningTrades() {
		return listOfFrontRunningTrades;
	}

	public void setListOfFrontRunningTrades(ArrayList<TradeList> listOfFrontRunningTrades) {
		ArrayList<Integer> tempList = this.frontRunningTrades.getTradeNumberList();
		for (Integer tradeId : tempList) {
			this.listOfFrontRunningTrades.add(new TradeListDAOImpl().findTradeListByID(tradeId));
		}
	}

	
	
	
	
	public TradeList getCurrentTrade() {
		return currentTrade;
	}

	public void setCurrentTrade(TradeList currentTrade) {
		this.currentTrade = currentTrade;
	}
	
	public ResultObject(TradeList currentTrade, TradeInfo suspiciousTrades, TradeInfo frontRunningTrades, int scenarioNumber) {
		this.scenarioNumber = scenarioNumber;
		this.currentTrade = currentTrade;
		this.suspiciousTrades = suspiciousTrades;
		this.frontRunningTrades = frontRunningTrades;
		this.setListOfSuspiciousTrades(this.listOfSuspiciousTrades);
		System.out.println("Done");
		this.setListOfFrontRunningTrades(this.listOfFrontRunningTrades);
		System.out.println("Done");
	}

	public int getScenarioNumber() {
		return scenarioNumber;
	}

	public void setScenarioNumber(int scenarioNumber) {
		this.scenarioNumber = scenarioNumber;
	}

	@Override
	public String toString() {
		return "ResultObject [currentTrade=" + currentTrade + ", suspiciousTrades=" + suspiciousTrades
				+ ", frontRunningTrades=" + frontRunningTrades + ", listOfSuspiciousTrades=" + listOfSuspiciousTrades
				+ ", listOfFrontRunningTrades=" + listOfFrontRunningTrades + ", scenarioNumber=" + scenarioNumber + "]";
	}

}
