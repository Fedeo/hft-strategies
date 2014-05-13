package com.hft.connector.ib;


public abstract class IBConnection {

	// private final Logger m_inLogger = new Logger();
	// private final Logger m_outLogger = new Logger();
	// protected final ApiController apiController = new ApiController(this,
	// m_inLogger, m_outLogger);

	public void connect() {
		HFTOrderController.getInstance().controller().connect("127.0.0.1", 4001, 0); // 7496 - 4001
		// apiController.connect("127.0.0.1", 4001, 0); // 7496 - 4001
	}
	
	public void disconnect() {
		HFTOrderController.getInstance().controller().disconnect();
		// apiController.connect("127.0.0.1", 4001, 0); // 7496 - 4001
	}

}
