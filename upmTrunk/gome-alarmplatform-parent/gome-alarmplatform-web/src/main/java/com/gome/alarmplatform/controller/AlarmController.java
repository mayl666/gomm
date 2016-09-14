package com.gome.alarmplatform.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gome.alarmplatform.common.util.AppConfigUtil;
import com.gome.alarmplatform.common.util.ResponsesDTO;
import com.gome.alarmplatform.constants.ReturnCode;
import com.gome.alarmplatform.controller.base.BaseController;
import com.gome.alarmplatform.domain.Alarm;
import com.gome.alarmplatform.service.AlarmService;
import com.gome.alarmplatform.service.MailService;
import com.gome.loom.facade.ISendSmsFacade;
import com.gome.loom.model.SmsModel;
import com.gome.loom.model.TpModel;
import com.gome.memberCore.lang.model.Result;

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
	
	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
	
	@Resource
	private MailService mailService;
	
	@Resource
	private AlarmService alarmService;
	
	@Resource 
	private ISendSmsFacade sendSmsFacade;

	/**
	 *
	 * 发送邮件.
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
	public ResponsesDTO sendMail(@Param(value="type")String type, @Param(value="mail")String mail, @Param(value="id")String id, @Param(value="level")String level, @Param(value="content")String content, @Param(value="subject")String subject, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.STATUS_SUCCESS);
		try {
			if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(subject)){
				logger.info("RemoteIP:{}; method: {}", request.getRemoteAddr(), request.getMethod());
				logger.info("type:" + type + ";level:" + level + ";id:" + id + ";content:" + content + ";subject:" + subject);
				//根据监控类型的不同，发送邮件给不同的收件人
				mail = AppConfigUtil.getStringValue(type);
				logger.info("mail:" + mail);
				if(StringUtils.isNotEmpty(mail)){
					mailService.sendMail(mail, subject, content);
					Alarm alarm = new Alarm();
					alarm.setType(type);
					alarm.setContent(content);
					Date date = new Date();
					alarm.setSendTime(date);
					if(StringUtils.isNotEmpty(id)){
						alarm.setPid(Long.parseLong(id));
					}
					if(StringUtils.isNotEmpty(level)){
						alarm.setLevel(Integer.parseInt(level));
					}else{
						alarm.setLevel(3);
					}
//					System.out.println(date.getTime());
					alarmService.addAlarmRecord(alarm);
				}else{
					logger.info("type错误,未找到匹配的邮件地址！");
				}
				
			} else {
				logger.info("参数中有空值！");
				responses.setReturnCode(ReturnCode.PARAMS_IS_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.STATUS_FAILURE);
		}
		return responses;
	}
	
	/**
	 *
	 * 发送短信.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月01日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/sms", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO sendSms(@Param(value="type")String type, @Param(value="businessName")String businessName, @Param(value="templateId")String templateId, @Param(value="category")String category, @Param(value="data")String data, @Param(value="datetime")String datetime, @Param(value="desc")String desc, @Param(value="level")String level, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.STATUS_SUCCESS);
		try {
			if(StringUtils.isNotEmpty(businessName) && StringUtils.isNotEmpty(templateId) && StringUtils.isNotEmpty(type)){
				logger.info("RemoteIP:{}; method: {}", request.getRemoteAddr(), request.getMethod());
				logger.info("type:" + type + ";businessName:" + businessName + ";templateId:" + templateId + ";category:" + category + ";data:" + data + ";datetime:" + datetime + ";desc:" + desc + ";level:" + level);
				
				//根据监控类型的不同，发送短信给不同的收件人
				String phone = AppConfigUtil.getStringValue(type);
				logger.info("phone:" + phone);
				 
				TpModel smsModel = new TpModel(businessName,templateId);// TpModel的参数对应申请的BusinessName,模板Id 
//				smsModel.setIntervalTime(1);//是否延迟发送如果延迟发送需要设置,单位:小时.（实时发送不需要设置，默讣0） 
				smsModel.putTempParams("category", category);//模板要替换的参数,对接短信模板里的#code#（有多个参数，调用此方法多次）
				smsModel.putTempParams("data", data);
				smsModel.putTempParams("datetime", datetime);
				smsModel.putTempParams("desc", desc);
				smsModel.putTempParams("level", level);
				if(StringUtils.isNotEmpty(phone)){
					String[] phones = phone.split(";");
					for (String pho : phones) {
						smsModel.setPhone(pho); //发送的手机号 
						//dubbo调用发送短信(result一定会返回，不用做null判断)
						logger.info("ISendSmsFacade:" + sendSmsFacade);
						Result<SmsModel> result = sendSmsFacade.sendSms(smsModel);
						if(result.isSuccess()){
							//发送成功
							logger.info("message:" + result.getMessage() + ";code:" + result.getCode());
						}else{
							//发送失败 
							logger.info("message:" + result.getMessage() + ";code:" + result.getCode());
						}
					}
				}else{
					logger.info("phone错误,未找到匹配的手机号！");
				}
				
			} else {
				logger.info("参数存在空值！");
				responses.setReturnCode(ReturnCode.PARAMS_IS_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.STATUS_FAILURE);
		}
		return responses;
	}
	
}
