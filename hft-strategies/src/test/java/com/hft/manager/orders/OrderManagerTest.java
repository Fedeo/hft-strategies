package com.hft.manager.orders;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.hft.data.HftCurrencyPair;
import com.hft.data.HftOrder;
import com.hft.data.IHftSecurity;
import com.hft.run.Constant;

public class OrderManagerTest {
	
	@Test
	public void testOneOrder() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0);
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(120));

	}

	@Test
	public void testTwoBuyOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0);
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0);
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(152));

	}
	
	@Test
	public void testTwoBuyOneSellOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0);
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0);
		HftOrder order3 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 90, 125.0);
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);
		ordersPlaced.add(order3);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(62));

	}
	
	@Test
	public void testTwoBuyTwoSellOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0);
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 90, 125.0);
		HftOrder order3 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0);
		HftOrder order4 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 62, 125.0);
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);
		ordersPlaced.add(order3);
		ordersPlaced.add(order4);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(0));

	}
}
