package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.OracleDelayHistoryMapper;
import com.gome.upm.domain.OracleDelayHistory;
import com.gome.upm.service.OracleDelayHistoryService;
import com.gome.upm.service.OracleDelayHistoryService;
import com.gome.upm.service.util.DBContextHolder;


/**
 * 
 * oracle主从延迟异常历史service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年09月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class OracleDelayHistoryServiceImpl implements OracleDelayHistoryService {

	@Resource
	private OracleDelayHistoryMapper oracleDelayHistoryMapper;
	
	public int addOracleDelayHistory(OracleDelayHistory oracleDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.insertOracleDelayHistory(oracleDelayHistory);
	}

	public Page<OracleDelayHistory> findOracleDelayHistoryListByPage(Page<OracleDelayHistory> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<OracleDelayHistory> oracleDelayHistoryList = oracleDelayHistoryMapper.selectOracleDelayHistoryListByPage(page);
		int totalResult = oracleDelayHistoryMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<OracleDelayHistory>(page.getPageNo(), page.getPageSize(), totalResult, oracleDelayHistoryList, page.getConditions());
	}

	public int editOracleDelayHistory(OracleDelayHistory oracleDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.updateOracleDelayHistory(oracleDelayHistory);
	}

	public int deleteOracleDelayHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.deleteOracleDelayHistoryById(id);
	}

	public OracleDelayHistory findOracleDelayHistoryById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.selectOracleDelayHistoryById(id);
	}

	public List<OracleDelayHistory> findOracleDelayHistoryListByOracleDelayHistory(OracleDelayHistory oracleDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.selectOracleDelayHistoryListByOracleDelayHistory(oracleDelayHistory);
	}

	@Override
	public int batchDeleteOracleDelayHistoryByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.batchDeleteOracleDelayHistoryByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(OracleDelayHistory oracleDelayHistory) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayHistoryMapper.selectTotalResultByConditions(oracleDelayHistory);
	}
	
}
