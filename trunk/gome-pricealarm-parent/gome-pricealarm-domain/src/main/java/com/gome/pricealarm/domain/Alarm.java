package com.gome.pricealarm.domain;

import java.util.Date;

/**
 * 报警实体类
 * @author caowei-ds1
 * 2016年06月16日
 *
 */
public class Alarm {

	//报警记录id
	private Long id;

	//价格日志ID
	private String logId;
	
	//节点
	private String node;
	
	//内容
	private String content;
	
	//报警时间
	private Date sendTime;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
