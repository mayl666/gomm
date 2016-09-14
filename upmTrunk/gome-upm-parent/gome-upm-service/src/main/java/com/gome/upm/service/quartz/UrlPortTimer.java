package com.gome.upm.service.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gome.upm.domain.PortRecord;
import com.gome.upm.domain.UrlRecord;
import com.gome.upm.service.PortMonitorService;
import com.gome.upm.service.UrlMonitorService;
public class UrlPortTimer {
	private static final Logger logger = LoggerFactory.getLogger(UrlPortTimer.class);
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private UrlMonitorService urlMonitorService;
	@Autowired
	private PortMonitorService portMonitorService;
	
	public void work() {
		long current = new Date().getTime();
		Date start = new Date(current-2*24*60*60000);
		String startTime = df.format(start);
		System.out.println("*******************开始时间"+startTime);
		UrlRecord urlRecord = new UrlRecord();
		urlRecord.setStartTime(startTime);
		logger.info("*******************定时把历史记录加入到ES库里************************");
		List<UrlRecord> urlList =urlMonitorService.findUrlRecordList(urlRecord);
		PortRecord port = new PortRecord();
		port.setStartTime(startTime);
		List<PortRecord> portList =portMonitorService.findPortRecordList(port);
		System.out.println("定时删除url和端口访问记录开始");
		logger.info("*******************定时删除url和端口访问记录开始************************");
		int urlCount =urlMonitorService.deleteByTime(startTime);
		logger.info("*******************url 删除"+urlCount+"条************************");
		int portCount =portMonitorService.deleteByTime(startTime);
		logger.info("*******************端口  删除"+portCount+"条************************");
		urlMonitorService.add(urlList);
		portMonitorService.add(portList);
	}
}
