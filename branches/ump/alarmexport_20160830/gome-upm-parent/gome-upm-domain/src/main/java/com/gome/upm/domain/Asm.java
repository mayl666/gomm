package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ASM空间
 * @author caowei-ds1
 *
 */
public class Asm {
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** 服务器地址 */
	private String serverAddr;

	/** 数据库名 */
	private String dbName;
	
	/** 磁盘组名 */
	private String diskGroup;
	
	/** 总容量（MB） */
	private Float totalMB;
	
	/** 已使用容量（MB) */
	private Float usedMB;
	
	/** 已使用百分比 */
	private Float usedPercent;
	
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

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDiskGroup() {
		return diskGroup;
	}

	public void setDiskGroup(String diskGroup) {
		this.diskGroup = diskGroup;
	}

	public Float getTotalMB() {
		return totalMB;
	}

	public void setTotalMB(Float totalMB) {
		this.totalMB = totalMB;
	}

	public Float getUsedMB() {
		return usedMB;
	}

	public void setUsedMB(Float usedMB) {
		this.usedMB = usedMB;
	}

	public Float getUsedPercent() {
		return usedPercent;
	}

	public void setUsedPercent(Float usedPercent) {
		this.usedPercent = usedPercent;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		if(updateTime != null){
			this.updateTimeStr = df.format(updateTime);
		}
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	@Override
	public String toString() {
		return "ASM [服务器：" + serverAddr + ", 数据库：" + dbName + ", 磁盘组：" + diskGroup + ", 总容量："
				+ totalMB + "MB, 已使用容量：" + usedMB + "MB, 已使用百分比：<font color='#FF0000'>" + usedPercent + "%</font>, 创建时间：" + createTimeStr
				+ ", 采样时间：" + updateTimeStr + "]";
	}

}
