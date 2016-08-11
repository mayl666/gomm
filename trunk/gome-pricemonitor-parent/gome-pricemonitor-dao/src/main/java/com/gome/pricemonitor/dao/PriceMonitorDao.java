package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.PriceLog;

/**
 * 价格监控接口
 * @author caowei-ds1
 *
 */
public interface PriceMonitorDao {
	
	/** 从ES中根据条件查询所有价格日志 */
	List<PriceLog> selectPriceLogListFromEsByConditionsPage(Page<PriceLog> page);

	/** 从ES中根据条件查询价格日志总数 */
	int selectTotalResultFromEsByConditions(PriceLog conditions);
	
	/** 从数据库中根据条件查询所有价格日志 */
	List<PriceLog> selectPriceLogListFromSqlByConditionsPage(Page<PriceLog> page);

	/** 从数据库中根据条件查询价格日志总数 */
	int selectTotalResultFromSqlByConditions(PriceLog conditions);
}
