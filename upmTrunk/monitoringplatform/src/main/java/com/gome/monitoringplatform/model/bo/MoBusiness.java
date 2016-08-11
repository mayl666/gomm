package com.gome.monitoringplatform.model.bo;

import java.io.Serializable;
import java.util.Date;

public class MoBusiness implements Serializable{
	private static final long serialVersionUID = 5373021679091005191L;
	private String name;
	private Integer amount;
	private Date startTime;
	private Date endTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
