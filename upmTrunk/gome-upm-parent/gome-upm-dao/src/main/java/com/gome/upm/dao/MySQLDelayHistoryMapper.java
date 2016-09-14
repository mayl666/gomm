package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelayHistory;

/**
 * MySQL主从延迟异常历史Dao
 * @author caowei-ds1
 *
 */
public interface MySQLDelayHistoryMapper {

	/**
	 * 插入一个MySQL主从延迟异常历史
	 * @param mysqlDelayHistory
	 * 			MySQL主从延迟异常历史
	 * @return
	 * 2016年9月13日   caowei-ds1
	 */
	int insertMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);
	
	/**
	 * 分页查询MySQL主从延迟异常历史列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			MySQL主从延迟异常历史列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelayHistory> selectMySQLDelayHistoryListByPage(Page<MySQLDelayHistory> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年9月13日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(MySQLDelayHistory condition);
	
	/**
	 * 更新MySQL主从延迟异常历史.
	 * @param mysqlDelayHistory
	 * 				MySQL主从延迟异常历史
	 * @return
	 * 				更新记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int updateMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);
	
	/**
	 * 根据ID删除MySQL主从延迟异常历史.
	 * @param id
	 * 				MySQL主从延迟异常历史ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int deleteMySQLDelayHistoryById(Long id);
	
	/**
	 * 根据ID数组批量删除MySQL主从延迟异常历史.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int batchDeleteMySQLDelayHistoryByIds(Long[] ids);
	
	/**
	 * 根据ID查找MySQL主从延迟异常历史.
	 * @param id
	 * 				MySQL主从延迟异常历史ID
	 * @return
	 * 				MySQL主从延迟异常历史
	 * 2016年9月13日   caowei-ds1
	 */
	MySQLDelayHistory selectMySQLDelayHistoryById(Long id);
	
	/**
	 * 根据条件查询MySQL主从延迟异常历史列表,不分页.
	 * @param mysqlDelayHistory
	 * 				MySQL主从延迟异常历史
	 * @return
	 * 				MySQL主从延迟异常历史列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelayHistory> selectMySQLDelayHistoryListByMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);

}

