package com.gome.monitoringplatform.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoPayRatioDAO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;
import com.gome.monitoringplatform.model.bo.MoPayRatioBO;
import com.gome.monitoringplatform.slave1.dao.PayDAO;

public class MonitoPayRatioJobImplTest extends BaseTest{
	class ReObj {
		private Date endTime;
		private Date startTime;
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
	}
	@Autowired
	private PayDAO payDAO;
	@Autowired
	private MoPayRatioDAO moPayRatioDAO;
	public void setMoPayRatioDAO(MoPayRatioDAO moPayRatioDAO) {
		this.moPayRatioDAO = moPayRatioDAO;
	}
	public void setPayDAO(PayDAO payDAO) {
		this.payDAO = payDAO;
	}
	@Test
	public void testsdfsdf() {
		//每1小时统计1次
		String runResult = "1小时订单支付率Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoOrderRechargeBO notPayVO=new MoOrderRechargeBO();
		try {
			ReObj boj=getHour();
			notPayVO.setStartTime(boj.getStartTime());
			notPayVO.setEndTime(boj.getEndTime());
			//查询所有订单数量：以配送单为准
			Integer all=payDAO.searchAllOrderCount(notPayVO);
			if(all!=null){
				MoPayRatioBO moPayRatioBO=new MoPayRatioBO();
				moPayRatioBO.setStartTime(boj.getStartTime());
				moPayRatioBO.setEndTime(boj.getEndTime());
				moPayRatioBO.setCount(all);
				moPayRatioBO.setType("all");
				moPayRatioDAO.saveMoPayRatio(moPayRatioBO);
			}
			//查询成功在线支付的订单数量：以配送单为准
			Integer online=payDAO.searchOnLineOrderCount(notPayVO);
			if(online!=null){
				MoPayRatioBO moPayRatioBO=new MoPayRatioBO();
				moPayRatioBO.setStartTime(boj.getStartTime());
				moPayRatioBO.setEndTime(boj.getEndTime());
				moPayRatioBO.setCount(online);
				moPayRatioBO.setType("onLine");
				moPayRatioDAO.saveMoPayRatio(moPayRatioBO);
			}
		} catch (Exception e) {
		}
	}
	private ReObj getHour() throws ParseException{
		ReObj reObj=new ReObj();
		SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String endTimeStr=sdfNow.format(new Date());
		//当前时间-1小时
		Date startTime=new Date(sdfNow.parse(endTimeStr).getTime()-1000*60*60);
		Date endTime=sdfNow.parse(endTimeStr);
		reObj.setStartTime(startTime);
		reObj.setEndTime(endTime);
		return reObj;
	}
}
