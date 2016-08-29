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
public class MemoryTimer {

	/*@Resource(name = "monitorGcache")
	Gcache monitorGcache;*/
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	public void work() {
		try {
			List<Map<String,Object>> hostList = ZabbixUtils.getHostList("0");
			for (int i = 0; i < hostList.size(); i++) {
				String hostid = hostList.get(i).get("hostid").toString();
				Double memorylastValue = ZabbixUtils.getLastValue(hostList.get(i), "vm.memory.size[pavailable]");
				if(memorylastValue!=null){
					String host = (String) hostList.get(i).get("host");
					//CPU使用率达到90%
					String memoryInitValue = AppConfigUtil.getStringValue("server.monitor.memory");
					if(memorylastValue>=Double.parseDouble(memoryInitValue)){
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentMe = "服务器组"+groupName+"中的"+host+"服务器的内存使用率已达到了"+memorylastValue+"%,请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setGroupName(groupName);
						alarmRecord.setHost(host);
						alarmRecord.setAlarmTime(date);
						alarmRecord.setContent(contentMe);
						alarmRecord.setStatus("0");
						alarmRecord.setAlarmValue(String.valueOf(memorylastValue));
						alarmRecord.setType("3");
						serverMonitorService.addAlarmRecord(alarmRecord);
					}else{
						String date = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String groupName = ZabbixUtils.getHostGroup(hostid);
						//调用产生报警记录接口
						String contentMe = "服务器组"+groupName+"中的"+host+"服务器的内存使用率已降到"+memorylastValue+"%,已在正常范围";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(host);
						alarmRecord.setUpdateAlarmTime(date);
						alarmRecord.setContent(contentMe);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(memorylastValue));
						alarmRecord.setType("3");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
