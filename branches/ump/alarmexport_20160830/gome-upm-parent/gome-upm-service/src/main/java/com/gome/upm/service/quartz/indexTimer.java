package com.gome.upm.service.quartz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gome.loom.facade.ISendSmsFacade;
import com.gome.loom.model.SmsModel;
import com.gome.loom.model.TpModel;
import com.gome.memberCore.lang.model.Result;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class indexTimer {

	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	/** loom短信接口 */
	@Resource
	protected ISendSmsFacade sendSmsFacade;
	public void work() {
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			for (int i = 0; i < hostList.size(); i++) {
				String hostid = hostList.get(i).get("hostid").toString();
				String host = (String) hostList.get(i).get("host");
				//agent.ping
				Double pinglastValue = ZabbixUtils.getLastValue(hostList.get(i), "agent.ping");
				if(pinglastValue!=null){
					//used_swap使用率达到30%
					if(pinglastValue==0 || pinglastValue==0.0 || pinglastValue==0.00){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentServer = "服务器组"+groupName+"中的"+host+"服务器宕机，请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setGroupName(groupName);
						alarmRecord.setHost(host);
						alarmRecord.setAlarmTime(date);
						alarmRecord.setContent(contentServer);
						alarmRecord.setStatus("0");
						alarmRecord.setAlarmValue("宕机");
						alarmRecord.setType("0");
						serverMonitorService.addAlarmRecord(alarmRecord);
						//判断当前是否已经发过短信，如果已经报过警，当前不报警
						ServerAlarmRecord alarmRecordQ = new ServerAlarmRecord();
						alarmRecordQ.setHost(host);
						alarmRecordQ.setStatus("0");
						alarmRecordQ.setType("0");
						List<ServerAlarmRecord> alarmRecordList = serverMonitorService.queryAlarmRecord(alarmRecordQ);
						if(alarmRecordList!=null && alarmRecordList.size()>0){
							//调用短信接口
							// TpModel的参数对应申请的BusinessName,模板Id 
							TpModel smsModel = new TpModel("cloud_isSystem_down","2003"); 
							String phone = AppConfigUtil.getStringValue("message.phone");
							smsModel.setPhone(phone); //发送的手机号 
							smsModel.setIntervalTime(0);//是否延迟发送如果延迟发送需要设置,单位:小时.（实时发送丌需要设置，默讣0）
							smsModel.putTempParams("ipAddress", host);
							smsModel.putTempParams("year", DateUtil.getYear());
							smsModel.putTempParams("month", DateUtil.getMonth());
							smsModel.putTempParams("day",DateUtil.getDay());
							
							smsModel.putTempParams("hour",DateUtil.get24Hour());
							smsModel.putTempParams("minute", DateUtil.getMinute());
							smsModel.putTempParams("seconds",DateUtil.getSecond());
							Result<SmsModel> result = sendSmsFacade.sendSms(smsModel);
							if(result.isSuccess()){//发送成功
								System.out.println(result);
							}else{//发送失败 } }
								System.out.println(result);
							}
						}
					}else{
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentServer = "服务器组"+groupName+"中的"+host+"服务器已恢复正常！";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(host);
						alarmRecord.setAlarmTime(date);
						alarmRecord.setContent(contentServer);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue("正常");
						alarmRecord.setType("0");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
