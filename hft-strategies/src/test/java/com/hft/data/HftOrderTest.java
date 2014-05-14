package com.hft.data;

import org.junit.Assert;
import org.junit.Test;

import com.hft.run.Constant;

public class HftOrderTest {

	@Test
	public void testEquals() {
		IHftSecurity eurUsd1 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(10, eurUsd1, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		HftOrder order2 = new HftOrder(10, eurUsd2, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		Assert.assertTrue(order1.equals(order2));
	}
	
	@Test
	public void testEqualsDifferentType() {
		IHftSecurity eurUsd1 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(10, eurUsd1, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 121.0);
		HftOrder order2 = new HftOrder(10, eurUsd2, Constant.ACTION_SELL, Constant.ORDER_LMT, 100, 121.0);
		Assert.assertTrue(order1.equals(order2));
	}
	
	
	@Test
	public void testNotEqualsDifferentSecurity() {
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity usdJpy = new HftCurrencyPair("USD", "IDEALPRO", "JPY", "CASH",new Double(0.01));
		HftOrder order1 = new HftOrder(10, eurUsd, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		HftOrder order2 = new HftOrder(10, usdJpy, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		Assert.assertFalse(order1.equals(order2));
	}
	
	@Test
	public void testNotEqualsDifferentQty() {
		IHftSecurity eurUsd1 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(10, eurUsd1, Constant.ACTION_BUY, Constant.ORDER_MKT, 110, 121.0);
		HftOrder order2 = new HftOrder(10, eurUsd2, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		Assert.assertFalse(order1.equals(order2));
	}
	
	@Test
	public void testNotEqualsDifferentAction() {
		IHftSecurity eurUsd1 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(10, eurUsd1, Constant.ACTION_BUY, Constant.ORDER_MKT, 100, 121.0);
		HftOrder order2 = new HftOrder(10, eurUsd2, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 121.0);
		Assert.assertFalse(order1.equals(order2));
	}
	
	@Test
	public void testNotEqualsDifferentPrice() {
		IHftSecurity eurUsd1 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		HftOrder order1 = new HftOrder(10, eurUsd1, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 121.5);
		HftOrder order2 = new HftOrder(10, eurUsd2, Constant.ACTION_SELL, Constant.ORDER_MKT, 100, 121.0);
		Assert.assertFalse(order1.equals(order2));
	}
	


}
