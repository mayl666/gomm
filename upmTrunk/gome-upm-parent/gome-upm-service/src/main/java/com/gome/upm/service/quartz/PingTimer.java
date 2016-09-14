package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.loom.facade.ISendSmsFacade;
import com.gome.upm.domain.ServerAlarmRecord;
import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.ServerMonitorService;

/**
 *
 */
public class PingTimer {
	private static final Logger logger = LoggerFactory.getLogger(PingTimer.class);
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	/** loom短信接口 */
	@Resource
	protected ISendSmsFacade sendSmsFacade;
	private Date date;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void work() {
		date = new Date();
		logger.info(df.format(date) + "-----PING监控任务启动...");
		System.out.println(df.format(date) + "-----PING监控任务启动...");
		try {
			ServerHost serverHost = new ServerHost();
			serverHost.setStatus("0");
			serverHost.setKey_("agent.ping");
			List<ServerHost> hostsList_P = serverMonitorService.queryServerPingValue(serverHost);
			if(hostsList_P!=null && hostsList_P.size()>0){
				for (int i = 0; i < hostsList_P.size(); i++) {
					double value = hostsList_P.get(i).getValue();
					//服务器宕机
					if(value==0 || value==0.0|| value==0.00){
						Date date = new Date(hostsList_P.get(i).getClock()*1000l);
						SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String contentCPU = "服务器组中的"+hostsList_P.get(i).getGroupName()+"服务器"+hostsList_P.get(i).getName()+"服务器宕机了,请关注！及时解决";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setGroupName(hostsList_P.get(i).getGroupName());
						alarmRecord.setHost(hostsList_P.get(i).getName());
						alarmRecord.setLevel("1");
						alarmRecord.setAlarmTime(df1.format(date));
						alarmRecord.setContent(contentCPU);
						alarmRecord.setStatus("0");
						alarmRecord.setAlarmValue(String.valueOf(value));
						alarmRecord.setType("5");
						serverMonitorService.addAlarmRecord(alarmRecord);
						//调用发送手机短信接口
						
					}else{
						String contentCPU = "服务器组中的"+hostsList_P.get(i).getGroupName()+"服务器"+hostsList_P.get(i).getName()+"服务器恢复正常啦";
						ServerAlarmRecord alarmRecord = new ServerAlarmRecord();
						alarmRecord.setGroupName(hostsList_P.get(i).getGroupName());
						alarmRecord.setHost(hostsList_P.get(i).getName());
						alarmRecord.setLevel("1");
						alarmRecord.setUpdateAlarmTime(df.format(date));
						alarmRecord.setContent(contentCPU);
						alarmRecord.setStatus("1");
						alarmRecord.setAlarmValue(String.valueOf(value));
						alarmRecord.setType("5");
						serverMonitorService.updateAlarmRecord(alarmRecord);
					}
					ServerHost host = serverMonitorService.queryHostsBase(hostsList_P.get(i));
					if(host!=null){
						ServerHost items = serverMonitorService.queryItemsBase(hostsList_P.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList_P.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList_P.get(i));
						}
					}else{
						try {
							serverMonitorService.addHostsBase(hostsList_P.get(i));
						} catch (Exception e) {
							serverMonitorService.updateHostsBase(hostsList_P.get(i));
						}
					}
				}
			}
			Date date2 = new Date();
			logger.info(df.format(date2) + "-----PING监控任务结束...");
			System.out.println(df.format(date2) + "-----PING监控任务结束...");
			logger.info("-----PING监控任务，本次任务共耗时..."+((date.getTime()/1000)-(date2.getTime()/1000))+"秒");
			System.out.println("-----PING监控任务结束，本次任务共耗时..."+((date.getTime()/1000)-(date2.getTime()/1000))+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
