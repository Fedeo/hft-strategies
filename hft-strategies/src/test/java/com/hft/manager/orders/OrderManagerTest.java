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
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(120));
		Assert.assertTrue(OrderManager.ordersExist(ordersPlaced, ordersPlaced));
	}

	@Test
	public void testTwoBuyOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0,"SOB-LE");
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);
		
		HftOrder order3 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		List<HftOrder> ordersPending = new ArrayList<HftOrder>();
		ordersPending.add(order3);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(152));
		Assert.assertTrue(OrderManager.ordersExist(ordersPlaced, ordersPending));

	}
	
	@Test
	public void testTwoBuyOneSellOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0,"SOB-LE");
		HftOrder order3 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 90, 125.0,"SOB-LE");
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);
		ordersPlaced.add(order3);
		
		HftOrder order4 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		HftOrder order5 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 90, 125.0,"SOB-LE");
		List<HftOrder> ordersPending = new ArrayList<HftOrder>();
		ordersPending.add(order4);
		ordersPending.add(order5);

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(62));
		Assert.assertTrue(OrderManager.ordersExist(ordersPlaced, ordersPending));
		
	}
	
	@Test
	public void testTwoBuyTwoSellOrders() {

		// Orders
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 120, 125.0,"SOB-LE");
		HftOrder order2 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 90, 125.0,"SOB-LE");
		HftOrder order3 = new HftOrder(20, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 32, 125.0,"SOB-LE");
		HftOrder order4 = new HftOrder(20, eurUsd, Constant.ACTION_SELL, Constant.ORDER_MKT, 62, 125.0,"SOB-LE");
		List<HftOrder> ordersPlaced = new ArrayList<HftOrder>();
		ordersPlaced.add(order1);
		ordersPlaced.add(order2);
		ordersPlaced.add(order3);
		ordersPlaced.add(order4);
		
		List<HftOrder> ordersPending = new ArrayList<HftOrder>();

		Assert.assertEquals(OrderManager.getQuantityOnMarket(ordersPlaced), new Long(0));
		Assert.assertFalse(OrderManager.ordersExist(ordersPlaced, ordersPending));

	}
}
