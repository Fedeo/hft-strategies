package com.hft.order.book;

import java.util.HashMap;

import com.hft.strategy.StrategiesHandler;

public class OrderBookController {

	private final static int MAX_ORDER_BOOK = 10;
	static protected HashMap<Integer, OrderBook> orderBooks = new HashMap<Integer, OrderBook>(MAX_ORDER_BOOK);

	static public void addBid(int orderBookKey, BookItem bookItem, int position) {
		getOrderBook(orderBookKey).addBid(bookItem, position);
		notifyStrategies(position);
	}

	static public void addAsk(int orderBookKey, BookItem bookItem, int position) {
		getOrderBook(orderBookKey).addAsk(bookItem, position);
		notifyStrategies(position);
	}

	static public Double spreadBidAsk(int orderBookKey) {
		return getOrderBook(orderBookKey).spreadBidAsk();
	}
	
	static protected void notifyStrategies(int position){
		if (position == 0)
			notifyStrategiesForTopLevelMktDataChange();
		notifyStrategiesForBookChange();
	}

	static protected void notifyStrategiesForBookChange() {
		StrategiesHandler.notifyStrategiesForBookChange();
	}

	static protected void notifyStrategiesForTopLevelMktDataChange() {
		StrategiesHandler.notifyStrategiesForTopLevelMktDataChange();
	}

	static protected OrderBook getOrderBook(int orderBookKey) {
		if (!orderBooks.containsKey(orderBookKey)) {
			addOrderBook(orderBookKey);
		}
		return orderBooks.get(orderBookKey);
	}

	static protected void addOrderBook(int orderBookKey) {
		if (!orderBooks.containsKey(orderBookKey))
			orderBooks.put(orderBookKey, new OrderBook());
	}

}
