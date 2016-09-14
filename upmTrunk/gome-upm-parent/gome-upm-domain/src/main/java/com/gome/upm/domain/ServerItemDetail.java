package com.gome.upm.domain;

/**
 * 
 * 服务器监控实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    liuhk    新建
 * </pre>
 */
public class ServerItemDetail {

	//ID
	private Long id;
	
	//host
	private Long hostid;
	
	private Long itemid;
	
	//host
	private long clock;
	//host
	private long time_from;
	
	private long time_till;
	//host
	private double value;
	//host
	private String value_min;
	//host
	private String value_avg;
	//host
	private String value_max;
	//host
	private String key_;
	
	private String showTime;
	private String vType;

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public long getTime_from() {
		return time_from;
	}

	public void setTime_from(long time_from) {
		this.time_from = time_from;
	}

	public long getTime_till() {
		return time_till;
	}

	public void setTime_till(long time_till) {
		this.time_till = time_till;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getClock() {
		return clock;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getValue_min() {
		return value_min;
	}

	public void setValue_min(String value_min) {
		this.value_min = value_min;
	}

	public String getValue_avg() {
		return value_avg;
	}

	public void setValue_avg(String value_avg) {
		this.value_avg = value_avg;
	}

	public String getValue_max() {
		return value_max;
	}

	public void setValue_max(String value_max) {
		this.value_max = value_max;
	}

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}

	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}
	
}
