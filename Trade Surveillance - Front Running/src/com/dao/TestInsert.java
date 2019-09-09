package com.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.pojo.TradeList;
import com.pojo.Trader;

public class TestInsert {

	public static void main(String[] args) {
		TradeListDAO dao = new TradeListDAOImpl();
		

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Trade ID: ");
		int tradeID = scanner.nextInt();
		
		System.out.println("Enter trader id: ");

		int traderID = scanner.nextInt();
		
		System.out.println("Enter trader name: ");
		String traderName = scanner.next();
		
		Trader trader=new Trader(traderID, traderName);
		
		System.out.println("Enter price: ");
		float price = scanner.nextFloat();
		
		System.out.println("Enter quantity: ");

		int quantity = scanner.nextInt();
		System.out.println("Enter security: ");

		String security = scanner.next();
		System.out.println("Enter trade type: ");
		String tradeType = scanner.next();

		
		
		System.out.println("Enter broker name: ");

		String brokerName = scanner.next();
		System.out.println("Enter company name: ");

		String company = scanner.next();
		Date date = new Date();

		long time = date.getTime();

		Timestamp ts = new Timestamp(time);
		System.out.println(ts);

		TradeList tradelist = new TradeList(tradeID, ts, trader, tradeType, security, quantity, price, brokerName, company);
		int inserted = dao.insertBetween(tradelist);
		System.out.println(inserted);
		
		List<TradeList> list=dao.display();
		list.forEach(System.out::println);
	}

}
