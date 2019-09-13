package com.dao;

import java.util.List; 

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.TradeList;



public class TestShowAll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TradeListDAO dao = new TradeListDAOImpl();
		List<TradeList> list = dao.display();

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

		
		
		
		
	}
	
	
	

}
