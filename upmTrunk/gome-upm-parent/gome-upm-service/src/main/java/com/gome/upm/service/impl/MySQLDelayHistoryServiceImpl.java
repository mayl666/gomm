package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.MySQLDelayHistoryMapper;
import com.gome.upm.domain.MySQLDelayHistory;
import com.gome.upm.service.MySQLDelayHistoryService;
import com.gome.upm.service.util.DBContextHolder;


/**
 * 
 * mysql主从延迟异常历史service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年09月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class MySQLDelayHistoryServiceImpl implements MySQLDelayHistoryService {

	@Resource
	private MySQLDelayHistoryMapper mysqlDelayHistoryMapper;
	
	public int addMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.insertMySQLDelayHistory(mysqlDelayHistory);
	}

	public Page<MySQLDelayHistory> findMySQLDelayHistoryListByPage(Page<MySQLDelayHistory> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<MySQLDelayHistory> mysqlDelayHistoryList = mysqlDelayHistoryMapper.selectMySQLDelayHistoryListByPage(page);
		int totalResult = mysqlDelayHistoryMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<MySQLDelayHistory>(page.getPageNo(), page.getPageSize(), totalResult, mysqlDelayHistoryList, page.getConditions());
	}

	public int editMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.updateMySQLDelayHistory(mysqlDelayHistory);
	}

	public int deleteMySQLDelayHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.deleteMySQLDelayHistoryById(id);
	}

	public MySQLDelayHistory findMySQLDelayHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.selectMySQLDelayHistoryById(id);
	}

	public List<MySQLDelayHistory> findMySQLDelayHistoryListByMySQLDelayHistory(MySQLDelayHistory mysqlDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.selectMySQLDelayHistoryListByMySQLDelayHistory(mysqlDelayHistory);
	}

	@Override
	public int batchDeleteMySQLDelayHistoryByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.batchDeleteMySQLDelayHistoryByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(MySQLDelayHistory mysqlDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayHistoryMapper.selectTotalResultByConditions(mysqlDelayHistory);
	}

}
