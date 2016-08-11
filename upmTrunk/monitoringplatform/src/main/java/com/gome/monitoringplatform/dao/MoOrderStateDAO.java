package com.gome.monitoringplatform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoOrderStateBO;
@Repository("moOrderStateDAO")
public interface MoOrderStateDAO {
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	List<MoOrderStateBO> searchMoOrderStateList(MoOrderStateBO omsOrder);
	/**
	 * @param omsOrder  开始时间  结束时间
	 * @return
	 */
	Integer searchCountByTime(MoOrderStateBO omsOrder);
	/**
	 * 保存
	 * @param omsOrder
	 */
	void saveMoOrderState(MoOrderStateBO omsOrder);
}
