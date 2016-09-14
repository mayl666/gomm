package com.gome.upm.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, List<ServerHost>> queryIndexCpu(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceSix");
		Map<String, List<ServerHost>> map = new HashMap<String, List<ServerHost>>();
		List<ServerHost> hostsList60 = new ArrayList<ServerHost>();
		List<ServerHost> hostsList80 = new ArrayList<ServerHost>();
		List<ServerHost> hostsList90 = new ArrayList<ServerHost>();
		List<ServerHost> hostsList = serverMonitorMapper.queryHostsList(serverHost);
		if(hostsList!=null && hostsList.size()>0){
			for (int i = 0; i < hostsList.size(); i++) {
				Long itemid = hostsList.get(i).getItemid();
				serverHost.setItemid(itemid);
				serverHost.setTime_from(new Date().getTime()/1000-60);
				serverHost.setTime_till(new Date().getTime()/1000);
				ServerHost itemValue = serverMonitorMapper.queryItemValue(serverHost);
				if(itemValue!=null){
					hostsList.get(i).setValue(itemValue.getValue());
					System.out.println(hostsList.get(i).getName()+"----"+i+"---"+itemValue.getValue());
					if(itemValue.getValue()>=0 && itemValue.getValue()<60.0000){
						hostsList60.add(hostsList.get(i));
					}else if(itemValue.getValue()<90.0000){
						hostsList80.add(hostsList.get(i));
					}else{
						hostsList90.add(hostsList.get(i));
					}
				}
			}
			map.put("hostsList60", hostsList60);
			map.put("hostsList80", hostsList80);
			map.put("hostsList90", hostsList90);
		}
		return map;
	}

	@Override
	public List<ServerHost> queryServerTimeValue(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceSix");
		List<ServerHost> hostsListNew = new ArrayList<ServerHost>();
		List<ServerHost> hostsList = serverMonitorMapper.queryHostsList(serverHost);
		if(hostsList!=null && hostsList.size()>0){
			for (int i = 0; i < hostsList.size(); i++) {
				Long itemid = hostsList.get(i).getItemid();
				serverHost.setItemid(itemid);
				serverHost.setTime_from(new Date().getTime()/1000-60);
				serverHost.setTime_till(new Date().getTime()/1000);
				ServerHost itemValue = serverMonitorMapper.queryItemValue(serverHost);
				if(itemValue!=null){
					hostsList.get(i).setValue(itemValue.getValue());
					hostsList.get(i).setClock(itemValue.getClock());
					hostsListNew.add(hostsList.get(i));
				}
			}
		}
		return hostsListNew;
	}
	@Override
	public List<ServerHost> queryServerPingValue(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceSix");
		List<ServerHost> hostsListNew = new ArrayList<ServerHost>();
		List<ServerHost> hostsList = serverMonitorMapper.queryHostsList(serverHost);
		if(hostsList!=null && hostsList.size()>0){
			for (int i = 0; i < hostsList.size(); i++) {
				Long itemid = hostsList.get(i).getItemid();
				serverHost.setItemid(itemid);
				serverHost.setTime_from(new Date().getTime()/1000-60);
				serverHost.setTime_till(new Date().getTime()/1000);
				ServerHost itemValue = serverMonitorMapper.queryItemPingValue(serverHost);
				if(itemValue!=null){
					hostsList.get(i).setValue(itemValue.getValue());
					hostsList.get(i).setClock(itemValue.getClock());
					hostsListNew.add(hostsList.get(i));
				}
			}
		}
		return hostsListNew;
	}

	@Override
	public ServerHost queryHostsBase(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverMonitorMapper.queryHostsBase(serverHost);
	}

	@Override
	public void updateHostsBase(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.updateHostsBase(serverHost);
	}

	@Override
	public void addHostsBase(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.addHostsBase(serverHost);
		serverMonitorMapper.addItemsBase(serverHost);
	}

	@Override
	public void updateItemsBase(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.updateItemsBase(serverHost);
	}

	@Override
	public ServerHost queryItemsBase(ServerHost serverHost) throws Exception{
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverMonitorMapper.queryItemsBase(serverHost);
	}

	@Override
	public void addItemsBase(ServerHost serverHost) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverMonitorMapper.addItemsBase(serverHost);
	}
}
