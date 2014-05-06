package com.hft.strategy;

import com.hft.data.IHftSecurity;
import com.hft.run.HFT;

public abstract class BaseStrategy {
	
	// Sell and Buy methods
	
	protected void buyMarket(IHftSecurity security, int qty) {
		genericBuy(security, "MKT", qty);
	}

	protected void sellMarket(IHftSecurity security, int qty) {
		genericSell(security, "MKT", qty);
	}
	
	protected void buyLimit(IHftSecurity security,Double price, int qty) {
		genericBuy(security, "LMT", qty);
	}

	protected void sellLimit(IHftSecurity security,Double price, int qty) {
		genericSell(security, "LMT", qty);
	}

	// Market Positions Methods
	protected Boolean isInMarket(int strategyKey){
		return null;
	}
	
	protected Boolean isLong(int strategyKey){
		return null;
	}
	
	protected Boolean isShort(int strategyKey){
		return null;
	}
	
	//Accessories Methods
	
	private void genericBuy(IHftSecurity security, String orderType, int qty) {
		genericOrder(security, "BUY", orderType, qty);
	}

	private void genericSell(IHftSecurity security, String orderType, int qty) {
		genericOrder(security, "SELL", orderType, qty);
	}

	private void genericOrder(IHftSecurity security, String action, String orderType,  int qty) {
		HFT.orderConnector().sendOrder(security, action, orderType, new Double(0.0), qty);
	}

}
