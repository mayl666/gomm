package com.gome.upm.controler;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.ServerMonitorService;
@Controller
@RequestMapping(value="/server")
public class ServerController {
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
	/*@Resource(name = "monitorGcache")
	Gcache monitorGcache;*/
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	private Date date;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		model.setViewName("/upm/serverMonitor");
		model.addObject("leftMenu", "server.serverMenu");
		return model;
	}

	/**
	 * 查询server首页数据 包括健康指数、总设备数、故障设备数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIndex", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIndex (HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
//			String totalHost = monitorGcache.get("totalHost");
//			String validHost = monitorGcache.get("validHost");
//			String validPHost = monitorGcache.get("validPHost");
//			String invalidHost = monitorGcache.get("invalidHost");
//			String invalidPHost = monitorGcache.get("invalidPHost");
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 查询server监控项首页数据 包括健康指数、总设备监控项数、监控项故障数等
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getItemIndex", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getItemIndex (HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			date = new Date();
			logger.info(df.format(date) + "-----CPU监控任务启动...");
			System.out.println(df.format(date) + "-----CPU监控任务启动...");
			try {
				ServerHost serverHost = new ServerHost();
				serverHost.setStatus("0");
				serverHost.setKey_("cpu_use_all");
				List<ServerHost> hostsList = serverMonitorService.queryServerTimeValue(serverHost);
				if(hostsList!=null && hostsList.size()>0){
					for (int i = 0; i < hostsList.size(); i++) {
						ServerHost host = serverMonitorService.queryHostsBase(hostsList.get(i));
						if(host!=null){
							serverMonitorService.updateHostsBase(hostsList.get(i));
						}else{
							serverMonitorService.addHostsBase(hostsList.get(i));
						}
					}
				}
				logger.info(df.format(date) + "-----CPU监控任务结束...");
				System.out.println(df.format(date) + "-----CPU监控任务结束...");
				logger.info(df.format(date) + "-----内存监控任务启动...");
				System.out.println(df.format(date) + "-----内存监控任务启动...");
				serverHost.setKey_("used_memory");
				List<ServerHost> hostsList_M = serverMonitorService.queryServerTimeValue(serverHost);
				if(hostsList_M!=null && hostsList_M.size()>0){
					for (int i = 0; i < hostsList_M.size(); i++) {
						ServerHost host = serverMonitorService.queryHostsBase(hostsList_M.get(i));
						if(host!=null){
							serverMonitorService.updateHostsBase(hostsList_M.get(i));
						}else{
							serverMonitorService.addHostsBase(hostsList_M.get(i));
						}
					}
				}
				logger.info(df.format(date) + "-----内存监控任务结束...");
				System.out.println(df.format(date) + "-----内存监控任务结束...");
				logger.info(df.format(date) + "-----磁盘监控任务启动...");
				System.out.println(df.format(date) + "-----磁盘监控任务启动...");
				serverHost.setKey_("vfs.fs.size[/");
				serverHost.setKey1_("pfree]");
				List<ServerHost> hostsList_D = serverMonitorService.queryServerTimeValue(serverHost);
				if(hostsList_D!=null && hostsList_D.size()>0){
					for (int i = 0; i < hostsList_D.size(); i++) {
						ServerHost host = serverMonitorService.queryHostsBase(hostsList_D.get(i));
						if(host!=null){
							serverMonitorService.updateHostsBase(hostsList_D.get(i));
						}else{
							serverMonitorService.addHostsBase(hostsList_D.get(i));
						}
					}
				}
				logger.info(df.format(date) + "-----磁盘监控任务结束...");
				System.out.println(df.format(date) + "-----磁盘监控任务结束...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取CPU监控数据使用率各区间占比
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIndexCpu", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getCpu(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, List<ServerHost>> map = new HashMap<String, List<ServerHost>>();
		try{
			ServerHost serverHost = new ServerHost();
			serverHost.setKey_("cpu_use_all");
			serverHost.setStatus("0");
			map = serverMonitorService.queryIndexCpu(serverHost);
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	/**
	 * 获取内存监控数据使用率各区间占比
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getIndexMemory", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getIndexMemory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		Map<String, List<ServerHost>> map = new HashMap<String, List<ServerHost>>();
		try{
			ServerHost serverHost = new ServerHost();
			serverHost.setKey_("used_memory");
			serverHost.setStatus("0");
			map = serverMonitorService.queryIndexCpu(serverHost);
			res.setCode(ReturnCode.ACTIVE_SUCCESS.code());
			res.setAttach(map);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取host查询条件列表
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getHost", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getHost(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "group", required = true) String group,@RequestParam(value = "status", required = true) String status) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			ServerHost serverHost = new ServerHost();
			serverHost.setGroupName(group);
			serverHost.setStatus(status);
			String[] hostNameList = serverMonitorService.queryHostName(serverHost);
			res.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			res.setAttach(hostNameList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	
	/**
	 * 获取host查询条件列表
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/getHostNew", method={RequestMethod.POST,RequestMethod.GET}, produces = "application/json;charset=utf-8")
	public ResponsesDTO getHostNew(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "group", required = true) String group,@RequestParam(value = "status", required = true) String status) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			ServerHost serverHost = new ServerHost();
			serverHost.setGroupName(group);
			serverHost.setStatus(status);
			String[] hostNameList = serverMonitorService.queryHostNameNew(serverHost);
			res.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			res.setAttach(hostNameList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
	}
	

	/**
	 * 
	 * 跳转到报警列表页.
	 *
	 * @param dataType
	 * 				数据类型
	 * @param alarmLevel
	 * 				报警级别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmListView(@Param(value="content")String content, @Param(value="page")String page,@Param(value="status")String status, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/upm/serverMonitor-alarmlog");
			//下拉列表
		} else {
			model.setViewName("/upm/serverMonitor-alarmlogTable");
		}
		model.addObject("leftMenu", "server.serverMenu");
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		int pageSize = 15;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<ServerAlarmRecord> p = new Page<ServerAlarmRecord>(pageNo, pageSize);
		String groupName = "";
		String hostName = "";
		ServerAlarmRecord condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, ServerAlarmRecord.class);
			groupName = condition.getGroupName();
			hostName = condition.getHost();
			if(groupName!=null && groupName!=""){
				groupName=java.net.URLDecoder.decode(groupName,"UTF-8");
				condition.setGroupName(groupName);
			}
			if(hostName!=null && hostName!=""){
				hostName=java.net.URLDecoder.decode(hostName,"UTF-8");
				condition.setHost(hostName);
			}
			status = condition.getStatus();
		}else{
			condition = new ServerAlarmRecord();
			condition.setStatus(status);
		}
		ServerHost serverHost = new ServerHost();
		serverHost.setGroupName(groupName);
		serverHost.setStatus(status);
		String[] groupList = serverMonitorService.queryHostGroupNew(serverHost);
		String[] hostNameList = serverMonitorService.queryHostNameNew(serverHost);
		model.addObject("groupList", groupList);
		model.addObject("hostNameList", hostNameList);
		p.setConditions(condition);
		Page<ServerAlarmRecord> pageAlarm = serverMonitorService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		model.addObject("status", status);
		return model;
	}
}
