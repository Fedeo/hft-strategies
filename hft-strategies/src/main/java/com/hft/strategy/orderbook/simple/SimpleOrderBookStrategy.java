package com.hft.strategy.orderbook.simple;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hft.data.IHftSecurity;
import com.hft.order.book.OrderBookController;
import com.hft.run.HFT;
import com.hft.strategy.IStrategy;
import com.hft.strategy.orderbook.OrderBookStrategy;

public class SimpleOrderBookStrategy extends OrderBookStrategy implements IStrategy {

	private final Double spreadApplied;
	private final IHftSecurity security;
	private final int orderBookKey;
	private final static String STRATEGY_NAME = "SimpleOrderBookStrategy";

	static Logger logger = Logger.getLogger(SimpleOrderBookStrategy.class.getName());

	public SimpleOrderBookStrategy(Double spreadApplied, IHftSecurity security) {
		super();
		this.spreadApplied = spreadApplied;
		this.security = security;
		this.orderBookKey = security.hashCode();
	}

	@Override
	public void onStart() {
		// Request the feed for the strategy
		HFT.dataFeed().requestDeepMktData(security);
	}

	@Override
	public void onOrderBookDataChange() {
		logger.debug("Change of book for SimpleOrderBookStrategy " + security.getSymbol() + "- notified");
	}

	@Override
	public void onTopLevelMktDataChange() {
		logger.debug("Change of TopLevelMktDataChange for SimpleOrderBookStrategy " + security.getSymbol()
				+ "- notified");
		Boolean entryCondition = false;
		Double currentSpread = OrderBookController.spreadBidAsk(orderBookKey);
		// OrderBookController.spreadBidAsk(orderBookKey) > spreadApplied; // &&
		// notInMarket
		if (entryCondition && !isInMarket(this.hashCode())) {
			logger.info("Send Order for SimpleOrderBookStrategy " + security.getSymbol() + "- Spread:" + currentSpread);
			buyLimit(security, new Double(10.0), 1);
		}
	}

	@Override
	public void onOrderChange() {
		// if Order has been submitted then place as OCA
		// HFT.orderManager().sendOrder(); // 1) order for the Profit Target
		// HFT.orderManager().sendOrder(); // 2) order for the Stop Loss
	}

	@Override
	public void onClose() {
		// Verify that all orders have been closed
	}
	

	@Override
	public String getStrategyName() {
		return STRATEGY_NAME;
	}

	@Override
	public List<IHftSecurity> getAllSecurities() {
		ArrayList<IHftSecurity> securities = new ArrayList<IHftSecurity>();
		securities.add(this.security);
		return securities;
	}

}
