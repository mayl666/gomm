package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.HostsInfo;
import com.gome.upm.domain.ServerItemDetail;

/**
 * 
 * 表空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    liuhaikun    新建
 * </pre>
 */
public interface ServerAnalysisMapper {
	HostsInfo queryHost(Long hostid);

	HostsInfo queryHostsInfo(HostsInfo hostsInfo);

	void updateHostsInfo(HostsInfo hostsInfo);

	void addHostsInfo(HostsInfo hostsInfo);

	ServerItemDetail queryValue(ServerItemDetail serverItemDetail);

	List<ServerItemDetail> queryValueList(ServerItemDetail serverItemDetail);

	void addItemValue(ServerItemDetail serverItemDetail);

	void addItem(ServerItemDetail serverItemDetail);

	ServerItemDetail queryItems(ServerItemDetail serverItemDetail);

	List<HostsInfo> queryHostsList(Page<HostsInfo> p);

	int selectTotalResultByConditions(HostsInfo conditions);

	String[] queryHostName(String vType);

	HostsInfo queryHostDetail(HostsInfo hostsInfo);

	List<ServerItemDetail> queryItemValueList(ServerItemDetail serverItem);

	List<ServerItemDetail> queryCpuValueList(ServerItemDetail serverItemDetail);

	void addItemValueNew(ServerItemDetail serverItemDetail);

	List<ServerItemDetail> queryItemValueListNew(ServerItemDetail serverItem);

	String queryMaxCpuValue(HostsInfo hostsInfo);

	String querySumCpuValue(HostsInfo hostsInfo);

	List<HostsInfo> queryForExportExcel(HostsInfo hostsInfo);

	List<ServerItemDetail> queryProCpuValueList(ServerItemDetail serverItemDetail);

	void addItemValuePro(ServerItemDetail serverItemDetail);

	List<ServerItemDetail> queryItemValueListPro(ServerItemDetail serverItem);

	String queryProSumCpuValue(HostsInfo hostsInfo);

	HostsInfo queryHostPro(Long hostid);

	List<HostsInfo> queryHostsInfoList(String vType);
}
