package com.hft.data.feed;

public interface IDataFeed {
	
	public void connect();
	public void disconnect();
	public void requestMktData();

}
