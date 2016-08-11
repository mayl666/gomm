package com.gome.monitoringplatform;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoOrderRechargeDAO;
import com.gome.monitoringplatform.model.bo.OmsOrder;

public class OmsOrderDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MoOrderRechargeDAO omsOrderDAO;
	public MoOrderRechargeDAO getOmsOrderDAO() {
		return omsOrderDAO;
	}
	public void setOmsOrderDAO(MoOrderRechargeDAO omsOrderDAO) {
		this.omsOrderDAO = omsOrderDAO;
	}
	@Test
	public void findL() throws ParseException{
		OmsOrder omsOrder=new OmsOrder();
		//'20160622000000'
		omsOrder.setStartTime(formatter.parse("2016-06-22 00:00:00"));
		//'20160623000000'
		omsOrder.setEndTime(formatter.parse("2016-06-23 00:00:00"));
//		Integer fff=omsOrderDAO.selectTotalResultByTime(omsOrder);
//		System.out.println(fff);
	}
	@Test
	public void findList(){
		OmsOrder omsOrderBO=new OmsOrder();
		omsOrderBO.setState("PENDING_PAYMENT");
//		long num=omsOrderDAO.countOmsOrderByState(omsOrderBO);
//		System.out.println(num);
	}
}
