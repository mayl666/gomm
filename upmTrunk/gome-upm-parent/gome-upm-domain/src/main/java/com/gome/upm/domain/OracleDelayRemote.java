package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * oracle主从延迟（oracle库）
 * @author caowei-ds1
 *
 */
public class OracleDelayRemote {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 数据库ID */
	private Long dbID;
	
	/** 数据库名 */
	private String dbName;
	
	/** 从库IP */
	private String slaveAddr;

	/** 从表空间 */
	private String slaveTbs;
	
	/** 主库IP */
	private String masterAddr;
	
	/** 主库表空间 */
	private String masterTbs;
	
	/** 延迟时间（单位分钟）*/
	private Integer delay;
	
	/** 延迟时间字符串*/
	private String delayStr;
	
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

	public Long getDbID() {
		return dbID;
	}

	public void setDbID(Long dbID) {
		this.dbID = dbID;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getSlaveAddr() {
		return slaveAddr;
	}

	public void setSlaveAddr(String slaveAddr) {
		this.slaveAddr = slaveAddr;
	}

	public String getSlaveTbs() {
		return slaveTbs;
	}

	public void setSlaveTbs(String slaveTbs) {
		this.slaveTbs = slaveTbs;
	}

	public String getMasterAddr() {
		return masterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		this.masterAddr = masterAddr;
	}

	public String getMasterTbs() {
		return masterTbs;
	}

	public void setMasterTbs(String masterTbs) {
		this.masterTbs = masterTbs;
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
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
		if(StringUtils.isNotEmpty(updateTimeStr)){
			try {
				this.updateTime = df.parse(updateTimeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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

	public String getDelayStr() {
		return delayStr;
	}

	public void setDelayStr(String delayStr) {
		this.delayStr = delayStr;
		if(StringUtils.isNotEmpty(delayStr)){
			this.delay = Integer.parseInt(delayStr);
		}
	}
	
}
