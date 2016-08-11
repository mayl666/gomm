package com.gome.monitoringplatform.model.bo;

import java.util.Date;

import com.gome.framework.base.BaseBO;

public class MoOrderStateBO extends BaseBO{
	private static final long serialVersionUID = -5920475856092853671L;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private Integer minute;
	private String state;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
