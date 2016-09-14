package com.gome.threshold.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.MoCpsBO;
import com.gome.threshold.domain.MoOrderRechargeBO;

@Repository("moPayDAO")
public interface MoPayDAO {
	Integer searchMoPayCountByTime(MoOrderRechargeBO monitoVO);
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
}
