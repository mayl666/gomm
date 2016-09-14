package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.HostsInfo;
import com.gome.upm.domain.ServerItemDetail;

/**
 * 服务器分析相关接口
 * @author zhangzhixiang-ds
 *
 */
public interface ServerAnalysisService {

	public HostsInfo queryHostsInfo(HostsInfo hostsInfo);

	public void updateHostsInfo(HostsInfo hostsInfo);

	public void addHostsInfo(HostsInfo hostsInfo);

	public ServerItemDetail queryValue(ServerItemDetail serverItemDetail);

	public List<ServerItemDetail> queryValueList(ServerItemDetail serverItemDetail);

	public void addItemValue(ServerItemDetail serverItemDetail);

	public HostsInfo queryHost(Long hostid);

	public void addItem(ServerItemDetail serverItemDetail);

	public ServerItemDetail queryItems(ServerItemDetail serverItemDetail);

	public Page<HostsInfo> queryHostsList(Page<HostsInfo> p);

	public String[] queryHostName(String vType);

	public HostsInfo queryHostDetail(HostsInfo hostsInfo);

	public List<ServerItemDetail> queryItemValueList(ServerItemDetail serverItem);

	public List<ServerItemDetail> queryCpuValueList(ServerItemDetail serverItemDetail);

	public void addItemValueNew(ServerItemDetail serverItemDetail);

	public List<ServerItemDetail> queryItemValueListNew(ServerItemDetail serverItem);

	public Page<HostsInfo> queryHostsListNew(Page<HostsInfo> p);

	public List<HostsInfo> queryForExportExcel(HostsInfo condition);

	public List<ServerItemDetail> queryProCpuValueList(ServerItemDetail serverItemDetail);

	public void addItemValuePro(ServerItemDetail serverItemDetail);

	public List<ServerItemDetail> queryItemValueListPro(ServerItemDetail serverItem);

	public List<HostsInfo> queryProForExportExcel(HostsInfo hostsInfo);

	public HostsInfo queryHostPro(Long hostid);

	public Page<HostsInfo> queryHostsListPro(Page<HostsInfo> p);

	public HostsInfo queryHostDetailPro(HostsInfo hostsInfo);

	public List<HostsInfo> queryHostsInfoList(String vType);
}
