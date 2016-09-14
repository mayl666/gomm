package com.gome.upm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 服务器实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    liuhk    新建
 * </pre>
 */
public class HostsInfo {
	//ID
	private Long hostid;
	//ID
	private Long itemid;
	//ID
	private String key_;
	//host
	private String host;
	//host
	private String hostgroup;
	
	private String ip;
	
	private String status;
	
	private String available;
	//host
	private String name;
	//itemId
	private String osType;
	
	//itemId
	private String vType;
	
	private String projectName;
	
	private String projectLeader;
	
	private String applicationName;
	
	private String applicationLeader ;
	private String maxCpu ;
	private String maxMemory ;
	//host
	private long time_from;
	
	private long time_till;
	//host
	private double value;
	
	private int num;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMaxCpu() {
		return maxCpu;
	}

	public void setMaxCpu(String maxCpu) {
		this.maxCpu = maxCpu;
	}

	public String getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
	}

	List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();

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

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHostgroup() {
		return hostgroup;
	}

	public void setHostgroup(String hostgroup) {
		this.hostgroup = hostgroup;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationLeader() {
		return applicationLeader;
	}

	public void setApplicationLeader(String applicationLeader) {
		this.applicationLeader = applicationLeader;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServerItemDetail> getItemDetailList() {
		return itemDetailList;
	}

	public void setItemDetailList(List<ServerItemDetail> itemDetailList) {
		this.itemDetailList = itemDetailList;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
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
	
}
