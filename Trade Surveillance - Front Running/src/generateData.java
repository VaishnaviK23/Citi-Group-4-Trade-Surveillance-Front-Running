import com.dao.TradeListDAOImpl;

public class generateData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TradeListDAOImpl obj = new TradeListDAOImpl();
		
		obj.genTrades(100);
	}

}
