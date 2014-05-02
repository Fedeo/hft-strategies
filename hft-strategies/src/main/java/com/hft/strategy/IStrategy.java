package com.hft.strategy;

import java.util.List;

import com.hft.data.IHftSecurity;

public interface IStrategy {

	public void onStart();

	public void onOrderBookDataChange();

	public void onTopLevelMktDataChange();

	public void onOrderChange();

	public void onClose();

	public String getStrategyName();

	public List<IHftSecurity> getAllSecurities();

	public int hashCode();

}
