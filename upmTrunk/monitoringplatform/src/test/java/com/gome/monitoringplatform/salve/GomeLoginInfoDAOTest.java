package com.gome.monitoringplatform.salve;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
import com.gome.monitoringplatform.slave1.dao.GomeLoginInfoDAO;

public class GomeLoginInfoDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	GomeLoginInfoDAO gomeLoginInfoDAO;
	public void setGomeLoginInfoDAO(GomeLoginInfoDAO gomeLoginInfoDAO) {
		this.gomeLoginInfoDAO = gomeLoginInfoDAO;
	}
	@Test
	public void testSearchList() throws Exception{
		MoLoginInfoBO gomeLoginInfoVO=new MoLoginInfoBO();
		gomeLoginInfoVO.setStartTime(formatter.parse("2016-06-15 16:12:21"));
		gomeLoginInfoVO.setEndTime(formatter.parse("2016-06-15 16:12:21"));
		Integer count=  gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
		System.out.println("--"+count);
	}
}
