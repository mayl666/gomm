package com.gome.alarmplatform.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gome.alarmplatform.common.util.ResponsesDTO;
import com.gome.alarmplatform.constants.ReturnCode;
import com.gome.alarmplatform.controller.base.BaseController;
import com.gome.alarmplatform.domain.Alarm;
import com.gome.alarmplatform.service.AlarmService;
import com.gome.alarmplatform.service.MailService;

/**
 * 报警控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月14日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/")
public class AlarmController extends BaseController {
	
	@Resource
	private MailService mailService;
	
	@Resource
	private AlarmService alarmService;

	/**
	 *
	 * 发送报警.
	 * url、port报警时需要传入id，其他监控不需要
	 *
	 * @param key
	 * 			监控点key
	 * @param subject
	 * 			主题
	 * @param content
	 * 			内容
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月14日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/alarm", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO sendAlarm(@Param(value="type")String type, @Param(value="mail")String mail, @Param(value="id")String id, @Param(value="content")String content, @Param(value="subject")String subject, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.STATUS_SUCCESS);
		try {
			if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(mail) && StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(subject)){
//				if("url".equals(type) || "port".equals(type)){
//					if(StringUtils.isEmpty(id)){
//						responses.setReturnCode(ReturnCode.PARAMS_IS_NULL);
//						return responses;
//					}
//				}
//				if(StringUtils.isEmpty(mail)){
//					mail = "itserver3@yolo24.com";
//				}
				mailService.sendMail(mail, subject, content);
				Alarm alarm = new Alarm();
				alarm.setType(type);
				alarm.setContent(content);
				Date date = new Date();
				alarm.setSendTime(date);
				if(StringUtils.isNotEmpty(id)){
					alarm.setPid(Long.parseLong(id));
				}
//				System.out.println(date.getTime());
				alarmService.addAlarmRecord(alarm);
			} else {
				responses.setReturnCode(ReturnCode.PARAMS_IS_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.STATUS_FAILURE);
		}
		return responses;
	}
	
}
