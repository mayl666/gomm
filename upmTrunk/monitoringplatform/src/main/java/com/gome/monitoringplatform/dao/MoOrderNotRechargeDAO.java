package com.gome.monitoringplatform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoOrderNotRechargeBO;

@Repository("moOrderNotRechargeDAO")
public interface MoOrderNotRechargeDAO {
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	List<MoOrderNotRechargeBO> searchMoOrderNotRechargeList(MoOrderNotRechargeBO omsOrder);
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	Integer searchCountByTime(MoOrderNotRechargeBO omsOrder);
	/**
	 * 保存
	 * @param omsOrder
	 */
	void saveMoOrderNotRecharge(MoOrderNotRechargeBO omsOrder);
}
