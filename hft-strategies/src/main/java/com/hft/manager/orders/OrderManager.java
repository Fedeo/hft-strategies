package com.hft.manager.orders;

import java.util.List;

import com.hft.data.HftOrder;
import com.hft.run.Constant;
import com.hft.strategy.IStrategy;

public class OrderManager {
	
	//The Strategy is in Market if the sum of qty is <> 0
	//TODO: Understand if we need to add a check on the Security
	
	static public Boolean isInMarket(IStrategy strategy) {
		return getQuantityOnMarket(OrderWorkFlowManager.getInstance().getOrders(strategy.hashCode()))>0;
	}
	
	static public void addOrder(HftOrder order,IStrategy strategy){
		OrderWorkFlowManager.getInstance().addOrder(strategy.hashCode(), order);
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
