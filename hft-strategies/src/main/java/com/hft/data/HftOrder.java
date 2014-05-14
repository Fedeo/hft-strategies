package com.hft.data;

import java.util.Date;

import com.hft.run.Constant;

public class HftOrder {
	public int orderId;
	public IHftSecurity security;
	public String action;
	public String orderType;
	public int qty;
	public Double lmtPrice;
	public String status;
	public String description;
	public Date timeStamp;

	public HftOrder(int orderId, IHftSecurity security, String action, String orderType, int qty, Double lmtPrice,
			String description) {
		super();
		this.orderId = orderId;
		this.security = security;
		this.action = action;
		this.orderType = orderType;
		this.qty = qty;
		this.lmtPrice = lmtPrice;
		this.status = Constant.ORDER_NEW;
		this.description = description;
		timeStamp = new Date(System.currentTimeMillis());
	}

	public void acknowleged() {
		setStatus(Constant.ORDER_ACKNOWLEDGED);
	}

	public void filled() {
		setStatus(Constant.ORDER_FILLED);
	}

	public Boolean isAcknowleged() {
		return status.compareToIgnoreCase(Constant.ORDER_ACKNOWLEDGED) == 0 ? new Boolean(true) : new Boolean(false);
	}

	public Boolean isFilled() {
		return status.compareToIgnoreCase(Constant.ORDER_FILLED) == 0 ? new Boolean(true) : new Boolean(false);
	}

	public Boolean isNew() {
		return status.compareToIgnoreCase(Constant.ORDER_NEW) == 0 ? new Boolean(true) : new Boolean(false);
	}

	private void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((lmtPrice == null) ? 0 : lmtPrice.hashCode());
		result = prime * result + orderId;
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + qty;
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HftOrder other = (HftOrder) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (lmtPrice == null) {
			if (other.lmtPrice != null)
				return false;
		} else if (!lmtPrice.equals(other.lmtPrice))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (qty != other.qty)
			return false;
		if (security == null) {
			if (other.security != null)
				return false;
		} else if (!security.equals(other.security))
			return false;
		return true;
	}

}
