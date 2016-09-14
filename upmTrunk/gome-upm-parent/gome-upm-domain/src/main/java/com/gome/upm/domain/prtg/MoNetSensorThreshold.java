package com.gome.upm.domain.prtg;

import java.util.Date;

public class MoNetSensorThreshold {
	public static final Integer NORMAL_STATE = 0;//正常
	public static final Integer ALARM_STATE = 1;//报警
	public static final Integer ALARM_LEVEL0 = 0;//重置
	public static final Integer ALARM_LEVEL1 = 1;//一级报警
	public static final Integer ALARM_LEVEL2 = 2;//二级报警
	public static final Integer ALARM_LEVEL3 = 3;//三级报警
	private Integer sensorId;

	private Double sensorThreshold;

	private Double level1;

	private Double level2;

	private Double level3;

	private String aliaName;

	private Integer state;

	private Date alarmTime;

	private String alarmType;

	private String groupName;

	private String deviceName;

	private String sensorName;

	private Integer sensorState;

	/**
	 * 入站流量报警级别默认为0不报警，1 2 3
	 */
	private Integer alarmInLevel;

	/**
	 * 出站流量报警级别默认为0不报警，1 2 3
	 */
	private Integer alarmOutLevel;

	public Integer getAlarmInLevel() {
		return alarmInLevel;
	}

	public void setAlarmInLevel(Integer alarmInLevel) {
		this.alarmInLevel = alarmInLevel;
	}

	public Integer getAlarmOutLevel() {
		return alarmOutLevel;
	}

	public void setAlarmOutLevel(Integer alarmOutLevel) {
		this.alarmOutLevel = alarmOutLevel;
	}

	public Double getLevel1() {
		return level1;
	}

	public void setLevel1(Double level1) {
		this.level1 = level1;
	}

	public Double getLevel2() {
		return level2;
	}

	public void setLevel2(Double level2) {
		this.level2 = level2;
	}

	public Double getLevel3() {
		return level3;
	}

	public void setLevel3(Double level3) {
		this.level3 = level3;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getSensorState() {
		return sensorState;
	}

	public void setSensorState(Integer sensorState) {
		this.sensorState = sensorState;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Double getSensorThreshold() {
		return sensorThreshold;
	}

	public void setSensorThreshold(Double sensorThreshold) {
		this.sensorThreshold = sensorThreshold;
	}

	public String getAliaName() {
		return aliaName;
	}

	public void setAliaName(String aliaName) {
		this.aliaName = aliaName == null ? null : aliaName.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
}