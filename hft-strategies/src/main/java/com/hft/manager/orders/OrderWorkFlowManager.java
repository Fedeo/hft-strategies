package com.hft.manager.orders;

import java.util.List;

import org.apache.commons.collections.MultiHashMap;

import com.hft.data.HftOrder;

public class OrderWorkFlowManager {

	private static OrderWorkFlowManager instance = null;
	static protected MultiHashMap mapStrategyOrders = new MultiHashMap();

	protected OrderWorkFlowManager() {
	}

	public static OrderWorkFlowManager getInstance() {
		if (instance == null) {
			instance = new OrderWorkFlowManager();
		}
		return instance;

	}

	public void addOrder(Integer strategyKey, HftOrder order) {
		mapStrategyOrders.put(strategyKey, order);
	}

	@SuppressWarnings("unchecked")
	public List<HftOrder> getOrders(Integer strategyKey) {
		return (List<HftOrder>) mapStrategyOrders.get(strategyKey);
	}
	
	public void clearOrdersForStrategy(Integer strategyKey) {
		mapStrategyOrders.remove(strategyKey);
	}

	public void clearOrdersForAllStrategies() {
		mapStrategyOrders.clear();
	}

}
