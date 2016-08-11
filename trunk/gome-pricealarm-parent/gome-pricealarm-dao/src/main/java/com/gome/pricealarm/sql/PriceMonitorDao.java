package com.gome.pricealarm.sql;

import java.util.List;

import com.gome.pricealarm.common.Page;
import com.gome.pricealarm.domain.PriceLog;

/**
 * 价格监控接口
 * @author caowei-ds1
 *
 */
public interface PriceMonitorDao {
	
	/** 从数据库中根据条件查询所有价格日志,分页 */
	List<PriceLog> selectPriceLogListFromSqlByConditionsPage(Page<PriceLog> page);

	/** 从数据库中根据条件查询价格日志总数 */
	int selectTotalResultFromSqlByConditions(PriceLog conditions);
	
	/** 从数据库中根据条件查询所有价格日志,不分页 */
	List<PriceLog> selectPriceLogListFromSqlByConditions(PriceLog conditions);
}
