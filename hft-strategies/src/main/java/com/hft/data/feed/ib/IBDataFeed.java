package com.hft.data.feed.ib;

import java.util.ArrayList;

import com.hft.adapter.ib.controller.ApiController.IDeepMktDataHandler;
import com.hft.adapter.ib.controller.NewContract;
import com.hft.adapter.ib.controller.Types.DeepSide;
import com.hft.adapter.ib.controller.Types.DeepType;
import com.hft.connector.ib.IBConnection;
import com.hft.data.IHftSecurity;
import com.hft.data.feed.IDataFeed;
import com.hft.order.book.BookItem;
import com.hft.order.book.OrderBook;

public class IBDataFeed extends IBConnection implements IDataFeed {

	public IBDataFeed() {
		super.connect();
	}

	@Override
	public void connected() {
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

	@Override
	public void connect() {
		super.connect();
	}

	@Override
	public void disconnect() {
	}

	@Override
	public void requestMktData(IHftSecurity security) {

		NewContract contract = new NewContract(IBAdapter.convertSecurity(security));
		BookResult resultHandler = new BookResult();

		apiController.reqDeepMktData(contract, 5, resultHandler);
	}

	private static class BookResult implements IDeepMktDataHandler {

		static int index = 0;

		@Override
		public void updateMktDepth(int position, String marketMaker, DeepType operation, DeepSide side, double price,
				int size) {
			// System.out.println(position + " " + marketMaker + " " + operation
			// + " " + side + " " + price + " " + size);
			System.out.println(index++);
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
		}
	}

}
