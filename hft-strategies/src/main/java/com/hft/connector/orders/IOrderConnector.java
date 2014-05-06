package com.hft.connector.orders;

import com.hft.data.HftOrder;

public interface IOrderConnector {

	void sendOrder(HftOrder newOrder);

}
