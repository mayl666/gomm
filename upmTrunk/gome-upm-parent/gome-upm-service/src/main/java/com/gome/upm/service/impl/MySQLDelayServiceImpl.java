package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.MySQLDelayMapper;
import com.gome.upm.domain.MySQLDelay;
import com.gome.upm.service.MySQLDelayService;
import com.gome.upm.service.util.DBContextHolder;


/**
 * 
 * mysql主从延迟service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年09月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class MySQLDelayServiceImpl implements MySQLDelayService {

	@Resource
	private MySQLDelayMapper mysqlDelayMapper;
	
	public int addMySQLDelay(MySQLDelay mysqlDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.insertMySQLDelay(mysqlDelay);
	}

	public Page<MySQLDelay> findMySQLDelayListByPage(Page<MySQLDelay> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<MySQLDelay> mysqlDelayList = mysqlDelayMapper.selectMySQLDelayListByPage(page);
		int totalResult = mysqlDelayMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<MySQLDelay>(page.getPageNo(), page.getPageSize(), totalResult, mysqlDelayList, page.getConditions());
	}

	public int editMySQLDelay(MySQLDelay mysqlDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.updateMySQLDelay(mysqlDelay);
	}

	public int deleteMySQLDelayById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.deleteMySQLDelayById(id);
	}

	public MySQLDelay findMySQLDelayById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.selectMySQLDelayById(id);
	}

	public List<MySQLDelay> findMySQLDelayListByMySQLDelay(MySQLDelay mysqlDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.selectMySQLDelayListByMySQLDelay(mysqlDelay);
	}

	@Override
	public int batchDeleteMySQLDelayByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.batchDeleteMySQLDelayByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(MySQLDelay mysqlDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.selectTotalResultByConditions(mysqlDelay);
	}

	@Override
	public List<MySQLDelay> findTotalResultForAlarmLevel() {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.selectCountForAlarmLevel();
	}

	@Override
	public List<MySQLDelay> findTop5() {
		DBContextHolder.setDataSource("dataSourceOne");
		return mysqlDelayMapper.selectTop5();
	}
	
}
