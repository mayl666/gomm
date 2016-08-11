package com.gome.monitoringplatform.gjob;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoOrderNotRechargeDAO;
import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;
import com.gome.monitoringplatform.slave1.dao.NotPayDAO;

public class MonitoNotPayJobImpl extends AbsJobImpl{
	@Autowired
	private NotPayDAO notPayDAO;
	@Autowired
	private MoOrderNotRechargeDAO moOrderNotRechargeDAO;
	public void setMoOrderNotRechargeDAO(MoOrderNotRechargeDAO moOrderNotRechargeDAO) {
		this.moOrderNotRechargeDAO = moOrderNotRechargeDAO;
	}
	public void setNotPayDAO(NotPayDAO notPayDAO) {
		this.notPayDAO = notPayDAO;
	}
	@Override
	public void run() {
		String runResult = "5分钟非充值监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoOrderNotRechargeBO notPayVO=new MoOrderNotRechargeBO();
		MoOrderNotRechargeBO   moLoginInfoBO=new MoOrderNotRechargeBO();
		try {
			ReObj boj=getTime();
			notPayVO.setStartTime(boj.getStartTime());
			notPayVO.setEndTime(boj.getEndTime());
			
			moLoginInfoBO.setStartTime(boj.getEndTime());
			moLoginInfoBO.setMinute(boj.getEndTime().getMinutes());
			//查询是否有数据
			Integer count= moOrderNotRechargeDAO.searchCountByTime(moLoginInfoBO);
			if(count==null){
				Integer count1=notPayDAO.searchCountByTime(notPayVO);
				moLoginInfoBO.setCount(count1);
				moOrderNotRechargeDAO.saveMoOrderNotRecharge(moLoginInfoBO);
			}
			ExecutorManager.callBack(logId, "5分钟非充值订单监控Job运行成功", 1);
		} catch (Exception e) {
			ExecutorManager.callBack(logId, "5分钟非充值订单监控Job运行失败", 1);
		}
	}

}
