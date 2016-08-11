package com.gome.monitoringplatform.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoBusiness;
import com.gome.monitoringplatform.model.bo.MoPayRatioBO;
@Repository("moPayRatioDAO")
public interface MoPayRatioDAO {
	void saveMoPayRatio(MoPayRatioBO moPayRatioBO);
	void saveCps(MoPayRatioBO moPayRatioBO);
	/**
	 * 查询所有订单数量
	 */
	public List<MoBusiness> searchAllOrderCountHistory(MoBusiness moBusiness);
	/**
	 * 查询成功在线支付的订单数量
	 */
	public List<MoBusiness> searchOneLineOrderCountHistory(MoBusiness moBusiness);
}
