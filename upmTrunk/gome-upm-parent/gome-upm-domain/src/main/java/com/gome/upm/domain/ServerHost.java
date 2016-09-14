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
public class ServerHost {
	/** 主键ID */
	private Long id;
	//groupids
	private String num;
	//groupids
	private String groupids;
	//groupName
	private String groupName;
	//hostId
	private Long hostid;
	//host
	private String host;
	//itemid
	private Long itemid;
	//key_
	private String key_;
	//key1_
	private String key1_;
	//name
	private String name;
	//available
	private String available;
	//status
	private String status;
	//status
	private String ip;
	//status
	private String port;

	//host
	private long clock;
	//host
	private long time_from;

	private long time_till;
	//host
	private double value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getGroupids() {
		return groupids;
	}
	public void setGroupids(String groupids) {
		this.groupids = groupids;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getHostid() {
		return hostid;
	}
	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getKey_() {
		return key_;
	}
	public void setKey_(String key_) {
		this.key_ = key_;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public long getClock() {
		return clock;
	}
	public void setClock(long clock) {
		this.clock = clock;
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
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getKey1_() {
		return key1_;
	}
	public void setKey1_(String key1_) {
		this.key1_ = key1_;
	}
}
