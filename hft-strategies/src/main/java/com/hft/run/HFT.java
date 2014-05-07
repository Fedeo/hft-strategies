package com.hft.run;

import org.apache.log4j.Logger;

import com.hft.connector.orders.IOrderConnector;
import com.hft.data.HftCurrencyPair;
import com.hft.data.IHftSecurity;
import com.hft.data.feed.IDataFeed;
import com.hft.data.feed.ib.IBDataFeed;
import com.hft.manager.orders.ib.IBOrderConnector;
import com.hft.order.book.OrderBookController;
import com.hft.strategy.StrategiesHandler;
import com.hft.strategy.orderbook.simple.SimpleOrderBookStrategy;

public class HFT {
	static HFT INSTANCE = new HFT();
	static IDataFeed dataFeed;
	static IOrderConnector orderConnector;

	static Logger logger = Logger.getLogger(HFT.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		INSTANCE.run();
	}

	public static IDataFeed dataFeed() {
		return dataFeed;
	}

	public static IOrderConnector orderConnector() {
		return orderConnector;
	}

	public void run() {

		logger.info("Starting HFT Strategies version: " + ApplicationConfiguration.getVersion());

		// Initialize the datafeed and OrderManager
		dataFeed = new IBDataFeed();
		orderConnector = new IBOrderConnector("DU153566");

		// Initialize Strategies
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		IHftSecurity usdJpy = new HftCurrencyPair("USD", "IDEALPRO", "JPY", "CASH");

		// Initialize Strategies
		SimpleOrderBookStrategy simpleOrderBookStratEurUsd = new SimpleOrderBookStrategy(0.001, eurUsd);
		SimpleOrderBookStrategy simpleOrderBookStraUsdJpy = new SimpleOrderBookStrategy(200.0, usdJpy);

		StrategiesHandler.addStrategy(simpleOrderBookStratEurUsd);
		StrategiesHandler.addStrategy(simpleOrderBookStraUsdJpy);
		StrategiesHandler.initialize();

		// Start the main
		try {
			Thread.sleep(10000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		logger.info("disconnecting");
		//System.out.println("EURUSD Spread = " + OrderBookController.spreadBidAsk(eurUsd.hashCode()));
		//System.out.println("USDJPY Spread = " + OrderBookController.spreadBidAsk(usdJpy.hashCode()));
		dataFeed.disconnect();
	}
}
