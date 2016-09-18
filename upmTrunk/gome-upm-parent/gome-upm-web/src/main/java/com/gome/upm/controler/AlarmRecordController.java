package com.gome.upm.controler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.common.Constant;
import com.gome.upm.common.Page;
import com.gome.upm.common.util.ExcelUtil;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.controler.base.BaseController;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.domain.UrlMonitor;
import com.gome.upm.service.AlarmRecordService;
import com.gome.upm.service.quartz.DBConnectionAndASMAlarmBean;
import com.google.gson.Gson;

/**
 * 报警记录控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月26日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/alarm")
public class AlarmRecordController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AlarmRecordController.class);

	@Resource
	private AlarmRecordService alarmRecordService;
	
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
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/alarm/alarmList");
			
			//下拉列表
			List<AlarmRecord> alarmList = alarmRecordService.findAllTypes();
			model.addObject("alarmList", alarmList);
		} else {
			model.setViewName("/alarm/alarmTable");
		}
		
		model.addObject("leftMenu", "alarmMenu");
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<AlarmRecord> p = new Page<AlarmRecord>(pageNo, pageSize);
		
		AlarmRecord condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, AlarmRecord.class);
		} else {
			condition = new AlarmRecord();
		}
		p.setConditions(condition);
		Page<AlarmRecord> pageAlarm = alarmRecordService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		return model;
	}
	
	@RequestMapping(value="/businessList", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toBusinessListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/alarm/alarmBusinessList");
			
			//下拉列表
			List<AlarmRecord> alarmList = alarmRecordService.findAllBusinessTypes();
			model.addObject("alarmList", alarmList);
		} else {
			model.setViewName("/alarm/alarmBusinessTable");
		}
		
		model.addObject("leftMenu", "business.alarmBusinessMenu");
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<AlarmRecord> p = new Page<AlarmRecord>(pageNo, pageSize);
		
		AlarmRecord condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, AlarmRecord.class);
		} else {
			condition = new AlarmRecord();
		}
		if(StringUtils.isEmpty(condition.getType())){
			condition.setType("business");
		}
		p.setConditions(condition);
		Page<AlarmRecord> pageAlarm = alarmRecordService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		return model;
	}
	
	/**
	 * 
	 * 跳转到报警详情页.
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
	 */
	@RequestMapping(value="/detail", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmDetailView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/alarm/detail");
		
		model.addObject("leftMenu", "alarmMenu");
		
		long id2 = 0;
		if(StringUtils.isNotEmpty(id)){
			id2 = Long.parseLong(id);
		} else {
			return model;
		}
		
		AlarmRecord alarm = alarmRecordService.findAlarmRecordById(id2);
		model.addObject("alarm", alarm);
		return model;
	}
	
	
	@RequestMapping(value="/detailBusiness", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmBusinessDetailView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/alarm/detail");
		
		model.addObject("leftMenu", "business.alarmBusinessMenu");
		
		long id2 = 0;
		if(StringUtils.isNotEmpty(id)){
			id2 = Long.parseLong(id);
		} else {
			return model;
		}
		
		AlarmRecord alarm = alarmRecordService.findAlarmRecordById(id2);
		model.addObject("alarm", alarm);
		return model;
	}
	
	/**
	 * 更新状态
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO update(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "content", required = true) String content){
		logger.info("content:"+content);
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			AlarmRecord alarmRecord = null;
			alarmRecord = JSON.parseObject(content, AlarmRecord.class);
			alarmRecordService.editAlarmRecord(alarmRecord);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}

		return res;

	}
	
	/**
	 * 
	 * 记录导出.
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
	 */
	@RequestMapping(value="/exportExcel", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView exportExcel(@Param(value="conditions")String conditions, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		AlarmRecord alarmRecord = null;
		if(StringUtils.isNotEmpty(conditions)){
			alarmRecord = JSON.parseObject(conditions, AlarmRecord.class);
		}else{
			alarmRecord = new AlarmRecord();
		}
		List<AlarmRecord> alarmList = alarmRecordService.findAlarmRecordListByConditions(alarmRecord);
		int num = 0;
		if(alarmList != null && alarmList.size() > 0){
			for (AlarmRecord record : alarmList) {
				num++;
				record.setNum(num);
			}
		}
		String[] title ={"序号","报警时间","类型","报警内容","报警级别","当前状态"};
        String[] header = {"num","sendTimeStr","typeStr","content","levelStr","statusStr"};
		String fileName = "报警记录.xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(alarmList);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		return null;
	}
	
	@RequestMapping(value="/toList", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView formIndexToAlarmListView(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
								@RequestParam(value="level",required=true) Integer level){
		model.setViewName("/alarm/alarmList");
		
		//下拉列表
		List<AlarmRecord> alarmList = alarmRecordService.findAllTypes();
		model.addObject("alarmList", alarmList);
		
		model.addObject("leftMenu", "alarmMenu");
		int pageNo = 1;
		Page<AlarmRecord> p = new Page<AlarmRecord>(pageNo, Constant.PAGE_SIZE);
		
		AlarmRecord condition = new AlarmRecord();
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", true);
		//通过数据库类型查找
		if(level == 1){
			condition.setLevel(1);
			model.addObject("select","<select id='level' class='form-control'  style='width: 180px;'> "
    					      	+"<option value=''>全部</option>"
    					      	+"<option value='1' selected='selected'>一级</option>"
    					      	+"<option value='2'>二级</option>"
    					      	+"<option value='3'>三级</option>"
    					      	+"</select>'");
			map.put("level", 1);
		}else if(level == 2){
			condition.setLevel(2);
			model.addObject("select","<select id='level' class='form-control'  style='width: 180px;'> "
			      	+"<option value=''>全部</option>"
			      	+"<option value='1'>一级</option>"
			      	+"<option value='2' selected='selected'>二级</option>"
			      	+"<option value='3'>三级</option>"
			      	+"</select>'");
			map.put("level", 2);
		}else if(level == 3){
			condition.setLevel(3);
			model.addObject("select","<select id='level' class='form-control'  style='width: 180px;'> "
			      	+"<option value=''>全部</option>"
			      	+"<option value='1'>一级</option>"
			      	+"<option value='2'>二级</option>"
			      	+"<option value='3' selected='selected'>三级</option>"
			      	+"</select>'");
			map.put("level", 3);
		}
		p.setConditions(condition);
		Page<AlarmRecord> pageAlarm = alarmRecordService.findAlarmRecordListByPage(p);
		model.addObject("page", pageAlarm);
		model.addObject("chance",1);
		model.addObject("search",gson.toJson(map));
		return model;
	}
	
	/**
	 * 用户登录后每五分钟查询一次,消息提示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOneAlarmList",method={RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf-8")
	public String checkMessage(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		HttpSession session = request.getSession();
		String message = (String) session.getAttribute("message");
		String information = "";
		String time = (String) session.getAttribute("firstLoginTime");
		if(StringUtils.isNotEmpty(time)){
			//用户登录期间，没有失效
			AlarmRecord alarmRecord = new AlarmRecord();
			/*alarmRecord.setStartTime(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String end = sdf.format(new Date());
			alarmRecord.setEndTime(end);*/
			alarmRecord.setLevel(1);
			List<AlarmRecord> oneLevelList = alarmRecordService.findAlarmRecordListByConditions(alarmRecord);
			//方法 method
			int methodNum = 0;
			//dbconn 
			int dbconnNum = 0;
			//network
			int networkNum = 0;
			//url
			int urlNum = 0;
			//port
			int portNum = 0;
			//process
			int processNum = 0;
			//finance
			int financeNum = 0;
			//server
			int serverNum = 0;
			//dragon
			int dragonNum = 0;
			//oms
			int omsNum = 0;
			//forward
			int forwardNum = 0;
			//一级报警总记录数
			int oneTotalNum = 0;
			if(oneLevelList.size() > 0){
				if(StringUtils.isEmpty(message)){
					session.setAttribute("message", "message");
				}
				String informa = (String) session.getAttribute("information");
				if(StringUtils.isNotEmpty(informa)){
					session.removeAttribute(informa);
				}
				oneTotalNum = oneLevelList.size();
				for (AlarmRecord record : oneLevelList) {
					String type = record.getType();
					if("method".equals(type)){
						methodNum++;
					}else if("dbconn".equals(type)){
						dbconnNum++;
					}else if ("network".equals(type)) {
						networkNum++;
					}else if ("url".equals(type)) {
						urlNum++;
					}else if ("port".equals(type)) {
						portNum++;
					}else if ("process".equals(type)) {
						processNum++;
					}else if ("finance".equals(type)) {
						financeNum++;
					}else if("server".equals(type)){
						serverNum ++;
					}else if("dragon".equals(type)){
						dragonNum ++;
					}else if("oms".equals(type)){
						omsNum ++;
					}else if("forward".equals(type)){
						forwardNum ++;
					}
				}
				
				session.setAttribute("oneTotalNum", oneTotalNum);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(new Date());
				information = "截止到"+currentTime+"，统一监控平台共收到一级报警信息<strong>"+oneTotalNum+"</strong>条，其中";
                if(methodNum > 0){
                	information += "方法监控:<strong>"+methodNum+"</strong>条，";
                }
                if(dbconnNum > 0){
                	information += "数据库连接与表空间监控&nbsp:&nbsp&nbsp<strong>"+dbconnNum+"</strong>条，";
                }
                if(networkNum > 0){
                	information += "网络监控&nbsp:&nbsp&nbsp<strong>"+networkNum+"</strong>条，";
                }
                if(urlNum > 0){
                	information += "URL存活监控&nbsp:&nbsp&nbsp<strong>"+urlNum+"</strong>条，";
                }
                if(portNum > 0){
                	information += "端口存活监控&nbsp:&nbsp&nbsp<strong>"+portNum+"</strong>条，";
                }
                if(processNum > 0){
                	information += "系统进程存活监控&nbsp:&nbsp&nbsp<strong>"+processNum+"</strong>条，";
                }
                if(financeNum > 0){
                	information += "金融系统监控&nbsp:&nbsp&nbsp<strong>"+financeNum+"</strong>条，";
                }
                if(serverNum > 0){
                	information += "服务器监控&nbsp:&nbsp&nbsp<strong>"+serverNum+"</strong>条，";
                }
                if(dragonNum > 0){
                	information += "DRAGON&nbsp:&nbsp&nbsp<strong>"+dragonNum+"</strong>条，";
                }
                if(omsNum > 0){
                	information += "OMS&nbsp:&nbsp&nbsp<strong>"+omsNum+"</strong>条，";
                }
                if(forwardNum > 0){
                	information += "正向单&nbsp:&nbsp&nbsp<strong>"+forwardNum+"</strong>条，";
                }
                
                if(information.indexOf("，") != -1){
                	information = information.substring(0, information.length()-1);
                }
                information = information + "&nbsp请<a herf='javascript:void(0);' onclick='toAlarmListOne()'>查看报警记录</a>进行处理";
                session.setAttribute("information", information);
                model.setViewName("/common/flat-alarm");
                logger.info("一级报警信息："+information);
			}
		}
		
		return information;
	}
	
}
