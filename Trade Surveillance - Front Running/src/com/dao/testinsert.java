package com.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import com.pojo.TradeList;

public class testinsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TradeListDAO dao = new TradeListDAOImpl();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter trade id: ");
		int tradeID = scanner.nextInt();
		System.out.println("Enter price: ");
		double price = scanner.nextDouble();
		System.out.println("Enter quantity: ");

		long quantity = scanner.nextLong();
		System.out.println("Enter security: ");

		String security = scanner.next();
		System.out.println("Enter trade type: ");
		String tradeType = scanner.next();

		System.out.println("Enter trader id: ");

		int traderID = scanner.nextInt();

		
		System.out.println("Enter broker name: ");

		String brokerName = scanner.next();
		Date date = new Date();

		long time = date.getTime();

		Timestamp ts = new Timestamp(time);
		System.out.println(ts);

		TradeList tradelist = new TradeList(tradeID, price, quantity, security, tradeType, traderID, brokerName, ts);

		int inserted = dao.insertBetween(tradelist);
		System.out.println(inserted);
	}

}
