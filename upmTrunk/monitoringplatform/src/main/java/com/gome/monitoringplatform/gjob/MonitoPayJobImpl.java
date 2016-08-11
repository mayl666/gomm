package com.gome.monitoringplatform.gjob;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoOrderRechargeDAO;
import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;
import com.gome.monitoringplatform.slave1.dao.PayDAO;

public class MonitoPayJobImpl extends AbsJobImpl{
	@Autowired
	private PayDAO payDAO;
	@Autowired
	private MoOrderRechargeDAO moOrderRechargeDAO;
	public PayDAO getPayDAO() {
		return payDAO;
	}
	public void setPayDAO(PayDAO payDAO) {
		this.payDAO = payDAO;
	}
	public MoOrderRechargeDAO getMoOrderRechargeDAO() {
		return moOrderRechargeDAO;
	}
	public void setMoOrderRechargeDAO(MoOrderRechargeDAO moOrderRechargeDAO) {
		this.moOrderRechargeDAO = moOrderRechargeDAO;
	}
	@Override
	public void run() {
		String runResult = "5分钟充值监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoOrderRechargeBO notPayVO=new MoOrderRechargeBO();
		MoOrderRechargeBO   bo=new MoOrderRechargeBO();
		try {
			ReObj boj=getTime();
			notPayVO.setStartTime(boj.getStartTime());
			notPayVO.setEndTime(boj.getEndTime());
			
			bo.setStartTime(boj.getEndTime());
			bo.setMinute(boj.getEndTime().getMinutes());
			//查询是否有数据
			Integer count= moOrderRechargeDAO.searchCountByTime(bo);
			if(count==null){
				Integer count1=payDAO.searchCountByTime(notPayVO);
				bo.setCount(count1);
				moOrderRechargeDAO.saveMoOrderRecharge(bo);
			}
			ExecutorManager.callBack(logId, "5分钟充值订单监控Job运行成功", 1);
		} catch (Exception e) {
			ExecutorManager.callBack(logId, "5分钟充值订单监控Job运行失败", 1);
		}
	}
}
