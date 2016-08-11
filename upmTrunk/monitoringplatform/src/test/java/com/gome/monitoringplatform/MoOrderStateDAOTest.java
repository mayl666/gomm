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
import com.gome.monitoringplatform.dao.MoOrderStateDAO;
import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
import com.gome.monitoringplatform.model.bo.MoOrderStateBO;

public class MoOrderStateDAOTest extends BaseTest{
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String[] orderState=new String[]{
			"PENDING_TECHNICIAN_NOTIFICATION",
			"PENDING_SUBMITTED",
			"PENDING_FINANCE_ACCEPT",
			"PENDING_PAYMENT",
			"PENDING_ORDER_CONFIRM",
			"PENDING_CUSTOMER_RETURN",
			"PENDING_CONTRACTMOBILE_APPROVE",
			"PENDING_CUSTOMER_ACTION",
			"PENDING_MERCHANT_ACTION",
			"PENDING_GOODS_IN_STORAGE",
			"PENDING_FINAL_PAYMENT",
			"NO_PENDING_ACTION",
			"DEPOSIT_PAYMENT",
			"REMOVED",
			"PENDING_DEPOSIT_PAYMENT",
			"PENDING_REMOVE",
			"PENDING_GIFTCARD_PAYMENT",
			"PROCESSING",
			"PENDING_3P_APPROVE_CONFIRM",
			"SUBMITTED",
			"TPSP_SUBMITTED",
			"ERROR",
			"PENDING_POST_PROCESS",
			"ALLOWANCE_PENDING_AUDIT",
			"PENDING_THIRD_AUDIT",
			"PENDING_ADD_RESOURCE"
	};
	/**
	 * 配送单类型
	 */
	private static final String[] shippingState=new String[]{
			"CO",
			"CL",
			"PR",
			"RCO"
	};
	@Autowired
	private MoOrderStateDAO moOrderStateDAO;
	public void setMoOrderStateDAO(MoOrderStateDAO moOrderStateDAO) {
		this.moOrderStateDAO = moOrderStateDAO;
	}
	@Test
	public void addObj() throws ParseException{
		class AddData implements Runnable{
			Date startTime;
			Date endTime;
			String state;
			public AddData(Date startTime,Date endTime,String state){
				this.startTime=startTime;
				this.endTime=endTime;
				this.state=state;
			}
			public void run() {
				try {
					//测试通过
					int max=1000;
					int min=10;
					Random random = new Random();
					//开始时间和结束时间一共有几天，每天一个线程
//					System.out.println("---添加数据--开始时间"+formatter.format(startTime)+"   结束时间："+formatter.format(endTime));
					while(startTime.getTime()<endTime.getTime()){
						MoOrderStateBO m=new MoOrderStateBO();
						m.setCount(random.nextInt(max)%(max-min+1) + min);
//						System.out.println("---@@@--开始时间"+formatter.format(startTime)+"   结束时间："+formatter.format(endTime));
						m.setStartTime(new Date(startTime .getTime()));
						startTime = new Date(startTime .getTime() + 300000);
						m.setEndTime(startTime);
						m.setMinute(startTime.getMinutes());
						m.setState(state);
						moOrderStateDAO.saveMoOrderState(m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Date startTime=formatter.parse("2016-07-01 09:00:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-02 17:16:00");
		//计算间隔多少天
		Integer ttt=  (int) ((endTime.getTime()-startTime.getTime())/1000/60/60/24);
		List<Thread> runList=new ArrayList<Thread>();
		for(int jj=0;jj<shippingState.length;jj++){
			String state=shippingState[jj];
			for(int i=0;i<=ttt;i++){
				//当前时间
				Date startTimeNow=new Date(startTime.getTime()+i*1000*60*60*24);
				Date endTimeNow=formatter.parse(formatter.format(startTimeNow).split(" ")[0]+" 23:55:00");
				Thread thread=new Thread(new AddData(startTimeNow,endTimeNow,state));
				thread.start();
				runList.add(thread);
			}
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
		Date startTime=formatter.parse("2016-07-01 09:40:00");
		//结束时间
		Date endTime=formatter.parse("2016-07-01 10:50:00");
		MoOrderStateBO bo=new MoOrderStateBO();
		bo.setStartTime(startTime);
		bo.setEndTime(endTime);
		List<MoOrderStateBO> li=moOrderStateDAO.searchMoOrderStateList(bo);
		for(MoOrderStateBO s:li){
			System.out.println(formatter.format(s.getStartTime())+"-----"+s.getCount());
		}
	}
	@Test
	public void countNum() throws ParseException{
		//测试通过
		Date startTime=formatter.parse("2016-07-01 09:40:00");
		Date endTime=formatter.parse("2016-07-01 09:45:00");
		MoOrderStateBO searchBo=new MoOrderStateBO();
		searchBo.setStartTime(startTime);
		searchBo.setEndTime(endTime);
		searchBo.setState("TPSP_SUBMITTED");
		Integer count= moOrderStateDAO.searchCountByTime(searchBo);
		System.out.println(count);
	}
	
}
