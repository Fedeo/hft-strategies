package com.hft.strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategiesHandler {

	private final static int MAX_STRATEGIES = 10;
	protected HashMap<Integer, IStrategy> strategies = new HashMap<Integer, IStrategy>(MAX_STRATEGIES);

	public void addStrategy(IStrategy strategy) {
		strategies.put(strategy.hashCode(), strategy);
	}

	public IStrategy getStrategy(Integer hashCode) {
		return strategies.get(hashCode);
	}

	public void initialize() {
		// Execute the onStart method for all strategies
		for (Map.Entry<Integer, IStrategy> entry : strategies.entrySet()) {
			entry.getValue().onStart();
		}
	}

}
