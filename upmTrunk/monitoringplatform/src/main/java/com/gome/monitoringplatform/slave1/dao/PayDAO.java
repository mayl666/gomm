package com.gome.monitoringplatform.slave1.dao;

import org.springframework.stereotype.Repository;

import com.gome.monitoringplatform.model.bo.MoOrderRechargeBO;

@Repository("payDAO")
public interface PayDAO {
	/**
	 * @param monitoVO
	 * @return
	 */
	Integer searchCountByTime(MoOrderRechargeBO monitoVO);
	/**
	 * 查询成功在线支付的订单数量
	 * @param monitoVO
	 * @return
	 */
	Integer searchOnLineOrderCount(MoOrderRechargeBO monitoVO);
	/**
	 * 查询所有订单数量
	 * @param monitoVO
	 * @return
	 */
	Integer searchAllOrderCount(MoOrderRechargeBO monitoVO);
	/**
	 * 查询CPS数据
	 * @param monitoVO
	 * @return
	 */
	Integer searchCpsCount(MoOrderRechargeBO monitoVO);
}
