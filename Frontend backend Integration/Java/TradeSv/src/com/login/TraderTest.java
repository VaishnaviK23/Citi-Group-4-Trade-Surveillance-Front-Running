package com.login;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/check")

public class TraderTest {

		// TODO Auto-generated method stub
		
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)

		public int crunchifyREST(JsonObject incomingData) {
			
			String received =incomingData.getString("traderid");
//					incomingData.getString("traderid");
			TraderDAO trader = new TraderDAOImpl();

			//System.out.println("data received is : - "+ incomingData);

			
			int traderid= Integer.parseInt(received);

			System.out.println("trader id is" + traderid);
			
		//int traderID=1;
		Trader tr=trader.findTraderByID(traderid);
		System.out.println("tradername: "+ tr.getTraderName());
		System.out.println("traderID: "+ tr.getTraderID());

		return(tr.getTraderID());

		}
}

