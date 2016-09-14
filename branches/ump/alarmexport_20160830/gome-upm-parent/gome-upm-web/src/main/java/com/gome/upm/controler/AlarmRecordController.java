package com.gome.upm.controler;

import java.util.Date;
import java.util.List;

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
	
}
