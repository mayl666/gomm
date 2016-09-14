package com.gome.threshold.dao;

import java.sql.SQLException;

import com.gome.threshold.domain.AlarmRule;
import com.gome.threshold.domain.Application;

public interface AlarmRuleMaintainDao {
	AlarmRule queryGlobalAlarmRule(String userId) throws SQLException;

	int insertGlobalConfig(AlarmRule alarmRule);

	int updateAlarmRule(AlarmRule alarmRule) throws SQLException;
	
	AlarmRule queryAlarmRule(AlarmRule alarmRule) throws SQLException;
}
