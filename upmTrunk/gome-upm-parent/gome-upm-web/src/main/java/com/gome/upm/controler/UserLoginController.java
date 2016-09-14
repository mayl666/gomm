package com.gome.upm.controler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.gome.upm.common.login.LoginUtil;
import com.gome.upm.common.util.CookieUtil;
import com.gome.upm.common.util.ResponsesDTO;
import com.gome.upm.constants.ReturnCode;
import com.gome.upm.controler.base.BaseController;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.service.AlarmRecordService;

@Controller
//@RequestMapping(value = "/")
public class UserLoginController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	private CookieUtil cookieUtil=new CookieUtil();
	
	@Resource
	private AlarmRecordService alarmRecordService;
	
	/**
	 * 跳转到登录页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("/login");
		String userName = (String) request.getSession().getAttribute("userName");
		if(StringUtils.isNotEmpty(userName)){
			//直接跳转到主页
			model.setViewName("redirect:/index");
		}
    	return model;
	}
	
	/**
	 * 处理用户登录
	 * @param request
	 * @param response
	 * @param hsession
	 * @param content
	 * @return
	 * 2016年7月27日 上午11:28:03   caowei-ds1
	 */
	@ResponseBody
	@RequestMapping(value = "/toLogin", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String loginTime = sdf.format(new Date());
            hsession.setAttribute("firstLoginTime",loginTime);
			hsession.setAttribute("userName", loginName);
		} catch (Exception e) {
			logger.error("系统出现异常", e);
			res.setReturnCode(ReturnCode.ERROR_SERVER);
		}

		return res;
	}
	
	/**
	 * 跳转到首页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月27日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/index", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toIndexView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/index");
		//用户登录的情况下
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if(StringUtils.isNotEmpty(userName)){
			
			AlarmRecord alarmRecord = new AlarmRecord();
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
				List<AlarmRecord> oneTotalList = new ArrayList<AlarmRecord>();
				if(oneLevelList.size() > 5){
					for (int i=0; i < 5; i++) {
						oneTotalList.add(oneLevelList.get(i));
					}
					session.setAttribute("oneLevelList", oneTotalList);
				}else{					
					session.setAttribute("oneLevelList", oneLevelList);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String loginTime = sdf.format(new Date());
                //session.setAttribute("loginTime",loginTime);
                String content = "截止到"+loginTime+"，统一监控平台共收到一级报警信息<strong>"+oneTotalNum+"</strong>条，其中";
                if(methodNum > 0){
                	content += "方法监控:<strong>"+methodNum+"</strong>条，";
                }
                if(dbconnNum > 0){
                	content += "数据库连接与表空间监控&nbsp:&nbsp&nbsp<strong>"+dbconnNum+"</strong>条，";
                }
                if(networkNum > 0){
                	content += "网络监控&nbsp:&nbsp&nbsp<strong>"+networkNum+"</strong>条，";
                }
                if(urlNum > 0){
                	content += "URL存活监控&nbsp:&nbsp&nbsp<strong>"+urlNum+"</strong>条，";
                }
                if(portNum > 0){
                	content += "端口存活监控&nbsp:&nbsp&nbsp<strong>"+portNum+"</strong>条，";
                }
                if(processNum > 0){
                	content += "系统进程存活监控&nbsp:&nbsp&nbsp<strong>"+processNum+"</strong>条，";
                }
                if(financeNum > 0){
                	content += "金融系统监控&nbsp:&nbsp&nbsp<strong>"+financeNum+"</strong>条，";
                }
                if(serverNum > 0){
                	content += "服务器监控&nbsp:&nbsp&nbsp<strong>"+serverNum+"</strong>条，";
                }
                if(dragonNum > 0){
                	content += "DRAGON&nbsp:&nbsp&nbsp<strong>"+dragonNum+"</strong>条，";
                }
                if(omsNum > 0){
                	content += "OMS&nbsp:&nbsp&nbsp<strong>"+omsNum+"</strong>条，";
                }
                if(forwardNum > 0){
                	content += "正向单&nbsp:&nbsp&nbsp<strong>"+forwardNum+"</strong>条，";
                }
                
                if(content.indexOf("，") != -1){
                	content = content.substring(0, content.length()-1);
                }
                session.setAttribute("content", content);
			}
			logger.info("一级报警总数："+oneTotalNum+" ,其中： method:"+methodNum+" dbconn:"
						+dbconnNum+" network:"+networkNum+" url:"+urlNum+" port:"
						+portNum+" process:"+processNum+" finance:"+financeNum+" server:"
						+serverNum+" dragon:"+dragonNum+" oms:"+omsNum+" forward:"+forwardNum);
			//二级报警信息
			alarmRecord.setLevel(2);
			List<AlarmRecord> twoLevelList = alarmRecordService.findAlarmRecordListByConditions(alarmRecord);
			session.setAttribute("twoLevelTotal", twoLevelList.size());
			List<AlarmRecord> twoTotalList = new ArrayList<AlarmRecord>();
			if(twoLevelList.size() > 5){
				for (int i=0; i < 5; i++) {
					twoTotalList.add(twoLevelList.get(i));
				}
				session.setAttribute("twoLevelList", twoTotalList);
			}else{					
				session.setAttribute("twoLevelList", twoLevelList);
			}
			
			//三级报警信息
			alarmRecord.setLevel(3);
			List<AlarmRecord> threeLevelList = alarmRecordService.findAlarmRecordListByConditions(alarmRecord);
			session.setAttribute("threeLevelTotal", threeLevelList.size());
			List<AlarmRecord> threeTotalList = new ArrayList<AlarmRecord>();
			if(threeLevelList.size() > 5){
				for (int i=0; i < 5; i++) {
					threeTotalList.add(threeLevelList.get(i));
				}
				session.setAttribute("threeLevelList", threeTotalList);
			}else{					
				session.setAttribute("threeLevelList", threeLevelList);
			}
		}
	
		return model;
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 2016年7月27日 下午3:23:52   caowei-ds1
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

		try {

			request.getSession().invalidate();
		} catch (Exception e) {
			logger.info("系统出现异常", e);
			model.setViewName("error");
		}

		return "redirect:/";
	}

}
