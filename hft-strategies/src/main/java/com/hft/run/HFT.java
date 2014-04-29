package com.hft.run;

import java.util.ArrayList;
import java.util.HashMap;

import com.hft.adapter.ib.client.Contract;
import com.hft.adapter.ib.client.Order;
import com.hft.adapter.ib.controller.ApiConnection.ILogger;
import com.hft.adapter.ib.controller.ApiController;
import com.hft.adapter.ib.controller.ApiController.IConnectionHandler;
import com.hft.adapter.ib.controller.ApiController.IDeepMktDataHandler;
import com.hft.adapter.ib.controller.ApiController.ILiveOrderHandler;
import com.hft.adapter.ib.controller.ApiController.IOrderHandler;
import com.hft.adapter.ib.controller.NewContract;
import com.hft.adapter.ib.controller.NewOrder;
import com.hft.adapter.ib.controller.NewOrderState;
import com.hft.adapter.ib.controller.OrderStatus;
import com.hft.adapter.ib.controller.Types.DeepSide;
import com.hft.adapter.ib.controller.Types.DeepType;
import com.hft.order.book.BookItem;
import com.hft.order.book.OrderBook;

public class HFT implements IConnectionHandler {
	static HFT INSTANCE = new HFT();
	private static int startingId = 200;
	private final Logger m_inLogger = new Logger();
	private final Logger m_outLogger = new Logger();
	private final ApiController apiController = new ApiController(this, m_inLogger, m_outLogger);
	private final static double BOOK_THRESHOLD = 0.25;
	
	public ApiController controller() 		{ return apiController; }
	
	private static int getId() {return startingId++;}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		INSTANCE.run();
	}

	public void run() {
		apiController.connect("127.0.0.1", 7496, 0); // 7496 - 4001

		// EURUSD
		Contract c = new Contract();
		c.m_symbol = "EUR";
		c.m_exchange = "IDEALPRO";
		c.m_currency = "USD";
		c.m_secType = "CASH";

		// LUX.MI

		NewContract contract = new NewContract(c);
		BookResult resultHandler = new BookResult();

		apiController.reqDeepMktData(contract, 5, resultHandler);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		System.out.println("github test changed");
		
/*		// Create an Order
		Order order = new Order();
		order.m_orderId = getId();
		order.m_account = "DU153566";
		order.m_action = "BUY";
		order.m_orderType = "MKT";
		order.m_totalQuantity = order.m_orderId;
		order.m_tif = "DAY";
		order.m_referencePriceType = 0;

		// Traccio ordini real-time
		OrdersModel liveModel = new OrdersModel();
		apiController.reqLiveOrders(liveModel);

		// Submit di un ordine
		apiController.placeOrModifyOrder(contract, new NewOrder(order), new IOrderHandler() {
			@Override
			public void orderState(NewOrderState orderState) {
				apiController.removeOrderHandler(this);
				System.out.println("Inside orderState");
			}

			@Override
			public void handle(int errorCode, final String errorMsg) {
				System.out.println("Inside handle");
			}
		});*/

/*		System.out.println("submitted order");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

*/		/*
		 * try { Object lock = new Object(); synchronized (lock) { while (true)
		 * { lock.wait(); } } } catch (InterruptedException ex) { }
		 */

		System.out.println("going to disconect");

		apiController.disconnect();
		System.out.println(OrderBook.getInstance().toString());
	}

	@Override
	public void connected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void accountList(ArrayList<String> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void message(int id, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show(String string) {
		// TODO Auto-generated method stub

	}
	
	public void buy(){
		
		Contract c = new Contract();
		c.m_symbol = "EUR";
		c.m_exchange = "IDEALPRO";
		c.m_currency = "USD";
		c.m_secType = "CASH";
		NewContract contract = new NewContract(c);

		// Create an Order
		Order order = new Order();
		order.m_orderId = getId();
		order.m_account = "DU153566";
		order.m_action = "BUY";
		order.m_orderType = "MKT";
		order.m_totalQuantity = order.m_orderId;
		order.m_tif = "DAY";
		order.m_referencePriceType = 0;
		
		// Submit di un ordine
		apiController.placeOrModifyOrder(contract, new NewOrder(order), new IOrderHandler() {
			@Override
			public void orderState(NewOrderState orderState) {
				apiController.removeOrderHandler(this);
				System.out.println("Inside orderState");
			}

			@Override
			public void handle(int errorCode, final String errorMsg) {
				System.out.println("Inside handle");
			}
		});
		
	}

	private static class Logger implements ILogger {

		@Override
		public void log(String valueOf) {
			// TODO Auto-generated method stub
		}
	}

	private static class BookResult implements IDeepMktDataHandler {

		@Override
		public void updateMktDepth(int position, String marketMaker, DeepType operation, DeepSide side, double price,
				int size) {
			System.out.println(position + " " + marketMaker + " " + operation + " " + side + " " + price + " " + size);

			// Update del Book
			BookItem bookItem = new BookItem(size, price);
			if (operation == DeepType.INSERT) {
				if (side == DeepSide.BUY) {
					OrderBook.getInstance().addAsk(bookItem, position);
				} else {
					OrderBook.getInstance().addBid(bookItem, position);
				}
			} else if (operation == DeepType.UPDATE) {
				if (side == DeepSide.BUY) {
					OrderBook.getInstance().addAsk(bookItem, position);
				} else {
					OrderBook.getInstance().addBid(bookItem, position);
				}
			}

			// Analisi Strategia Book

			// Se e' cambiato il primo livello
			if (position == 0) {
				if (OrderBook.getInstance().spreadBidAsk() > BOOK_THRESHOLD) {
					INSTANCE.buy();
				}
			}

		}
	}

	static class OrdersModel implements ILiveOrderHandler {
		private HashMap<Long, OrderRow> m_map = new HashMap<Long, OrderRow>();
		private ArrayList<OrderRow> m_orders = new ArrayList<OrderRow>();

		public void clear() {
			m_orders.clear();
			m_map.clear();
		}

		public OrderRow get(int i) {
			return m_orders.get(i);
		}

		@Override
		public void openOrder(NewContract contract, NewOrder order, NewOrderState orderState) {

			System.out.println("submitted openOrder" + order.orderId());

			OrderRow full = m_map.get(order.permId());
			if (full != null) {
				full.m_order = order;
				full.m_state = orderState;
			} else if (shouldAdd(contract, order, orderState)) {
				full = new OrderRow(contract, order, orderState);
				add(full);
				m_map.put(order.permId(), full);
			}
		}

		protected boolean shouldAdd(NewContract contract, NewOrder order, NewOrderState orderState) {
			return true;
		}

		protected void add(OrderRow full) {
			m_orders.add(full);
		}

		@Override
		public void openOrderEnd() {
		}

		@Override
		public void orderStatus(int orderId, OrderStatus status, int filled, int remaining, double avgFillPrice,
				long permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {

			System.out.println("submitted openOrder" + orderId + " " + status.toString());

			OrderRow full = m_map.get(permId);
			if (full != null) {
				full.m_state.status(status);
			}
		}

		@Override
		public void handle(int orderId, int errorCode, String errorMsg) {
		}

		static class OrderRow {
			NewContract m_contract;
			NewOrder m_order;
			NewOrderState m_state;

			OrderRow(NewContract contract, NewOrder order, NewOrderState state) {
				m_contract = contract;
				m_order = order;
				m_state = state;
			}
		}
	}

}
