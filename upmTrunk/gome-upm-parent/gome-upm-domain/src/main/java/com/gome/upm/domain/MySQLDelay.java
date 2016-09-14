package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mysql主从延迟
 * @author caowei-ds1
 *
 */
public class MySQLDelay {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 主键ID */
	private Long id;
	
	/** 从库IP */
	private String slaveAddr;

	/** 从库端口 */
	private String slavePort;
	
	/** 主库IP */
	private String masterAddr;
	
	/** 主库端口 */
	private String masterPort;
	
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
	
	/** 报警时间 */
	private Date alarmTime;
	
	/** 报警时间字符串 */
	private String alarmTimeStr;
	
	/** 报警原因，1：长时间未更新；2：进程终止；3：主从延迟；4：等级提升 */
	private Integer alarmReason;
	
	/** 报警原因字符串 */
	private String alarmReasonStr;
	
	/** 报警级别（0：正常；1：一级；2：二级；3：三级） */
	private Integer alarmLevel;
	
	/** 报警级别字符串 */
	private String alarmLevelStr;
	
	/** 数目 */
	private Integer num;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSlaveAddr() {
		return slaveAddr;
	}

	public void setSlaveAddr(String slaveAddr) {
		this.slaveAddr = slaveAddr;
	}

	public String getSlavePort() {
		return slavePort;
	}

	public void setSlavePort(String slavePort) {
		this.slavePort = slavePort;
	}

	public String getMasterAddr() {
		return masterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		this.masterAddr = masterAddr;
	}

	public String getMasterPort() {
		return masterPort;
	}

	public void setMasterPort(String masterPort) {
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

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
		if(alarmTime != null){
			this.alarmTimeStr = df.format(alarmTime);
		}
	}

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}

	public Integer getAlarmReason() {
		return alarmReason;
	}

	public void setAlarmReason(Integer alarmReason) {
		this.alarmReason = alarmReason;
		if(alarmReason == 1){
			this.alarmReasonStr = "长时间未更新";
		}else if(alarmReason == 2){
			this.alarmReasonStr = "进程终止";
		}else if(alarmReason == 3){
			this.alarmReasonStr = "主从延迟";
		}else if(alarmReason == 4){
			this.alarmReasonStr = "等级提升";
		}else{
			this.alarmReasonStr = "未知";
		}
	}

	public String getAlarmReasonStr() {
		return alarmReasonStr;
	}

	public void setAlarmReasonStr(String alarmReasonStr) {
		this.alarmReasonStr = alarmReasonStr;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
		if(alarmLevel == 0){
			this.alarmLevelStr = "正常";
		}else if(alarmLevel == 1){
			this.alarmLevelStr = "一级";
		}else if(alarmLevel == 2){
			this.alarmLevelStr = "二级";
		}else if(alarmLevel == 3){
			this.alarmLevelStr = "三级";
		}
	}

	public String getAlarmLevelStr() {
		return alarmLevelStr;
	}

	public void setAlarmLevelStr(String alarmLevelStr) {
		this.alarmLevelStr = alarmLevelStr;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
}
