package com.hft.connector.ib;

import java.util.ArrayList;

import com.hft.adapter.ib.controller.ApiConnection.ILogger;
import com.hft.adapter.ib.controller.ApiController;
import com.hft.adapter.ib.controller.ApiController.IConnectionHandler;

public abstract class IBConnection implements IConnectionHandler {

	private final Logger m_inLogger = new Logger();
	private final Logger m_outLogger = new Logger();
	protected final ApiController apiController = new ApiController(this, m_inLogger, m_outLogger);
	
	public void connect() {
		apiController.connect("127.0.0.1", 4001, 0); // 7496 - 4001
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

	private static class Logger implements ILogger {
		@Override
		public void log(String valueOf) {
			// TODO Auto-generated method stub
		}
	}

}
