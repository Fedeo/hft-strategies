package com.hft.run;

import com.hft.data.HftCurrencyPair;
import com.hft.data.IHftSecurity;
import com.hft.data.feed.IDataFeed;
import com.hft.data.feed.ib.IBDataFeed;
import com.hft.manager.orders.IOrderManager;
import com.hft.manager.orders.ib.IBOrderManager;

public class HFT {
	static HFT INSTANCE = new HFT();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		INSTANCE.run();
	}

	public void run() {

		System.out.println("Starting");

		//Initialize the datafeed and OrderManager
		IDataFeed dataFeed = new IBDataFeed();
		IOrderManager orderManager = new IBOrderManager(); 
		
		//Initialize Strategies
		IHftSecurity eurUsd = new HftCurrencyPair("EUR","IDEALPRO","USD","CASH");
		
		//Add DataFeed Request
		dataFeed.requestMktData(eurUsd);
		
		//Start the main
		try {
			Thread.sleep(30000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		System.out.println("disconnecting");
		dataFeed.disconnect();
	}

}
