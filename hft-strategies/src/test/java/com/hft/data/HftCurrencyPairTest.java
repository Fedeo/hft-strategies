package com.hft.data;

import org.junit.Assert;
import org.junit.Test;

public class HftCurrencyPairTest {

	@Test
	public void testNotEquals() {
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity usdJpy = new HftCurrencyPair("USD", "IDEALPRO", "JPY", "CASH",new Double(0.01));
		Assert.assertFalse(eurUsd.equals(usdJpy));
	}

	@Test
	public void testEquals() {
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		Assert.assertTrue(eurUsd.equals(eurUsd2));
	}

}
