package com.hft.order.book;

import static org.junit.Assert.fail;

import org.junit.Test;

public class OrderBookControllerTest {

	BookItem bookAsk1 = new BookItem(10, new Double(12.1));
	BookItem bookAsk2 = new BookItem(5, new Double(12.08));
	BookItem bookBid1 = new BookItem(10, new Double(12.25));
	BookItem bookBid2 = new BookItem(8, new Double(12.27));

	@Test
	public void test() {
	}

}
