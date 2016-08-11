package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Version;

/**
 * 
 * 版本dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月23日    caowei    新建
 * </pre>
 */
public interface VersionMapper {

	/**
	 *
	 * 插入版本.
	 *
	 * @param version
	 * 			版本信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月23日    caowei    新建
	 * </pre>
	 */
	int insertVersion(Version version);

	/**
	 *
	 * 分页查询版本列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			版本列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月26日    caowei    新建
	 * </pre>
	 */
	List<Version> selectVersionListByPage(Page<Version> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param version
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(Version version);

	/**
	 *
	 * 更新版本.
	 *
	 * @param version
	 * 			版本信息
	 * @return
	 * 			更新记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	int updateVersion(Version version);
	*/

	/**
	 *
	 * 根据ID删除版本.
	 *
	 * @param id
	 * 			版本ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	int deleteVersionById(Long id);
	*/
	
	/**
	 *
	 * 根据ID查找版本.
	 *
	 * @param id
	 * 			版本ID
	 * @return
	 * 			版本信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	Version selectVersionById(Long id);
	*/

	/**
	 *
	 * 根据版本信息查询版本列表.
	 *
	 * @param version
	 * 			版本信息
	 * @return
	 * 			版本列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	List<Version> selectVersionListByVersion(Version version);
	*/
}
