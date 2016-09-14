package com.gome.threshold.dao;

import java.util.List;

import com.gome.threshold.common.Page;
import com.gome.threshold.domain.Application;
import com.gome.threshold.domain.ApplicationInfo;

public interface ApplicationsDao {
	/**
	 * 查询记录
	 * @param page
	 * @return
	 */
	List<ApplicationInfo> selectApplicationListByPage(Page<ApplicationInfo> page);
	
	/**
	 * 查询总记录数
	 * @param applicationInfo
	 * @return
	 */
	Integer selectTotalResultByConditions(ApplicationInfo applicationInfo);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDeleteAppByIds(Long[] ids);
	
	/**
	 * 是否存在
	 * @param code
	 * @return
	 */
	Integer selectCountByCode(String code);
	
	/**
	 * 插入
	 * @param application
	 * @return
	 */
	int insertApplication(Application application);
	
	/**
	 * 单条记录删除
	 * @param id
	 * @return
	 */
	int deleteById(int id);
}
