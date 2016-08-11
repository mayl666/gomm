package com.gome.pricemonitor.service;

import java.util.List;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.domain.Ad;

/**
 * 广告service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年10月31日    caowei    新建
 * </pre>
 */
public interface AdService {

	/**
	 * 
	 * 新增广告.
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
	int addAd(Ad ad);

	/**
	 * 
	 * 分页查询广告列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	Page<Ad> findAdListByPage(Page<Ad> page);

	/**
	 *
	 * 编辑广告.
	 *
	 * @param ad
	 * 			广告信息
	 * @return
	 * 			修改记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年10月31日    caowei    新建
	 * </pre>
	 */
	int editAd(Ad ad);

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
	 * 根据ID查询广告.
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
	Ad findAdById(Long id);

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
	List<Ad> findAdListByAd(Ad ad);
	
	/**
	 *
	 * 调换广告位.
	 *
	 * @param ad1
	 * 			广告1
	 * @param ad2
	 * 			广告2
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月5日    caowei    新建
	 * </pre>
	 */
	boolean exchangeAd(Ad ad1, Ad ad2);
}
