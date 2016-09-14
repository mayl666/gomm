package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.OracleDelayHistory;

/**
 * Oracle主从延迟异常历史Dao
 * @author caowei-ds1
 *
 */
public interface OracleDelayHistoryMapper {

	/**
	 * 插入一个Oracle主从延迟异常历史
	 * @param oracleDelayHistory
	 * 			Oracle主从延迟异常历史
	 * @return
	 * 2016年9月13日   caowei-ds1
	 */
	int insertOracleDelayHistory(OracleDelayHistory oracleDelayHistory);
	
	/**
	 * 分页查询Oracle主从延迟异常历史列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			Oracle主从延迟异常历史列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<OracleDelayHistory> selectOracleDelayHistoryListByPage(Page<OracleDelayHistory> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param condition
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年9月13日   caowei-ds1
	 */
	Integer selectTotalResultByConditions(OracleDelayHistory condition);
	
	/**
	 * 更新Oracle主从延迟异常历史.
	 * @param oracleDelayHistory
	 * 				Oracle主从延迟异常历史
	 * @return
	 * 				更新记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int updateOracleDelayHistory(OracleDelayHistory oracleDelayHistory);
	
	/**
	 * 根据ID删除Oracle主从延迟异常历史.
	 * @param id
	 * 				Oracle主从延迟异常历史ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int deleteOracleDelayHistoryById(Long id);
	
	/**
	 * 根据ID数组批量删除Oracle主从延迟异常历史.
	 * @param ids
	 * 				数组ID
	 * @return
	 * 				删除记录数
	 * 2016年9月13日   caowei-ds1
	 */
	int batchDeleteOracleDelayHistoryByIds(Long[] ids);
	
	/**
	 * 根据ID查找Oracle主从延迟异常历史.
	 * @param id
	 * 				Oracle主从延迟异常历史ID
	 * @return
	 * 				Oracle主从延迟异常历史
	 * 2016年9月13日   caowei-ds1
	 */
	OracleDelayHistory selectOracleDelayHistoryById(Long id);
	
	/**
	 * 根据条件查询Oracle主从延迟异常历史列表,不分页.
	 * @param oracleDelayHistory
	 * 				Oracle主从延迟异常历史
	 * @return
	 * 				Oracle主从延迟异常历史列表
	 * 2016年9月13日   caowei-ds1
	 */
	List<OracleDelayHistory> selectOracleDelayHistoryListByOracleDelayHistory(OracleDelayHistory oracleDelayHistory);
	
}

