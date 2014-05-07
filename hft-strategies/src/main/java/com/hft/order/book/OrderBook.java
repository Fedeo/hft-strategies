package com.hft.order.book;

import java.util.ArrayList;

import com.hft.order.book.exceptions.BookItemNotAvailableException;
import com.hft.order.book.exceptions.SpreadNotAvailableException;

public class OrderBook {

	private ArrayList<BookItem> bid = new ArrayList<BookItem>();
	private ArrayList<BookItem> ask = new ArrayList<BookItem>();

	public OrderBook() {
	}

	public void update(ArrayList<BookItem> bid, ArrayList<BookItem> ask) {
		this.bid = bid;
		this.ask = ask;
	}

	public void addAsk(BookItem bookItem, int position) {
		ask.add(position, bookItem);
	}

	public void addBid(BookItem bookItem, int position) {
		bid.add(position, bookItem);
	}

	public Double spreadBidAsk() throws SpreadNotAvailableException {
		try {
			return bid.get(0).price - ask.get(0).price;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SpreadNotAvailableException();
		}
	}

	public BookItem getBestBid() throws BookItemNotAvailableException {
		try {
			return bid.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookItemNotAvailableException("Best Bid not available");
		}
	}

	public BookItem getBestAsk() throws BookItemNotAvailableException {
		try {
			return ask.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookItemNotAvailableException("Best Ask not available");
		}
	}

	@Override
	public String toString() {
		return "OrderBook [bid=" + bid.get(0).price + ", ask=" + ask.get(0).price + "]";
	}

}
