package com.gome.monitoringplatform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.gome.framework.base.BaseTest;
import com.gome.monitoringplatform.dao.MoOrderRechargeDAO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;

public class MoOrderRechargeDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private MoOrderRechargeDAO moOrderRechargeDAO;
	public void setMoOrderRechargeDAO(MoOrderRechargeDAO moOrderRechargeDAO) {
		this.moOrderRechargeDAO = moOrderRechargeDAO;
	}
	@Test
	public void addObj() throws ParseException{
		class AddData implements Runnable{
			Date startTime;
			Date endTime;
			public AddData(Date startTime,Date endTime){
				this.startTime=startTime;
				this.endTime=endTime;
			}
			public void run() {
				try {
					//测试通过
					int max=230;
					int min=100;
					Random random = new Random();
					//开始时间和结束时间一共有几天，每天一个线程
//					System.out.println("---添加数据--开始时间"+formatter.format(startTime)+"   结束时间："+formatter.format(endTime));
					while(startTime.getTime()<endTime.getTime()){
						MoOrderRechargeBO m=new MoOrderRechargeBO();
						m.setCount(random.nextInt(max)%(max-min+1) + min);
						startTime = new Date(startTime .getTime() + 300000);
//						System.out.println("---@@@--开始时间"+formatter.format(startTime)+"   结束时间："+formatter.format(endTime));
						m.setStartTime(startTime);
						m.setMinute(startTime.getMinutes());
						moOrderRechargeDAO.saveMoOrderRecharge(m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Date startTime=formatter.parse("2016-07-08 09:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-15 20:16:00");
		//计算间隔多少天
		Integer ttt=  (int) ((endTime.getTime()-startTime.getTime())/1000/60/60/24);
		List<Thread> runList=new ArrayList<Thread>();
		for(int i=0;i<=ttt;i++){
			//当前时间
			Date startTimeNow=new Date(startTime.getTime()+i*1000*60*60*24);
//			System.out.println("开始时间："+formatter.format(startTimeNow));
			Date endTimeNow=formatter.parse(formatter.format(startTimeNow).split(" ")[0]+" 23:55:00");
//			System.out.println("结束时间："+formatter.format(endTimeNow));
			Thread thread=new Thread(new AddData(startTimeNow,endTimeNow));
			thread.start();
			runList.add(thread);
		}
		for(Thread thread:runList){
			try {
				thread.join();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}
	}
	@Test
	public void searchList() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-06-29 09:40:00");
		//结束时间
		Date endTime=formatter.parse("2016-06-29 10:50:00");
		MoOrderRechargeBO bo=new MoOrderRechargeBO();
		bo.setStartTime(startTime);
		bo.setEndTime(endTime);
		List<MoOrderRechargeBO> li=moOrderRechargeDAO.searchMoOrderRechargeList(bo);
		for(MoOrderRechargeBO s:li){
//			System.out.println(formatter.format(s.getStartTime())+"-----"+s.getCount());
		}
	}
	@Test
	public void countNum() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-06-29 09:40:00");
		MoOrderRechargeBO searchBo=new MoOrderRechargeBO();
		searchBo.setStartTime(startTime);
		Integer count= moOrderRechargeDAO.searchCountByTime(searchBo);
		System.out.println(count);
	}
}
