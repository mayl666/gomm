package com.gome.monitoringplatform.salve;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;
import com.gome.monitoringplatform.slave1.dao.NotPayDAO;
import com.gome.monitoringplatform.slave1.dao.PayDAO;

public class NotPayDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private NotPayDAO notPayDAO;
	@Autowired
	private PayDAO payDAO;
	public void setPayDAO(PayDAO payDAO) {
		this.payDAO = payDAO;
	}
	public void setNotPayDAO(NotPayDAO notPayDAO) {
		this.notPayDAO = notPayDAO;
	}
	@Test
	public void testSearchList() throws Exception{
		MoOrderNotRechargeBO gomeLoginInfoVO=new MoOrderNotRechargeBO();
		gomeLoginInfoVO.setStartTime(formatter.parse("2016-06-15 16:12:21"));
		gomeLoginInfoVO.setEndTime(formatter.parse("2016-06-15 16:12:21"));
		Integer count=notPayDAO.searchCountByTime(gomeLoginInfoVO);
		
//		GomeLoginInfoVO gomeLoginInfoVO=new GomeLoginInfoVO();
//		gomeLoginInfoVO.setStartTime(formatter.parse("2016-06-15 16:12:21"));
//		gomeLoginInfoVO.setEndTime(formatter.parse("2016-06-15 16:12:21"));
//		Integer count=  gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
		System.out.println("--"+count);
	}
	@Test
	public void testSearchCps() throws ParseException{
		MoOrderRechargeBO gomeLoginInfoVO=new MoOrderRechargeBO();
		gomeLoginInfoVO.setStartTime(formatter.parse("2016-06-15 16:12:21"));
		gomeLoginInfoVO.setEndTime(formatter.parse("2016-07-28 16:12:21"));
		Integer count=payDAO.searchCpsCount(gomeLoginInfoVO);
		System.out.println(count);
	}
}
