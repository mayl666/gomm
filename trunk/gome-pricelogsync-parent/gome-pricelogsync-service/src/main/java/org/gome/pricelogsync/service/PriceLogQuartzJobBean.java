package org.gome.pricelogsync.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gome.pricelogsync.dao.PriceLogDao;
import org.gome.pricelogsync.dao.Impl.PriceLogDaoImpl;
import org.gome.pricelogsync.domain.PriceLog;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class PriceLogQuartzJobBean extends QuartzJobBean {
	
	private static Logger log = LoggerFactory.getLogger(PriceLogQuartzJobBean.class);
	
	private Date date;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private PriceLogDao priceLogDao = new PriceLogDaoImpl();
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("*************************************************************************************");
		//任务的具体实现定义在该方法中
		date = new Date();
		log.info(df.format(date) + "-----定时任务开始执行...");
        long begin = date.getTime();
        List<PriceLog> logList = priceLogDao.selectPriceLogList();
        log.info("其中有效数据：" + logList.size() + "条.");
        priceLogDao.insertPriceLogList(logList);
        date = new Date();
        log.info(df.format(date) + "-----定时任务执行完毕...");
        long end = date.getTime();
        log.info("本次定时任务共耗时" + (end - begin)/1000 + "秒.");
        log.info("*************************************************************************************");
	}

}
