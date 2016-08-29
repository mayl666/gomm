package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;

/**
 * 网络监控相关接口
 * @author zhangzhixiang-ds
 *
 */
public interface ServerMonitorService {
	
	public void addHost(ServerHost serverHost);

	public List<ServerHost> queryHost(String hostId);

	public Page<ServerHost> queryHostList(Page<ServerHost> page)  throws Exception;

	public void updateHost(ServerHost serverHost);

	public String[] queryHostGroup(ServerHost serverHost);

	public String[] queryHostName(ServerHost serverHost);

	public void addAlarmRecord(ServerAlarmRecord alarmRecord);

	public List<ServerAlarmRecord> queryAlarmRecord(ServerAlarmRecord alarmRecordQ);

	public void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	public Page<ServerAlarmRecord> findAlarmRecordListByPage(Page<ServerAlarmRecord> p);

	public String[] queryHostGroupNew(ServerHost serverHost);

	public String[] queryHostNameNew(ServerHost serverHost);

	public int queryItemInvalidTotal(ServerHost serverHost);

	public int queryServerTotal(ServerHost serverHost);

	public List<ServerHost> queryHostsList();
}
