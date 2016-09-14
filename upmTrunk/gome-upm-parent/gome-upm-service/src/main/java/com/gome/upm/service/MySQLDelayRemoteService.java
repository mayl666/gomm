package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelayRemote;

/**
 * mysql主从延迟service接口(oracle库)
 * @author caowei-ds1
 *
 */
public interface MySQLDelayRemoteService {

	/**
	 * 
	 * 分页查询mysql主从延迟列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	Page<MySQLDelayRemote> findMySQLDelayRemoteListByPage(Page<MySQLDelayRemote> page);


	/**
	 *
	 * 根据条件查询mysql主从延迟列表,不分页.
	 *
	 * @param mysqlDelay
	 * 				封装了查询条件
	 * @return
	 * 				MySQL主从延迟列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<MySQLDelayRemote> findMySQLDelayRemoteListByMySQLDelayRemote(MySQLDelayRemote mysqlDelay);
	
}
