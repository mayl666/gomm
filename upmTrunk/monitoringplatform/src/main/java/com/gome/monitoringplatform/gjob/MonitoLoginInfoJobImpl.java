package com.gome.monitoringplatform.gjob;

import org.springframework.beans.factory.annotation.Autowired;

import com.gome.job.util.ExecutorManager;
import com.gome.monitoringplatform.dao.MoLoginInfoDAO;
import com.gome.monitoringplatform.model.bo.MoLoginInfoBO;
import com.gome.monitoringplatform.slave1.dao.GomeLoginInfoDAO;
public class MonitoLoginInfoJobImpl extends AbsJobImpl {
	@Autowired
	private GomeLoginInfoDAO gomeLoginInfoDAO;
	@Autowired
	private MoLoginInfoDAO moLoginInfoDAO;
	public void setGomeLoginInfoDAO(GomeLoginInfoDAO gomeLoginInfoDAO) {
		this.gomeLoginInfoDAO = gomeLoginInfoDAO;
	}
	public void setMoLoginInfoDAO(MoLoginInfoDAO moLoginInfoDAO) {
		this.moLoginInfoDAO = moLoginInfoDAO;
	}
	@Override
	public void run() {
		// 完成任务退出，知道任务中心jobcenter
		String runResult = "5分钟登录监控Job运行成功";
		System.out.println("定时任务========================="+runResult);
		MoLoginInfoBO gomeLoginInfoVO=new MoLoginInfoBO();
		MoLoginInfoBO   moLoginInfoBO=new MoLoginInfoBO();
		try {
			ReObj boj=getTime();
			gomeLoginInfoVO.setStartTime(boj.getStartTime());
			gomeLoginInfoVO.setEndTime(boj.getEndTime());
			
			moLoginInfoBO.setStartTime(boj.getEndTime());
			moLoginInfoBO.setMinute(boj.getEndTime().getMinutes());
			//查询是否有数据
			Integer count= moLoginInfoDAO.searchCountByTime(moLoginInfoBO);
			if(count==null){
				Integer count1=gomeLoginInfoDAO.searchCountByTime(gomeLoginInfoVO);
				moLoginInfoBO.setCount(count1);
				moLoginInfoDAO.saveMoLoginInfo(moLoginInfoBO);
			}
			ExecutorManager.callBack(logId, "5分钟登录监控Job运行成功", 1);
		} catch (Exception e) {
			ExecutorManager.callBack(logId, "5分钟登录监控Job运行失败", 1);
		}
	}
}