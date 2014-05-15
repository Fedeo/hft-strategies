package com.hft.manager.orders;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.MultiHashMap;
import org.apache.log4j.Logger;

import com.hft.data.HftOrder;
import com.hft.strategy.IStrategy;

public class OrderWorkFlowManager {

	private static OrderWorkFlowManager instance = null;
	static protected MultiHashMap mapStrategyOrders = new MultiHashMap();
	static protected HashMap<Integer, IStrategy> mapOrderStrategy = new HashMap<Integer, IStrategy>();
	
	static Logger logger = Logger.getLogger(OrderWorkFlowManager.class.getName());

	protected OrderWorkFlowManager() {
	}

	public static OrderWorkFlowManager getInstance() {
		if (instance == null) {
			instance = new OrderWorkFlowManager();
		}
		return instance;
	}

	public void addOrder(IStrategy strategy, HftOrder order) {
		registerOrder(strategy, order);
	}
	
	public void changeStatusToOrder(int orderId,String newStatus){
		int indexOfOrder = getOrderIndex(orderId);
		if (indexOfOrder!=-1) {
			IStrategy strategyOfTheOrder = mapOrderStrategy.get(orderId);
			getOrders(strategyOfTheOrder.hashCode()).get(getOrderIndex(orderId)).status=newStatus;
			strategyOfTheOrder.onOrderChange(orderId); //notify the strategy the order is changed
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<HftOrder> getOrders(Integer strategyKey) {
		return (List<HftOrder>) mapStrategyOrders.get(strategyKey);
	}

	public void clearOrdersForStrategy(Integer strategyKey) {
		clearOrderStrategyMapForStrategy(strategyKey);
		clearStrategyOrdersMapForStrategy(strategyKey);
	}

	public void clearOrdersForAllStrategies() {
		mapOrderStrategy.clear();
		mapStrategyOrders.clear();
	}

	protected void clearOrderStrategyMapForStrategy(Integer strategyKey) {
		for (HftOrder order : getOrders(strategyKey)) {
			mapOrderStrategy.remove(order.orderId);
		}
	}

	protected void clearStrategyOrdersMapForStrategy(Integer strategyKey) {
		mapStrategyOrders.remove(strategyKey);
	}

	protected HftOrder getOrder(int orderId) {
		List<HftOrder> storedOrders = getOrders(mapOrderStrategy.get(orderId).hashCode());
		ListIterator<HftOrder> listIter = storedOrders.listIterator(storedOrders.size());
		while (listIter.hasPrevious()) {
			//System.out.println(listIter.previousIndex());
			HftOrder prev = listIter.previous();
			if (prev.orderId == orderId)
				return prev;
		}
		return null;
	}
	
	//TODO: refactor this method with previous one
	protected int getOrderIndex(int orderId) {
		List<HftOrder> storedOrders = getOrders(mapOrderStrategy.get(orderId).hashCode());
		ListIterator<HftOrder> listIter = storedOrders.listIterator(storedOrders.size());
		int idx=-1;
		while (listIter.hasPrevious()) {
			idx = listIter.previousIndex();
			HftOrder prev = listIter.previous();
			if (prev.orderId == orderId)
				return idx;
		}
		return -idx;
	}
	
	protected void registerOrder(IStrategy strategy, HftOrder order) {
		mapStrategyOrders.put(strategy.hashCode(), order);
		mapOrderStrategy.put(order.orderId, strategy);
	}

}
