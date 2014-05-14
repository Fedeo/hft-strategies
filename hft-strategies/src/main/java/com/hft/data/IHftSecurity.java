package com.hft.data;

public interface IHftSecurity {

	public abstract void setSecType(String secType);

	public abstract String getSecType();

	public abstract void setCurrency(String currency);

	public abstract String getCurrency();

	public abstract void setExchange(String exchange);

	public abstract String getExchange();

	public abstract void setSymbol(String symbol);

	public abstract String getSymbol();
	
	public abstract Double getTick();
	
	public abstract void setTick(Double tick);
	
	public abstract int hashCode();

	public abstract boolean equals(Object obj);

}
