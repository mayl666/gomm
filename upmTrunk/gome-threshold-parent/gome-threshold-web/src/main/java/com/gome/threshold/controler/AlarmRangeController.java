package com.gome.threshold.controler;

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
import com.alibaba.fastjson.JSONObject;
import com.gome.threshold.common.Constant;
import com.gome.threshold.common.Page;
import com.gome.threshold.common.util.ResponsesDTO;
import com.gome.threshold.constants.ReturnCode;
import com.gome.threshold.controler.base.BaseController;
import com.gome.threshold.domain.AlarmRange;
import com.gome.threshold.service.AlarmRangeService;

/**
 * 报警记录控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年08月30日    wangxiaye    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/alarmRange")
public class AlarmRangeController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AlarmRangeController.class);

	@Resource
	private AlarmRangeService alarmRangeService;
	
	/**
	 * 
	 * 跳转到报警列表页.
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月19日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toAlarmListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		if(StringUtils.isEmpty(content)){
			model.setViewName("/alarmRange/alarmRangeList");
			//下拉列表业务类型
			List<AlarmRange> businessTypeList = alarmRangeService.findAllBusinessTypes();
			model.addObject("businessTypeList", businessTypeList);
			//下拉列表业务类型
			List<AlarmRange> typeList = alarmRangeService.findTypesByBusiness("");
			model.addObject("typeList", typeList);
		} else {
			model.setViewName("/alarmRange/alarmRangeTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<AlarmRange> p = new Page<AlarmRange>(pageNo, pageSize);
		
		AlarmRange condition = null;
		//通过数据库类型查找
		if(StringUtils.isNotEmpty(content)){
			condition = JSON.parseObject(content, AlarmRange.class);
		} else {
			condition = new AlarmRange();
		}
		p.setConditions(condition);
		Page<AlarmRange> pageAlarm = alarmRangeService.findAlarmRangeListByPage(p);
		model.addObject("page", pageAlarm);
		return model;
	}
	
	@RequestMapping(value="/getTypeByBusiness", method={RequestMethod.GET,RequestMethod.POST})
	public ResponsesDTO toAlarmListView(@Param(value="businessType")String businessType) throws Exception{
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		try{
			List<AlarmRange> typeList = alarmRangeService.findTypesByBusiness(businessType);
			res.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
			res.setAttach(typeList);
		}catch(Exception e){
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
			throw new Exception(e);
		}
		return res;
		
	}
	
	/**
	 * 更新值
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
			AlarmRange alarmRange = JSON.parseObject(content, AlarmRange.class);
			alarmRange.setUpdateTime(new Date());
			String userName = (String) request.getSession().getAttribute("userName");
			alarmRange.setUid(userName);
			alarmRangeService.editAlarmRange(alarmRange);
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return res;
	}
	
	/**
	 * 增加值域
	 * @param request
	 * @param response
	 * @param model
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add", method={RequestMethod.POST}, produces = "application/json;charset=utf-8")
	public ResponsesDTO add(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value = "content", required = true) String content){
		logger.info("content:"+content);
		
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String userName = (String) request.getSession().getAttribute("userName");
		try{
			JSONObject obj = JSON.parseObject(content);
			String level_one = obj.getString("level_one");
			if(StringUtils.isNotBlank(level_one)){
				AlarmRange alarmRange1 = getAlarmRange(1,obj,userName,level_one);
				if(alarmRange1!=null){
					alarmRangeService.insertAlarmRange(alarmRange1);
				}
			}
			String level_two = obj.getString("level_two");
			if(StringUtils.isNotBlank(level_two)){
				AlarmRange alarmRange2 = getAlarmRange(2,obj,userName,level_two);
				if(alarmRange2!=null){
					alarmRangeService.insertAlarmRange(alarmRange2);
				}
			}
			String level_three = obj.getString("level_three");
			if(StringUtils.isNotBlank(level_three)){
				AlarmRange alarmRange3 = getAlarmRange(3,obj,userName,level_three);
				if(alarmRange3!=null){
					alarmRangeService.insertAlarmRange(alarmRange3);
				}
			}
			
		}catch(Exception e){
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return res;
	}
	
	/**
	 * 根据主键删除
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO del(HttpServletRequest request, HttpServletResponse response,
					@RequestParam(value = "id", required = true) String id) {
		logger.info("id:" + id );
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		String useName = (String)request.getSession().getAttribute("userName");
		try{
			if(!"".equals(useName)){
				alarmRangeService.deleteAlarmRangeById(Integer.parseInt(id));
			}else{
				res.setAttach("请登录");
				res.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return res;
	}
	
	public AlarmRange getAlarmRange(int level,JSONObject obj,String userName,String value){
		AlarmRange alarmRange = new AlarmRange();
		String businessType = obj.getString("businessType");
		String type = obj.getString("type");
		alarmRange.setType(type);
		alarmRange.setBusinessType(businessType);
		alarmRange.setLevel(level);
		
		//校验相同模块,相同类型,相同level的阈值记录是否存在,如果存在则做更新.
		List<AlarmRange> alarmRangeList = alarmRangeService.findAlarmRangeListByConditions(alarmRange);
		if(alarmRangeList!=null&&alarmRangeList.size()>0){
			AlarmRange updateRange = alarmRangeList.get(0);
			updateRange.setValue(value);
			updateRange.setUid(userName);
			updateRange.setUpdateTime(new Date());
			alarmRangeService.editAlarmRange(updateRange);
			return null;
		}else{
			alarmRange.setUid(userName);
			alarmRange.setValue(value);
			alarmRange.setCreateTime(new Date());
			alarmRange.setUpdateTime(new Date());
			return alarmRange;
		}
	}
}
