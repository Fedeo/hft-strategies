package com.hft.manager.orders;

import java.util.concurrent.atomic.AtomicLong;

public class Sequence {

	private static Sequence instance = null;
	private static final int MIN = 0;
	private static final int MAX = 100000;

	protected static AtomicLong ordercounter = new AtomicLong(MIN + (int) (Math.random() * ((MAX - MIN) + 1)));

	protected Sequence() {
		//System.out.println(ordercounter);
	}

	public static Sequence getInstance() {
		if (instance == null) {
			instance = new Sequence();
		}
		return instance;
	}

	public int getAndIncreaseOrderId() {
		ordercounter.getAndIncrement();
		return ordercounter.intValue();
	}

}
