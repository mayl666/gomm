package com.gome.monitoringplatform.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoLoginInfoDAO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
import com.gome.monitoringplatform.slave1.dao.GomeLoginInfoDAO;

public class MonitoLoginInfoJobTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private GomeLoginInfoDAO gomeLoginInfoDAO;
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	public void setGomeLoginInfoDAO(GomeLoginInfoDAO gomeLoginInfoDAO) {
		this.gomeLoginInfoDAO = gomeLoginInfoDAO;
	}
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
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
		String runResult = "5分钟登录监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoLoginInfoBO gomeLoginInfoVO=new MoLoginInfoBO();
		MoLoginInfoBO   moLoginInfoBO=new MoLoginInfoBO();
		try {
			ReObj boj=getTime();
			gomeLoginInfoVO.setStartTime(boj.getStartTime());
			gomeLoginInfoVO.setEndTime(boj.getEndTime());
			
			moLoginInfoBO.setStartTime(boj.getEndTime());
			//查询是否有数据
			Integer count= moLoginInfoDAO.searchCountByTime(moLoginInfoBO);
			if(count==null){
				Integer count1=gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
				moLoginInfoBO.setCount(count1);
				moLoginInfoDAO.saveMoLoginInfo(moLoginInfoBO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
