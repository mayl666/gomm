package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.MySQLDelayRemoteMapper;
import com.gome.upm.domain.MySQLDelayRemote;
import com.gome.upm.service.MySQLDelayRemoteService;
import com.gome.upm.service.util.DBContextHolder;


/**
 * 
 * mysql主从延迟service接口实现类.（oracle库）
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年09月13日    caowei-ds1    新建
 * </pre>
 */
@Service
public class MySQLDelayRemoteServiceImpl implements MySQLDelayRemoteService {

	@Resource
	private MySQLDelayRemoteMapper mysqlDelayRemoteMapper;
	
	public Page<MySQLDelayRemote> findMySQLDelayRemoteListByPage(Page<MySQLDelayRemote> page) {
		DBContextHolder.setDataSource("dataSourceFour");
		List<MySQLDelayRemote> mysqlDelayRemoteList = mysqlDelayRemoteMapper.selectMySQLDelayRemoteListByPage(page);
		int totalResult = mysqlDelayRemoteMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<MySQLDelayRemote>(page.getPageNo(), page.getPageSize(), totalResult, mysqlDelayRemoteList, page.getConditions());
	}

	public List<MySQLDelayRemote> findMySQLDelayRemoteListByMySQLDelayRemote(MySQLDelayRemote mysqlDelayRemote) {
		DBContextHolder.setDataSource("dataSourceFour");
		return mysqlDelayRemoteMapper.selectMySQLDelayRemoteListByMySQLDelayRemote(mysqlDelayRemote);
	}

}
