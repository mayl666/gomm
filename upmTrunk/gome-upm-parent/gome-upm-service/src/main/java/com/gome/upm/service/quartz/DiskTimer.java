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
public class DiskTimer {
	private static final Logger logger = LoggerFactory.getLogger(DiskTimer.class);
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	@Resource(name = "alarmRangeService")
	AlarmRangeService alarmRangeService;
	
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void work() {
		Date dateD = new Date();
		logger.info(df.format(dateD) + "-----磁盘监控任务启动...");
		System.out.println(df.format(dateD) + "-----磁盘监控任务启动...");
		try {
			ServerHost serverHost = new ServerHost();
			serverHost.setStatus("0");
			serverHost.setKey_("vfs.fs.size[/");
			serverHost.setKey1_("pfree]");
			List<ServerHost> hostsList_D = serverMonitorService.queryServerTimeValue(serverHost);
			
			/**
			 * 查询cpu三级阈值
			 */
			AlarmRange alarmRange = new AlarmRange();
			alarmRange.setBusinessType("server");
			alarmRange.setType("磁盘使用率");
			AlarmRange range = alarmRangeService.findAlarmRangeByBusinessType(alarmRange);
			if(hostsList_D!=null && hostsList_D.size()>0){
				for (int i = 0; i < hostsList_D.size(); i++) {
					double value = hostsList_D.get(i).getValue();
					Date date = new Date(hostsList_D.get(i).getClock()*1000l);
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//使用率低于三级阈值，恢复正常范围
					if(value<Double.parseDouble(range.getValue3())){
						String date1 = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
						String contentCPU = "服务器组中的"+hostsList_D.get(i).getGroupName()+"服务器"+hostsList_D.get(i).getName()+"磁盘使用率降到了"+value+"%,已在正常范围！";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setHost(hostsList_D.get(i).getName());
						alarmRecord.setUpdateAlarmTime(date1);
						alarmRecord.setContent(contentCPU);
						alarmRecord.setKey_(hostsList_D.get(i).getKey_());
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(value));
						alarmRecord.setType("3");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}else if(value<Double.parseDouble(range.getValue2())){
						//磁盘当前值大于等于3级小于2级阈值，产生报警
						addAlarm(hostsList_D.get(i).getGroupName(), hostsList_D.get(i).getName(), String.valueOf(value), "3",df1.format(date),hostsList_D.get(i).getKey_());
					}else if(value<Double.parseDouble(range.getValue1())){
						//磁盘当前值大于等于2级小于1级阈值，产生报警
						addAlarm(hostsList_D.get(i).getGroupName(), hostsList_D.get(i).getName(), String.valueOf(value), "2",df1.format(date),hostsList_D.get(i).getKey_());
					}else{
						//磁盘当前值大于1级阈值，产生报警
						addAlarm(hostsList_D.get(i).getGroupName(), hostsList_D.get(i).getName(), String.valueOf(value), "1",df1.format(date),hostsList_D.get(i).getKey_());
					}
					
					ServerHost host = serverMonitorService.queryHostsBase(hostsList_D.get(i));
					if(host!=null){
						ServerHost items = serverMonitorService.queryItemsBase(hostsList_D.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList_D.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList_D.get(i));
						}
					}else{
						try {
							serverMonitorService.addHostsBase(hostsList_D.get(i));
						} catch (Exception e) {
							serverMonitorService.updateHostsBase(hostsList_D.get(i));
						}
					}
				}
			}
			Date date2 = new Date();
			logger.info(df.format(date2) + "-----磁盘监控任务结束...");
			System.out.println(df.format(date2) + "-----磁盘监控任务结束...");
			logger.info("-----磁盘监控任务，本次任务共耗时..."+((dateD.getTime()/1000)-(date2.getTime()/1000))+"秒");
			System.out.println("-----磁盘监控任务结束，本次任务共耗时..."+((dateD.getTime()/1000)-(date2.getTime()/1000))+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void addAlarm(String groupName,String host,String value,String level,String time,String key_){
		String contentCPU = "服务器组中的"+groupName+"服务器"+host+"磁盘使用率达到了"+value+"%,请关注！及时解决";
		ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
		alarmRecord.setGroupName(groupName);
		alarmRecord.setHost(host);
		alarmRecord.setLevel(level);
		alarmRecord.setAlarmTime(time);
		alarmRecord.setContent(contentCPU);
		alarmRecord.setKey_(key_);
		alarmRecord.setStatus("0");
		alarmRecord.setAlarmValue(String.valueOf(value));
		alarmRecord.setType("3");
		serverMonitorService.addAlarmRecord(alarmRecord);
	}
}
