package com.hft.strategy.orderbook;

import java.util.List;

import com.hft.data.IHftSecurity;
import com.hft.strategy.BaseStrategy;

public abstract class OrderBookStrategy extends BaseStrategy {

	public void onStart() {
		// TODO Auto-generated method stub
	}

	public void onOrderBookDataChange() {
		// TODO Auto-generated method stub
	}

	public void onTopLevelMktDataChange() {
		// TODO Auto-generated method stub
	}

	public void onOrderChange() {
		// TODO Auto-generated method stub
	}

	public void onClose() {
		// TODO Auto-generated method stub
	}

	public abstract List<IHftSecurity> getAllSecurities();

	public abstract String getStrategyName();

	@Override
	public int hashCode() {
		final int strategyPrimeCode = 31;
		int result = 1;
		result = strategyPrimeCode * result + ((getStrategyName() == null) ? 0 : getStrategyName().hashCode());
		for (IHftSecurity currentSecurity : getAllSecurities()) {
			result = strategyPrimeCode * result + ((currentSecurity == null) ? 0 : currentSecurity.hashCode());
		}

		return result;
	}
}
