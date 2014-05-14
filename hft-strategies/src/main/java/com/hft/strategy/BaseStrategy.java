package com.hft.strategy;

import java.util.List;

import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.manager.orders.OrderManager;
import com.hft.manager.orders.Sequence;
import com.hft.run.Constant;
import com.hft.run.HFT;

public abstract class BaseStrategy {

	// Market Positions Methods
	protected Boolean isInMarket(IStrategy strategy) {
		return OrderManager.isInMarket(strategy);
	}

	protected Boolean ordersHaveBeenSubmitted(IStrategy strategy, List<HftOrder> pendingOrders) {
		return OrderManager.ordersExist(strategy, pendingOrders);
	}

	protected Boolean isLong(int strategyKey) {
		return null;
	}

	protected Boolean isShort(int strategyKey) {
		return null;
	}

	// Methods for creating Orders

	protected HftOrder createBuyMarketOrder(IHftSecurity security, int qty, String description) {
		return createGenericBuyOrder(security, Constant.ORDER_MKT, new Double(0.0), qty, description);
	}

	protected HftOrder createSellMarketOrder(IHftSecurity security, int qty, String description) {
		return createGenericBuyOrder(security, Constant.ORDER_MKT, new Double(0.0), qty, description);
	}

	protected HftOrder createBuyLimitOrder(IHftSecurity security, Double lmtPrice, int qty, String description) {
		return createGenericBuyOrder(security, Constant.ORDER_LMT, lmtPrice, qty, description);
	}

	protected HftOrder createSellLimitOrder(IHftSecurity security, Double lmtPrice, int qty, String description) {
		return createGenericSellOrder(security, Constant.ORDER_LMT, lmtPrice, qty, description);
	}

	// Accessories Methods for creating Order

	private HftOrder createGenericBuyOrder(IHftSecurity security, String orderType, Double lmtPrice, int qty,
			String description) {
		return createGenericOrder(security, Constant.ACTION_BUY, orderType, lmtPrice, qty, description);
	}

	private HftOrder createGenericSellOrder(IHftSecurity security, String orderType, Double lmtPrice, int qty,
			String description) {
		return createGenericOrder(security, Constant.ACTION_SELL, orderType, lmtPrice, qty, description);
	}

	private HftOrder createGenericOrder(IHftSecurity security, String action, String orderType, Double lmtPrice,
			int qty, String description) {
		return new HftOrder(Sequence.getInstance().getAndIncreaseOrderId(), security, action, orderType, qty, lmtPrice,
				description);
	}

	protected void submitOrder(HftOrder newOrder) {
		HFT.orderConnector().sendOrder(newOrder);
		OrderManager.addOrder(newOrder, getStrategy());
	}

	protected abstract IStrategy getStrategy();

}
