package com.gome.upm.service.impl;


import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.dao.ServerAnalysisMapper;
import com.gome.upm.domain.HostsInfo;
import com.gome.upm.domain.ServerItemDetail;
import com.gome.upm.service.ServerAnalysisService;
import com.gome.upm.service.util.DBContextHolder;


@Service("serverAnalysisService")
public class ServerAnalysisServiceImpl implements ServerAnalysisService {

	@Resource
	ServerAnalysisMapper serverAnalysisMapper;

	@Override
	public HostsInfo queryHost(Long hostid) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceTwo");
		return serverAnalysisMapper.queryHost(hostid);
	}
	@Override
	public HostsInfo queryHostPro(Long hostid) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceSix");
		return serverAnalysisMapper.queryHostPro(hostid);
	}

	@Override
	public HostsInfo queryHostsInfo(HostsInfo hostsInfo) {
		DBContextHolder.setDataSource("dataSourceOne");
		// TODO Auto-generated method stub
		return serverAnalysisMapper.queryHostsInfo(hostsInfo);
	}

	@Override
	public void updateHostsInfo(HostsInfo hostsInfo) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.updateHostsInfo(hostsInfo);
	}

	@Override
	public void addHostsInfo(HostsInfo hostsInfo) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.addHostsInfo(hostsInfo);
	}

	@Override
	public ServerItemDetail queryValue(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceTwo");
		return serverAnalysisMapper.queryValue(serverItemDetail);
	}

	@Override
	public List<ServerItemDetail> queryValueList(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceTwo");
		return serverAnalysisMapper.queryValueList(serverItemDetail);
	}

	@Override
	public void addItemValue(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.addItemValue(serverItemDetail);
	}

	@Override
	public void addItem(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.addItem(serverItemDetail);
	}

	@Override
	public ServerItemDetail queryItems(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryItems(serverItemDetail);
	}

	@Override
	public Page<HostsInfo> queryHostsList(Page<HostsInfo> p) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		List<HostsInfo> hostList = serverAnalysisMapper.queryHostsList(p);
		if(hostList!=null && hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				//查询服务器时间段内最大的cpu和内存值
				HostsInfo hostsInfo = new HostsInfo();
				hostsInfo.setvType("1");
				hostsInfo.setKey_("cpu_use_all");
				hostsInfo.setHostid(hostList.get(i).getHostid());
				String maxCpu = serverAnalysisMapper.queryMaxCpuValue(hostsInfo);
				BigDecimal bd = new BigDecimal(maxCpu);
				bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);
				hostList.get(i).setMaxCpu(String.valueOf(bd));
				hostsInfo.setKey_("used_memory");
				String maxMemory = serverAnalysisMapper.queryMaxCpuValue(hostsInfo);
				BigDecimal bdM = new BigDecimal(maxMemory);
				bdM = bdM.setScale(1,BigDecimal.ROUND_HALF_UP);
				hostList.get(i).setMaxMemory(String.valueOf(bdM));
				//查询服务器的基本信息
				if(hostList.get(i).getOsType()==null || hostList.get(i).getOsType() ==""){
					hostList.get(i).setOsType("无");
				}
				if(hostList.get(i).getProjectName()==null || hostList.get(i).getProjectName() ==""){
					hostList.get(i).setProjectName("无");
				}
				if(hostList.get(i).getProjectLeader()==null || hostList.get(i).getProjectLeader() ==""){
					hostList.get(i).setProjectLeader("无");
				}
				if(hostList.get(i).getApplicationName()==null || hostList.get(i).getApplicationName() ==""){
					hostList.get(i).setApplicationName("无");
				}
				if(hostList.get(i).getApplicationLeader()==null || hostList.get(i).getApplicationLeader() ==""){
					hostList.get(i).setApplicationLeader("无");
				}
			}
		}
		int total = serverAnalysisMapper.selectTotalResultByConditions(p.getConditions());
		Page<HostsInfo> page = new Page<HostsInfo>(p.getPageNo(), p.getPageSize(), total, hostList, p.getConditions());
		return page;
	}
	
	@Override
	public Page<HostsInfo> queryHostsListNew(Page<HostsInfo> p) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		List<HostsInfo> hostList = serverAnalysisMapper.queryHostsList(p);
		if(hostList!=null && hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				//查询服务器时间段内的cpu和内存值出现的次数
				HostsInfo hostsInfo = new HostsInfo();
				hostsInfo.setvType(p.getConditions().getvType());
				hostsInfo.setKey_("cpu_use_all");
				hostsInfo.setHostid(hostList.get(i).getHostid());
				//cpu使用率高于80%
				String analyzeCpuValue = AppConfigUtil.getStringValue("server.analyze.cpu");
				hostsInfo.setValue(Double.parseDouble(analyzeCpuValue));
				String maxCpu = serverAnalysisMapper.querySumCpuValue(hostsInfo);
				hostList.get(i).setMaxCpu(maxCpu);
				//查询当前时间点Memory的值高于90%
				String analyzeMemoryValue = AppConfigUtil.getStringValue("server.analyze.memory");
				hostsInfo.setKey_("used_memory");
				hostsInfo.setValue(Double.parseDouble(analyzeMemoryValue));
				String maxMemory = serverAnalysisMapper.querySumCpuValue(hostsInfo);
				hostList.get(i).setMaxMemory(maxMemory);
				//查询服务器的基本信息
				if(hostList.get(i).getOsType()==null || hostList.get(i).getOsType() ==""){
					hostList.get(i).setOsType("无");
				}
				if(hostList.get(i).getProjectName()==null || hostList.get(i).getProjectName() ==""){
					hostList.get(i).setProjectName("无");
				}
				if(hostList.get(i).getProjectLeader()==null || hostList.get(i).getProjectLeader() ==""){
					hostList.get(i).setProjectLeader("无");
				}
				if(hostList.get(i).getApplicationName()==null || hostList.get(i).getApplicationName() ==""){
					hostList.get(i).setApplicationName("无");
				}
				if(hostList.get(i).getApplicationLeader()==null || hostList.get(i).getApplicationLeader() ==""){
					hostList.get(i).setApplicationLeader("无");
				}
			}
		}
		int total = serverAnalysisMapper.selectTotalResultByConditions(p.getConditions());
		Page<HostsInfo> page = new Page<HostsInfo>(p.getPageNo(), p.getPageSize(), total, hostList, p.getConditions());
		return page;
	}
	@Override
	public Page<HostsInfo> queryHostsListPro(Page<HostsInfo> p) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		List<HostsInfo> hostList = serverAnalysisMapper.queryHostsList(p);
		if(hostList!=null && hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				//查询服务器时间段内的cpu和内存值出现的次数
				HostsInfo hostsInfo = new HostsInfo();
				hostsInfo.setvType(p.getConditions().getvType());
				hostsInfo.setKey_("cpu_use_all");
				hostsInfo.setHostid(hostList.get(i).getHostid());
				//cpu使用率高于80%
				String analyzeCpuValue = AppConfigUtil.getStringValue("server.analyze.cpu");
				hostsInfo.setValue(Double.parseDouble(analyzeCpuValue));
				String maxCpu = serverAnalysisMapper.queryProSumCpuValue(hostsInfo);
				hostList.get(i).setMaxCpu(maxCpu);
				//查询当前时间点Memory的值高于90%
				String analyzeMemoryValue = AppConfigUtil.getStringValue("server.analyze.memory");
				hostsInfo.setKey_("used_memory");
				hostsInfo.setValue(Double.parseDouble(analyzeMemoryValue));
				String maxMemory = serverAnalysisMapper.queryProSumCpuValue(hostsInfo);
				hostList.get(i).setMaxMemory(maxMemory);
				//查询服务器的基本信息
				if(hostList.get(i).getOsType()==null || hostList.get(i).getOsType() ==""){
					hostList.get(i).setOsType("无");
				}
				if(hostList.get(i).getProjectName()==null || hostList.get(i).getProjectName() ==""){
					hostList.get(i).setProjectName("无");
				}
				if(hostList.get(i).getProjectLeader()==null || hostList.get(i).getProjectLeader() ==""){
					hostList.get(i).setProjectLeader("无");
				}
				if(hostList.get(i).getApplicationName()==null || hostList.get(i).getApplicationName() ==""){
					hostList.get(i).setApplicationName("无");
				}
				if(hostList.get(i).getApplicationLeader()==null || hostList.get(i).getApplicationLeader() ==""){
					hostList.get(i).setApplicationLeader("无");
				}
			}
		}
		int total = serverAnalysisMapper.selectTotalResultByConditions(p.getConditions());
		Page<HostsInfo> page = new Page<HostsInfo>(p.getPageNo(), p.getPageSize(), total, hostList, p.getConditions());
		return page;
	}

	@Override
	public String[] queryHostName(String vType) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryHostName(vType);
	}

	@Override
	public HostsInfo queryHostDetail(HostsInfo hostsInfo) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		HostsInfo queryHostDetail = serverAnalysisMapper.queryHostDetail(hostsInfo);
		return queryHostDetail;
	}
	@Override
	public HostsInfo queryHostDetailPro(HostsInfo hostsInfo) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		HostsInfo queryHostDetail = serverAnalysisMapper.queryHostDetail(hostsInfo);
		return queryHostDetail;
	}

	@Override
	public List<ServerItemDetail> queryItemValueList(ServerItemDetail serverItem) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryItemValueList(serverItem);
	}
	@Override
	public List<ServerItemDetail> queryItemValueListNew(ServerItemDetail serverItem) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryItemValueListNew(serverItem);
	}
	@Override
	public List<ServerItemDetail> queryItemValueListPro(ServerItemDetail serverItem) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryItemValueListPro(serverItem);
	}

	@Override
	public List<ServerItemDetail> queryCpuValueList(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceTwo");
		return serverAnalysisMapper.queryCpuValueList(serverItemDetail);
	}

	@Override
	public void addItemValueNew(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.addItemValueNew(serverItemDetail);
	}

	@Override
	public List<HostsInfo> queryForExportExcel(HostsInfo condition) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		List<HostsInfo> hostList = serverAnalysisMapper.queryForExportExcel(condition);
		if(hostList!=null && hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				//查询服务器时间段内的cpu和内存值出现的次数
				HostsInfo hostsInfo = new HostsInfo();
				hostsInfo.setvType("2");
				hostsInfo.setKey_("cpu_use_all");
				hostsInfo.setHostid(hostList.get(i).getHostid());
				//cpu使用率高于80%
				String analyzeCpuValue = AppConfigUtil.getStringValue("server.analyze.cpu");
				hostsInfo.setValue(Double.parseDouble(analyzeCpuValue));
				String maxCpu = serverAnalysisMapper.querySumCpuValue(hostsInfo);
				hostList.get(i).setMaxCpu(maxCpu);
				//查询当前时间点Memory的值高于90%
				String analyzeMemoryValue = AppConfigUtil.getStringValue("server.analyze.memory");
				hostsInfo.setKey_("used_memory");
				hostsInfo.setValue(Double.parseDouble(analyzeMemoryValue));
				String maxMemory = serverAnalysisMapper.querySumCpuValue(hostsInfo);
				hostList.get(i).setMaxMemory(maxMemory);
			}
		}
		return hostList;
	}
	@Override
	public List<HostsInfo> queryProForExportExcel(HostsInfo condition) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		List<HostsInfo> hostList = serverAnalysisMapper.queryForExportExcel(condition);
		if(hostList!=null && hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				//查询服务器时间段内的cpu和内存值出现的次数
				HostsInfo hostsInfo = new HostsInfo();
				hostsInfo.setvType("3");
				hostsInfo.setKey_("cpu_use_all");
				hostsInfo.setHostid(hostList.get(i).getHostid());
				//cpu使用率高于80%
				String analyzeCpuValue = AppConfigUtil.getStringValue("server.analyze.cpu");
				hostsInfo.setValue(Double.parseDouble(analyzeCpuValue));
				String maxCpu = serverAnalysisMapper.queryProSumCpuValue(hostsInfo);
				hostList.get(i).setMaxCpu(maxCpu);
				//查询当前时间点Memory的值高于90%
				String analyzeMemoryValue = AppConfigUtil.getStringValue("server.analyze.memory");
				hostsInfo.setKey_("used_memory");
				hostsInfo.setValue(Double.parseDouble(analyzeMemoryValue));
				String maxMemory = serverAnalysisMapper.queryProSumCpuValue(hostsInfo);
				hostList.get(i).setMaxMemory(maxMemory);
			}
		}
		return hostList;
	}

	@Override
	public List<ServerItemDetail> queryProCpuValueList(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceSix");
		return serverAnalysisMapper.queryProCpuValueList(serverItemDetail);
	}

	@Override
	public void addItemValuePro(ServerItemDetail serverItemDetail) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		serverAnalysisMapper.addItemValuePro(serverItemDetail);
	}
	@Override
	public List<HostsInfo> queryHostsInfoList(String vType) {
		// TODO Auto-generated method stub
		DBContextHolder.setDataSource("dataSourceOne");
		return serverAnalysisMapper.queryHostsInfoList(vType);
	}
}
