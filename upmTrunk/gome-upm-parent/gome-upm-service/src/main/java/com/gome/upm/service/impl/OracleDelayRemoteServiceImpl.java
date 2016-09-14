package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.OracleDelayRemoteMapper;
import com.gome.upm.domain.OracleDelayRemote;
import com.gome.upm.service.OracleDelayRemoteService;
import com.gome.upm.service.OracleDelayRemoteService;
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
public class OracleDelayRemoteServiceImpl implements OracleDelayRemoteService {

	@Resource
	private OracleDelayRemoteMapper oracleDelayRemoteMapper;
	
	public Page<OracleDelayRemote> findOracleDelayRemoteListByPage(Page<OracleDelayRemote> page) {
		DBContextHolder.setDataSource("dataSourceFour");
		List<OracleDelayRemote> oracleDelayRemoteList = oracleDelayRemoteMapper.selectOracleDelayRemoteListByPage(page);
		int totalResult = oracleDelayRemoteMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<OracleDelayRemote>(page.getPageNo(), page.getPageSize(), totalResult, oracleDelayRemoteList, page.getConditions());
	}

	public List<OracleDelayRemote> findOracleDelayRemoteListByOracleDelayRemote(OracleDelayRemote oracleDelayRemote) {
		DBContextHolder.setDataSource("dataSourceFour");
		return oracleDelayRemoteMapper.selectOracleDelayRemoteListByOracleDelayRemote(oracleDelayRemote);
	}

}
