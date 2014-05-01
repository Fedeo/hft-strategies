package com.hft.strategy;

public interface IStrategy {
	
	public void onStart();
	public void onOrderBookDataChange();
	public void onTopLevelMktDataChange();
	public void onOrderChange();
	public void onClose();
	

}
