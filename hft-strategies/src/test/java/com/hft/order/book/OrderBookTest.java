package com.hft.order.book;

import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.Test;

public class OrderBookTest {
	
	BookItem bookAsk1 = new  BookItem(10,new Double(12.1));
	BookItem bookAsk2 = new  BookItem(5,new Double(12.08));
	BookItem bookBid1 = new  BookItem(10,new Double(12.25));
	BookItem bookBid2 = new  BookItem(8,new Double(12.27));
	
	ArrayList<BookItem> bid = new ArrayList<BookItem>();
	ArrayList<BookItem> ask = new ArrayList<BookItem>();


	@Test
	public void testInitialize() {		
		assertNotNull(OrderBook.getInstance());
	}
	
	@Test
	public void testUpdate() {
		ask.add(bookAsk1);
		ask.add(bookAsk2);
		bid.add(bookBid1);
		bid.add(bookBid2);
		
		OrderBook.getInstance().update(bid, ask);
		
		assertTrue(OrderBook.getInstance().getBestAsk().equals(bookAsk1));
		assertTrue(OrderBook.getInstance().getBestBid().equals(bookBid1));
		
	}
	
	@Test
	public void testSpread() {
		ask.add(bookAsk1);
		ask.add(bookAsk2);
		bid.add(bookBid1);
		bid.add(bookBid2);
		
		OrderBook.getInstance().update(bid, ask);
		Double price = bookBid1.price-bookAsk1.price;
		assertEquals(OrderBook.getInstance().spreadBidAsk(),price);
		
	}

}
