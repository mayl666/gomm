package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.Page;
import com.gome.upm.dao.TbsMapper;
import com.gome.upm.domain.DBConnection;
import com.gome.upm.domain.Tbs;
import com.gome.upm.service.TbsService;
import com.gome.upm.service.util.DBContextHolder;
@Service
public class TbsServiceImpl implements TbsService {
	
	@Resource
	private TbsMapper tbsMapper;

	@Override
	public List<Tbs> findTbsListByCondition(Tbs condition) {
		DBContextHolder.setDataSource("dataSourceFour");
		return tbsMapper.selectTbsListByConditions(condition);
	}

	@Override
	public Page<Tbs> findTbsListByPage(Page<Tbs> page) {
		DBContextHolder.setDataSource("dataSourceFour");
		List<Tbs> tbsList = tbsMapper.selectTbsListByPage(page);
		int totalResult = tbsMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Tbs>(page.getPageNo(), page.getPageSize(), totalResult, tbsList, page.getConditions());
	}

}
