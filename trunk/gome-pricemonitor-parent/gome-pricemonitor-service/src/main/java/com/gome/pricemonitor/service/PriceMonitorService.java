package com.gome.pricemonitor.service;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.PriceLog;

/**
 * 价格监控service
 * @author caowei-ds1
 *
 */
public interface PriceMonitorService {

	/** 查询所有价格日志 */
	public Page<PriceLog> selectPriceLogListByConditionsPage(Page<PriceLog> page);
	
	/**
	 * 查询价格监控列表
	 * @param skuNo      sku编码
	 * @param type       价格类型
	 * @param result     处理结果
	 * @param beginTime  开始时间
	 * @param endTime    结束时间
	 * @param areaCode   区域编码
	 * @param status     状态
	 * @param node       节点
	 * @param action     处理动作
	 * @return
	 */
	public Page<PriceLog> queryAll(String id, String skuNo, String type, String result, String beginTime, String endTime, String areaCode, String status, String node, String action, Integer pageNo, Integer pageSize);
	
}
