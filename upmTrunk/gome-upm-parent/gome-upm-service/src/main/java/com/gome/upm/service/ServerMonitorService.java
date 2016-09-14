package com.gome.upm.service;

import java.util.List;
import java.util.Map;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;

/**
 * 网络监控相关接口
 * @author zhangzhixiang-ds
 *
 */
public interface ServerMonitorService {
	
	public String[] queryHostGroup(ServerHost serverHost);

	public String[] queryHostName(ServerHost serverHost);

	public void addAlarmRecord(ServerAlarmRecord alarmRecord);

	public List<ServerAlarmRecord> queryAlarmRecord(ServerAlarmRecord alarmRecordQ);

	public void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	public Page<ServerAlarmRecord> findAlarmRecordListByPage(Page<ServerAlarmRecord> p);

	public String[] queryHostGroupNew(ServerHost serverHost);

	public String[] queryHostNameNew(ServerHost serverHost);

	public Map<String, List<ServerHost>> queryIndexCpu(ServerHost serverHost);

	public List<ServerHost> queryServerTimeValue(ServerHost serverHost);

	public ServerHost queryHostsBase(ServerHost serverHost);

	public void updateHostsBase(ServerHost serverHost);

	public void addHostsBase(ServerHost serverHost) throws Exception;

	public void updateItemsBase(ServerHost serverHost);

	public ServerHost queryItemsBase(ServerHost serverHost) throws Exception;

	public void addItemsBase(ServerHost serverHost);

	public List<ServerHost> queryServerPingValue(ServerHost serverHost);
}
