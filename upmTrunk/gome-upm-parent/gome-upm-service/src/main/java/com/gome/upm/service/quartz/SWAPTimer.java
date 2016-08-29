package com.gome.upm.service.quartz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gome.loom.facade.ISendSmsFacade;
import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class SWAPTimer {

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
				Double swaplastValue = ZabbixUtils.getLastValue(hostList.get(i), "used_swap");
				String host = (String) hostList.get(i).get("host");
				if(swaplastValue!=null){
					//used_swap使用率达到30%
					String swapInitValue = AppConfigUtil.getStringValue("server.monitor.swap");
					if(swaplastValue>=Double.parseDouble(swapInitValue)){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentSWAP = "服务器组"+groupName+"中的"+host+"服务器的SWAP使用率已达到了"+swaplastValue+"%,请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(host);
						alarmRecord.setGroupName(groupName);
						alarmRecord.setAlarmTime(date);
						alarmRecord.setContent(contentSWAP);
						alarmRecord.setStatus("0");
						alarmRecord.setAlarmValue(String.valueOf(swaplastValue));
						alarmRecord.setType("4");
						serverMonitorService.addAlarmRecord(alarmRecord);
					}else{
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentSWAP = "服务器组"+groupName+"中的"+host+"服务器的SWAP使用率已达到了"+swaplastValue+"%,请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(host);
						alarmRecord.setUpdateAlarmTime(date);
						alarmRecord.setContent(contentSWAP);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(swaplastValue));
						alarmRecord.setType("4");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
