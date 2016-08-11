package com.gome.monitoringplatform.model.bo;

import java.util.Date;
import com.gome.framework.base.BaseBO;

public class MoOrderNotRechargeBO extends BaseBO {
	private static final long serialVersionUID = 7319054013976673047L;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private Integer minute;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
}
