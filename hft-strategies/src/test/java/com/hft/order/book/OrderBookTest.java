package com.hft.order.book;

import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.Test;

import com.hft.order.book.exceptions.BookItemNotAvailableException;
import com.hft.order.book.exceptions.SpreadNotAvailableException;

public class OrderBookTest {
	
	BookItem bookAsk1 = new  BookItem(10,new Double(12.1));
	BookItem bookAsk2 = new  BookItem(5,new Double(12.08));
	BookItem bookBid1 = new  BookItem(10,new Double(12.25));
	BookItem bookBid2 = new  BookItem(8,new Double(12.27));
	
	ArrayList<BookItem> bid = new ArrayList<BookItem>();
	ArrayList<BookItem> ask = new ArrayList<BookItem>();
	
	@Test
	public void testUpdate() throws BookItemNotAvailableException {
		ask.add(bookAsk1);
		ask.add(bookAsk2);
		bid.add(bookBid1);
		bid.add(bookBid2);
		
		OrderBook book=new OrderBook();
		book.update(bid, ask);
		
		assertTrue(book.getBestAsk().equals(bookAsk1));
		assertTrue(book.getBestBid().equals(bookBid1));
		
	}
	
	@Test(expected=BookItemNotAvailableException.class)
	public void testBidNotAvailable() throws BookItemNotAvailableException {
		ask.add(bookAsk1);
		ask.add(bookAsk2);
		
		OrderBook book=new OrderBook();
		book.update(bid, ask);
		
		assertTrue(book.getBestAsk().equals(bookAsk1));
		assertTrue(book.getBestBid().equals(bookBid1));
		
	}
	
	@Test(expected=BookItemNotAvailableException.class)
	public void testAskNotAvailable() throws BookItemNotAvailableException {
		bid.add(bookBid1);
		bid.add(bookBid2);
		
		OrderBook book=new OrderBook();
		book.update(bid, ask);
		
		assertTrue(book.getBestAsk().equals(bookAsk1));
		assertTrue(book.getBestBid().equals(bookBid1));
		
	}
	
	@Test
	public void testSpread() throws SpreadNotAvailableException {
		ask.add(bookAsk1);
		ask.add(bookAsk2);
		bid.add(bookBid1);
		bid.add(bookBid2);
		
		OrderBook book=new OrderBook();
		book.update(bid, ask);
		Double price = bookBid1.price-bookAsk1.price;
		assertEquals(book.spreadBidAsk(),price);
	}
	
	@Test(expected=SpreadNotAvailableException.class)
	public void testSpreadNotAvailable() throws SpreadNotAvailableException {		
		OrderBook book=new OrderBook();
		book.update(bid, ask);
		Double price = bookBid1.price-bookAsk1.price;
		assertEquals(book.spreadBidAsk(),price);
	}

}
