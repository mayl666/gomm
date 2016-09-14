package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;

/**
 * 
 * 表空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    liuhaikun    新建
 * </pre>
 */
public interface ServerMonitorMapper {


	String[] queryHostGroup(ServerHost serverHost);

	String[] queryHostName(ServerHost serverHost);

	List<ServerAlarmRecord> queryAlarmRecord(ServerAlarmRecord alarmRecord);

	void addAlarmRecord(ServerAlarmRecord alarmRecord);

	void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	List<ServerAlarmRecord> selectAlarmRecordListByPage(Page<ServerAlarmRecord> page);

	int selectTotalResultByConditionsA(ServerAlarmRecord conditions);

	String[] queryHostGroupNew(ServerHost serverHost);

	String[] queryHostNameNew(ServerHost serverHost);

	List<ServerHost> queryHostsList(ServerHost serverHost);

	ServerHost queryItemValue(ServerHost serverHost);

	ServerHost queryHostsBase(ServerHost serverHost);

	void updateHostsBase(ServerHost serverHost);

	void addHostsBase(ServerHost serverHost);

	void updateItemsBase(ServerHost serverHost);

	ServerHost queryItemsBase(ServerHost serverHost);

	void addItemsBase(ServerHost serverHost);

	ServerHost queryItemPingValue(ServerHost serverHost);

}
