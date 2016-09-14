package com.gome.upm.controler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ExcelUtil;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.HostsInfo;
import com.gome.upm.domain.ServerItemDetail;
import com.gome.upm.service.ServerAnalysisService;
@Controller
@RequestMapping(value="/serverAnalysis")
public class ServerAnalysisController {
	@Resource(name = "serverAnalysisService")
	ServerAnalysisService serverAnalysisService;

	/**
	 * 获取全部设备
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToHostAll")
	public ModelAndView goToHostAll(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		model.setViewName("/servernanlysis/serverAnalysis-allfacility");
		model.addObject("leftMenu", "server.serverAnalysis");
		return model;
	}
	
	/**
	 * 获取全部设备
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryHostAll" , method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryHostAll(@Param(value="content")String content, @Param(value="page")String page,@Param(value="status")String status, @Param(value="size")String size,HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/servernanlysis/serverAnalysis-allfacility");
		}else{
			model.setViewName("/servernanlysis/serverAnalysis-allfacilityTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<HostsInfo> p = new Page<HostsInfo>(pageNo, pageSize);
		HostsInfo condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, HostsInfo.class);
			String name = condition.getName();
			if(name!=null && name!=""){
				name=java.net.URLDecoder.decode(name,"UTF-8");
				condition.setName(name);
			}
		} else {
			condition = new HostsInfo();
		}
		condition.setvType("1");
		p.setConditions(condition);
		Page<HostsInfo>  hostList = serverAnalysisService.queryHostsList(p);
		String[] hostNameList = serverAnalysisService.queryHostName("1");
		model.addObject("hostNameList", hostNameList);
		model.addObject("page", hostList);
		model.addObject("status", status);
		model.addObject("leftMenu", "server.serverAnalysis");
		return model;
	}
	
	
	/**
	 * 详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToDetail")
	public ModelAndView goToDetail(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		model.setViewName("/servernanlysis/serverAnalysis-detail");
		HostsInfo hostsInfo = new HostsInfo();
		hostsInfo.setvType("1");
		hostsInfo.setHostid(hostid);
		hostsInfo = serverAnalysisService.queryHostDetail(hostsInfo);
		model.addObject("hostsInfo",hostsInfo);
		model.addObject("leftMenu", "server.serverAnalysis");
		return model;
	}
	
	/**
	 * 获取cpu和内存使用率监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getCpuMemoryDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getDiskUseDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setKey_("cpu_use_all");
			serverItem.setvType("1");
			List<ServerItemDetail> cpuDetailList = serverAnalysisService.queryItemValueList(serverItem);
			if(cpuDetailList!=null && cpuDetailList.size()>0){
				for (int i = 0; i < cpuDetailList.size(); i++) {
					long clock = cpuDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					cpuDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoCpu = new HostsInfo();
			hostsInfoCpu.setName("cpu使用率");
			hostsInfoCpu.setItemDetailList(cpuDetailList);
			serverItem.setKey_("used_memory");
			List<ServerItemDetail>  memoryDetailList = serverAnalysisService.queryItemValueList(serverItem);
			if(memoryDetailList!=null && memoryDetailList.size()>0){
				for (int i = 0; i < memoryDetailList.size(); i++) {
					long clock = memoryDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					memoryDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoMemory = new HostsInfo();
			hostsInfoMemory.setName("内存使用率");
			hostsInfoMemory.setItemDetailList(memoryDetailList);
			itemList.add(hostsInfoCpu);
			itemList.add(hostsInfoMemory);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取TCP监控数据---详情
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getTCPDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getTCPDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setvType("1");
			serverItem.setKey_("iptstate.tcp.syn");
			List<ServerItemDetail> synDetailList = serverAnalysisService.queryItemValueList(serverItem);
			if(synDetailList!=null && synDetailList.size()>0){
				for (int i = 0; i < synDetailList.size(); i++) {
					long clock = synDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					synDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfosyn = new HostsInfo();
			hostsInfosyn.setName("tcp syn连接数");
			hostsInfosyn.setItemDetailList(synDetailList);
			serverItem.setKey_("iptstate.tcp.established");
			List<ServerItemDetail>  establishedDetailList = serverAnalysisService.queryItemValueList(serverItem);
			if(establishedDetailList!=null && establishedDetailList.size()>0){
				for (int i = 0; i < establishedDetailList.size(); i++) {
					long clock = establishedDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					establishedDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoestablished = new HostsInfo();
			hostsInfoestablished.setName("tcp established连接数");
			hostsInfoestablished.setItemDetailList(establishedDetailList);
			itemList.add(hostsInfosyn);
			itemList.add(hostsInfoestablished);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取全部设备cpu和内存》80/90-----测试zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryHostAllNew" , method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryHostAllNew(@Param(value="content")String content, @Param(value="page")String page,@Param(value="status")String status, @Param(value="size")String size,HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/servernanlysis/serverAnalysis-allfacilitynew");
		}else{
			model.setViewName("/servernanlysis/serverAnalysis-allfacilityTablenew");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<HostsInfo> p = new Page<HostsInfo>(pageNo, pageSize);
		HostsInfo condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, HostsInfo.class);
			String name = condition.getName();
			if(name!=null && name!=""){
				name=java.net.URLDecoder.decode(name,"UTF-8");
				condition.setName(name);
			}
		} else {
			condition = new HostsInfo();
		}
		condition.setvType("2");
		p.setConditions(condition);
		Page<HostsInfo>  hostList = serverAnalysisService.queryHostsListNew(p);
		String[] hostNameList = serverAnalysisService.queryHostName("2");
		model.addObject("hostNameList", hostNameList);
		model.addObject("page", hostList);
		model.addObject("status", status);
		model.addObject("leftMenu", "server.serverAnalysisNew");
		return model;
	}
	/**
	 * 获取全部设备cpu和内存》80/90-----生产zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="queryHostAllPro" , method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView queryHostAllPro(@Param(value="content")String content, @Param(value="page")String page,@Param(value="status")String status, @Param(value="size")String size,HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/servernanlysis/serverAnalysis-allfacilitypro");
		}else{
			model.setViewName("/servernanlysis/serverAnalysis-allfacilityTablepro");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<HostsInfo> p = new Page<HostsInfo>(pageNo, pageSize);
		HostsInfo condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, HostsInfo.class);
			String name = condition.getName();
			if(name!=null && name!=""){
				name=java.net.URLDecoder.decode(name,"UTF-8");
				condition.setName(name);
			}
		} else {
			condition = new HostsInfo();
		}
		condition.setvType("3");
		p.setConditions(condition);
		Page<HostsInfo>  hostList = serverAnalysisService.queryHostsListPro(p);
		String[] hostNameList = serverAnalysisService.queryHostName("3");
		model.addObject("hostNameList", hostNameList);
		model.addObject("page", hostList);
		model.addObject("status", status);
		model.addObject("leftMenu", "server.serverAnalysisPro");
		return model;
	}
	
	
	/**
	 * 详情-----测试zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToDetailNew")
	public ModelAndView goToDetailNew(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		model.setViewName("/servernanlysis/serverAnalysis-detailnew");
		HostsInfo hostsInfo = new HostsInfo();
		hostsInfo.setvType("2");
		hostsInfo.setHostid(hostid);
		hostsInfo = serverAnalysisService.queryHostDetail(hostsInfo);
		model.addObject("hostsInfo",hostsInfo);
		model.addObject("leftMenu", "server.serverAnalysisNew");
		return model;
	}
	/**
	 * 详情-----生产zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="goToDetailPro")
	public ModelAndView goToDetailPro(HttpServletRequest request, HttpServletResponse response, ModelAndView model,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		model.setViewName("/servernanlysis/serverAnalysis-detailpro");
		HostsInfo hostsInfo = new HostsInfo();
		hostsInfo.setvType("3");
		hostsInfo.setHostid(hostid);
		hostsInfo = serverAnalysisService.queryHostDetailPro(hostsInfo);
		model.addObject("hostsInfo",hostsInfo);
		model.addObject("leftMenu", "server.serverAnalysisPro");
		return model;
	}
	
	/**
	 * 获取内存使用率监控数据---详情----测试zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getMemoryDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getMemoryDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setvType("2");
			serverItem.setKey_("used_memory");
			List<ServerItemDetail>  memoryDetailList = serverAnalysisService.queryItemValueListNew(serverItem);
			if(memoryDetailList!=null && memoryDetailList.size()>0){
				for (int i = 0; i < memoryDetailList.size(); i++) {
					long clock = memoryDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					memoryDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoMemory = new HostsInfo();
			hostsInfoMemory.setName("内存使用率");
			hostsInfoMemory.setItemDetailList(memoryDetailList);
			itemList.add(hostsInfoMemory);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取内存使用率监控数据---详情----生产zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getMemoryDetailPro", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getMemoryDetailPro(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setvType("3");
			serverItem.setKey_("used_memory");
			List<ServerItemDetail>  memoryDetailList = serverAnalysisService.queryItemValueListPro(serverItem);
			if(memoryDetailList!=null && memoryDetailList.size()>0){
				for (int i = 0; i < memoryDetailList.size(); i++) {
					long clock = memoryDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					memoryDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoMemory = new HostsInfo();
			hostsInfoMemory.setName("内存使用率");
			hostsInfoMemory.setItemDetailList(memoryDetailList);
			itemList.add(hostsInfoMemory);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取cpu使用率监控数据---详情----测试zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getCpuDetail", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getCpuDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setKey_("cpu_use_all");
			serverItem.setvType("2");
			List<ServerItemDetail> cpuDetailList = serverAnalysisService.queryItemValueListNew(serverItem);
			if(cpuDetailList!=null && cpuDetailList.size()>0){
				for (int i = 0; i < cpuDetailList.size(); i++) {
					long clock = cpuDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					cpuDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoCpu = new HostsInfo();
			hostsInfoCpu.setName("cpu使用率");
			hostsInfoCpu.setItemDetailList(cpuDetailList);
			itemList.add(hostsInfoCpu);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取cpu使用率监控数据---详情----生产zabbix服务器
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getCpuDetailPro", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getCpuDetailPro(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "hostid", required = true) long hostid) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<HostsInfo>  itemList = new ArrayList<HostsInfo>();
			ServerItemDetail serverItem = new ServerItemDetail();
			serverItem.setHostid(hostid);
			serverItem.setKey_("cpu_use_all");
			serverItem.setvType("3");
			List<ServerItemDetail> cpuDetailList = serverAnalysisService.queryItemValueListPro(serverItem);
			if(cpuDetailList!=null && cpuDetailList.size()>0){
				for (int i = 0; i < cpuDetailList.size(); i++) {
					long clock = cpuDetailList.get(i).getClock()*1000L;
					Date date = new Date(clock);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
					String showTime = df.format(date);
					cpuDetailList.get(i).setShowTime(showTime);
				}
			}
			HostsInfo hostsInfoCpu = new HostsInfo();
			hostsInfoCpu.setName("cpu使用率");
			hostsInfoCpu.setItemDetailList(cpuDetailList);
			itemList.add(hostsInfoCpu);
			res.setAttach(itemList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 
	 * 记录导出.----测试zabbix服务器
	 *
	 * @param conditions
	 * 			搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    caowei    新建
	 * </pre>
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportExcel", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView exportExcel(@Param(value="content")String content, @Param(value="vType")String vType, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		HostsInfo  hostsInfo = null;
		if(StringUtils.isNotEmpty(content)){
			hostsInfo = JSON.parseObject(content, HostsInfo.class);
			String name = hostsInfo.getName();
			if(name!=null && name!=""){
				name=java.net.URLDecoder.decode(name,"UTF-8");
				hostsInfo.setName(name);
			}
		}else{
			hostsInfo = new HostsInfo();
		}
		hostsInfo.setvType(vType);
		List<HostsInfo>  hostList = serverAnalysisService.queryForExportExcel(hostsInfo);
		int num = 0;
		if(hostList != null && hostList.size() > 0){
			for (HostsInfo record : hostList) {
				num++;
				record.setNum(num);
			}
		}
		String[] title ={"序号","服务器名称","ip","CPU(次)","内存(次)","环境类型","项目名称","项目负责人","应用名称","应用负责人"};
        String[] header = {"num","name","ip","maxCpu","maxMemory","osType","projectName","projectLeader","applicationName","applicationLeader"};
		String fileName = "服务器高利用率统计表格"+hostsInfo.getTime_from()+"-"+hostsInfo.getTime_till()+".xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(hostList);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		return null;
	}
	/**
	 * 
	 * 记录导出.----生产zabbix服务器
	 *
	 * @param conditions
	 * 			搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    caowei    新建
	 * </pre>
	 * @throws Exception 
	 */
	@RequestMapping(value="/exportExcelPro", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView exportExcelPro(@Param(value="content")String content, @Param(value="vType")String vType, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception{
		HostsInfo  hostsInfo = null;
		if(StringUtils.isNotEmpty(content)){
			hostsInfo = JSON.parseObject(content, HostsInfo.class);
			String name = hostsInfo.getName();
			if(name!=null && name!=""){
				name=java.net.URLDecoder.decode(name,"UTF-8");
				hostsInfo.setName(name);
			}
		}else{
			hostsInfo = new HostsInfo();
		}
		hostsInfo.setvType(vType);
		List<HostsInfo>  hostList = serverAnalysisService.queryProForExportExcel(hostsInfo);
		int num = 0;
		if(hostList != null && hostList.size() > 0){
			for (HostsInfo record : hostList) {
				num++;
				record.setNum(num);
			}
		}
		String[] title ={"序号","服务器名称","ip","CPU(次)","内存(次)","环境类型","项目名称","项目负责人","应用名称","应用负责人"};
		String[] header = {"num","name","ip","maxCpu","maxMemory","osType","projectName","projectLeader","applicationName","applicationLeader"};
		String fileName = "服务器高利用率统计表格"+hostsInfo.getTime_from()+"-"+hostsInfo.getTime_till()+".xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(hostList);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		return null;
	}
	
}
