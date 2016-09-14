package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.OracleDelay;

/**
 * Oracle主从延迟Dao
 * @author caowei-ds1
 *
 */
public interface OracleDelayMapper {

	/**
	 * 插入一个Oracle主从延迟
	 * @param oracleDelay
	 * 			Oracle主从延迟
	 * @return
	 * 2016年9月13日   caowei-ds1
	 */
	int insertOracleDelay(OracleDelay oracleDelay);
	
	/**
	 * 分页查询Oracle主从延迟列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			Oracle主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<OracleDelay> selectOracleDelayListByPage(Page<OracleDelay> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年9月13日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(OracleDelay condition);
	
	/**
	 * 更新Oracle主从延迟.
	 * @param oracleDelay
	 * 				Oracle主从延迟
	 * @return
	 * 				更新记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int updateOracleDelay(OracleDelay oracleDelay);
	
	/**
	 * 根据ID删除Oracle主从延迟.
	 * @param id
	 * 				Oracle主从延迟ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int deleteOracleDelayById(Long id);
	
	/**
	 * 根据ID数组批量删除Oracle主从延迟.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int batchDeleteOracleDelayByIds(Long[] ids);
	
	/**
	 * 根据ID查找Oracle主从延迟.
	 * @param id
	 * 				Oracle主从延迟ID
	 * @return
	 * 				Oracle主从延迟
	 * 2016年9月13日   caowei-ds1
	 */
	OracleDelay selectOracleDelayById(Long id);
	
	/**
	 * 根据条件查询Oracle主从延迟列表,不分页.
	 * @param oracleDelay
	 * 				Oracle主从延迟
	 * @return
	 * 				Oracle主从延迟列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<OracleDelay> selectOracleDelayListByOracleDelay(OracleDelay oracleDelay);

	/**
	 * 查询各个报警级别的异常数
	 * @param alarmLevel
	 * @return
	 * 2016年9月13日 下午3:41:00   caowei-ds1
	 */
	List<OracleDelay> selectCountForAlarmLevel();

	/**
	 * 查询异常的Top5
	 * @return
	 * 2016年9月13日 下午3:42:10   caowei-ds1
	 */
	List<OracleDelay> selectTop5();
	
}

