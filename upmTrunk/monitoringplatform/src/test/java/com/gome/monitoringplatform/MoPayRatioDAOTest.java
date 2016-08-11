package com.gome.monitoringplatform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoCpsDAO;
import com.gome.monitoringplatform.dao.MoLoginInfoDAO;
import com.gome.monitoringplatform.dao.MoPayRatioDAO;
import com.gome.monitoringplatform.model.bo.MoBusiness;
import com.gome.monitoringplatform.model.bo.MoCpsBO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;
import com.gome.monitoringplatform.model.bo.MoPayRatioBO;
import com.gome.monitoringplatform.slave1.dao.PayDAO;

public class MoPayRatioDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MoPayRatioDAO moPayRatioDAO;
	@Autowired
	private PayDAO payDAO;
	@Autowired
	private MoCpsDAO moCpsDAO;
	public void setPayDAO(PayDAO payDAO) {
		this.payDAO = payDAO;
	}
	public void setMoPayRatioDAO(MoPayRatioDAO moPayRatioDAO) {
		this.moPayRatioDAO = moPayRatioDAO;
	}
	@Test
	public void addObj() throws ParseException{
		//测试ok
		int max=1000;
        int min=10;
        Random random = new Random();
		//开始时间
		Date startTime=formatter.parse("2016-07-29 09:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-29 18:00:00");
		while(startTime.getTime()<endTime.getTime()){
			MoPayRatioBO m=new MoPayRatioBO();
			m.setCount(random.nextInt(max)%(max-min+1) + min);
			m.setStartTime(startTime);
			m.setEndTime(new Date(startTime .getTime() + 1000*60*60));
			//onLine   all
			m.setType("onLine");
			moPayRatioDAO.saveMoPayRatio(m);
			MoPayRatioBO m1=new MoPayRatioBO();
			m1.setCount(random.nextInt(max)%(max-min+1) + min);
			m1.setStartTime(startTime);
			startTime = new Date(startTime .getTime() + 1000*60*60);
			m1.setEndTime(startTime);
			m1.setType("all");
			moPayRatioDAO.saveMoPayRatio(m1);
		}
		System.out.println("sdf");
	}
	@Test
	public void searchList() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-07-29 08:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-29 18:00:00");
		MoOrderRechargeBO bo=new MoOrderRechargeBO();
		bo.setStartTime(startTime);
		bo.setEndTime(endTime);
		Integer all=payDAO.searchAllOrderCount(bo);
		System.out.println(all);
		Integer online=payDAO.searchOnLineOrderCount(bo);
		System.out.println(online);
		System.out.println((online*1.0)/all);
	}
	@Test
	public void searchListNow() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-07-29 12:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-29 18:00:00");
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(startTime);
		moBusiness.setEndTime(endTime);
		List<MoBusiness>  list=moPayRatioDAO.searchAllOrderCountHistory(moBusiness);
		for(MoBusiness  mo:list){
			System.out.println(formatter.format(mo.getEndTime())+"-----------"+mo.getAmount());
		}
//		List<MoBusiness>  list1=moPayRatioDAO.searchOneLineOrderCountHistory(moBusiness);
		System.out.println("--");
	}
	
	
	
//	@Test
//	public void countNum() throws ParseException{
//		//测试通过
//		Date startTime=formatter.parse("2016-07-06 09:40:00");
//		MoLoginInfoBO searchBo=new MoLoginInfoBO();
//		searchBo.setStartTime(startTime);
//		searchBo.setMinute(5);
//		Integer count= moPayRatioDAO.searchCountByTime(searchBo);
//		System.out.println(count);
//	}
	@Test
	public void savemoCps() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-07-06 09:40:00");
		MoCpsBO searchBo=new MoCpsBO();
		searchBo.setStartTime(startTime);
		searchBo.setEndTime(new Date());
		searchBo.setCount(234234);
		moCpsDAO.saveMoCps(searchBo);
	}
	
	
	
}
