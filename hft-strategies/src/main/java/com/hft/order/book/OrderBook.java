package com.hft.order.book;

import java.util.ArrayList;

public class OrderBook {
	
	private ArrayList<BookItem> bid  = new ArrayList<BookItem>();
	private ArrayList<BookItem> ask  = new ArrayList<BookItem>();

	private static OrderBook uniqInstance;

	private OrderBook() {
	}

	public static synchronized OrderBook getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new OrderBook();
		}
		return uniqInstance;
	}
	
	public void update(ArrayList<BookItem> bid,ArrayList<BookItem> ask){
		this.bid=bid;
		this.ask=ask;
		
	}
	
	public void addAsk(BookItem bookItem,int position){
		ask.add(position,bookItem);
	}
	
	public void addBid(BookItem bookItem,int position){
		bid.add(position,bookItem);
	}
	
	public Double spreadBidAsk(){
		return bid.get(0).price - ask.get(0).price;
	}
	
	public BookItem getBestBid(){
		return bid.get(0);
	}
	
	public BookItem getBestAsk(){
		return ask.get(0);
	}

	@Override
	public String toString() {
		return "OrderBook [bid=" + bid.get(0).price + ", ask=" + ask.get(0).price + "]";
	}

	
	
}
