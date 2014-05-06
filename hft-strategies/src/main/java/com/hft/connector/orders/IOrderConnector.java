package com.hft.connector.orders;

import com.hft.data.IHftSecurity;

public interface IOrderConnector {

	void sendOrder(IHftSecurity security, String action, String orderType, Double price, int qty);
	
	
}
