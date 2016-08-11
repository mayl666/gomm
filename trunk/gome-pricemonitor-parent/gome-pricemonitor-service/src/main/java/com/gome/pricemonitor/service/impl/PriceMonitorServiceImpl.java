package com.gome.pricemonitor.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.DateUtils;
import com.gome.pricemonitor.dao.PriceMonitorDao;
import com.gome.pricemonitor.dao.impl.PriceMonitorDaoImpl;
import com.gome.pricemonitor.domain.PriceLog;
import com.gome.pricemonitor.service.PriceMonitorService;
import com.gome.pricemonitor.service.utils.PriceLogConvertUtils;

@Service("priceMonitorService")
public class PriceMonitorServiceImpl implements PriceMonitorService {
	
	private static final Logger logger = LoggerFactory.getLogger(PriceMonitorServiceImpl.class);
	
	private DateFormat df;
	
	@Resource
	private PriceMonitorDao priceMonitorDao;

	/*
	@Override
	public Page<PriceLog> selectPriceLogListByConditionsPage(Page<PriceLog> page) {
		PriceMonitorDao priceMonitorDao = new PriceMonitorDaoImpl();
		long startTime = page.getConditions().getStartTimeStamp();
		long endTime = page.getConditions().getEndTimeStamp();
		df = new SimpleDateFormat("yyyy-MM-dd");
		String startTimeStr = df.format(startTime);
		String currentTimeStr = df.format(new Date());
		List<PriceLog> priceLogList;
		int totalResult;
		if((startTime == 0 && endTime == 0) || (currentTimeStr.equals(startTimeStr))){
			//当天数据从ES查
			priceLogList = priceMonitorDao.selectPriceLogListFromEsByConditionsPage(page);
			totalResult = priceMonitorDao.selectTotalResultFromEsByConditions(page.getConditions());
		} else {
			page.getConditions().setTimeStr(startTimeStr);
			//历史数据从数据库查
			priceLogList = priceMonitorDao.selectPriceLogListFromSqlByConditionsPage(page);
			totalResult = priceMonitorDao.selectTotalResultFromSqlByConditions(page.getConditions());
		}
		return new Page<PriceLog>(page.getPageNo(), page.getPageSize(), totalResult,
				PriceLogConvertUtils.propertyConvert(priceLogList), page.getConditions());
	}
	*/
	
	@Override
	public Page<PriceLog> selectPriceLogListByConditionsPage(Page<PriceLog> page) {
		PriceMonitorDao priceMonitorDao = new PriceMonitorDaoImpl();
		long startTime = page.getConditions().getStartTimeStamp();
		long endTime = page.getConditions().getEndTimeStamp();
		df = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		List<PriceLog> priceLogList;
		int totalResult;
		if(startTime == 0 && endTime == 0){
			//不选日期，默认查当天
			date = df.format(new Date());
		} else {
			date = df.format(startTime);
		}
		page.getConditions().setTimeStr(date);
		priceLogList = priceMonitorDao.selectPriceLogListFromSqlByConditionsPage(page);
		totalResult = priceMonitorDao.selectTotalResultFromSqlByConditions(page.getConditions());
		return new Page<PriceLog>(page.getPageNo(), page.getPageSize(), totalResult,
				PriceLogConvertUtils.propertyConvert(priceLogList), page.getConditions());
	}

	@Override
	public Page<PriceLog> queryAll(String id, String skuNo, String type, String result, String beginTime, String endTime,
			String areaCode, String status, String node, String action, Integer pageNo, Integer pageSize) {

		Page<PriceLog> page = new Page<PriceLog>(pageNo, pageSize);
		//page.setData(this.init());
		//page.setTotalResult(3);
		PriceLog priceLog = new PriceLog();
		priceLog.setAction(action);
		priceLog.setAreaCode(areaCode);
		priceLog.setNode(node);
		priceLog.setResult(result);
		priceLog.setSkuNo(skuNo);
		priceLog.setStartTime(beginTime);
		priceLog.setEndTime(endTime);
		if(!StringUtils.isEmpty(beginTime)){
			System.out.println(DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss").getTime());
			priceLog.setStartTimeStamp(DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss").getTime());
		}else{
			priceLog.setStartTimeStamp(0l);
		}
		
		if(!StringUtils.isEmpty(endTime)){
			priceLog.setEndTimeStamp(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss").getTime());
		}else{
			priceLog.setEndTimeStamp(0l);
		}
		
		
		priceLog.setStatus(status);
		priceLog.setType(type);
		priceLog.setId(id);
		page.setConditions(priceLog);
		
		page = this.selectPriceLogListByConditionsPage(page);
		return page;
	}
	
	public List<PriceLog> init(){
		List<PriceLog> list = new ArrayList<PriceLog>();
		PriceLog  log = new PriceLog();
			log.setId("11111111111111111");
			log.setAction("SEND");
			log.setActionStr("接收");
			log.setAreaCode("111111111111");
			log.setNode("GCC");
			log.setResult("TRUE");
			log.setResultStr("成功");
			log.setSkuNo("22222222222222");
			log.setStatus("START");
			log.setStatusStr("开始");
			log.setType("10");
			log.setTypeStr("GCC");
		list.add(log);
			PriceLog  log2 = new PriceLog();
			log2.setId("11111111111111112");
			log2.setAction("SEND");
			log2.setActionStr("接收");
			log2.setAreaCode("111111111111");
			log2.setNode("GCC");
			log2.setResult("TRUE");
			log2.setResultStr("成功");
			log2.setSkuNo("22222222222222");
			log2.setStatus("START");
			log2.setStatusStr("开始");
			log2.setType("10");
			log2.setTypeStr("GCC");
		
			PriceLog  log3 = new PriceLog();
			log3.setId("11111111111111113");
			log3.setAction("SEND");
			log3.setActionStr("接收");
			log3.setAreaCode("111111111111");
			log3.setNode("GCC");
			log3.setResult("TRUE");
			log3.setResultStr("成功");
			log3.setSkuNo("22222222222222");
			log3.setStatus("START");
			log3.setStatusStr("开始");
			log3.setType("10");
			log3.setTypeStr("GCC");
			
			PriceLog  log4 = new PriceLog();
			log4.setId("11111111111111114");
			log4.setAction("SEND");
			log4.setActionStr("接收");
			log4.setAreaCode("111111111111");
			log4.setNode("Rhino");
			log4.setResult("TRUE");
			log4.setResultStr("成功");
			log4.setSkuNo("22222222222222");
			log4.setStatus("START");
			log4.setStatusStr("开始");
			log4.setType("10");
			log4.setTypeStr("GCC");
			list.add(log2);list.add(log3);list.add(log4);
		
		return list;
		
	}

}
