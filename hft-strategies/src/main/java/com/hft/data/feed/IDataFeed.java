package com.hft.data.feed;

import com.hft.data.IHftSecurity;

public interface IDataFeed {

	public void connect();

	public void disconnect();

	public void requestMktData(IHftSecurity security);

}
