package com.gome.alarmplatform.dao;

import com.gome.alarmplatform.domain.Alarm;

/**
 * 
 * 报警dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月16日    caowei    新建
 * </pre>
 */
public interface AlarmMapper {

	/**
	 *
	 * 插入报警记录.
	 *
	 * @param alarm
	 * 			报警信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月16日    caowei    新建
	 * </pre>
	 */
	int insertAlarm(Alarm alarm);

}
