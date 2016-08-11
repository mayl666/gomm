package com.gome.pricemonitor.dao;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Ad;

/**
 * 
 * 广告dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月31日    caowei    新建
 * </pre>
 */
public interface AdMapper {

	/**
	 *
	 * 插入广告.
	 *
	 * @param ad
	 * 			广告信息
	 * @return
	 * 			插入记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	int insertAd(Ad ad);

	/**
	 *
	 * 分页查询广告列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			广告列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	List<Ad> selectAdListByPage(Page<Ad> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param ad
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(Ad ad);

	/**
	 *
	 * 更新广告.
	 *
	 * @param ad
	 * 			广告信息
	 * @return
	 * 			更新记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	int updateAd(Ad ad);

	/**
	 *
	 * 根据ID删除广告.
	 *
	 * @param id
	 * 			广告ID
	 * @return
	 * 			删除记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	int deleteAdById(Long id);

	/**
	 *
	 * 根据ID查找广告.
	 *
	 * @param id
	 * 			广告ID
	 * @return
	 * 			广告信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	Ad selectAdById(Long id);

	/**
	 *
	 * 根据广告信息查询广告列表.
	 *
	 * @param ad
	 * 			广告信息
	 * @return
	 * 			广告列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	List<Ad> selectAdListByAd(Ad ad);
}
