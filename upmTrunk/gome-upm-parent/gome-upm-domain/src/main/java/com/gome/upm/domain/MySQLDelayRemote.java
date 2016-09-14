package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mysql主从延迟(oracle库)
 * @author caowei-ds1
 *
 */
public class MySQLDelayRemote {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 从库IP */
	private String slaveAddr;

	/** 从库端口 */
	private Integer slavePort;
	
	/** 主库IP */
	private String masterAddr;
	
	/** 主库端口 */
	private Integer masterPort;
	
	/** 延迟，单位秒 */
	private Integer delay;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 创建时间字符串 */
	private String createTimeStr;
	
	/** 更新时间 */
	private Date updateTime;
	
	/** 更新时间字符串 */
	private String updateTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	/** 从库IO进程 */
	private String ioRunning;
	
	/** 从库SQL进程 */
	private String sqlRunning;

	public String getSlaveAddr() {
		return slaveAddr;
	}

	public void setSlaveAddr(String slaveAddr) {
		this.slaveAddr = slaveAddr;
	}

	public Integer getSlavePort() {
		return slavePort;
	}

	public void setSlavePort(Integer slavePort) {
		this.slavePort = slavePort;
	}

	public String getMasterAddr() {
		return masterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		this.masterAddr = masterAddr;
	}

	public Integer getMasterPort() {
		return masterPort;
	}

	public void setMasterPort(Integer masterPort) {
		this.masterPort = masterPort;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			this.createTimeStr = df.format(createTime);
		}
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		if(updateTime != null){
			this.updateTimeStr = df.format(updateTime);
		}
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
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

	public String getIoRunning() {
		return ioRunning;
	}

	public void setIoRunning(String ioRunning) {
		this.ioRunning = ioRunning;
	}

	public String getSqlRunning() {
		return sqlRunning;
	}

	public void setSqlRunning(String sqlRunning) {
		this.sqlRunning = sqlRunning;
	}
	
	
}
