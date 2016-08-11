package com.gome.pricemonitor.service;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Version;

/**
 * 版本service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月23日    caowei    新建
 * </pre>
 */
public interface VersionService {

	/**
	 * 
	 * 新增版本.
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
	int addVersion(Version version);

	/**
	 * 
	 * 分页查询版本列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月26日    caowei    新建
	 * </pre>
	 */
	Page<Version> findVersionListByPage(Page<Version> page);

	/**
	 *
	 * 编辑版本.
	 *
	 * @param version
	 * 			版本信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	int editVersion(Version version);
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
	 * 根据ID查询版本.
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
	 
	Version findVersionById(Long id);
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
	 
	List<Version> findVersionListByVersion(Version version);
	*/

	//void exportExcel(List<Version> versionList, HttpServletResponse response);
	
}
