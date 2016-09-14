package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.OracleDelayHistory;

/**
 * oracle主从延迟异常历史记录service接口
 * @author caowei-ds1
 *
 */
public interface OracleDelayHistoryService {

	/**
	 * 
	 * 新增oracle主从延迟异常历史记录.
	 *
	 * @param oracleDelayHistory
	 * 			Oracle主从延迟历史记录
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int addOracleDelayHistory(OracleDelayHistory oracleDelayHistory);

	/**
	 * 
	 * 分页查询oracle主从延迟异常历史记录列表.
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
	Page<OracleDelayHistory> findOracleDelayHistoryListByPage(Page<OracleDelayHistory> page);

	/**
	 *
	 * 编辑oracle主从延迟异常历史记录.
	 *
	 * @param oracleDelayHistory
	 * 			Oracle主从延迟历史记录
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int editOracleDelayHistory(OracleDelayHistory oracleDelayHistory);

	/**
	 *
	 * 根据ID数组批量删除oracle主从延迟异常历史记录.
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
	int batchDeleteOracleDelayHistoryByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除oracle主从延迟异常历史记录.
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
	int deleteOracleDelayHistoryById(Long id);

	/**
	 *
	 * 根据ID查询oracle主从延迟异常历史记录.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			Oracle主从延迟历史记录
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	OracleDelayHistory findOracleDelayHistoryById(Long id);

	/**
	 *
	 * 根据条件查询oracle主从延迟异常历史记录列表,不分页.
	 *
	 * @param oracleDelayHistory
	 * 				封装了查询条件
	 * @return
	 * 				Oracle主从延迟历史记录列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<OracleDelayHistory> findOracleDelayHistoryListByOracleDelayHistory(OracleDelayHistory oracleDelayHistory);
	
	/**
	 *
	 * 根据条件查询oracle主从延迟异常历史记录记录数.
	 *
	 * @param oracleDelayHistory
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(OracleDelayHistory oracleDelayHistory);
	
}
