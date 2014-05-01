package com.hft.strategy.orderbook.simple;

import com.hft.manager.orders.IOrderManager;
import com.hft.order.book.OrderBook;
import com.hft.strategy.IStrategy;
import com.hft.strategy.orderbook.OrderBookStrategy;

public class SimpleOrderBookStrategy extends OrderBookStrategy implements IStrategy {

	private final Double spreadApplied;
	private final IOrderManager orderManager;

	public SimpleOrderBookStrategy(Double spreadApplied, IOrderManager orderManager) {
		super();
		this.spreadApplied = spreadApplied;
		this.orderManager = orderManager;
	}

	@Override
	public void onStart() {
		// TODO initialize the variable
	}

	@Override
	public void onOrderBookDataChange() {
		// no need to handle book change because this strategy
		// is only working on top level of bid/ask
	}

	@Override
	public void onTopLevelMktDataChange() {

		Boolean entryCondition = OrderBook.getInstance().spreadBidAsk() > spreadApplied; // &&
																							// notInMarket
		if (entryCondition) {
			orderManager.sendOrder();
		}
	}

	@Override
	public void onOrderChange() {
		// if Order has been submitted then place as OCA
		orderManager.sendOrder(); // 1) order for the Profit Target
		orderManager.sendOrder(); // 2) order for the Stop Loss
	}

	@Override
	public void onClose() {
		// Verify that all orders have been closed
	}

}
