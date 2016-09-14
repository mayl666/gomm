package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.domain.MoMonitorAtp;
import com.gome.upm.domain.MoMonitorDragon;
/**
 * 新的监控sql
 * @author liuyuqiang
 *
 */
public interface MoMonitorMapper {

	/**
	 * 十分钟前，当天的G3PP真预留状态大量停在NA
	 * @return
	 */
	List<MoMonitorAtp> g3pp_realy_na();
	
	/**
	 * 十分钟前，当天的G3PP真预留状态停在DH，要打印出原因代码
	 * @return
	 */
	List<MoMonitorAtp> g3pp_realy_dh();
	/**
	 * 十分钟前，一天内G3PP订单状态大量停在PR
	 * @return
	 */
	List<MoMonitorDragon> g3pp_order_pr();
	/**
	 * 十分钟前，一天内G3PP订单状态停在DH
	 * @return
	 */
	List<MoMonitorDragon> g3pp_order_dh();
	/**
	 * 十分钟前，一天内G3PP订单状态停在CCO
	 * @return
	 */
	List<MoMonitorDragon> g3pp_order_cco();
	/**
	 * 十分钟前，一天内，订单状态大量停在PR
	 * @return
	 */
	List<MoMonitorDragon> smi_order_pr();
	/**
	 * 十分钟前，7天内 订单状态大量停留在OD
	 * @return
	 */
	List<MoMonitorDragon> order_od();
	
	/**
	 * 十分钟前，当天的G3PP真预留状态大量停在NA的数量
	 * @return
	 */
	Integer g3pp_realy_na_count();
	
	/**
	 * 十分钟前，当天的G3PP真预留状态停在DH的数量
	 * @return
	 */
	Integer g3pp_realy_dh_count();
	/**
	 * 十分钟前，一天内G3PP订单状态大量停在PR的数量
	 * @return
	 */
	Integer g3pp_order_pr_count();
	/**
	 * 十分钟前，一天内G3PP订单状态停在DH的数量
	 * @return
	 */
	Integer g3pp_order_dh_count();
	/**
	 * 十分钟前，一天内G3PP订单状态停在CCO的数量
	 * @return
	 */
	Integer g3pp_order_cco_count();
	/**
	 * 十分钟前，一天内，订单状态大量停在PR的数量
	 * @return
	 */
	Integer smi_order_pr_count();
	/**
	 * 十分钟前，7天内 订单状态大量停留在OD的数量
	 * @return
	 */
	Integer order_od_count();
}
