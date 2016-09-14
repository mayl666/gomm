package com.gome.upm.mongo.model;

import java.io.Serializable;

/**
 * 自动化购物流程详情
 * 
 * @author zhangzhixiang-ds
 *
 */
public class GtpTaskReportDetaiView implements Serializable {

	private static final long serialVersionUID = 410414343979561562L;

	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 创建时间
	 */
	private String startTime;

	/**
	 * 截止时间
	 */
	private String endTime;

	/**
	 * 流程耗时
	 */
	private int usedTime;

	/**
	 * 状态 0通过测试 1测试失败
	 */
	private int state;

	/**
	 * 日志文件地址
	 */
	private String logAdress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(int usedTime) {
		this.usedTime = usedTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getLogAdress() {
		return logAdress;
	}

	public void setLogAdress(String logAdress) {
		this.logAdress = logAdress;
	}

	@Override
	public String toString() {
		return "GtpTaskReportDetaiView [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", usedTime="
				+ usedTime + ", state=" + state + ", logAdress=" + logAdress + "]";
	}



}
