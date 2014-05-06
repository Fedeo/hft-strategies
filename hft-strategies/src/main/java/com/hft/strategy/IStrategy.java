package com.hft.strategy;

import java.util.List;

import com.hft.data.IHftSecurity;

public interface IStrategy {

	public void onStart();

	public void onOrderBookDataChange();

	public void onTopLevelMktDataChange();

	public void onOrderChange(int OrderId);

	public void onClose();

	public IStrategy getStrategy();
	
	public String getStrategyName();

	public List<IHftSecurity> getAllSecurities();

	public int hashCode();

}
