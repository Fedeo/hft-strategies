package com.hft.data.feed.ib;

import org.apache.log4j.Logger;

import com.hft.adapter.ib.controller.ApiController.IDeepMktDataHandler;
import com.hft.adapter.ib.controller.NewContract;
import com.hft.adapter.ib.controller.Types.DeepSide;
import com.hft.adapter.ib.controller.Types.DeepType;
import com.hft.connector.ib.HFTOrderController;
import com.hft.connector.ib.IBConnection;
import com.hft.data.IHftSecurity;
import com.hft.data.feed.IDataFeed;
import com.hft.order.book.BookItem;
import com.hft.order.book.OrderBookController;

public class IBDataFeed extends IBConnection implements IDataFeed {

	static Logger logger = Logger.getLogger(IBDataFeed.class.getName());

	public IBDataFeed() {
		connect();
	}

	@Override
	public void connect() {
		super.connect();
	}

	@Override
	public void disconnect() {
		super.disconnect();
	}

	@Override
	public void requestDeepMktData(IHftSecurity security) {

		NewContract contract = new NewContract(IBAdapter.convertSecurity(security));
		BookResult resultHandler = new BookResult(security);

		// TODO add a check to send a request only if the security is not yet
		// controlled
		// apiController.reqDeepMktData(contract, 5, resultHandler);
		HFTOrderController.getInstance().controller().reqDeepMktData(contract, 5, resultHandler);
	}

	private class BookResult implements IDeepMktDataHandler {

		private int orderBookKey;

		public BookResult(IHftSecurity security) {
			this.orderBookKey = security.hashCode();
			logger.info("registering request for " + orderBookKey);
		}

		@Override
		public void updateMktDepth(int position, String marketMaker, DeepType operation, DeepSide side, double price,
				int size) {
			logger.debug("Getting feedback for " + orderBookKey + " " + position + " " + price);
			// Update del Book
			BookItem bookItem = new BookItem(size, price);
			if (operation == DeepType.INSERT) {
				if (side == DeepSide.BUY) {
					OrderBookController.addBid(orderBookKey, bookItem, position);
				} else {
					OrderBookController.addAsk(orderBookKey, bookItem, position);
				}
			} else if (operation == DeepType.UPDATE) {
				if (side == DeepSide.BUY) {
					OrderBookController.addBid(orderBookKey, bookItem, position);
				} else {
					OrderBookController.addAsk(orderBookKey, bookItem, position);
				}
			}
		}
	}

	@Override
	public void requestMktData(IHftSecurity security) {
		// TODO Implement
	}

}
