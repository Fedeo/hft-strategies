package com.hft.data.feed.ib;

import com.hft.adapter.ib.client.Contract;
import com.hft.data.IHftSecurity;

public class IBAdapter {

	public static Contract convertSecurity(IHftSecurity security) {
		
		Contract c = new Contract();
		c.m_symbol = security.getSymbol();
		c.m_exchange = security.getExchange();
		c.m_currency = security.getCurrency();
		c.m_secType = security.getSecType();

		return c;

	}

}
