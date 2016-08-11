package com.gome.alarmplatform.service;

import com.gome.alarmplatform.domain.Alarm;

/**
 * 报警service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月14日    caowei    新建
 * </pre>
 */
public interface AlarmService {

	/**
	 * 
	 * 添加一条报警记录.
	 *
	* @param alarm
	 * 			报警记录实体
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月14日    caowei    新建
	 * </pre>
	 */
	void addAlarmRecord(Alarm alarm);

}
