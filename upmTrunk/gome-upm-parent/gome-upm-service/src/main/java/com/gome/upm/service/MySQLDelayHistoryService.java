package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelayHistory;

/**
 * mysql主从延迟异常历史记录service接口
 * @author caowei-ds1
 *
 */
public interface MySQLDelayHistoryService {

	/**
	 * 
	 * 新增mysql主从延迟异常历史记录.
	 *
	 * @param mysqlDelayHistory
	 * 			MySQL主从延迟异常历史
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int addMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);

	/**
	 * 
	 * 分页查询mysql主从延迟异常历史记录列表.
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
	Page<MySQLDelayHistory> findMySQLDelayHistoryListByPage(Page<MySQLDelayHistory> page);

	/**
	 *
	 * 编辑mysql主从延迟异常历史记录.
	 *
	 * @param mysqlDelayHistory
	 * 			MySQL主从延迟异常历史
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int editMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);

	/**
	 *
	 * 根据ID数组批量删除mysql主从延迟异常历史记录.
	 *
	 * @param ids
	 * 			ID数组
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int batchDeleteMySQLDelayHistoryByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除mysql主从延迟异常历史记录.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int deleteMySQLDelayHistoryById(Long id);

	/**
	 *
	 * 根据ID查询mysql主从延迟异常历史记录.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			MySQL主从延迟异常历史
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	MySQLDelayHistory findMySQLDelayHistoryById(Long id);

	/**
	 *
	 * 根据条件查询mysql主从延迟异常历史记录列表,不分页.
	 *
	 * @param mysqlDelayHistory
	 * 				封装了查询条件
	 * @return
	 * 				MySQL主从延迟异常历史列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<MySQLDelayHistory> findMySQLDelayHistoryListByMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory);
	
	/**
	 *
	 * 根据条件查询mysql主从延迟异常历史记录数.
	 *
	 * @param mysqlDelayHistory
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(MySQLDelayHistory mysqlDelayHistory);
	
}
