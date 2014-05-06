package com.hft.strategy;

import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.manager.orders.OrderManager;
import com.hft.manager.orders.Sequence;
import com.hft.run.Constant;
import com.hft.run.HFT;

public abstract class BaseStrategy {

	// Sell and Buy methods

	protected void buyMarket(IHftSecurity security, int qty) {
		submitGenericBuy(security, Constant.ORDER_MKT, new Double(0.0), qty);
	}

	protected void sellMarket(IHftSecurity security, int qty) {
		submitGenericSell(security, Constant.ORDER_MKT, new Double(0.0), qty);
	}

	protected void buyLimit(IHftSecurity security, Double lmtPrice, int qty) {
		submitGenericBuy(security, Constant.ORDER_LMT, new Double(0.0), qty);
	}

	protected void sellLimit(IHftSecurity security, Double lmtPrice, int qty) {
		submitGenericSell(security, Constant.ORDER_LMT, new Double(0.0), qty);
	}

	// Market Positions Methods
	protected Boolean isInMarket(IStrategy strategy) {
		return OrderManager.isInMarket(strategy);
	}

	protected Boolean isLong(int strategyKey) {
		return null;
	}

	protected Boolean isShort(int strategyKey) {
		return null;
	}

	// Accessories Methods

	private void submitGenericBuy(IHftSecurity security, String orderType, Double lmtPrice, int qty) {
		submitGenericOrder(security, Constant.ACTION_BUY, orderType, lmtPrice, qty);
	}

	private void submitGenericSell(IHftSecurity security, String orderType, Double lmtPrice, int qty) {
		submitGenericOrder(security, Constant.ACTION_SELL, orderType, lmtPrice, qty);
	}

	private void submitGenericOrder(IHftSecurity security, String action, String orderType, Double lmtPrice, int qty) {
		HftOrder newOrder = new HftOrder(Sequence.getInstance().getAndIncreaseOrderId(), security, action, orderType,
				qty, lmtPrice);
		HFT.orderConnector().sendOrder(newOrder);
		OrderManager.addOrder(newOrder, getStrategy());
	}

	protected abstract IStrategy getStrategy();

}