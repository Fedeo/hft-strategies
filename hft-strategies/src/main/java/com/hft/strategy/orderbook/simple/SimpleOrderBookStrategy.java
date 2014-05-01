package com.hft.strategy.orderbook.simple;

import com.hft.data.IHftSecurity;
import com.hft.order.book.OrderBookController;
import com.hft.run.HFT;
import com.hft.strategy.IStrategy;
import com.hft.strategy.orderbook.OrderBookStrategy;

public class SimpleOrderBookStrategy extends OrderBookStrategy implements IStrategy {

	private final Double spreadApplied;
	private final IHftSecurity security;
	private final int orderBookKey;

	public SimpleOrderBookStrategy(Double spreadApplied, IHftSecurity security) {
		super();
		this.spreadApplied = spreadApplied;
		this.security = security;
		this.orderBookKey = security.hashCode();
	}

	@Override
	public void onStart() {
		// Request the feed for the strategy
		HFT.dataFeed().requestMktData(security);
	}

	@Override
	public void onOrderBookDataChange() {
		// no need to handle book change because this strategy
		// is only working on top level of bid/ask
	}

	@Override
	public void onTopLevelMktDataChange() {
		Boolean entryCondition = OrderBookController.getOrderBook(orderBookKey).spreadBidAsk() > spreadApplied; // &&
																							// notInMarket
		if (entryCondition) {
			HFT.orderManager().sendOrder();
		}
	}

	@Override
	public void onOrderChange() {
		// if Order has been submitted then place as OCA
		HFT.orderManager().sendOrder(); // 1) order for the Profit Target
		HFT.orderManager().sendOrder(); // 2) order for the Stop Loss
	}

	@Override
	public void onClose() {
		// Verify that all orders have been closed
	}
	
	@Override
	public int hashCode() {
		final int strategyPrimeCode = 31;
		int result = 1;
		result = strategyPrimeCode * result + ((security == null) ? 0 : security.hashCode());
		result = strategyPrimeCode * result + ((spreadApplied == null) ? 0 : spreadApplied.hashCode());
		return result;
	}



}
