package com.gome.upm.domain;

import java.util.Date;

public class MoMonitorDragon {

	private Long orderNum;
	
	private Long currentStatus;
	
	private String masLoc;
	
	private Date orderDate;

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Long currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getMasLoc() {
		return masLoc;
	}

	public void setMasLoc(String masLoc) {
		this.masLoc = masLoc;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	
}
