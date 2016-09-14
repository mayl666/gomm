package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.AlarmRecord;
import com.gome.upm.domain.UrlMonitor;

/**
 * 报警记录service接口
 * @author caowei-ds1
 *
 */
public interface AlarmRecordService {

	/**
	 * 
	 * 分页查询报警记录列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	Page<AlarmRecord> findAlarmRecordListByPage(Page<AlarmRecord> page);
	
	/**
	 *
	 * 根据条件查询报警记录数.
	 *
	 * @param alarmRecord
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月13日    caowei-ds1    新建
	 * </pre>
	 */
	int findTotalResultByConditions(AlarmRecord alarmRecord);
	
	/**
	 * 
	 * 条件查询报警记录列表，不分页.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年06月21日    caowei-ds1    新建
	 * </pre>
	 */
	List<AlarmRecord> findAlarmRecordListByConditions(AlarmRecord alarmRecord);

	/**
	 * 查询所有的类型
	 * @return
	 * 2016年7月26日 下午12:34:43   caowei-ds1
	 */
	List<AlarmRecord> findAllTypes();
	
	/**
	 * 查询所有业务的类型
	 * @return
	 * 2016年9月9日 下午   liuyuqiang
	 */
	List<AlarmRecord> findAllBusinessTypes();

	/**
	 * 根据ID查询报警记录
	 * @param id2
	 * @return
	 * 2016年7月26日 下午4:49:43   caowei-ds1
	 */
	AlarmRecord findAlarmRecordById(long id);
	
	/**
	 *
	 * 编辑报警记录.
	 *
	 * @param alarmRecord
	 * 			报警记录
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月12日    caowei-ds1    新建
	 * </pre>
	 */
	int editAlarmRecord(AlarmRecord alarmRecord);
	
	/**
	 * 根据条件查询报警记录数
	 * 
	 * @param alarmRecord
	 * @return
	 */
	List<AlarmRecord> selectAlarmRecordListByConditions(AlarmRecord alarmRecord);
	
	/**
	 * 插入一条新的报警记录
	 * @param alarmRecord
	 * @return
	 */
	int insertAlarmRecord(AlarmRecord alarmRecord);
	
}
