package com.gome.upm.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.ServerMonitorMapper;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.DBContextHolder;


@Service("serverMonitorService")
public class ServerMonitorServiceImpl implements ServerMonitorService {

	@Resource
	ServerMonitorMapper serverMonitorMapper;

	@Override
	public void addHost(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.addHost(serverHost);
	}

	@Override
	public List<ServerHost> queryHost(String hostId) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverMonitorMapper.queryHost(hostId);
	}

	@Override
	public Page<ServerHost> queryHostList(Page<ServerHost> page) throws Exception {
		DBContextHolder.setDataSource("dataSourceOne");
		List<ServerHost> hostList = serverMonitorMapper.queryHostList(page);
		int total = serverMonitorMapper.selectTotalResultByConditions(page.getConditions());
		Page<ServerHost> page2 = new Page<ServerHost>(page.getPageNo(), page.getPageSize(), total, hostList, page.getConditions());
		return page2;
	}

	@Override
	public void updateHost(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.updateHost(serverHost);
	}

	@Override
	public String[] queryHostGroup(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		String[] groupList = serverMonitorMapper.queryHostGroup(serverHost);
		return groupList;
	}

	@Override
	public String[] queryHostName(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		String[] hostNameList = serverMonitorMapper.queryHostName(serverHost);
		return hostNameList;
	}

	@Override
	public void addAlarmRecord(ServerAlarmRecord alarmRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		// TODO Auto-generated method stub
		serverMonitorMapper.addAlarmRecord(alarmRecord);
	}

	@Override
	public List<ServerAlarmRecord> queryAlarmRecord(ServerAlarmRecord alarmRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		return serverMonitorMapper.queryAlarmRecord(alarmRecord);
	}

	@Override
	public void updateAlarmRecord(ServerAlarmRecord alarmRecord) {
		DBContextHolder.setDataSource("dataSourceOne");
		// TODO Auto-generated method stub
		serverMonitorMapper.updateAlarmRecord(alarmRecord);
	}

	@Override
	public Page<ServerAlarmRecord> findAlarmRecordListByPage(Page<ServerAlarmRecord> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<ServerAlarmRecord> alarmRecordList = serverMonitorMapper.selectAlarmRecordListByPage(page);
		int totalResult = serverMonitorMapper.selectTotalResultByConditionsA(page.getConditions());
		return new Page<ServerAlarmRecord>(page.getPageNo(), page.getPageSize(), totalResult, alarmRecordList, page.getConditions());
	}

	@Override
	public String[] queryHostGroupNew(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		String[] groupList = serverMonitorMapper.queryHostGroupNew(serverHost);
		return groupList;
	}

	@Override
	public String[] queryHostNameNew(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		String[] hostNameList = serverMonitorMapper.queryHostNameNew(serverHost);
		return hostNameList;
	}

	@Override
	public int queryItemInvalidTotal(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		return serverMonitorMapper.queryItemInvalidTotal(serverHost);
	}

	@Override
	public int queryServerTotal(ServerHost serverHost) {
		DBContextHolder.setDataSource("dataSourceOne");
		// TODO Auto-generated method stub
		return serverMonitorMapper.queryServerTotal(serverHost);
	}

	@Override
	public List<ServerHost> queryHostsList() {
		DBContextHolder.setDataSource("dataSourceOne");
		// TODO Auto-generated method stub
		return serverMonitorMapper.queryHostsList();
	}
}
