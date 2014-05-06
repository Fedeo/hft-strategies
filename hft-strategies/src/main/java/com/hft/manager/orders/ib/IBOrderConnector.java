package com.hft.manager.orders.ib;

import java.util.ArrayList;
import java.util.HashMap;

import com.hft.adapter.ib.client.Contract;
import com.hft.adapter.ib.client.Order;
import com.hft.adapter.ib.controller.ApiController.ILiveOrderHandler;
import com.hft.adapter.ib.controller.ApiController.IOrderHandler;
import com.hft.adapter.ib.controller.NewContract;
import com.hft.adapter.ib.controller.NewOrder;
import com.hft.adapter.ib.controller.NewOrderState;
import com.hft.adapter.ib.controller.OrderStatus;
import com.hft.connector.ib.IBConnection;
import com.hft.connector.orders.IOrderConnector;
import com.hft.data.IHftSecurity;
import com.hft.manager.orders.Sequence;

public class IBOrderConnector extends IBConnection implements IOrderConnector {

	protected String accountId;

	public IBOrderConnector(String accountId) {
		super.connect();
		this.accountId = accountId;
		apiController.reqLiveOrders(new OrdersModel());
	}

	@Override
	public void sendOrder(IHftSecurity security, String action, String orderType, Double price,int qty) {

		Contract c = new Contract();
		c.m_symbol = security.getSymbol();
		c.m_exchange = security.getExchange();
		c.m_currency = security.getCurrency();
		c.m_secType = security.getSecType();

		// Create an Order
		Order order = new Order();
		order.m_orderId = Sequence.getInstance().getAndIncreaseOrderId();
		order.m_account = accountId;
		order.m_action = action;
		order.m_orderType = orderType;
		order.m_totalQuantity = qty;
		if (orderType.equals("LMT")) order.m_lmtPrice = price;
		order.m_tif = "DAY";
		order.m_referencePriceType = 0;

		placeOrder(new NewContract(c), new NewOrder(order));
	}

	private void placeOrder(NewContract contract, NewOrder order) {

		apiController.placeOrModifyOrder(contract, order, new IOrderHandler() {
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

			System.out.println("openOrder " + order.orderId() + " " + orderState.status());

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

			System.out.println("getting status " + orderId + " " + status.toString() + " " + filled);

			// if (status==OrderStatus.Filled) {INSTANCE.sell(filled);}

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
