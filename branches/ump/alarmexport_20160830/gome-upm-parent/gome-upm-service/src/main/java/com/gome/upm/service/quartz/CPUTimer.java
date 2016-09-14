package com.gome.upm.service.quartz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gome.upm.common.util.AppConfigUtil;
import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.service.ServerMonitorService;
import com.gome.upm.service.util.ZabbixUtils;

/**
 *
 */
public class CPUTimer {

	/*@Resource(name = "monitorGcache")
	Gcache monitorGcache;*/
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	public void work() {
		try {
			List<Map<String,Object>> hostListCPU = ZabbixUtils.getHostList("0");
			for (int i = 0; i < hostListCPU.size(); i++) {
				String hostid = hostListCPU.get(i).get("hostid").toString();
				Double cpulastValue = ZabbixUtils.getLastValue(hostListCPU.get(i), "cpu_use_all");
				if(cpulastValue!=null){
					String host = (String) hostListCPU.get(i).get("host");
					//CPU使用率达到75%
					String cpuInitValue = AppConfigUtil.getStringValue("server.monitor.cpu");
					if(cpulastValue>=Double.parseDouble(cpuInitValue)){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentCPU = "服务器组"+groupName+"中的"+host+"服务器的CPU使用率已达到了"+cpulastValue+"%,请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setGroupName(groupName);
						alarmRecord.setHost(host);
						alarmRecord.setAlarmTime(date);
						alarmRecord.setContent(contentCPU);
						alarmRecord.setStatus("0");
						alarmRecord.setAlarmValue(String.valueOf(cpulastValue));
						alarmRecord.setType("1");
						serverMonitorService.addAlarmRecord(alarmRecord);
					}else{
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentCPU = "服务器组"+groupName+"中的"+host+"服务器的CPU使用率已降到"+cpulastValue+"%,已在正常范围！";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(host);
						alarmRecord.setUpdateAlarmTime(date);
						alarmRecord.setContent(contentCPU);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(cpulastValue));
						alarmRecord.setType("1");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
