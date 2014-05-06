package com.hft.data;

import org.junit.Assert;
import org.junit.Test;

public class HftCurrencyPairTest {

	@Test
	public void testNotEquals() {
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		IHftSecurity usdJpy = new HftCurrencyPair("USD", "IDEALPRO", "JPY", "CASH");
		Assert.assertFalse(eurUsd.equals(usdJpy));
	}

	@Test
	public void testEquals() {
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		IHftSecurity eurUsd2 = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH");
		Assert.assertTrue(eurUsd.equals(eurUsd2));
	}

}
