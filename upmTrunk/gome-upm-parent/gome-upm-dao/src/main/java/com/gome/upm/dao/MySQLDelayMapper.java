package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelay;

/**
 * MySQL主从延迟Dao
 * @author caowei-ds1
 *
 */
public interface MySQLDelayMapper {

	/**
	 * 插入一个MySQL主从延迟
	 * @param mysqlDelay
	 * 			MySQL主从延迟
	 * @return
	 * 2016年9月13日   caowei-ds1
	 */
	int insertMySQLDelay(MySQLDelay mysqlDelay);
	
	/**
	 * 分页查询MySQL主从延迟列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			MySQL主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelay> selectMySQLDelayListByPage(Page<MySQLDelay> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年9月13日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(MySQLDelay condition);
	
	/**
	 * 更新MySQL主从延迟.
	 * @param mysqlDelay
	 * 				MySQL主从延迟
	 * @return
	 * 				更新记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int updateMySQLDelay(MySQLDelay mysqlDelay);
	
	/**
	 * 根据ID删除MySQL主从延迟.
	 * @param id
	 * 				MySQL主从延迟ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int deleteMySQLDelayById(Long id);
	
	/**
	 * 根据ID数组批量删除MySQL主从延迟.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int batchDeleteMySQLDelayByIds(Long[] ids);
	
	/**
	 * 根据ID查找MySQL主从延迟.
	 * @param id
	 * 				MySQL主从延迟ID
	 * @return
	 * 				MySQL主从延迟
	 * 2016年9月13日   caowei-ds1
	 */
	MySQLDelay selectMySQLDelayById(Long id);
	
	/**
	 * 根据条件查询MySQL主从延迟列表,不分页.
	 * @param mysqlDelay
	 * 				MySQL主从延迟
	 * @return
	 * 				MySQL主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<MySQLDelay> selectMySQLDelayListByMySQLDelay(MySQLDelay mysqlDelay);

	/**
	 * 查询各个报警级别异常数
	 * @param alarmLevel
	 * @return
	 * 2016年9月13日 下午3:41:00   caowei-ds1
	 */
	List<MySQLDelay> selectCountForAlarmLevel();

	/**
	 * 查询异常的Top5
	 * @return
	 * 2016年9月13日 下午3:42:10   caowei-ds1
	 */
	List<MySQLDelay> selectTop5();
	
}

