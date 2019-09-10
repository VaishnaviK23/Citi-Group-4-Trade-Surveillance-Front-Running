import java.util.Collections;
import java.util.LinkedList;

import com.businesslogic.FrontRunningDetectionList;
import com.dao.TradeListDAOImpl;
import com.pojo.TradeList;

import java.sql.*;

public class ClientDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TradeListDAOImpl obj = new TradeListDAOImpl();
		LinkedList<TradeList> tradeList = new LinkedList<TradeList>();
		Connection conn = obj.openConnection();
		FrontRunningDetectionList detect = new FrontRunningDetectionList();
		
		for (int i = 1; i <= 203; i++) {
			TradeList o = obj.fetchBySr(i, conn);
			// System.out.println(tradeList.size());
			
			tradeList.addFirst(o);
			

		}
		
		Collections.reverse(tradeList);
		System.out.println(detect.findFRScenario1(tradeList));
		System.out.println(detect.findFRScenario2(tradeList));
		// boolean b = obj.findFR(tradeList);

	}

}
