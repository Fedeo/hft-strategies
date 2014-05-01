package com.hft.data;

public class HftCurrencyPair implements IHftSecurity {
	
	protected String symbol;
	protected String exchange;
	protected String currency;
	protected String secType;
	
	public HftCurrencyPair(String symbol, String exchange, String currency, String secType) {
		super();
		this.symbol = symbol;
		this.exchange = exchange;
		this.currency = currency;
		this.secType = secType;
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getExchange() {
		return exchange;
	}

	@Override
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	@Override
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String getSecType() {
		return secType;
	}

	@Override
	public void setSecType(String secType) {
		this.secType = secType;
	}


	
	
	

}
