package com.hft.manager.orders;

import java.util.List;

import com.hft.data.HftOrder;
import com.hft.run.Constant;
import com.hft.strategy.IStrategy;

public class OrderManager {

	// The Strategy is in Market if the sum of qty is <> 0
	// TODO: Understand if we need to add a check on the Security

	static public Boolean isInMarket(IStrategy strategy) {
		return getQuantityOnMarket(OrderWorkFlowManager.getInstance().getOrders(strategy.hashCode())) > 0;
	}

	static public void addOrder(HftOrder order, IStrategy strategy) {
		OrderWorkFlowManager.getInstance().addOrder(strategy, order);
	}

	static public void setOrderAcknowledged(int orderId) {
		OrderWorkFlowManager.getInstance().changeStatusToOrder(orderId, Constant.ORDER_ACKNOWLEDGED);
	}

	static public void setOrderFilled(int orderId) {
		OrderWorkFlowManager.getInstance().changeStatusToOrder(orderId, Constant.ORDER_FILLED);
	}

	static public Boolean ordersExist(IStrategy strategy, List<HftOrder> pendingOrders) {
		return ordersExist(OrderWorkFlowManager.getInstance().getOrders(strategy.hashCode()), pendingOrders);
	}

	static protected Boolean ordersExist(List<HftOrder> ordersPlaced, List<HftOrder> pendingOrders) {
		Boolean result = new Boolean(false);
		if (pendingOrders != null && ordersPlaced != null) {
			for (HftOrder pendingOrder : pendingOrders) {
				for (HftOrder placedOrder : ordersPlaced) {
					if (pendingOrder.equals(placedOrder))
						return true;
				}
			}
		}
		return result;
	}

	static protected Long getQuantityOnMarket(List<HftOrder> ordersPlaced) {
		Long sum = new Long(0);
		if (ordersPlaced != null) {
			for (HftOrder order : ordersPlaced) {
				sum = sum + (order.action.compareTo(Constant.ACTION_BUY) == 0 ? order.qty : -(order.qty));
			}
		}
		return sum;
	}

}
