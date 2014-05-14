package com.hft.data;

public class HftCurrencyPair implements IHftSecurity {
	
	protected String symbol;
	protected String exchange;
	protected String currency;
	protected String secType;
	protected Double tick;
	
	public HftCurrencyPair(String symbol, String exchange, String currency, String secType,Double tick) {
		super();
		this.symbol = symbol;
		this.exchange = exchange;
		this.currency = currency;
		this.secType = secType;
		this.tick = tick;
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
	
	@Override
	public Double getTick() {
		return tick;
	}
	
	@Override
	public void setTick(Double tick) {
		this.tick = tick;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
		result = prime * result + ((secType == null) ? 0 : secType.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HftCurrencyPair other = (HftCurrencyPair) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		if (secType == null) {
			if (other.secType != null)
				return false;
		} else if (!secType.equals(other.secType))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}


	
	
	

}
