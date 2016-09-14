package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.upm.common.util.DateUtil;
import com.gome.upm.domain.AlarmRange;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.AlarmRangeService;
import com.gome.upm.service.ServerMonitorService;

/**
 *
 */
public class MemoryTimer {
	private static final Logger logger = LoggerFactory.getLogger(MemoryTimer.class);
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	@Resource(name = "alarmRangeService")
	AlarmRangeService alarmRangeService;
	
	
	private Date date;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void work() {
		date = new Date();
		logger.info(df.format(date) + "-----内存监控任务启动...");
		System.out.println(df.format(date) + "-----内存监控任务启动...");
		try {
			ServerHost serverHost = new ServerHost();
			serverHost.setStatus("0");
			serverHost.setKey_("used_memory");
			List<ServerHost> hostsList_M = serverMonitorService.queryServerTimeValue(serverHost);
			/**
			 * 查询cpu三级阈值
			 */
			AlarmRange alarmRange = new AlarmRange();
			alarmRange.setBusinessType("server");
			alarmRange.setType("内存");
			AlarmRange range = alarmRangeService.findAlarmRangeByBusinessType(alarmRange);
			if(hostsList_M!=null && hostsList_M.size()>0){
				for (int i = 0; i < hostsList_M.size(); i++) {
					double value = hostsList_M.get(i).getValue();
					Date date = new Date(hostsList_M.get(i).getClock()*1000l);
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//使用率低于三级阈值，恢复正常范围
					if(value<Double.parseDouble(range.getValue3())){
						String date1 = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String contentD = "服务器组中的"+hostsList_M.get(i).getGroupName()+"服务器"+hostsList_M.get(i).getName()+"内存使用率降到了"+value+"%,已在正常范围！";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(hostsList_M.get(i).getName());
						alarmRecord.setUpdateAlarmTime(date1);
						alarmRecord.setContent(contentD);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(value));
						alarmRecord.setType("2");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}else if(value<Double.parseDouble(range.getValue2())){
						//内存当前值大于等于3级小于2级阈值，产生报警
						addAlarm(hostsList_M.get(i).getGroupName(), hostsList_M.get(i).getName(), String.valueOf(value), "3",df1.format(date));
					}else if(value<Double.parseDouble(range.getValue1())){
						//内存当前值大于等于2级小于1级阈值，产生报警
						addAlarm(hostsList_M.get(i).getGroupName(), hostsList_M.get(i).getName(), String.valueOf(value), "2",df1.format(date));
					}else{
						//内存当前值大于1级阈值，产生报警
						addAlarm(hostsList_M.get(i).getGroupName(), hostsList_M.get(i).getName(), String.valueOf(value), "1",df1.format(date));
					}
					
					ServerHost host = serverMonitorService.queryHostsBase(hostsList_M.get(i));
					if(host!=null){
						ServerHost items = serverMonitorService.queryItemsBase(hostsList_M.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList_M.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList_M.get(i));
						}
					}else{
						try {
							serverMonitorService.addHostsBase(hostsList_M.get(i));
						} catch (Exception e) {
							serverMonitorService.updateHostsBase(hostsList_M.get(i));
						}
					}
				}
			}
			Date date2 = new Date();
			logger.info(df.format(date2) + "-----内存监控任务结束...");
			System.out.println(df.format(date2) + "-----内存监控任务结束...");
			logger.info("-----内存监控任务，本次任务共耗时..."+((date.getTime()/1000)-(date2.getTime()/1000))+"秒");
			System.out.println("-----内存监控任务结束，本次任务共耗时..."+((date.getTime()/1000)-(date2.getTime()/1000))+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void addAlarm(String groupName,String host,String value,String level,String time){
		String contentD = "服务器组中的"+groupName+"服务器"+host+"内存使用率达到了"+value+"%,请关注！及时解决";
		ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
		alarmRecord.setGroupName(groupName);
		alarmRecord.setHost(host);
		alarmRecord.setLevel(level);
		alarmRecord.setAlarmTime(time);
		alarmRecord.setContent(contentD);
		alarmRecord.setStatus("0");
		alarmRecord.setAlarmValue(String.valueOf(value));
		alarmRecord.setType("2");
		serverMonitorService.addAlarmRecord(alarmRecord);
	}
}
