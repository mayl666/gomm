package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.OracleDelayMapper;
import com.gome.upm.domain.OracleDelay;
import com.gome.upm.service.OracleDelayService;
import com.gome.upm.service.OracleDelayService;
import com.gome.upm.service.util.DBContextHolder;


/**
 * 
 * oracle主从延迟service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年09月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class OracleDelayServiceImpl implements OracleDelayService {

	@Resource
	private OracleDelayMapper oracleDelayMapper;
	
	public int addOracleDelay(OracleDelay oracleDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.insertOracleDelay(oracleDelay);
	}

	public Page<OracleDelay> findOracleDelayListByPage(Page<OracleDelay> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<OracleDelay> oracleDelayList = oracleDelayMapper.selectOracleDelayListByPage(page);
		int totalResult = oracleDelayMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<OracleDelay>(page.getPageNo(), page.getPageSize(), totalResult, oracleDelayList, page.getConditions());
	}

	public int editOracleDelay(OracleDelay oracleDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.updateOracleDelay(oracleDelay);
	}

	public int deleteOracleDelayById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.deleteOracleDelayById(id);
	}

	public OracleDelay findOracleDelayById(Long id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.selectOracleDelayById(id);
	}

	public List<OracleDelay> findOracleDelayListByOracleDelay(OracleDelay oracleDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.selectOracleDelayListByOracleDelay(oracleDelay);
	}

	@Override
	public int batchDeleteOracleDelayByIds(Long[] ids) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.batchDeleteOracleDelayByIds(ids);
	}

	@Override
	public int findTotalResultByConditions(OracleDelay oracleDelay) {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.selectTotalResultByConditions(oracleDelay);
	}

	@Override
	public List<OracleDelay> findTotalResultForAlarmLevel() {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.selectCountForAlarmLevel();
	}

	@Override
	public List<OracleDelay> findTop5() {
		DBContextHolder.setDataSource("dataSourceOne");
		return oracleDelayMapper.selectTop5();
	}
	
}
