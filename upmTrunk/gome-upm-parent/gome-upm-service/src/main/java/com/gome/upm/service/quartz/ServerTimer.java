package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.upm.domain.ServerHost;
import com.gome.upm.service.ServerMonitorService;

/**
 *
 */
public class ServerTimer {
	private static final Logger logger = LoggerFactory.getLogger(ServerTimer.class);
	@Resource(name = "serverMonitorService")
	ServerMonitorService serverMonitorService;
	private Date date;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void work() {
		date = new Date();
		logger.info(df.format(date) + "-----CPU监控任务启动...");
		System.out.println(df.format(date) + "-----CPU监控任务启动...");
		try {
			ServerHost serverHost = new ServerHost();
			serverHost.setStatus("0");
			serverHost.setKey_("cpu_use_all");
			List<ServerHost> hostsList = serverMonitorService.queryServerTimeValue(serverHost);
			if(hostsList!=null && hostsList.size()>0){
				for (int i = 0; i < hostsList.size(); i++) {
					ServerHost host = serverMonitorService.queryHostsBase(hostsList.get(i));
					if(host!=null){
						serverMonitorService.updateHostsBase(hostsList.get(i));
						ServerHost items = serverMonitorService.queryItemsBase(hostsList.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList.get(i));
						}
					
					}else{
						serverMonitorService.addHostsBase(hostsList.get(i));
					}
				}
			}
			date = new Date();
			logger.info(df.format(date) + "-----CPU监控任务结束...");
			System.out.println(df.format(date) + "-----CPU监控任务结束...");
			logger.info(df.format(date) + "-----内存监控任务启动...");
			System.out.println(df.format(date) + "-----内存监控任务启动...");
			serverHost.setKey_("used_memory");
			List<ServerHost> hostsList_M = serverMonitorService.queryServerTimeValue(serverHost);
			if(hostsList_M!=null && hostsList_M.size()>0){
				for (int i = 0; i < hostsList_M.size(); i++) {
					ServerHost host = serverMonitorService.queryHostsBase(hostsList_M.get(i));
					if(host!=null){
						ServerHost items = serverMonitorService.queryItemsBase(hostsList_M.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList_M.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList_M.get(i));
						}
					}else{
						serverMonitorService.addHostsBase(hostsList_M.get(i));
					}
				}
			}
			date = new Date();
			logger.info(df.format(date) + "-----内存监控任务结束...");
			System.out.println(df.format(date) + "-----内存监控任务结束...");
			logger.info(df.format(date) + "-----磁盘监控任务启动...");
			System.out.println(df.format(date) + "-----磁盘监控任务启动...");
			serverHost.setKey_("vfs.fs.size[/");
			serverHost.setKey1_("pfree]");
			List<ServerHost> hostsList_D = serverMonitorService.queryServerTimeValue(serverHost);
			if(hostsList_D!=null && hostsList_D.size()>0){
				for (int i = 0; i < hostsList_D.size(); i++) {
					ServerHost host = serverMonitorService.queryHostsBase(hostsList_D.get(i));
					if(host!=null){
						ServerHost items = serverMonitorService.queryItemsBase(hostsList_D.get(i));
						if(items!=null){
							serverMonitorService.updateItemsBase(hostsList_D.get(i));
						}else{
							serverMonitorService.addItemsBase(hostsList_D.get(i));
						}
					}else{
						serverMonitorService.addHostsBase(hostsList_D.get(i));
					}
				}
			}
			date = new Date();
			logger.info(df.format(date) + "-----磁盘监控任务结束...");
			System.out.println(df.format(date) + "-----磁盘监控任务结束...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
