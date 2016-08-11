package com.gome.pricealarm.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.gome.pricealarm.common.util.SendMail;
import com.gome.pricealarm.dao.AlarmMapper;
import com.gome.pricealarm.domain.Alarm;
import com.gome.pricealarm.domain.PriceLog;
import com.gome.pricealarm.sql.PriceMonitorDao;

public class PriceAlarmQuartzJobBean {
	
	private static Logger logger = Logger.getLogger(PriceAlarmQuartzJobBean.class);
	
	private Properties props = new Properties();
	
	{
		
		try {
//			System.out.println("path:" + PriceAlarmQuartzJobBean.class.getClassLoader().getResource("mail.properties").getPath());
			InputStream in = PriceAlarmQuartzJobBean.class.getClassLoader().getResourceAsStream("mail.properties");
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Date date;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//收件人
	private String str_to = "";
	
	//主题
	private String str_title = "价格监控日志报警";
	
	//邮件内容
	private String str_content = "";

	@Resource
	private PriceMonitorDao priceMonitorDao;
	
	@Resource
	private AlarmMapper alarmMapper;
	
	protected void work() {
		logger.info("*************************************************************************************");
		//任务的具体实现定义在该方法中
		date = new Date();
		logger.info(df.format(date) + "-----定时任务开始执行...");
        long begin = date.getTime();
        PriceLog priceLog = new PriceLog();
        priceLog.setResult("0");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        priceLog.setTimeStr(df2.format(new Date()));
//        System.out.println("priceMonitorDao:" + priceMonitorDao);
        List<PriceLog> priceList = priceMonitorDao.selectPriceLogListFromSqlByConditions(priceLog);
        int i = 0;
        for (PriceLog log : priceList) {
        	Alarm alarm = new Alarm();
        	alarm.setLogId(log.getId());
        	alarm.setNode(log.getNode());
        	List<Alarm> alarmList = alarmMapper.selectAlarmRecordListByConditions(alarm);
        	if(alarmList != null && alarmList.size() == 0){
        		i++;
        		str_content = log.toString();
        		if(StringUtils.isNotEmpty(log.getNode())){
        			str_to = props.getProperty(log.getNode());
        			logger.info("node:" + log.getNode() + ";str_to:" + str_to);
        			logger.info("content:" + str_content);
        		}
        		if(StringUtils.isNotEmpty(str_to)){
        			SendMail.send(str_to, str_title, str_content);
            		alarm.setContent(str_content);
            		alarm.setSendTime(new Date());
            		alarmMapper.insertAlarmRecord(alarm);
        		}
        		
        	}
		}
        logger.info("查询到当天失败日志总共" + priceList.size() + "条.");
        logger.info("本次将发送报警信息" + i + "条.");
        date = new Date();
        logger.info(df.format(date) + "-----定时任务执行完毕...");
        long end = date.getTime();
        logger.info("本次定时任务共耗时" + (end - begin)/1000 + "秒.");
//        for (PriceLog priceLog2 : priceList) {
//			logger.info(priceLog2.toString());
//		}
        logger.info("*************************************************************************************");
	}

}
