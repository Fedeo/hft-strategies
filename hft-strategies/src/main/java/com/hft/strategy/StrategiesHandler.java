package com.hft.strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategiesHandler {
	
	//TODO Add Multimap to store the Strategies related that use a specific feed
	//https://commons.apache.org/proper/commons-collections/javadocs/api-3.2.1/org/apache/commons/collections/MultiMap.html

	private final static int MAX_STRATEGIES = 10;
	static protected HashMap<Integer, IStrategy> strategies = new HashMap<Integer, IStrategy>(MAX_STRATEGIES);

	static public void addStrategy(IStrategy strategy) {
		//TODO: implement registration table based on securities 
		strategies.put(strategy.hashCode(), strategy);
	}

	static public IStrategy getStrategy(Integer hashCode) {
		return strategies.get(hashCode);
	}

	static public void initialize() {
		// Execute the onStart method for all strategies
		for (Map.Entry<Integer, IStrategy> entry : strategies.entrySet()) {
			entry.getValue().onStart();
		}
	}
	
	static public void notifyStrategiesForBookChange(){
		//TODO: implement notify only for strategies with current security
		// Execute the onOrderBookDataChange method for all strategies
		for (Map.Entry<Integer, IStrategy> entry : strategies.entrySet()) {
			entry.getValue().onOrderBookDataChange();
		}
	}
	
	static public void notifyStrategiesForTopLevelMktDataChange(){
		//TODO: implement notify only for strategies with current security
		// Execute the onTopLevelMktDataChange method for all strategies
		for (Map.Entry<Integer, IStrategy> entry : strategies.entrySet()) {
			entry.getValue().onTopLevelMktDataChange();
		}
	}

}
