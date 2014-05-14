package com.hft.strategy.orderbook.simple;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.hft.adapter.ib.controller.Types.DeepSide;
import com.hft.adapter.ib.controller.Types.DeepType;
import com.hft.connector.orders.IOrderConnector;
import com.hft.data.HftCurrencyPair;
import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.data.feed.IDataFeed;
import com.hft.manager.orders.OrderManager;
import com.hft.manager.orders.OrderWorkFlowManager;
import com.hft.order.book.BookItem;
import com.hft.order.book.OrderBookController;
import com.hft.run.HFT;
import com.hft.strategy.StrategiesHandler;

public class SimpleOrderBookStrategyTest {
	
	IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH", new Double(0.0001));
	SimpleOrderBookStrategy simpleStrategy = new SimpleOrderBookStrategy(new Double(0.02), eurUsd);
	int strategyKey = simpleStrategy.hashCode();
	int securityKey = eurUsd.hashCode();
	
	// Initialize the datafeed and OrderManager
	TestDataFeed dataFeed = new TestDataFeed();
	TestOrderConnector orderConnector = new TestOrderConnector();

	
	//Order Books
	BookItem bookAsk2 = new  BookItem(10,new Double(10.14));
	BookItem bookAsk1 = new  BookItem(5,new Double(10.12));
	BookItem bookBid1 = new  BookItem(10,new Double(10.09));
	BookItem bookBid2 = new  BookItem(8,new Double(10.08));
	
	ArrayList<BookItem> bid = new ArrayList<BookItem>();
	ArrayList<BookItem> ask = new ArrayList<BookItem>();


	@Test
	public void testEntryOrderSubmission() {
		
		OrderWorkFlowManager.getInstance().clearOrdersForAllStrategies();
		StrategiesHandler.cleanStrategies();
		
		// Initialize feed and orders
		HFT.setDataFeed(dataFeed);
		HFT.setOrderConnector(orderConnector);

		StrategiesHandler.addStrategy(simpleStrategy);
		StrategiesHandler.initialize();

		Assert.assertFalse(OrderManager.isInMarket(simpleStrategy));
		
		//Add items on Book
		OrderBookController.addBid(securityKey, bookBid1, 0);
		OrderBookController.addAsk(securityKey, bookAsk1, 0);
		
		Assert.assertTrue(OrderManager.isInMarket(simpleStrategy));
	}

	
	@Test
	public void testExits() {
		
		OrderWorkFlowManager.getInstance().clearOrdersForAllStrategies();
		
		// Initialize feed and orders
		HFT.setDataFeed(dataFeed);
		HFT.setOrderConnector(orderConnector);

		StrategiesHandler.addStrategy(simpleStrategy);
		StrategiesHandler.initialize();

		Assert.assertFalse(OrderManager.isInMarket(simpleStrategy));
		
		//Add items on Book
		OrderBookController.addBid(securityKey, bookBid1, 0);
		OrderBookController.addAsk(securityKey, bookAsk1, 0);
		
		Assert.assertTrue(OrderManager.isInMarket(simpleStrategy));
		
		orderConnector.onOrder();
		orderConnector.onOrder();
	}

	
	private class TestDataFeed implements IDataFeed {

		@Override
		public void connect() {

		}

		@Override
		public void disconnect() {

		}

		@Override
		public void requestMktData(IHftSecurity security) {

		}

		@Override
		public void requestDeepMktData(IHftSecurity security) {
			// TODO Auto-generated method stub
			
		}


	}

	private class TestOrderConnector implements IOrderConnector {
		
		int lastOrderId;
		
		@Override
		public void sendOrder(HftOrder newOrder) {
			lastOrderId = newOrder.orderId;
		}

		public void onOrder() {
			OrderManager.setOrderFilled(lastOrderId);
		}

		public void onBookChanged() {

		}

	}

}
