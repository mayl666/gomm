package com.gome.monitoringplatform.salve;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.model.bo.MoBusiness;
import com.gome.monitoringplatform.slave1.dao.MoBusinessDAO;

public class MoBusinessDaoTest  extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	MoBusinessDAO moBusinessDAO;
	public void setMoBusinessDAO(MoBusinessDAO moBusinessDAO) {
		this.moBusinessDAO = moBusinessDAO;
	}
	@Test
	public void testSearchList() throws Exception{
		MoBusiness moBusiness=new MoBusiness();
		moBusiness.setStartTime(formatter.parse("2016-07-25 16:12:21"));
		moBusiness.setEndTime(formatter.parse("2016-07-28 16:12:21"));
		Integer count= moBusinessDAO.getOms_Drg_forward_state_error(moBusiness);
		System.out.println("--【【【【【"+count+"】】】】】】");
	}
	
}
