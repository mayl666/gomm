package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.AlarmRange;

/**
 * 
 * @Description: 报警值域dao接口
 * @author wangxiaye
 * @date 2016年8月26日 
 * @version V1.0
 *
 */
public interface AlarmRangeMapper {

	/**
	 * 分页查询报警值域列表.
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			报警值域列表
	 * 2016年8月26日   wangxiaye
	 */
	List<AlarmRange> selectAlarmRangeListByPage(Page<AlarmRange> page);
	
	/**
	 * 根据搜索条件查询总记录数.
	 * @param alarmRange
	 * 				搜索条件
	 * @return
	 * 				总记录数
	 * 2016年8月26日   wangxiaye
	 */
	Integer selectTotalResultByConditions(AlarmRange alarmRange);
	
	/**
	 * 根据ID查找报警值域.
	 * @param id
	 * 				报警值域ID
	 * @return
	 * 				报警值域
	 * 2016年6月22日   wangxiaye
	 */
	AlarmRange selectAlarmRangeById(int id);
	
	/**
	 * 根据类型查询报警阈值
	 * @param list
	 * @return
	 */
	List<AlarmRange> selectAlarmRangeByType(List<String> list);
	
	/**
	 * 根据条件查询报警值域列表,不分页.
	 * @param urlRecord
	 * 				搜索条件
	 * @return
	 * 				报警值域列表
	 * 2016年8月26日 上午11:42:56   wangxiaye
	 */
	List<AlarmRange> selectAlarmRangeListByAlarmRange(AlarmRange alarmRange);

	/**
	 * 查询所有的类型
	 * @return
	 * 2016年8月26日 下午12:36:05   wangxiaye
	 */
	List<AlarmRange> selectAllBusinessTypes();
	
	/**
	 * 更新报警值域.
	 * @param alarmRange
	 * 				报警记录
	 * @return
	 * 				更新记录数
	 * 2016年8月26日   wangxiaye
	 */
	int updateAlarmRange(AlarmRange alarmRange);
	
	/**
	 * 增加报警值域.
	 * @param alarmRange
	 * @return
	 * 
	 * 2016年9月1日   wangxiaye
	 */
	int insertAlarmRange(AlarmRange alarmRange);
	
	/**
	 * 单条记录删除
	 * @param id
	 * @return
	 * 
	 * 2016年9月5日   wangxiaye
	 */
	int deleteById(int id);
	
	/**
	 * 根据类型查询
	 * @param businessType
	 * @return
	 */
	List<AlarmRange> selectByBusinessType(AlarmRange alarmRange);

	AlarmRange findAlarmRangeByBusinessType(AlarmRange alarmRange);
}

