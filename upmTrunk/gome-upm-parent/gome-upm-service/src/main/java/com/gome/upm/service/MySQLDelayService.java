package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.MySQLDelay;

/**
 * mysql主从延迟service接口
 * @author caowei-ds1
 *
 */
public interface MySQLDelayService {

	/**
	 * 
	 * 新增mysql主从延迟.
	 *
	 * @param mysqlDelay
	 * 			MySQL主从延迟
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int addMySQLDelay(MySQLDelay mysqlDelay);

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
	Page<MySQLDelay> findMySQLDelayListByPage(Page<MySQLDelay> page);

	/**
	 *
	 * 编辑mysql主从延迟.
	 *
	 * @param mysqlDelay
	 * 			MySQL主从延迟
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int editMySQLDelay(MySQLDelay mysqlDelay);

	/**
	 *
	 * 根据ID数组批量删除mysql主从延迟.
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
	int batchDeleteMySQLDelayByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除mysql主从延迟.
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
	int deleteMySQLDelayById(Long id);

	/**
	 *
	 * 根据ID查询mysql主从延迟.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			MySQL主从延迟
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	MySQLDelay findMySQLDelayById(Long id);

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
	List<MySQLDelay> findMySQLDelayListByMySQLDelay(MySQLDelay mysqlDelay);
	
	/**
	 *
	 * 根据条件查询mysql主从延迟记录数.
	 *
	 * @param mysqlDelay
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(MySQLDelay mysqlDelay);
	
	/**
	 *
	 * 查询各个报警级别的mysql主从延迟的记录数.
	 *
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<MySQLDelay> findTotalResultForAlarmLevel();
	
	/**
	 *
	 * 查询异常记录的top5.
	 *
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<MySQLDelay> findTop5();
	
}
