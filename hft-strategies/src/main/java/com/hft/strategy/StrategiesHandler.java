package com.hft.strategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;
import org.apache.log4j.Logger;

import com.hft.data.IHftSecurity;

public class StrategiesHandler {

	private final static int MAX_STRATEGIES = 10;
	static protected HashMap<Integer, IStrategy> strategies = new HashMap<Integer, IStrategy>(MAX_STRATEGIES);
	static protected MultiHashMap mapSecuritiesStrategy = new MultiHashMap();
	
	static Logger logger = Logger.getLogger(StrategiesHandler.class.getName());

	static public void addStrategy(IStrategy strategy) {
		strategies.put(strategy.hashCode(), strategy);
		registerSecuritiesForDataFeed(strategy);
	}

	static public IStrategy getStrategy(Integer hashCode) {
		return strategies.get(hashCode);
	}

	static public void initialize() {
		for (Map.Entry<Integer, IStrategy> entry : strategies.entrySet()) {
			entry.getValue().onStart();
		}
	}

	@SuppressWarnings("unchecked")
	static public void notifyStrategiesForBookChange(int securityKey) {
		List<IStrategy> strategiesForCurrency = (List<IStrategy>) mapSecuritiesStrategy.get(securityKey);
		Iterator<IStrategy> i = strategiesForCurrency.iterator();
		while (i.hasNext()) {
			IStrategy strategy = (IStrategy) i.next();
			strategy.onOrderBookDataChange();
		}
	}

	@SuppressWarnings("unchecked")
	static public void notifyStrategiesForTopLevelMktDataChange(int securityKey) {
		List<IStrategy> strategiesForCurrency = (List<IStrategy>) mapSecuritiesStrategy.get(securityKey);
		Iterator<IStrategy> i = strategiesForCurrency.iterator();
		while (i.hasNext()) {
			IStrategy strategy = (IStrategy) i.next();
			strategy.onTopLevelMktDataChange();
		}
	}
	
	static public void cleanStrategies(){
		strategies.clear();
		mapSecuritiesStrategy.clear();
	}

	static protected void registerSecuritiesForDataFeed(IStrategy strategy) {
		for (IHftSecurity security : strategy.getAllSecurities()) {
			mapSecuritiesStrategy.put(security.hashCode(), strategy);
			logger.debug("Registering " + security.getSymbol() + security.hashCode() + " for "
					+ strategy.getStrategyName());
		}

	}

}
