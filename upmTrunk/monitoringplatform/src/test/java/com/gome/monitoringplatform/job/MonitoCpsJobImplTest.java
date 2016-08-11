package com.gome.monitoringplatform.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoCpsDAO;
import com.gome.monitoringplatform.model.bo.MoCpsBO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;
import com.gome.monitoringplatform.slave1.dao.PayDAO;

public class MonitoCpsJobImplTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private PayDAO payDAO;
	@Autowired
	private MoCpsDAO moCpsDAO;
	public void setPayDAO(PayDAO payDAO) {
		this.payDAO = payDAO;
	}
	@Test
	public void testsdfsdf() {
		//没半小时刷新1次
		String runResult = "Cps半小时监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		try {
			Date endTime = getEndDate();
			Date startTime=new Date(endTime.getTime()-1000*60*30);
			//获取正式库数据库
			MoOrderRechargeBO monitoVO=new MoOrderRechargeBO();
			monitoVO.setStartTime(startTime);
			monitoVO.setEndTime(endTime);
			Integer count= payDAO.searchCpsCount(monitoVO);
			//保存数据
			MoCpsBO moCpsBO=new MoCpsBO();
			moCpsBO.setCount(count);
			moCpsBO.setStartTime(startTime);
			moCpsBO.setEndTime(endTime);
			moCpsDAO.saveMoCps(moCpsBO);
		} catch (ParseException e) {
		}
	}
	Date getEndDate() throws ParseException{
		SimpleDateFormat ww = new SimpleDateFormat("yyyy-MM-dd HH@mm:ss");
		Date now=new Date();
		String minutes="00";
		if(now.getMinutes()>30){
			minutes="30";
		}
		String nwo=ww.format(now);
		nwo=nwo.substring(0, nwo.indexOf("@"));
		return formatter.parse(nwo+":"+minutes+":00");
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat ww = new SimpleDateFormat("yyyy-MM-dd HH@mm:ss");
		Date now=new Date();
		String minutes="00";
		if(now.getMinutes()>30){
			minutes="30";
		}
		String nwo=ww.format(now);
		nwo=nwo.substring(0, nwo.indexOf("@"));
		Date endTime=formatter.parse(nwo+":"+minutes+":00");
		String dddddd=formatter.format(new Date(endTime.getTime()-1000*60*30));
		System.out.println(dddddd);
		System.out.println(nwo+":"+minutes+":00");
	}
}
