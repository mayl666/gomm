package org.gome.pricelogsync.dao;

import java.util.List;

import org.gome.pricelogsync.domain.PriceLog;

/**
 * 价格监控接口
 * @author caowei-ds1
 *
 */
public interface PriceLogDao {
	
	/** 根据条件查询所有价格日志 */
	List<PriceLog> selectPriceLogList();

	void insertPriceLogList(List<PriceLog> logList);

}
