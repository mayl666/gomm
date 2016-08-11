package com.gome.pricemonitor.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.login.LoginUtil;
import com.gome.pricemonitor.common.util.AppConfigUtil;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.controler.base.BaseController;
import com.gome.pricemonitor.domain.PriceLog;
import com.gome.pricemonitor.service.PriceMonitorService;

/**
 * 价格监控
 * 
 * @author zhangzhixiang-ds
 *
 */
@Controller
@RequestMapping(value = "/price/monitor")
public class PriceMonitorController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PriceMonitorController.class);

	@Resource
	private PriceMonitorService priceMonitorService;


	/**
	 * 价格监控登录
	 * 
	 * @param request
	 * @param response
	 * @param hsession
	 * @param content
	 * @param checked
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	public ResponsesDTO login(HttpServletRequest request, HttpServletResponse response, HttpSession hsession,
			@RequestParam(value = "content", required = true) String content) {
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		logger.info("content:" + content);
		try {

			JSONObject obj = JSON.parseObject(content);
			String loginName = obj.getString("userName");// 传入参数
			String passwd = obj.getString("passwd");
			boolean checkLogin = LoginUtil.checkLogin(loginName, passwd);
			if (!checkLogin) {
				res.setReturnCode(ReturnCode.ERROR_USER_PASSWORD);
				return res;
			}
		//	String startDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00";
			String endDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:59";
		//	request.getSession().setAttribute("startDate", startDate);
			request.getSession().setAttribute("endDate", endDate);
			hsession.setAttribute("userName", loginName);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		model.setViewName("priceMonitor/failLog");
		try {
			Page<PriceLog> page = priceMonitorService.queryAll(null, null, null, "0", null, null, null, null, null,
					null, 1, 10);
			model.addObject("page", page);
			//model.addObject("result", "0");
			//model.addObject("resultStr", "失败");
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	@RequestMapping(value = "/all")
	public ModelAndView allLog(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			String content) {
		logger.info("content:" + content);
		model.setViewName("priceMonitor/allLog");
		try {
			String skuNo = "";
			String type = "";
			String result = "";
			String beginTime = "";
			String endTime = "";
			String areaCode = "";
			String node = "";
			String action = "";
			String status = "";
			Integer pageNo = 1;
			if (!StringUtils.isEmpty(content)) {
				JSONObject json = JSON.parseObject(content);
				skuNo = json.getString("skuNo");
				type = json.getString("type");
				result = json.getString("result");
				beginTime = json.getString("beginTime");
				endTime = json.getString("endTime");
				areaCode = json.getString("areaCode");
				node = json.getString("node");
				action = json.getString("action");
				status = json.getString("status");
				pageNo = json.getInteger("pageNo");
			}

			int pageSize = AppConfigUtil.getIntValue("price.monitor.pageSize");// 分页大小
			Page<PriceLog> page = priceMonitorService.queryAll(null, skuNo, type, result, beginTime, endTime, areaCode,
					status, node, action, pageNo, pageSize);
			model.addObject("page", page);
			model.addObject("skuNo", skuNo);
			model.addObject("type", type);
			model.addObject("typeName",
					type == null ? "" : type + AppConfigUtil.getStringValue("price.type." + type + ".name"));
			model.addObject("result", result);
			model.addObject("beginTime", beginTime);
			model.addObject("endTime", endTime);
			model.addObject("areaCode", areaCode);
			model.addObject("node", node);
			model.addObject("action", action);
			model.addObject("status", status);
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}


	/**
	 * 查询详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * 价格类型
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, ModelAndView model, @RequestParam(value="type") String type, @RequestParam(value="id") String id,
			@RequestParam(value="beginTime") String beginTime, @RequestParam(value="endTime") String endTime) {
		logger.info("参数:" + "type:" + type + "|id:" + id+"|type:"+type+"|id:"+id);
		try {
			
			Page<PriceLog> page = priceMonitorService.queryAll(id, null, null, null, beginTime, endTime, null, null, null, null,
					1, 9999);
			String typePage = AppConfigUtil.getStringValue("price.type." + type + ".page");
			if(!StringUtils.isEmpty(typePage)){
				model.setViewName("priceMonitor/" + typePage);
			}else{
				model.setViewName("/error");
				return model;
			}
			model.addObject("page", page);
			model.addObject("list", JSON.toJSONString(page.getData()));
			//model.addObject("list", JSON.toJSONString(this.init()));
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}

	@RequestMapping(value = "/listTable")
	public ModelAndView listTable(HttpServletRequest request, HttpServletResponse response, ModelAndView model,
			String content) {
		logger.info("content:" + content);
		model.setViewName("priceMonitor/priceListTable");
		try {
			String skuNo = "";
			String type = "";
			String result = "";
			String beginTime = "";
			String endTime = "";
			String areaCode = "";
			String node = "";
			String action = "";
			String status = "";
			Integer pageNo = 1;
			if (!StringUtils.isEmpty(content)) {
				JSONObject json = JSON.parseObject(content);
				skuNo = json.getString("skuNo");
				type = json.getString("type");
				result = json.getString("result");
				beginTime = json.getString("beginTime");
				endTime = json.getString("endTime");
				areaCode = json.getString("areaCode");
				node = json.getString("node");
				action = json.getString("action");
				status = json.getString("status");
				pageNo = json.getInteger("pageNo");
			}

			int pageSize = AppConfigUtil.getIntValue("price.monitor.pageSize");// 分页大小
			Page<PriceLog> page = priceMonitorService.queryAll(null, skuNo, type, result, beginTime, endTime, areaCode,
					status, node, action, pageNo, pageSize);
			model.addObject("page", page);
			/*
			 * model.addObject("skuNo", skuNo); model.addObject("type", type);
			 * model.addObject("typeName", type==null ? "" :
			 * type+AppConfigUtil.getStringValue("price.type."+type+".name"));
			 * model.addObject("result", result); model.addObject("beginTime",
			 * beginTime); model.addObject("endTime", endTime);
			 * model.addObject("areaCode", areaCode); model.addObject("node",
			 * node); model.addObject("action", action);
			 * model.addObject("status", status);
			 */
		} catch (Exception e) {
			logger.error("error", e);
			model.setViewName("/error");
		}

		return model;

	}
	
	
	public List<PriceLog> init(){
		List<PriceLog> list = new ArrayList<PriceLog>();
		PriceLog p3 = new PriceLog();
		p3.setNode("Stedeliver");
		p3.setResult("0");
		//list.add(p3);
		PriceLog p4 = new PriceLog();
		p4.setNode("GCC");
		p4.setResult("1");
		//list.add(p4);
		PriceLog p5 = new PriceLog();
		p5.setNode("INF");
		p5.setResult("1");
		//list.add(p5);
		PriceLog p6 = new PriceLog();
		p6.setNode("OA");
		p6.setResult("1");
		//list.add(p6);
		PriceLog p7 = new PriceLog();
		p7.setNode("INF");
		p7.setResult("0");
		list.add(p7);
		PriceLog p8 = new PriceLog();
		p8.setNode("GCC");
		p8.setResult("1");
		list.add(p8);
		return list;
		
	}

}
