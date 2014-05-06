package com.hft.manager.orders;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.hft.data.HftCurrencyPair;
import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.run.Constant;
import com.hft.strategy.orderbook.simple.SimpleOrderBookStrategy;

public class OrderWorkFlowManagerTest {

	@Test
	public void testAddOneOrder() {

		// Securities
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");

		// Strategy
		SimpleOrderBookStrategy simpleStrategy = new SimpleOrderBookStrategy(new Double(1.0), eurUsd);

		// Orders
		HftOrder order1 = new HftOrder(10, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		OrderWorkFlowManager.getInstance().addOrder(simpleStrategy, order1);

		List<HftOrder> orders = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy.hashCode());

		Assert.assertTrue(orders.size() == 1);
		Assert.assertTrue(orders.get(0).action.compareTo(Constant.ACTION_BUY) == 0);
		Assert.assertTrue(orders.get(0).security.equals(eurUsd));
		
		
		HftOrder storedOrder = OrderWorkFlowManager.getInstance().getOrder(orders.get(0).orderId);
		Assert.assertNotNull(storedOrder);
		Assert.assertTrue(storedOrder.security.equals(eurUsd));
		
		OrderWorkFlowManager.getInstance().changeStatusToOrder(10,"");
	}

	@Test
	public void testAddTwoOrdersSameStrategy() {

		// Securities
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");

		// Strategy
		SimpleOrderBookStrategy simpleStrategy = new SimpleOrderBookStrategy(new Double(1.0), eurUsd);

		// Clear Orders for the Strategy
		OrderWorkFlowManager.getInstance().clearOrdersForStrategy(simpleStrategy.hashCode());

		// Orders
		HftOrder order1 = new HftOrder(10, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		HftOrder order2 = new HftOrder(11, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 122.0);
		OrderWorkFlowManager.getInstance().addOrder(simpleStrategy, order1);
		OrderWorkFlowManager.getInstance().addOrder(simpleStrategy, order2);

		List<HftOrder> orders = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy.hashCode());

		Assert.assertTrue(orders.size() == 2);
		Assert.assertTrue(orders.get(0).action.compareTo(Constant.ACTION_BUY) == 0);
		Assert.assertTrue(orders.get(0).security.equals(eurUsd));

		Assert.assertTrue(orders.get(1).action.compareTo(Constant.ACTION_SELL) == 0);
		Assert.assertTrue(orders.get(1).security.equals(eurUsd));
		
		HftOrder storedOrder = OrderWorkFlowManager.getInstance().getOrder(11);
		Assert.assertNotNull(storedOrder);
		Assert.assertTrue(storedOrder.security.equals(eurUsd));
		Assert.assertTrue(storedOrder.action.compareTo(Constant.ACTION_SELL) == 0);
		
	}

	@Test
	public void testAddTwoOrdersForDifferentStrategies() {

		// Securities
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		IHftSecurity eurJpy = new HftCurrencyPair("EUR", "IDEALPRO", "JPY", "CASH");

		// Strategy
		SimpleOrderBookStrategy simpleStrategy1 = new SimpleOrderBookStrategy(new Double(1.0), eurUsd);
		SimpleOrderBookStrategy simpleStrategy2 = new SimpleOrderBookStrategy(new Double(1.0), eurJpy);

		// Orders
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0);
		HftOrder order2 = new HftOrder(21, eurJpy, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 129.0);
		OrderWorkFlowManager.getInstance().addOrder(simpleStrategy1, order1);
		OrderWorkFlowManager.getInstance().addOrder(simpleStrategy2, order2);

		List<HftOrder> ordersStrategy1 = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy1.hashCode());
		List<HftOrder> ordersStrategy2 = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy2.hashCode());

		Assert.assertTrue(ordersStrategy1.size() == 3);
		Assert.assertTrue(ordersStrategy1.get(0).action.compareTo(Constant.ACTION_BUY) == 0);
		Assert.assertTrue(ordersStrategy1.get(0).security.equals(eurUsd));
		Assert.assertTrue(ordersStrategy1.get(0).lmtPrice.compareTo(new Double(121.0)) == 0);

		Assert.assertTrue(ordersStrategy1.get(1).action.compareTo(Constant.ACTION_SELL) == 0);
		Assert.assertTrue(ordersStrategy1.get(1).security.equals(eurUsd));
		Assert.assertTrue(ordersStrategy1.get(1).lmtPrice.compareTo(new Double(122.0)) == 0);

		Assert.assertTrue(ordersStrategy1.get(2).action.compareTo(Constant.ACTION_BUY) == 0);
		Assert.assertTrue(ordersStrategy1.get(2).security.equals(eurUsd));
		Assert.assertTrue(ordersStrategy1.get(2).lmtPrice.compareTo(new Double(125.0)) == 0);

		Assert.assertTrue(ordersStrategy2.size() == 1);
		Assert.assertTrue(ordersStrategy2.get(0).action.compareTo(Constant.ACTION_SELL) == 0);
		Assert.assertTrue(ordersStrategy2.get(0).security.equals(eurJpy));
		Assert.assertTrue(ordersStrategy2.get(0).lmtPrice.compareTo(new Double(129.0)) == 0);
		
		
		HftOrder storedOrderStrategy1 = OrderWorkFlowManager.getInstance().getOrder(20);
		Assert.assertNotNull(storedOrderStrategy1);
		Assert.assertTrue(storedOrderStrategy1.security.equals(eurUsd));
		Assert.assertTrue(storedOrderStrategy1.action.compareTo(Constant.ACTION_BUY) == 0);
		
		HftOrder storedOrderStrategy2 = OrderWorkFlowManager.getInstance().getOrder(21);
		Assert.assertNotNull(storedOrderStrategy2);
		Assert.assertTrue(storedOrderStrategy2.security.equals(eurJpy));
		Assert.assertTrue(storedOrderStrategy2.action.compareTo(Constant.ACTION_SELL) == 0);
		
		Assert.assertTrue(OrderWorkFlowManager.getInstance().getOrder(20).status.compareTo(Constant.ORDER_NEW) == 0);
		OrderWorkFlowManager.getInstance().changeStatusToOrder(20,Constant.ORDER_ACKNOWLEDGED);
		Assert.assertTrue(OrderWorkFlowManager.getInstance().getOrder(20).status.compareTo(Constant.ORDER_ACKNOWLEDGED) == 0);
		
		Assert.assertTrue(OrderWorkFlowManager.getInstance().getOrder(21).status.compareTo(Constant.ORDER_NEW) == 0);
		OrderWorkFlowManager.getInstance().changeStatusToOrder(21,Constant.ORDER_FILLED);
		Assert.assertTrue(OrderWorkFlowManager.getInstance().getOrder(21).status.compareTo(Constant.ORDER_FILLED) == 0);
		
	}

	@Test
	public void testCleanAll() {

		// Clear Orders for All the Strategies
		OrderWorkFlowManager.getInstance().clearOrdersForAllStrategies();

		// Securities
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		IHftSecurity eurJpy = new HftCurrencyPair("EUR", "IDEALPRO", "JPY", "CASH");

		// Strategies
		SimpleOrderBookStrategy simpleStrategy1 = new SimpleOrderBookStrategy(new Double(1.0), eurUsd);
		SimpleOrderBookStrategy simpleStrategy2 = new SimpleOrderBookStrategy(new Double(1.0), eurJpy);

		// Orders
		List<HftOrder> ordersStrategy1 = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy1.hashCode());
		List<HftOrder> ordersStrategy2 = OrderWorkFlowManager.getInstance().getOrders(simpleStrategy2.hashCode());

		Assert.assertNull(ordersStrategy1);
		Assert.assertNull(ordersStrategy2);
	}

}
