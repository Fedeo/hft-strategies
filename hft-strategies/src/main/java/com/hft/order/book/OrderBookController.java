package com.hft.order.book;

import java.util.HashMap;

public class OrderBookController {

	private final static int MAX_ORDER_BOOK = 10;
	static protected HashMap<Integer, OrderBook> orderBooks = new HashMap<Integer, OrderBook>(MAX_ORDER_BOOK);

	static public OrderBook getOrderBook(int orderBookKey) {
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
