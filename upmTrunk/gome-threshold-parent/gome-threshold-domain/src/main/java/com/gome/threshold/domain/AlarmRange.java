package com.gome.threshold.domain;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 报警值域实体类
 * @author wangxiaye
 * @date 2016年8月26日
 */

public class AlarmRange {
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Properties props = new Properties();
	{
		try {
			InputStream in = AlarmRecord.class.getClassLoader().getResourceAsStream("alarmRange.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 主键ID */
	private Integer id;
	
	/** 业务类型 */
	private String businessType;
	
	/** 业务类型描述 */
	private String businessTypeStr;
	
	
	/** 类型 */
	private String type;
	
	/** 操作用户 */
	private String uid;
	
	
	/** 报警级别   1：一级；2：二级；3：三级*/
	private Integer level;
	
	/** 报警级别描述 */
	private String levelStr;
	
	
	/** 报警时间字符串 */
	private String value;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 上次更新时间 */
	private Date updateTime;
	
	/** 创建时间字符串 */
	private String createTimeStr;
	
	/** 更新时间字符串 */
	private String updateTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
		this.businessTypeStr = props.getProperty(businessType);
	}

	public String getBusinessTypeStr() {
		return businessTypeStr;
	}

	public void setBusinessTypeStr(String businessTypeStr) {
		this.businessTypeStr = businessTypeStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
		if(level == 1){
			this.levelStr = "一级";
		}else if(level == 2){
			this.levelStr = "二级";
		}else if(level == 3){
			this.levelStr = "三级";
		}else {
			this.levelStr = "未知";
		}
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
