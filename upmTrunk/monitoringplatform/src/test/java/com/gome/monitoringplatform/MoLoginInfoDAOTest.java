package com.gome.monitoringplatform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoLoginInfoDAO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;

public class MoLoginInfoDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	public MoLoginInfoDAO getMoLoginInfoDAO() {
		return moLoginInfoDAO;
	}
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
	}
	@Test
	public void addObj() throws ParseException{
		//测试ok
		int max=1000;
        int min=10;
        Random random = new Random();
		//开始时间
		Date startTime=formatter.parse("2016-07-08 09:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-15 20:50:00");
		while(startTime.getTime()<endTime.getTime()){
			MoLoginInfoBO m=new MoLoginInfoBO();
			m.setCount(random.nextInt(max)%(max-min+1) + min);
			startTime = new Date(startTime .getTime() + 300000);
			m.setStartTime(startTime);
			m.setMinute(startTime.getMinutes());
			moLoginInfoDAO.saveMoLoginInfo(m);
		}
		System.out.println("sdf");
	}
	@Test
	public void searchList() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-06-29 09:40:00");
		//结束时间
		Date endTime=formatter.parse("2016-06-29 10:50:00");
		MoLoginInfoBO bo=new MoLoginInfoBO();
		bo.setStartTime(startTime);
		bo.setEndTime(endTime);
		bo.setMinute(60);
		List<MoLoginInfoBO> li=moLoginInfoDAO.searchMoLoginInfoList(bo);
		for(MoLoginInfoBO s:li){
			System.out.println(formatter.format(s.getStartTime())+"-----"+s.getCount());
		}
	}
	@Test
	public void countNum() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-07-06 09:40:00");
		MoLoginInfoBO searchBo=new MoLoginInfoBO();
		searchBo.setStartTime(startTime);
		searchBo.setMinute(5);
		Integer count= moLoginInfoDAO.searchCountByTime(searchBo);
		System.out.println(count);
	}
}
