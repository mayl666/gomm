package com.gome.threshold.dao;

import java.util.List;

import com.gome.threshold.common.Page;
import com.gome.threshold.domain.ServerAlarmRecord;
import com.gome.threshold.domain.ServerHost;

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

	void addHost(ServerHost serverHost);

	List<ServerHost> queryHost(String hostId);

	List<ServerHost> queryHostList(Page<ServerHost> page);

	int selectTotalResultByConditions(ServerHost conditions);

	void updateHost(ServerHost serverHost);

	String[] queryHostGroup(ServerHost serverHost);

	String[] queryHostName(ServerHost serverHost);

	List<ServerAlarmRecord> queryAlarmRecord(ServerAlarmRecord alarmRecord);

	void addAlarmRecord(ServerAlarmRecord alarmRecord);

	void updateAlarmRecord(ServerAlarmRecord alarmRecord);

	List<ServerAlarmRecord> selectAlarmRecordListByPage(Page<ServerAlarmRecord> page);

	int selectTotalResultByConditionsA(ServerAlarmRecord conditions);

	String[] queryHostGroupNew(ServerHost serverHost);

	String[] queryHostNameNew(ServerHost serverHost);

	int queryItemInvalidTotal(ServerHost serverHost);

	int queryServerTotal(ServerHost serverHost);

	List<ServerHost> queryHostsList();

}
