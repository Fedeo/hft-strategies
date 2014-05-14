package com.hft.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.hft.data.HftCurrencyPair;
import com.hft.data.IHftSecurity;

public class StrategyHandlerTest {

	@Test
	public void testAddAndGetStrategy() {
		// Strategy
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IStrategy dummyStrategy = new DummyStrategy(eurUsd);

		// Register the Strategy
		StrategiesHandler.addStrategy(dummyStrategy);
		Assert.assertNotNull(StrategiesHandler.getStrategy(dummyStrategy.hashCode()));
		Assert.assertTrue(StrategiesHandler.getStrategy(dummyStrategy.hashCode()).getStrategyName()
				.compareTo("DUMMY_STRATEGY") == 0);
		Assert.assertTrue(StrategiesHandler.getStrategy(dummyStrategy.hashCode()).getAllSecurities().get(0)
				.equals(eurUsd));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void tesInitialize() {
		// Strategy
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IStrategy dummyStrategy = new DummyStrategy(eurUsd);

		// Register the Strategy
		StrategiesHandler.addStrategy(dummyStrategy);

		// Initialize (should throw exception)
		StrategiesHandler.initialize();

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNotifyStrategiesForBookChange() {
		// Strategy
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IStrategy dummyStrategy = new DummyStrategy(eurUsd);

		// Register the Strategy
		StrategiesHandler.addStrategy(dummyStrategy);

		// Initialize (should throw exception)
		StrategiesHandler.notifyStrategiesForBookChange(eurUsd.hashCode());

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNotifyStrategiesForTopLevelMktDataChange() {
		// Strategy
		IHftSecurity eurUsd = new HftCurrencyPair("EUR", "IDEALPRO", "USD", "CASH",new Double(0.0001));
		IStrategy dummyStrategy = new DummyStrategy(eurUsd);

		// Register the Strategy
		StrategiesHandler.addStrategy(dummyStrategy);

		// Initialize (should throw exception)
		StrategiesHandler.notifyStrategiesForTopLevelMktDataChange(eurUsd.hashCode());

	}
	
	private class DummyStrategy extends BaseStrategy implements IStrategy {

		protected IHftSecurity security;

		public DummyStrategy(IHftSecurity security) {
			super();
			this.security = security;
		}

		@Override
		public void onStart() {
			List<String> dummyList = new ArrayList<String>();
			dummyList.get(1);
		}

		@Override
		public void onOrderBookDataChange() {
			List<String> dummyList = new ArrayList<String>();
			dummyList.get(1);
		}

		@Override
		public void onTopLevelMktDataChange() {
			List<String> dummyList = new ArrayList<String>();
			dummyList.get(1);
		}

		@Override
		public void onOrderChange(int OrderId) {
			List<String> dummyList = new ArrayList<String>();
			dummyList.get(1);
		}

		@Override
		public void onClose() {
		}

		@Override
		public IStrategy getStrategy() {
			return null;
		}

		@Override
		public String getStrategyName() {
			return "DUMMY_STRATEGY";
		}

		@Override
		public List<IHftSecurity> getAllSecurities() {
			return new ArrayList<IHftSecurity>(Arrays.asList(security));
		}

		protected void dummyMthods() {
		}

	}

}
