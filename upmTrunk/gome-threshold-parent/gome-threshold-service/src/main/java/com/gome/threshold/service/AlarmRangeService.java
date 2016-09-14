package com.gome.threshold.service;

import java.sql.SQLException;
import java.util.List;

import com.gome.threshold.common.Page;
import com.gome.threshold.domain.AlarmRange;

/**
 * 报警值域service接口
 * @author wangxiaye
 *
 */
public interface AlarmRangeService {

	/**
	 * 
	 * 分页查询报警值域列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    wangxiaye    新建
	 * </pre>
	 */
	Page<AlarmRange> findAlarmRangeListByPage(Page<AlarmRange> page);
	
	/**
	 *
	 * 根据条件查询报警值域数.
	 *
	 * @param alarmRange
	 * 				封装了查询条件
	 * @return
	 * 				记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    wangxiaye    新建
	 * </pre>
	 */
	int findTotalResultByConditions(AlarmRange alarmRange);
	
	/**
	 * 
	 * 条件查询报警值域列表，不分页.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    wangxiaye    新建
	 * </pre>
	 */
	List<AlarmRange> findAlarmRangeListByConditions(AlarmRange alarmRange);

	/**
	 * 查询所有的类型
	 * @return
	 * 2016年8月30日   wangxiaye
	 */
	List<AlarmRange> findAllBusinessTypes();

	/**
	 * 根据ID查询报警值域
	 * @param id
	 * @return
	 * 2016年8月30日 wangxiaye
	 */
	AlarmRange findAlarmRangeById(int id);
	
	/**
	 *
	 * 编辑报警值域.
	 *
	 * @param alarmRrange
	 * 			报警值域
	 * @return
	 * 			修改值域
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年08月30日    wangxiaye    新建
	 * </pre>
	 */
	int editAlarmRange(AlarmRange alarmRange);
	

	/**
	 * 新增报警值域.
	 * @param alarmRange
	 * @return
	 * 
	 * 2016年08月30日    wangxiaye    新建
	 */
	int insertAlarmRange(AlarmRange alarmRange) throws SQLException;

	int deleteAlarmRangeById(int id) throws SQLException;

	List<AlarmRange> findTypesByBusiness(String businessType);
	
	
	
}
