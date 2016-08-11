package com.gome.monitoringplatform.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoOrderNotRechargeDAO;
import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
import com.gome.monitoringplatform.slave1.dao.NotPayDAO;

public class NotPayJobTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private NotPayDAO notPayDAO;
	@Autowired
	private MoOrderNotRechargeDAO moOrderNotRechargeDAO;
	public void setNotPayDAO(NotPayDAO notPayDAO) {
		this.notPayDAO = notPayDAO;
	}
	public void setMoOrderNotRechargeDAO(MoOrderNotRechargeDAO moOrderNotRechargeDAO) {
		this.moOrderNotRechargeDAO = moOrderNotRechargeDAO;
	}
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
	
	public ReObj getTime() throws ParseException{
		ReObj reObj=new ReObj();
		String ww=formatter.format(new Date());
		String[]  ll=ww.split(" ");
		String[]  ll1=ll[1].split(":");
		String ddd=ll[0]+" "+ll1[0]+":"+Integer.parseInt(ll1[1])/5*5+":00";
		Date endTime=formatter.parse(ddd);
		reObj.setEndTime(endTime);
		Date startTime=new Date(endTime.getTime()-1000*60*5);
		reObj.setStartTime(startTime);
		return reObj;
	}
	@Test
	public void testsdfsdf() {
		// 完成任务退出，知道任务中心jobcenter
		String runResult = "5分钟非充值监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoOrderNotRechargeBO notPayVO=new MoOrderNotRechargeBO();
		MoOrderNotRechargeBO   moLoginInfoBO=new MoOrderNotRechargeBO();
		try {
			ReObj boj=getTime();
			notPayVO.setStartTime(boj.getStartTime());
			notPayVO.setEndTime(boj.getEndTime());
			
			moLoginInfoBO.setStartTime(boj.getEndTime());
			moLoginInfoBO.setMinute(boj.getEndTime().getMinutes());
			//查询是否有数据
			Integer count= moOrderNotRechargeDAO.searchCountByTime(moLoginInfoBO);
			if(count==null){
				Integer count1=notPayDAO.searchCountByTime(notPayVO);
				moLoginInfoBO.setCount(count1);
				moOrderNotRechargeDAO.saveMoOrderNotRecharge(moLoginInfoBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
