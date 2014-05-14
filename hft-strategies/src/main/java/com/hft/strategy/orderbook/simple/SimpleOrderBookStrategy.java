package com.hft.strategy.orderbook.simple;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.order.book.BookItem;
import com.hft.order.book.OrderBookController;
import com.hft.order.book.exceptions.BookItemNotAvailableException;
import com.hft.order.book.exceptions.SpreadNotAvailableException;
import com.hft.run.HFT;
import com.hft.strategy.IStrategy;
import com.hft.strategy.orderbook.OrderBookStrategy;

public class SimpleOrderBookStrategy extends OrderBookStrategy implements IStrategy {

	protected int counter = 0;
	private final Double spreadApplied;
	private final IHftSecurity security;
	private final int orderBookKey;
	private final static String STRATEGY_NAME = "SimpleOrderBookStrategy";
	protected Double sellReferencePrice;
	protected Double stopLossReferencePrice;

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
		Boolean entryCondition = (counter == 10);
		Double currentSpread;
		BookItem bestBid;
		BookItem bestAsk;

		try {
			currentSpread = OrderBookController.spreadAskBid(orderBookKey);
			bestBid = OrderBookController.getBestBid(orderBookKey);
			bestAsk = OrderBookController.getBestAsk(orderBookKey);

			// TODO: Fake Entry for testing: to be removed
			counter++;
			if ((counter % 10) == 0) {
				// entryCondition = true;
			}

			// TODO: Real Entry -> to add and remove the fake one

			if (currentSpread > spreadApplied) {
				entryCondition = true;
				logger.debug("SimpleOrderBookStrategy current spread:" + currentSpread);
			}

			if (entryCondition && !isInMarket(this)) {
				logger.info("Send Order for SimpleOrderBookStrategy " + security.getSymbol() + "- Spread:"
						+ currentSpread + " bid:" + bestBid.price + " ask:" + bestAsk.price);

				// TODO: Understand Rounding based on ticksize
				HftOrder entryOrder = createBuyLimitOrder(security, bestBid.price + security.getTick(), 200,"SOB-LE");
				submitOrder(entryOrder);
				sellReferencePrice = bestAsk.price - security.getTick();
				stopLossReferencePrice = currentSpread / 2;
				// buyMarket(security, 200);
			}
		} catch (SpreadNotAvailableException e) {
			logger.debug("Book not yet completed to calculate the spread");
		} catch (BookItemNotAvailableException e) {
			logger.debug(e.getDescription());
		}

	}

	@Override
	public void onOrderChange(int OrderId) {
		logger.info("Change of Order for SimpleOrderBookStrategy orderId:" + OrderId + "- notified");
		HftOrder profitTargetOrder = createSellLimitOrder(security, sellReferencePrice, 200,"SOB-PT");
		HftOrder stopLossOrder = createSellLimitOrder(security, stopLossReferencePrice, 200,"SOB-SL");

		List<HftOrder> pendingOrders = new ArrayList<HftOrder>();
		pendingOrders.add(profitTargetOrder);
		pendingOrders.add(stopLossOrder);

		Boolean exitOrdersHaveBeenSubmitted = ordersHaveBeenSubmitted(this,pendingOrders); // To implement
		if (isInMarket(this) && !exitOrdersHaveBeenSubmitted) {
			submitOrder(profitTargetOrder); // Profit Target - OCA
			submitOrder(stopLossOrder); // Stop Loss - OCA
		}
	}

	@Override
	public void onClose() {
		// Verify that all orders have been closed
	}

	@Override
	public IStrategy getStrategy() {
		return this;
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
