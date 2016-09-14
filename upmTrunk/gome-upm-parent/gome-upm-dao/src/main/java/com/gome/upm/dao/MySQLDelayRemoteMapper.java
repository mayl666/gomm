package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelay;
import com.gome.upm.domain.MySQLDelayRemote;

/**
 * MySQL主从延迟Dao(oracle库)
 * @author caowei-ds1
 *
 */
public interface MySQLDelayRemoteMapper {

	
	/**
	 * 分页查询MySQL主从延迟列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			MySQL主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelayRemote> selectMySQLDelayRemoteListByPage(Page<MySQLDelayRemote> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年9月13日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(MySQLDelayRemote condition);
	
	/**
	 * 根据条件查询MySQL主从延迟列表,不分页.
	 * @param mysqlDelayRemote
	 * 				MySQL主从延迟
	 * @return
	 * 				MySQL主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelayRemote> selectMySQLDelayRemoteListByMySQLDelayRemote(MySQLDelayRemote mysqlDelayRemote);

}

