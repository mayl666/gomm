package com.gome.alarmplatform.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.alarmplatform.dao.AlarmMapper;
import com.gome.alarmplatform.domain.Alarm;
import com.gome.alarmplatform.service.AlarmService;

/**
 * 
 * 频道service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月27日    caowei    新建
 * </pre>
 */
@Service
public class AlarmServiceImpl implements AlarmService {

	@Resource
	private AlarmMapper alarmMapper;

	public void addAlarmRecord(Alarm alarm) {
		alarmMapper.insertAlarm(alarm);
	}

}
