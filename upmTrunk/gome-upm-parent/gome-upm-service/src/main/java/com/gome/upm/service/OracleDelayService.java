package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.OracleDelay;

/**
 * oracle主从延迟service接口
 * @author caowei-ds1
 *
 */
public interface OracleDelayService {

	/**
	 * 
	 * 新增oracle主从延迟.
	 *
	 * @param oracleDelay
	 * 			Oracle主从延迟
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int addOracleDelay(OracleDelay oracleDelay);

	/**
	 * 
	 * 分页查询oracle主从延迟列表.
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
	Page<OracleDelay> findOracleDelayListByPage(Page<OracleDelay> page);

	/**
	 *
	 * 编辑oracle主从延迟.
	 *
	 * @param oracleDelay
	 * 			Oracle主从延迟
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int editOracleDelay(OracleDelay oracleDelay);

	/**
	 *
	 * 根据ID数组批量删除oracle主从延迟.
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
	int batchDeleteOracleDelayByIds(Long[] ids);
	
	/**
	 *
	 * 根据ID删除oracle主从延迟.
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
	int deleteOracleDelayById(Long id);

	/**
	 *
	 * 根据ID查询oracle主从延迟.
	 *
	 * @param id
	 * 			ID
	 * @return
	 * 			Oracle主从延迟
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	OracleDelay findOracleDelayById(Long id);

	/**
	 *
	 * 根据条件查询oracle主从延迟列表,不分页.
	 *
	 * @param oracleDelay
	 * 				封装了查询条件
	 * @return
	 * 				Oracle主从延迟列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<OracleDelay> findOracleDelayListByOracleDelay(OracleDelay oracleDelay);
	
	/**
	 *
	 * 根据条件查询oracle主从延迟记录数.
	 *
	 * @param oracleDelay
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(OracleDelay oracleDelay);
	
	/**
	 *
	 * 查询各个报警级别的oracle主从延迟的记录数.
	 *
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年09月13日    caowei-ds1    新建
	 * </pre>
	 */
	List<OracleDelay> findTotalResultForAlarmLevel();
	
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
	List<OracleDelay> findTop5();
	
}
