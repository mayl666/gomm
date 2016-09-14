package com.gome.threshold.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.threshold.common.Page;
import com.gome.threshold.dao.AlarmRangeMapper;
import com.gome.threshold.domain.AlarmRange;
import com.gome.threshold.service.AlarmRangeService;
import com.gome.threshold.service.util.DBContextHolder;

@Service
public class AlarmRrangeServiceImpl implements AlarmRangeService {
	
	@Resource
	private AlarmRangeMapper alarmRangeMapper;

	@Override
	public Page<AlarmRange> findAlarmRangeListByPage(Page<AlarmRange> page) {
		DBContextHolder.setDataSource("dataSourceOne");
		List<AlarmRange> alarmRangeList = alarmRangeMapper.selectAlarmRangeListByPage(page);
		int totalResult = alarmRangeMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<AlarmRange>(page.getPageNo(), page.getPageSize(), totalResult, alarmRangeList, page.getConditions());
	}

	@Override
	public int findTotalResultByConditions(AlarmRange alarmRange) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.selectTotalResultByConditions(alarmRange);
	}

	@Override
	public List<AlarmRange> findAlarmRangeListByConditions(AlarmRange alarmRange) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.selectAlarmRangeListByAlarmRange(alarmRange);
	}

	@Override
	public List<AlarmRange> findAllBusinessTypes() {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.selectAllBusinessTypes();
	}

	@Override
	public AlarmRange findAlarmRangeById(int id) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.selectAlarmRangeById(id);
	}
	
	public int editAlarmRange(AlarmRange alarmRange) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.updateAlarmRange(alarmRange);
	}

	@Override
	public int insertAlarmRange(AlarmRange alarmRange) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.insertAlarmRange(alarmRange);
	}

	@Override
	public int deleteAlarmRangeById(int id) throws SQLException {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.deleteById(id);
	}

	@Override
	public List<AlarmRange> findTypesByBusiness(String businessType) {
		DBContextHolder.setDataSource("dataSourceOne");
		return alarmRangeMapper.selectTypesByBusiness(businessType);
	}

}
